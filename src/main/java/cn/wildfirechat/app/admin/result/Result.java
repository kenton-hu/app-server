package cn.wildfirechat.app.admin.result;

import java.util.List;

/**
 * admin 返回结果
 */
public class Result<T> {
    /**
     * "{
     *  ""contentType"":null,
     *  ""data"":""tU3vdBK5BL5j4N7jI5N5uZgq_HQDo170w5C9Amnn:lxCMXB6jUEbJaB3sWUdQ8pxQk1I=:eyJzY29wZSI6InN0b3JhZ2UiLCJkZWFkbGluZSI6MTcyMzk5MjQ0M30="",
     *  ""errors"":[],
     *  ""exception"":null,
     *  ""extra"":null,
     *  ""nextReqId"":""175c06c1f68949979d1a3e1edf42586a"",
     *  ""reqId"":""27a0960c94854ad3875d1650ace4c1e9"",
     *  ""reqTime"":483,
     *  ""retCode"":""0"",
     *  ""retMsg"":""SUCCESS"",
     *  ""sessionId"":""4b1fad187f704ccd903f64bc395dffce""
     * }"
     */
    private String contentType;
    private T data;
    private List<String> errors;
    private String exception;
    private String extra;
    private String nextReqId;
    private String reqId;
    private long reqTime;
    private String retCode;
    private String retMsg;
    private String sessionId;

    /**
     * 成功
     * @param data 数据
     * @param sessionId 会话Id
     * @return
     */
    public Result<T> success(T data, String sessionId) {
        Result<T> result = new Result<>();
        result.setRetCode("0");
        result.setRetMsg("SUCCESS");
        result.setData(data);
        result.setSessionId(sessionId);
        return result;
    }

    /**
     * 错误
     * @param code 错误码
     * @param msg 错误内容
     * @param sessionId 会话id
     * @return
     */
    public Result<T> error(String code, String msg, String sessionId) {
        Result<T> result = new Result<>();
        result.setRetCode(code);
        result.setRetMsg(msg);
        result.setSessionId(sessionId);
        return result;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getNextReqId() {
        return nextReqId;
    }

    public void setNextReqId(String nextReqId) {
        this.nextReqId = nextReqId;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public long getReqTime() {
        return reqTime;
    }

    public void setReqTime(long reqTime) {
        this.reqTime = reqTime;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
