package com.llm.agents.core.llm;

/**
 * @Author Devin
 * @Date 2024/11/19 22:14
 * @Description:
 **/
public abstract class BaseLlm<T extends LlmConfig> implements LLM {
    protected T config;

    public BaseLlm(T config) {
        this.config = config;
    }

    public T getConfig() {
        return config;
    }

}
