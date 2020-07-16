package com.test.entity;

import lombok.Data;

/**
 * @author lkt
 * @Classname Weather
 * @Description TODO
 */
@Data
public class Weather {

    /**
     * 时间
     */
    private String dateTime;
    /**
     * 天气
     */
    private String weather;
    /**
     * 风向风速
     */
    private String windDirectionAndSpeed;
    /**
     * 气温
     */
    private String temperature;

}