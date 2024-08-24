package cn.wildfirechat.app.admin.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class MessageRespDTO implements Serializable {
    private int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private long mid;

    private String from;
    private String fromName;

    private int type;
    private String typeString;
    private String target;
    private String targetName;
    private int line;
    private String data;
    private String searchableKey;
    private long dt;
    private int contentType;
    private String contentTypeString;
    private String to;

    public MessageRespDTO() {
    }

    public MessageRespDTO(int id, long mid, String from, int type, String target, int line, String data, String searchableKey, long dt, int contentType, String to, String fromName, String targetName) {
        this.id = id;
        this.mid = mid;
        this.from = from;
        this.fromName = fromName;
        this.type = type;
        this.target = target;
        this.targetName = targetName;
        this.line = line;
        this.data = data;
        this.searchableKey = searchableKey;
        this.dt = dt;
        this.contentType = contentType;
        this.to = to;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSearchableKey() {
        return searchableKey;
    }

    public void setSearchableKey(String searchableKey) {
        this.searchableKey = searchableKey;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }

    public String getContentTypeString() {
        return contentTypeString;
    }

    public void setContentTypeString(String contentTypeString) {
        this.contentTypeString = contentTypeString;
    }
}
