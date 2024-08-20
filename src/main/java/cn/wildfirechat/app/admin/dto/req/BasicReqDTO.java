package cn.wildfirechat.app.admin.dto.req;

import javax.validation.constraints.NotBlank;

/**
 * 基础请求DTO
 */
public class BasicReqDTO {
    @NotBlank(message = "sessionId不能为空")
    private String sessionId;

    private String searchKey;

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
