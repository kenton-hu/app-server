package cn.wildfirechat.app.admin.dto.resp;

import java.io.Serializable;

public class UserRespDTO implements Serializable {
    private String name;

    private String userId;

    public UserRespDTO() {
    }

    public UserRespDTO(String name, String userId) {
        this.name = name;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
