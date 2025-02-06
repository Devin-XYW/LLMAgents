package com.llm.deepseek;

import com.llm.agents.core.llm.LlmConfig;

/**
 * @Author Devin
 * @Date 2025/2/5 22:13
 * @Description:
 **/
public class DeepSeekLLmConfig extends LlmConfig {
    private static final String DEFAULT_MODEL = "deepseek-chat";
    private static final String DEFAULT_EMBEDDING_MODEL = "";
    private static final String DEFAULT_ENDPOINT = "https://api.deepseek.com";

    public DeepSeekLLmConfig() {
        setEndpoint(DEFAULT_ENDPOINT);
        setModel(DEFAULT_MODEL);
    }

    public DeepSeekLLmConfig(String model) {
        this();
        setModel(model);
    }


}
