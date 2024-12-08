package com.llm.agents.core.llm;

import java.util.List;

/**
 * @Author Devin
 * @Date 2024/11/17 14:55
 * @Description: 大模型配置实现类
 **/
public class ChatOptions {
    private String seed;
    private Float temperature= 0.8f; //大模型随机度，默认0.8
    private Float topP;
    private Integer topK;
    private Integer maxTokens;
    private List<String> stop;

    public static final ChatOptions DEFAULT = new ChatOptions(){
        @Override
        public void setTemperature(Float temperature) {
            throw new IllegalStateException("Can not set temperature to the default instance.");
        }

        @Override
        public void setMaxTokens(Integer maxTokens) {
            throw new IllegalStateException("Can not set maxTokens to the default instance.");
        }
    };

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getTopP() {
        return topP;
    }

    public void setTopP(Float topP) {
        this.topP = topP;
    }

    public Integer getTopK() {
        return topK;
    }

    public void setTopK(Integer topK) {
        this.topK = topK;
    }

    public Integer getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
    }

    public List<String> getStop() {
        return stop;
    }

    public void setStop(List<String> stop) {
        this.stop = stop;
    }
}
