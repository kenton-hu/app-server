package cn.wildfirechat.app.admin.utils;

import cn.hutool.core.util.StrUtil;

/**
 * @Description: 消息工具类
 * @author: Dragon
 * @date: 2024/8/21 15:30
 */
public class MessageUtils {

    /**
     * 根据年月生成表名
     * @param tablePrefix 表前缀
     * @param monthNo 年月：202408
     * @return 表名
     */
    public static String genTableName(String tablePrefix, String monthNo) {
        if (StrUtil.isBlank(monthNo) || monthNo.length() != 6) {
            return null;
        }
        int year = Integer.parseInt(monthNo.substring(0, 4));
        int month = Integer.parseInt(monthNo.substring(4, 6));
        int tableSuffix = (month - 1 + (year % 3) * 12);
        return tablePrefix + "_" + tableSuffix;
    }
}
