package cn.wildfirechat.app.admin.dto.req;

import javax.validation.constraints.NotBlank;

/**
 * 消息列表请求DTO
 */
public class MessageListReqDTO {
    @NotBlank(message = "sessionId不能为空")
    private String sessionId;

    private String searchKey;

    private String messageType;
    private String contentType;
    @NotBlank(message = "monthNo不能为空")
    private String monthNo;
    private String type;

    private int pageNo = 1;

    private int pageSize = 10;


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public @NotBlank(message = "monthNo不能为空") String getMonthNo() {
        return monthNo;
    }

    public void setMonthNo(@NotBlank(message = "monthNo不能为空") String monthNo) {
        this.monthNo = monthNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
