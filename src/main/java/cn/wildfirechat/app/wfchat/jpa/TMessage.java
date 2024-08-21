package cn.wildfirechat.app.wfchat.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_message")
public class TMessage {
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
    private int line;
    @Column(name = "_data", columnDefinition = "BLOB(65535)")
    private String data;
    @Column(name = "__searchable_key")
    private String searchableKey;
    @Column(name = "_dt")
    private Date dt;
    @Column(name = "_content_type")
    private int contentType;
    @Column(name = "_to")
    private String to;

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

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
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
}
