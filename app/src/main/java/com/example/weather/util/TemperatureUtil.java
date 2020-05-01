package com.example.weather.util;

import java.math.BigDecimal;

public class TemperatureUtil {
    /**
     *
     * <p>华氏度  = 32 + 摄氏度 × 1.8。</p>
     * @param degree 需要转换的温度
     * @param scale 保留的小数位
     * @return
     */
    public static double centigrade2Fahrenheit(double degree,int scale) {
        double d = 32 + degree * 1.8;
        return new BigDecimal(d).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     *
     * <p>摄氏度  = (华氏度 - 32) ÷ 1.8。</p>
     * @param degree 需要转换的温度
     * @param scale 保留的小数位
     * @return
     */
    public static double fahrenheit2Centigrade(double degree, int scale) {
        double d = (degree - 32) / 1.8;
        return new BigDecimal(d).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
