package com.llm.chatglm;

import com.llm.agents.core.llm.LlmConfig;

/**
 * @Author Devin
 * @Date 2024/12/3 23:03
 * @Description:
 **/
public class ChatglmLlmConfig extends LlmConfig {

    private static final String DEFAULT_MODEL = "glm-4";
    private static final String DEFAULT_ENDPOINT = "https://open.bigmodel.cn";

    public ChatglmLlmConfig() {
        setEndpoint(DEFAULT_ENDPOINT);
        setModel(DEFAULT_MODEL);
    }
}
