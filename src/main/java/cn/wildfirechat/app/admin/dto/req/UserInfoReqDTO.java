package cn.wildfirechat.app.admin.dto.req;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户信息请求DTO
 */
public class UserInfoReqDTO implements Serializable {
    @NotBlank(message = "sessionId不能为空")
    private String sessionId;

    public UserInfoReqDTO() {
    }

    public UserInfoReqDTO(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
