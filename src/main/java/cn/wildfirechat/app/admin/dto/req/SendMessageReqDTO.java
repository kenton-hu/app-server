package cn.wildfirechat.app.admin.dto.req;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 群发消息请求DTO
 */
public class SendMessageReqDTO implements Serializable {
    @NotBlank(message = "content不能为空")
    private String content;

    private List<String> userIds;

    @NotBlank(message = "sessionId不能为空")
    private String sessionId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
