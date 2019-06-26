package cn.wensheng.studyredis.cache;

import cn.wensheng.studyredis.utils.NumTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;
import java.util.Map;

@Component
public class CacheDaoFactory
{
    private static final Logger logger = LoggerFactory.getLogger(CacheDaoFactory.class);

    /**
     * 存放cache_config.xml中缓存名-cachedao map集合
     */
    private static Map<String, CacheDao> cachedDAOMap = new HashMap<>();

    @Autowired
    RedisCache redisCache;

    public void init(String configFile)
    {
        cachedDAOMap.clear();

        try
        {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();

            Document doc = builder.parse(configFile);
            NodeList daoNodes = doc.getElementsByTagName("Item");
            for (int daoIndex = 0; daoIndex < daoNodes.getLength(); daoIndex++)
            {
                Element daoElem = (Element)daoNodes.item(daoIndex);
                String className = daoElem.getAttribute("daoClass");
                Class<?> daoClass = Class.forName(className);
                CacheDao daoInstance = (CacheDao)daoClass.newInstance();
                NodeList childList = daoElem.getChildNodes();
                for (int i = 0; i < childList.getLength(); i++)
                {
                    Node childNode = childList.item(i);
                    if (childNode.getNodeType() == Node.ELEMENT_NODE)
                    {
                        Element element = (Element)childNode;
                        String tagName = element.getTagName();
                        String textContent = element.getTextContent().trim();
                        if (textContent == null || "".equals(textContent))
                        {
                            continue;
                        }
                        if ("CacheName".equals(tagName))
                        {
                            cachedDAOMap.put(textContent, daoInstance);
                        }
                        else if ("KeyPrefix".equals(tagName))
                        {
                            daoInstance.setKeyPrefix(textContent);
                        }
                        else if ("SerializeType".equals(tagName))
                        {
                            daoInstance.setSerializeType(SerializeTypeEnum.valueOf(textContent));
                        }
                        // 缓存失效时间，单位：秒
                        else if ("TimeExpire".equals(tagName))
                        {
                            daoInstance.setTimeExpire(NumTools.toLong(textContent, 60));
                        }
                        else if ("DBDtaLoader".equals(tagName))
                        {
                            Class<?> loaderClz = Class.forName(textContent);
                            DBDataLoader loader = (DBDataLoader)loaderClz.newInstance();
                            daoInstance.setDataLoader(loader);
                        }
                    }

                    daoInstance.setRedisCache(redisCache);
                }
            }

            logger.error("=========>>>CacheDaoFactory init success!<<<=========");
        }
        catch (Exception e)
        {
            logger.error("=========>>>CacheDaoFactory init failed!<<<=========", e);
        }
    }

    public static CacheDao getCacheDao(String cacheName)
    {
        return cachedDAOMap.get(cacheName);
    }
}
