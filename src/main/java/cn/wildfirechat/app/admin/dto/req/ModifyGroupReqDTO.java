package cn.wildfirechat.app.admin.dto.req;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 修改群信息请求DTO
 */
public class ModifyGroupReqDTO implements Serializable {

    @NotBlank(message = "targetId不能为空")
    private String targetId;

    private int type;

    @NotBlank(message = "owner不能为空")
    private String owner;

    private int value;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
