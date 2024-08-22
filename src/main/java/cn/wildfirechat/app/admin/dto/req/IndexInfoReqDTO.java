package cn.wildfirechat.app.admin.dto.req;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 首页请求DTO
 */
public class IndexInfoReqDTO implements Serializable {
    @NotBlank(message = "日期不能为空")
    private String date;
    @NotBlank(message = "sessionId不能为空")
    private String sessionId;

    public IndexInfoReqDTO() {
    }

    public IndexInfoReqDTO(String date, String sessionId) {
        this.date = date;
        this.sessionId = sessionId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
