package cn.wildfirechat.app.admin.dto.req;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 撤回消息请求DTO
 */
public class RecallMessageReqDTO implements Serializable {
    @NotBlank(message = "messageId不能为空")
    private long messageId;

    @NotBlank(message = "sessionId不能为空")
    private String sessionId;

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
