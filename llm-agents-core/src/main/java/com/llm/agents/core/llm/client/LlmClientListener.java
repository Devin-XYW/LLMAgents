
package com.llm.agents.core.llm.client;

public interface LlmClientListener {

    void onStart(LlmClient client);

    void onMessage(LlmClient client,String response);

    void onStop(LlmClient client);

    void onFailure(LlmClient client, Throwable throwable);

}
