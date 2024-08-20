package cn.wildfirechat.app.admin.dto.req;

import javax.validation.constraints.NotBlank;

/**
 * 基础请求DTO
 */
public class BasicReqDTO {
    @NotBlank(message = "sessionId不能为空")
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
