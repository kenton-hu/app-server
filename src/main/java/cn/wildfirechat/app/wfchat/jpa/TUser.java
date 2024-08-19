package cn.wildfirechat.app.wfchat.jpa;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_user")
public class TUser {
    @Id
    @Column(length = 11)
    private int id;

    @Column(name = "_uid")
    private String uid;

    @Column(name = "_name")
    private String name;

    @Column(name = "_display_name")
    private String displayName;

    @Column(name = "_gender")
    private int gender;

    @Column(name = "_portrait")
    private String portrait;

    @Column(name = "_mobile")
    private String mobile;

    @Column(name = "_email")
    private String email;

    @Column(name = "_address")
    private String address;

    @Column(name = "_company")
    private String company;

    @Column(name = "_social")
    private String social;

    @Column(name = "_password_md5")
    private String passwordMD5;

    @Column(name = "_salt")
    private String salt;

    @Column(name = "_extra")
    private String extra;

    @Column(name = "_type")
    private int type;

    @Column(name = "_dt")
    private long dt;

    @Column(name = "_createTime")
    private Date createTime;

    @Column(name = "_deleted")
    private int deleted;

    @Transient
    private String userStatus;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSocial() {
        return social;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    public String getPasswordMD5() {
        return passwordMD5;
    }

    public void setPasswordMD5(String passwordMD5) {
        this.passwordMD5 = passwordMD5;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
