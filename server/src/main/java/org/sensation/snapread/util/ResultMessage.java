package org.sensation.snapread.util;

/**
 *
 * Created by Sissel on 2016/10/9.
 */
public class ResultMessage
{
    private boolean success;
    private String message;

    public ResultMessage(boolean success, String message)
    {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public String getMessage()
    {
        return message;
    }
}
