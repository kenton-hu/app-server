//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.wildfirechat.app.pojo;

import cn.wildfirechat.proto.WFCMessage;
import cn.wildfirechat.proto.WFCMessage.User;

public class InputOutputUserInfoExt {
    private String userId;
    private String name;
    private String password;
    private String displayName;
    private String portrait;
    private int gender;
    private String mobile;
    private String email;
    private String address;
    private String company;
    private String social;
    private String extra;
    private int type;
    private long updateDt;
    private boolean isNewUser;

    public InputOutputUserInfoExt() {
    }

    public static InputOutputUserInfoExt fromPbUser(WFCMessage.User pbUser) {
        InputOutputUserInfoExt inputCreateUser = new InputOutputUserInfoExt();
        inputCreateUser.userId = pbUser.getUid();
        inputCreateUser.name = pbUser.getName();
        inputCreateUser.displayName = pbUser.getDisplayName();
        inputCreateUser.portrait = pbUser.getPortrait();
        inputCreateUser.gender = pbUser.getGender();
        inputCreateUser.mobile = pbUser.getMobile();
        inputCreateUser.email = pbUser.getEmail();
        inputCreateUser.address = pbUser.getAddress();
        inputCreateUser.company = pbUser.getCompany();
        inputCreateUser.social = pbUser.getSocial();
        inputCreateUser.extra = pbUser.getExtra();
        inputCreateUser.type = pbUser.getType();
        inputCreateUser.updateDt = pbUser.getUpdateDt();
        return inputCreateUser;
    }

    public String getSocial() {
        return this.social;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    public WFCMessage.User toUser() {
        WFCMessage.User.Builder newUserBuilder = User.newBuilder().setUid(this.userId);
        if (this.name != null) {
            newUserBuilder.setName(this.name);
        }

        if (this.displayName != null) {
            newUserBuilder.setDisplayName(this.displayName);
        }

        if (this.getPortrait() != null) {
            newUserBuilder.setPortrait(this.getPortrait());
        }

        if (this.getEmail() != null) {
            newUserBuilder.setEmail(this.getEmail());
        }

        if (this.getAddress() != null) {
            newUserBuilder.setAddress(this.getAddress());
        }

        if (this.getCompany() != null) {
            newUserBuilder.setCompany(this.getCompany());
        }

        if (this.getSocial() != null) {
            newUserBuilder.setSocial(this.getSocial());
        }

        if (this.getMobile() != null) {
            newUserBuilder.setMobile(this.getMobile());
        }

        if (this.getExtra() != null) {
            newUserBuilder.setExtra(this.getExtra());
        }

        newUserBuilder.setGender(this.gender);
        newUserBuilder.setType(this.type);
        newUserBuilder.setUpdateDt(System.currentTimeMillis());
        return newUserBuilder.build();
    }

    public long getUpdateDt() {
        return this.updateDt;
    }

    public void setUpdateDt(long updateDt) {
        this.updateDt = updateDt;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPortrait() {
        return this.portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getExtra() {
        return this.extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isNewUser() {
        return isNewUser;
    }

    public void setNewUser(boolean newUser) {
        isNewUser = newUser;
    }
}
