package cn.wildfirechat.app.admin.dto.req;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 更新手机号请求DTO
 */
public class UpdatePhoneReqDTO implements Serializable {
    @NotBlank(message = "手机号不能为空")
    private String phoneNumber;
    @NotBlank(message = "sessionId不能为空")
    private String sessionId;

    public UpdatePhoneReqDTO() {
    }

    public UpdatePhoneReqDTO(String phoneNumber, String sessionId) {
        this.phoneNumber = phoneNumber;
        this.sessionId = sessionId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
