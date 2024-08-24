package cn.wildfirechat.app.admin.enums;

/**
 * 会话类型
 */
public enum SessionTypeEnum {
    SINGLE_CHAT("0", "单聊"),
    GROUP_CHAT("1", "群组"),
    ROOM_CHAT("2", "聊天室"),
    CHANNEL_CHAT("3", "频道");

    private String code;
    private String name;

    SessionTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getCodeName(String name) {
        SessionTypeEnum[] values = SessionTypeEnum.values();
        for (SessionTypeEnum sessionType : values) {
            if (sessionType.getCode().equals(name)) {
                return sessionType.getName();
            }
        }
        return "";
    }
}
