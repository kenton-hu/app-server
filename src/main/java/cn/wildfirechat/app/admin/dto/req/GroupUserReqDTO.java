package cn.wildfirechat.app.admin.dto.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 删除群成员请求DTO
 */
public class GroupUserReqDTO implements Serializable {

    @NotBlank(message = "targetId不能为空")
    private String targetId;

    @NotEmpty(message = "userIds不能为空")
    private List<String> userIds;

    @NotBlank(message = "sessionId不能为空")
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public @NotBlank(message = "memberId不能为空") List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(@NotBlank(message = "memberId不能为空") List<String> userIds) {
        this.userIds = userIds;
    }
}
