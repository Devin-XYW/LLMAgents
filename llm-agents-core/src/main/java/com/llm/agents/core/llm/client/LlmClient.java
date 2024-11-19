package com.llm.agents.core.llm.client;


import com.llm.agents.core.llm.LlmConfig;

import java.util.Map;

public interface LlmClient {

    void start(String url, Map<String, String> headers, String payload, LlmClientListener listener, LlmConfig config);

    void stop();
}
