package com.llm.llmagents.llmtest.function;

import com.llm.agents.core.functions.annotation.FunctionDef;
import com.llm.agents.core.functions.annotation.FunctionParam;

/**
 * @Author Devin
 * @Date 2024/11/30 16:55
 * @Description:
 **/
public class WeatherUtil {

    @FunctionDef(name = "get_the_weather_info",description = "获取城市天气信息")
    public static String getWeatherInfo(
            @FunctionParam(name = "city",description = "the city name") String name
    ){
        return name + "的天气是晴天";
    }

    @FunctionDef(name = "get_the_city_country",description = "获取城市对应的国家信息")
    public static String getCityCountry(
            @FunctionParam(name = "city",description = "城市名称") String name
    ){
        return name + "属于中国";
    }

}
