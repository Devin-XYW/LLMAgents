
package com.llm.agents.core.llm;

import com.llm.agents.core.llm.client.LlmClient;

public class ChatContext {
    private LLM llm;
    private LlmClient client;

    public ChatContext(LLM llm, LlmClient client) {
        this.llm = llm;
        this.client = client;
    }

    public LLM getLlm() {
        return llm;
    }

    public void setLlm(LLM llm) {
        this.llm = llm;
    }

    public LlmClient getClient() {
        return client;
    }

    public void setClient(LlmClient client) {
        this.client = client;
    }
}
