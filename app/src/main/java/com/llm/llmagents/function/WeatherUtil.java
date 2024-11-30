package com.llm.llmagents.function;

import com.llm.agents.core.functions.annotation.FunctionDef;
import com.llm.agents.core.functions.annotation.FunctionParam;

/**
 * @Author Devin
 * @Date 2024/11/30 16:55
 * @Description:
 **/
public class WeatherUtil {

    @FunctionDef(name = "get_the_weather_info",description = "get the weather info")
    public static String getWeatherInfo(
            @FunctionParam(name = "city",description = "the city name") String name
    ){
        return name + "的天气是晴天";
    }

}
