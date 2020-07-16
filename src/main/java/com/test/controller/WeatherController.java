package com.test.controller;

import com.test.entity.Weather;
import com.test.service.impl.WeatherServiceImpl;
import com.test.utils.PoiUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lkt
 * @Classname WeatherController
 * @Description TODO
 */
@Controller
@RequestMapping("weather")
public class WeatherController {

    @Resource
    private WeatherServiceImpl weatherService;

    @RequestMapping("export")
    public void export(HttpServletRequest request, HttpServletResponse response) {
        String forecastTime = "2020-07-15 08:00:00";
        String StationNo = "58343";
        Map<String, Object> params = new HashMap<>();
        // 第一部分
        Map<String, String> weathers = new HashMap<>();
        List<Weather> weatherList = weatherService.getAllWeather(forecastTime, StationNo);
        for (int i = 0; i < weatherList.size(); i++) {
            weathers.put("date", weatherList.get(i).getDateTime());
            weathers.put("wp", weatherList.get(i).getWeather());
            weathers.put("wdWs", weatherList.get(i).getWindDirectionAndSpeed());
            weathers.put("tmp", weatherList.get(i).getTemperature());
        }
        params.put("weathers", weathers);
        // 第二部分
        Map<String, String> weather = new HashMap<>();
        List<String> list = weatherService.getOneDayWind(forecastTime, StationNo);
        for (int i = 0; i < 8; i++) {
            weather.put("time" + i, list.get(i));
        }
        params.put("weather", weather);

        /**
         * 导出word
         */
        PoiUtil.exportWord("word/template.docx", "F:/test", "demo.docx", params, request, response);
    }
}