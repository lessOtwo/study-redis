package cn.wensheng.studyredis.bean;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 符合条件的更新的图书
 */
@Alias("UpdateBook")
public class UpdateBook implements Serializable
{
    private static final long serialVersionUID = -421982551984028506L;

    private String bookId;

    private String bookName;

    // 更新章节id
    private String chapterId;

    // 更新的章节名
    private String chapterName;

    // 更新时间
    private Date lastSerialTime;

    private transient String authorId;

    private transient int timeLimit;

    public String getBookId()
    {
        return bookId;
    }

    public void setBookId(String bookId)
    {
        this.bookId = bookId;
    }

    public String getBookName()
    {
        return bookName;
    }

    public void setBookName(String bookName)
    {
        this.bookName = bookName;
    }

    public String getChapterId()
    {
        return chapterId;
    }

    public void setChapterId(String chapterId)
    {
        this.chapterId = chapterId;
    }

    public String getChapterName()
    {
        return chapterName;
    }

    public void setChapterName(String chapterName)
    {
        this.chapterName = chapterName;
    }

    public Date getLastSerialTime()
    {
        return lastSerialTime;
    }

    public void setLastSerialTime(Date lastSerialTime)
    {
        this.lastSerialTime = lastSerialTime;
    }

    public String getAuthorId()
    {
        return authorId;
    }

    public void setAuthorId(String authorId)
    {
        this.authorId = authorId;
    }

    public int getTimeLimit()
    {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit)
    {
        this.timeLimit = timeLimit;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("UpdateBook{");
        sb.append("bookId='").append(bookId).append('\'');
        sb.append(", bookName='").append(bookName).append('\'');
        sb.append(", chapterId='").append(chapterId).append('\'');
        sb.append(", chapterName='").append(chapterName).append('\'');
        sb.append(", lastSerialTime=").append(lastSerialTime);
        sb.append(", authorId='").append(authorId).append('\'');
        sb.append(", timeLimit=").append(timeLimit);
        sb.append('}');
        return sb.toString();
    }
}
