package cn.wildfirechat.app.admin.dto.req;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 设置群主请求DTO
 */
public class TransferGroupReqDTO implements Serializable {
    @NotBlank(message = "targetId不能为空")
    private String targetId;
    @NotBlank(message = "newOwnerId不能为空")
    private String newOwnerId;

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

    public String getNewOwnerId() {
        return newOwnerId;
    }

    public void setNewOwnerId(String newOwnerId) {
        this.newOwnerId = newOwnerId;
    }
}
