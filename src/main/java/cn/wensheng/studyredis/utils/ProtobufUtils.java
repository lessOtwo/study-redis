package cn.wensheng.studyredis.utils;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import com.googlecode.protobuf.format.JsonFormat;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.runtime.RuntimeSchema;

public class ProtobufUtils
{

    private ProtobufUtils()
    {
    }

    public static <T> byte[] serialize(T t, Class<T> clazz)
    {
        if (null == t)
        {
            return null;
        }
        return ProtobufIOUtil.toByteArray(t, RuntimeSchema.createFrom(clazz),
            LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
    }

    public static <T> T deSerialize(byte[] data, Class<T> clazz)
    {
        if (null == data || data.length == 0)
        {
            return null;
        }
        RuntimeSchema<T> runtimeSchema = RuntimeSchema.createFrom(clazz);
        T t = runtimeSchema.newMessage();
        ProtobufIOUtil.mergeFrom(data, t, runtimeSchema);
        return t;
    }

    public static String printToString(Message message)
    {
        String jsonStr = "null";
        if (message == null)
        {
            return jsonStr;
        }
        try
        {
            jsonStr = JsonFormat.printToString(message);
        }
        catch (Throwable e)
        {

        }
        return jsonStr;
    }

    public static String printToString(GeneratedMessageV3.Builder build)
    {
        return printToString(build.build());
    }
}