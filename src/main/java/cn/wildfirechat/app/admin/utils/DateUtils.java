package cn.wildfirechat.app.admin.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.util.Map;
import java.util.TreeMap;

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

    /**
     * 获取日期区间
     * @param dateStr 结束日期
     * @param count 相差天数
     * @param format 日期格式
     * @return
     */
    public static Map<String, Long> getRangeDate(String dateStr, int count, String format) {
        Map<String, Long> map = new TreeMap<>();
        for (int i = count; i >= 0; i--) {
            map.put(getDateOffsetByFormat(dateStr, -i, format), 0L);
        }
        return map;
    }
}
