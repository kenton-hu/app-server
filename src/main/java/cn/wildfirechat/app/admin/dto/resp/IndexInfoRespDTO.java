package cn.wildfirechat.app.admin.dto.resp;

import java.io.Serializable;

public class IndexInfoRespDTO implements Serializable {
    private String count;

    private String date;

    public IndexInfoRespDTO() {
    }

    public IndexInfoRespDTO(String count, String date) {
        this.count = count;
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
