package cn.wildfirechat.app.admin.dto.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 请求DTO设置/取消群管理员
 */
public class ChangeGroupUserTypeReqDTO implements Serializable {

    @NotBlank(message = "groupId不能为空")
    private String groupId;

    @NotBlank(message = "memberId不能为空")
    private String memberId;
    @NotEmpty(message = "type不能为空")
    private int type;

    @NotBlank(message = "sessionId不能为空")
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
