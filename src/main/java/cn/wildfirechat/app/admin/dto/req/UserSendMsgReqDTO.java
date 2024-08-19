package cn.wildfirechat.app.admin.dto.req;

import javax.validation.constraints.NotBlank;

/**
 * 发送用户消息请求DTO
 */
public class UserSendMsgReqDTO {
    @NotBlank(message = "sessionId不能为空")
    private String sessionId;

    @NotBlank(message = "发送对象不能为空")
    private String to;

    @NotBlank(message = "内容不能为空")
    private String content;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
