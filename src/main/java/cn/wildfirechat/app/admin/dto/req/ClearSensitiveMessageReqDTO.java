package cn.wildfirechat.app.admin.dto.req;

import javax.validation.constraints.NotBlank;

/**
 * 基础请求DTO
 */
public class ClearSensitiveMessageReqDTO {
    @NotBlank(message = "sessionId不能为空")
    private String sessionId;

    private boolean clearFlag;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isClearFlag() {
        return clearFlag;
    }

    public void setClearFlag(boolean clearFlag) {
        this.clearFlag = clearFlag;
    }
}
