package cn.wensheng.studyredis.exception;

public class WithCodeException extends Exception
{
    private static final long serialVersionUID = -8378355646939838873L;

    private int exceptionCode;

    public WithCodeException(int code)
    {
        this(code, (String)null, (Throwable)null);
    }

    public WithCodeException(Throwable throwable)
    {
        super(throwable);
    }

    public WithCodeException(int code, String message)
    {
        this(code, message, (Throwable)null);
    }

    public WithCodeException(int code, Throwable t)
    {
        this(code, (String)null, t);
    }

    public WithCodeException(int code, String message, Throwable t)
    {
        super(message, t);
        this.exceptionCode = code;
    }

    public int getCode()
    {
        return this.exceptionCode;
    }

    public String toString()
    {
        return "Exception code: " + this.exceptionCode + ", " + super.toString();
    }
}

