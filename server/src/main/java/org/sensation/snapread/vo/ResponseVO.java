package org.sensation.snapread.vo;

/**
 *
 * Created by Sissel on 2016/10/11.
 */
public class ResponseVO
{
    public String condition;
    public String error_code;
    public String msg;
    public Object data;

    public ResponseVO(String condition, String error_code, String msg, Object data)
    {
        this.condition = condition;
        this.error_code = error_code;
        this.msg = msg;
        this.data = data;
    }
}
