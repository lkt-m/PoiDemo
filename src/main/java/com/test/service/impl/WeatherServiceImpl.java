package com.test.service.impl;

import com.test.entity.Weather;
import com.test.service.WeatherService;
import com.test.utils.WeatherUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author lkt
 * @Classname WeatherServiceImpl
 * @Description TODO
 */
@Service
public class WeatherServiceImpl implements WeatherService {

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    /**
     * 获取前几天全部天气信息
     *
     * @param forecastTime 测试时间
     * @param StationNo    站点
     * @return
     */
    @Override
    public List<Weather> getAllWeather(String forecastTime, String StationNo) {
        List<Weather> weatherList = new ArrayList<>();
        // 获得前两天的日期
        List<String> twoBeforeDay = WeatherUtil.twoBeforeDay(forecastTime, -2);
        // 获取天气
        List<String> wp = getWeather(twoBeforeDay, StationNo);
        // 获取风速和风向
        List<String> wdWs = getWindDirectionAndWindSpeed(twoBeforeDay, StationNo);
        // 获取温度
        List<String> tmp = getTemperature(twoBeforeDay, StationNo);
        for (int i = 0; i < 2; i++) {
            Weather weather = new Weather();
            weather.setDateTime(twoBeforeDay.get(i));
            weather.setWeather(wp.get(i));
            weather.setWindDirectionAndSpeed(wdWs.get(i));
            weather.setTemperature(tmp.get(i));
            weatherList.add(weather);
        }
        return weatherList;
    }

    /**
     * 获取未来一天风向风速
     *
     * @param forecastTime 测试时间
     * @param StationNo    站点
     * @return
     */
    @Override
    public List<String> getOneDayWind(String forecastTime, String StationNo) {
        List<String> windList = new ArrayList<>();
        String wdKey, wsKey, windDirection, windSpeed;
        for (int i = 0; i <= 7; i++) {
            wdKey = WeatherUtil.addDateMinute(forecastTime, 180 * i) + "_wd";
            wsKey = WeatherUtil.addDateMinute(forecastTime, 180 * i) + "_ws";
            windDirection = WeatherUtil.windDirection(Double.parseDouble(String.valueOf(redisTemplate.opsForHash().get(wdKey, StationNo))));
            windSpeed = WeatherUtil.windSpeed(Double.parseDouble(String.valueOf(redisTemplate.opsForHash().get(wsKey, StationNo))));
            windList.add(windDirection + windSpeed);
        }
        return windList;
    }

    /**
     * 获得天气
     *
     * @param day       日期
     * @param StationNo 站点
     * @return
     */
    private List<String> getWeather(List<String> day, String StationNo) {
        List<String> wpList = new ArrayList<>();
        List<Double> list = new ArrayList<>();
        WeatherUtil.loadWeatherName();
        String wp, str;
        for (String key : day) {
            for (int i = 0; i < 6; i++) {
                key = WeatherUtil.addDateMinute(key, 180 * i);
                str = String.valueOf(redisTemplate.opsForHash().get(key + "_wp", StationNo));
                if (str != null && !str.equals("") && !str.equals("null")) {
                    list.add(Double.valueOf(str));
                }
            }
            wp = WeatherUtil.WP_NAME_MAP.get(Collections.min(list)) + WeatherUtil.WP_NAME_MAP.get(Collections.max(list));
            wpList.add(wp);
        }
        return wpList;
    }

    /**
     * 获得风向和风速
     *
     * @param day       日期
     * @param StationNo 站点
     * @return
     */
    private List<String> getWindDirectionAndWindSpeed(List<String> day, String StationNo) {
        List<String> wdWsList = new ArrayList<>();
        List<Double> list1 = new ArrayList<>();
        List<Double> list2 = new ArrayList<>();
        String windDirection, windSpeed;
        String str1, str2;
        for (String key : day) {
            for (int i = 0; i < 6; i++) {
                key = WeatherUtil.addDateMinute(key, 180 * i);
                str1 = String.valueOf(redisTemplate.opsForHash().get(key + "_wd", StationNo));
                str2 = String.valueOf(redisTemplate.opsForHash().get(key + "_ws", StationNo));
                if (str1 != null && !str1.equals("") && !str1.equals("null") && str2 != null && !str2.equals("") && !str2.equals("null")) {
                    list1.add(Double.valueOf(str1));
                    list2.add(Double.valueOf(str2));
                }
            }
            windDirection = WeatherUtil.windDirection(Collections.max(list1)) + WeatherUtil.windDirection(Collections.min(list1));
            windSpeed = WeatherUtil.windSpeed(Collections.min(list2)) + "-" + WeatherUtil.windSpeed(Collections.max(list2));
            wdWsList.add(windDirection + windSpeed);
        }
        return wdWsList;
    }

    /**
     * 获取温度
     *
     * @param day       日期
     * @param StationNo 站点
     * @return
     */
    private List<String> getTemperature(List<String> day, String StationNo) {
        List<String> tmpList = new ArrayList<>();
        List<Double> list = new ArrayList<>();
        String str;
        for (String key : day) {
            for (int i = 0; i < 6; i++) {
                key = WeatherUtil.addDateMinute(key, 180 * i);
                str = String.valueOf(redisTemplate.opsForHash().get(key + "_tmp", StationNo));
                if (str != null && !str.equals("") && !str.equals("null")) {
                    list.add(Double.valueOf(str));
                }
            }
            tmpList.add(Collections.min(list) + "~" + Collections.max(list) + "℃");
        }
        return tmpList;
    }
}