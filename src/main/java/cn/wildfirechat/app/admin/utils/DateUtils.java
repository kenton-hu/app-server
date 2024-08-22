package cn.wildfirechat.app.admin.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

/**
* @Description: 日期
* @author: Dragon
*/
public class DateUtils {
    public static String getDateByFormat(String dateStr, String format) {
        DateTime date = DateUtil.parse(dateStr);
        return DateUtil.format(date, format);
    }

    public static String getDateOffsetByFormat(String dateStr, int offset, String format) {
        DateTime date = DateUtil.parse(dateStr);
        return DateUtil.format(DateUtil.offsetDay(date, offset), format);
    }
}
