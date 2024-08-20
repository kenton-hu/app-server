package cn.wildfirechat.app.admin.dto.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class SensetiveReqDTO {
    @NotBlank(message = "sessionId不能为空")
    private String sessionId;

    @NotEmpty(message = "敏感词不能为空")
    private List<String> words;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public @NotEmpty(message = "敏感词不能为空") List<String> getWords() {
        return words;
    }

    public void setWords(@NotEmpty(message = "敏感词不能为空") List<String> words) {
        this.words = words;
    }
}
