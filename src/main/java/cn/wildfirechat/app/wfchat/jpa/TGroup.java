package cn.wildfirechat.app.wfchat.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_group")
public class TGroup {
    @Id
    @Column(length = 11)
    private int id;

    @Column(name = "_gid")
    private String gid;

    @Column(name = "_name")
    private String name;

    @Column(name = "_portrait")
    private String portrait;

    @Column(name = "_owner")
    private String owner;

    @Column(name = "_type")
    private int type;

    @Column(name = "_extra")
    private String extra;

    @Column(name = "_dt")
    private long dt;

    @Column(name = "_member_count")
    private int memberCount;

    @Column(name = "_member_dt")
    private long memberDt;

    @Column(name = "_createTime")
    private Date createTime;

    @Column(name = "_mute")
    private int mute;

    @Column(name = "_join_type")
    private int joinType;

    @Column(name = "_private_chat")
    private int privateChat;

    @Column(name = "_searchable")
    private int searchable;

    @Column(name = "_history_message")
    private int historyMessage;

    @Column(name = "_max_member_count")
    private int maxMemberCount;

    @Column(name = "_super_group")
    private int superGroup;

    @Column(name = "_deleted")
    private int deleted;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public long getMemberDt() {
        return memberDt;
    }

    public void setMemberDt(long memberDt) {
        this.memberDt = memberDt;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getMute() {
        return mute;
    }

    public void setMute(int mute) {
        this.mute = mute;
    }

    public int getJoinType() {
        return joinType;
    }

    public void setJoinType(int joinType) {
        this.joinType = joinType;
    }

    public int getPrivateChat() {
        return privateChat;
    }

    public void setPrivateChat(int privateChat) {
        this.privateChat = privateChat;
    }

    public int getSearchable() {
        return searchable;
    }

    public void setSearchable(int searchable) {
        this.searchable = searchable;
    }

    public int getHistoryMessage() {
        return historyMessage;
    }

    public void setHistoryMessage(int historyMessage) {
        this.historyMessage = historyMessage;
    }

    public int getMaxMemberCount() {
        return maxMemberCount;
    }

    public void setMaxMemberCount(int maxMemberCount) {
        this.maxMemberCount = maxMemberCount;
    }

    public int getSuperGroup() {
        return superGroup;
    }

    public void setSuperGroup(int superGroup) {
        this.superGroup = superGroup;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}
