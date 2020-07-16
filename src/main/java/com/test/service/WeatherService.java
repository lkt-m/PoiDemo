package com.test.service;

import com.test.entity.Weather;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lkt
 * @Classname WeatherService
 * @Description TODO
 */
@Service
public interface WeatherService {

    /**
     * 获取具体时间某气象站数据
     *
     * @param forecastTime
     * @param StationNo
     * @return
     */
    List<Weather> getAllWeather(String forecastTime, String StationNo);

    /**
     * 获取未来一天风向风速
     *
     * @param forecastTime
     * @param StationNo
     * @return
     */
    List<String> getOneDayWind(String forecastTime, String StationNo);

}
