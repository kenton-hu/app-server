package cn.wildfirechat.app.admin.dto.req;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 更新密码请求DTO
 */
public class UpdatePwdReqDTO implements Serializable {
    /**
     * {"oldPwd":"admin","newPwd":"admin","newPwdRe":"admin","sessionId":"4b1fad187f704ccd903f64bc395dffce"}
     */
    @NotBlank(message = "原密码不能为空")
    private String oldPwd;
    @NotBlank(message = "新密码不能为空")
    private String newPwd;
    @NotBlank(message = "sessionId不能为空")
    private String sessionId;

    public UpdatePwdReqDTO() {
    }

    public UpdatePwdReqDTO(String oldPwd, String newPwd, String sessionId) {
        this.oldPwd = oldPwd;
        this.newPwd = newPwd;
        this.sessionId = sessionId;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
