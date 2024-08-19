package cn.wildfirechat.app.admin.dto.req;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 群最大人数请求DTO
 */
public class SettingsInfoReqDTO implements Serializable {
    @NotBlank(message = "sessionId不能为空")
    private String sessionId;

    private String value;

    public SettingsInfoReqDTO() {
    }

    public SettingsInfoReqDTO(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
