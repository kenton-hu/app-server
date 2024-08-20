package cn.wildfirechat.app.admin.dto.req;

import javax.validation.constraints.NotBlank;

/**
 * 群组成员列表请求DTO
 */
public class GroupUserListReqDTO {
    @NotBlank(message = "sessionId不能为空")
    private String sessionId;

    @NotBlank(message = "groupId不能为空")
    private String groupId;

    private String gid;

    private String searchKey;

    private int pageNo = 1;

    private int pageSize = 10;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
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
