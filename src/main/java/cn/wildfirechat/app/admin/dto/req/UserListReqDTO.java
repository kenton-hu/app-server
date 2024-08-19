package cn.wildfirechat.app.admin.dto.req;

import javax.validation.constraints.NotBlank;

/**
 * 用户信息请求DTO
 */
public class UserListReqDTO {
    @NotBlank(message = "sessionId不能为空")
    private String sessionId;

    @NotBlank(message = "searchKey不能为空")
    private String searchKey;

    private int pageNo = 1;

    private int pageSize = 10;

    public @NotBlank(message = "sessionId不能为空") String getSessionId() {
        return sessionId;
    }

    public void setSessionId(@NotBlank(message = "sessionId不能为空") String sessionId) {
        this.sessionId = sessionId;
    }

    public @NotBlank(message = "searchKey不能为空") String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(@NotBlank(message = "searchKey不能为空") String searchKey) {
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
