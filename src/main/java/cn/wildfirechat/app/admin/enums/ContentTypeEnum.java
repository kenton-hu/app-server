package cn.wildfirechat.app.admin.enums;

/**
 * 消息类型
 */
public enum ContentTypeEnum {
    TEXT("1", "文本"),
    VOICE("2", "语音"),
    PICTURE("3", "图片"),
    FILE("5", "文件"),
    VIDEO("6", "视频"),
    EXPRESSION("7", "表情"),
    RICH_MEDIA("8", "富媒体");

    private String code;
    private String name;

    ContentTypeEnum(String code, String name) {
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
        ContentTypeEnum[] values = ContentTypeEnum.values();
        for (ContentTypeEnum sessionType : values) {
            if (sessionType.getCode().equals(name)) {
                return sessionType.getName();
            }
        }
        return "";
    }
}
