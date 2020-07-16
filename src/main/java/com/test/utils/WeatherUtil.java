package com.test.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author lkt
 * @Classname WeatherUtil
 * @Description TODO
 */
public class WeatherUtil {
    /**
     * 天气判断
     */
    public static Map<Double, String> WP_NAME_MAP = new HashMap<>();

    public static void loadWeatherName() {
        WP_NAME_MAP.put(0.0, "晴");
        WP_NAME_MAP.put(1.0, "多云");
        WP_NAME_MAP.put(2.0, "阴");
        WP_NAME_MAP.put(3.0, "阵雨");
        WP_NAME_MAP.put(4.0, "雷阵雨");
        WP_NAME_MAP.put(5.0, "雷阵雨并伴有冰雹");
        WP_NAME_MAP.put(6.0, "雨夹雪");
        WP_NAME_MAP.put(7.0, "小雨");
        WP_NAME_MAP.put(8.0, "中雨");
        WP_NAME_MAP.put(9.0, "大雨");
        WP_NAME_MAP.put(10.0, "暴雨");
        WP_NAME_MAP.put(11.0, "大暴雨");
        WP_NAME_MAP.put(12.0, "特大暴雨");
        WP_NAME_MAP.put(13.0, "阵雪");
        WP_NAME_MAP.put(14.0, "小雪");
        WP_NAME_MAP.put(15.0, "中雪");
        WP_NAME_MAP.put(16.0, "大雪");
        WP_NAME_MAP.put(17.0, "暴雪");
        WP_NAME_MAP.put(18.0, "雾");
        WP_NAME_MAP.put(19.0, "冻雨");
        WP_NAME_MAP.put(20.0, "沙尘暴");
        WP_NAME_MAP.put(21.0, "小到中雨");
        WP_NAME_MAP.put(22.0, "中到大雨");
        WP_NAME_MAP.put(23.0, "大到暴雨");
        WP_NAME_MAP.put(24.0, "暴雨到大暴雨");
        WP_NAME_MAP.put(25.0, "大暴雨到特大暴雨");
        WP_NAME_MAP.put(26.0, "小到中雪");
        WP_NAME_MAP.put(27.0, "中到大雪");
        WP_NAME_MAP.put(28.0, "大到暴雪");
        WP_NAME_MAP.put(53.0, "霾");
    }

    /**
     * 风向判断
     *
     * @param windDirection 风向
     * @return
     */
    public static String windDirection(Double windDirection) {
        if (windDirection == null) {
            return null;
        }
        String windDirectionS = "";
        if (windDirection >= 0 && windDirection < 90) {
            windDirectionS = "西南风";
        } else if (windDirection >= 90 && windDirection < 180) {
            windDirectionS = "西北风";
        } else if (windDirection >= 180 && windDirection < 270) {
            windDirectionS = "东北风";
        } else if (windDirection >= 270 && windDirection < 360) {
            windDirectionS = "东南风";
        }
        return windDirectionS;
    }

    /**
     * 风速判断
     *
     * @param windSpeed 风速
     * @return
     */
    public static String windSpeed(Double windSpeed) {
        if (windSpeed == null) {
            return null;
        }
        String windSpeeds = "";
        if (windSpeed >= 0.0 && windSpeed <= 0.2) {
            windSpeeds = "0 级";
        } else if (windSpeed >= 0.3 && windSpeed <= 1.5) {
            windSpeeds = "1 级";
        } else if (windSpeed >= 1.6 && windSpeed <= 3.3) {
            windSpeeds = "2 级";
        } else if (windSpeed >= 3.4 && windSpeed <= 5.4) {
            windSpeeds = "3 级";
        } else if (windSpeed >= 5.5 && windSpeed <= 7.9) {
            windSpeeds = "4 级";
        } else if (windSpeed >= 8.0 && windSpeed <= 10.7) {
            windSpeeds = "5 级";
        } else if (windSpeed >= 10.8 && windSpeed <= 13.8) {
            windSpeeds = "6 级";
        } else if (windSpeed >= 13.9 && windSpeed <= 17.1) {
            windSpeeds = "7 级";
        } else if (windSpeed >= 17.2 && windSpeed <= 20.7) {
            windSpeeds = "8 级";
        } else if (windSpeed >= 20.8 && windSpeed <= 24.4) {
            windSpeeds = "9 级";
        } else if (windSpeed >= 24.5 && windSpeed <= 28.4) {
            windSpeeds = "10 级";
        } else if (windSpeed >= 28.5 && windSpeed <= 32.6) {
            windSpeeds = "11 级";
        } else if (windSpeed >= 32.7 && windSpeed <= 36.9) {
            windSpeeds = "12 级";
        }
        return windSpeeds;
    }

    /**
     * 获取时间
     *
     * @param dateTime 日期
     * @param count    天数 -：向前，+：向后
     * @return
     */
    public static List<String> twoBeforeDay(String dateTime, Integer count) {
        List<String> timeList = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parse = LocalDateTime.parse(dateTime, dateTimeFormatter);
        if (count < 0) {
            for (; count < 0; count++) {
                timeList.add(parse.plusDays(count).format(dateTimeFormatter));
            }
        } else {
            for (int i = 1; count >= i; i++) {
                timeList.add(parse.plusDays(i).format(dateTimeFormatter));
            }
        }
        return timeList;
    }

    /**
     * 加时间
     *
     * @param day 日期
     * @param x   分钟数
     * @return
     */
    public static String addDateMinute(String day, int x) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(day);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (date == null) {
            return "";
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, x);
        date = cal.getTime();
        cal = null;
        return format.format(date);
    }
}