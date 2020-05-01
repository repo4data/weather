package com.example.weather.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils {
    /**
     * 将与今天的相差天数转化为别名
     * @param i 今天的相差天数
     * @return 别名
     */
    public static String transToAlias(int i) {
        if (i < 0) return "";
        return  new String[]{"今天", "明天", "后天"}[i];
    }
    public static final String dateFormat = "M月d日";
}