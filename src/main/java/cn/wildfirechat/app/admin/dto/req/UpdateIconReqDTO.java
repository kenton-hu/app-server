package cn.wildfirechat.app.admin.dto.req;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 更新头像请求DTO
 */
public class UpdateIconReqDTO implements Serializable {
    @NotBlank(message = "头像url不能为空")
    private String icon;
    @NotBlank(message = "sessionId不能为空")
    private String sessionId;

    public UpdateIconReqDTO() {
    }

    public UpdateIconReqDTO(String icon, String sessionId) {
        this.icon = icon;
        this.sessionId = sessionId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(@NotBlank(message = "头像url不能为空")String icon) {
        this.icon = icon;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
