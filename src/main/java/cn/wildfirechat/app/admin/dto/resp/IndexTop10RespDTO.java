package cn.wildfirechat.app.admin.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class IndexTop10RespDTO implements Serializable {
    private String data;

    @JsonProperty("display_name")
    private String displayName;

    private String from;
    private String target;
    private String url;
    private int total;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
