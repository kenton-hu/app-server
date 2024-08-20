package cn.wildfirechat.app.admin.dto.req;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 销毁用户请求DTO
 */
public class DestroyUserReqDTO implements Serializable {
    @NotBlank(message = "用户id不能为空")
    private String userId;

    @NotBlank(message = "sessionId不能为空")
    private String sessionId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
