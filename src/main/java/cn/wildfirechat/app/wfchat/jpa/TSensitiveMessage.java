package cn.wildfirechat.app.wfchat.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_sensitive_messages")
public class TSensitiveMessage {
    @Id
    @Column(length = 11)
    private int id;

    @Column(name = "_mid")
    private long mid;

    @Column(name = "_from")
    private String from;

    @Column(name = "_type")
    private int type;

    @Column(name = "_target")
    private String target;

    @Column(name = "_line")
    private String line;

    @Column(name = "_data")
    private String data;

    @Column(name = "_searchable_key")
    private String searchableKey;

    @Column(name = "_dt")
    private long dt;

    @Column(name = "_content_type")
    private String contentType;

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

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
