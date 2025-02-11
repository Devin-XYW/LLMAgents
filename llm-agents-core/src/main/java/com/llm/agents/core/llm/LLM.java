package com.llm.agents.core.llm;

import com.llm.agents.core.util.exception.LlmException;
import com.llm.agents.core.llm.response.AbstractBaseMessageResponse;
import com.llm.agents.core.llm.response.AiMessageResponse;
import com.llm.agents.core.message.ai.AiMessage;
import com.llm.agents.core.prompt.Prompt;
import com.llm.agents.core.prompt.TextPrompt;

/**
 * @Author Devin
 * @Date 2024/11/16 14:22
 * @Description:
 **/
public interface LLM {

    /**
     * 一次性请求并返回大模型结果方法
     */
    <R extends MessageResponse<?>> R chat(Prompt<R> prompt, ChatOptions options);

    default <R extends MessageResponse<?>> R chat(Prompt<R> prompt) {
        return chat(prompt, ChatOptions.DEFAULT);
    }

    default String chat(String prompt){
        AbstractBaseMessageResponse<AiMessage> response = chat(new TextPrompt(prompt),ChatOptions.DEFAULT);
        if (response != null && response.isError()) throw new LlmException(response.getErrorMessage());
        return response != null && response.getMessage() != null ? response.getMessage().getContent() : null;
    }

    default String chat(String prompt,ChatOptions options){
        AbstractBaseMessageResponse<AiMessage> response = chat(new TextPrompt(prompt), options);
        if (response != null && response.isError()) throw new LlmException(response.getErrorMessage());
        return response != null && response.getMessage() != null ? response.getMessage().getContent() : null;
    }


    /**
     * 流式异步回调返回大模型结果方法
     */
    <R extends MessageResponse<?>> void chatStream(Prompt<R> prompt
            ,StreamResponseListener<R> listener,ChatOptions options);

    default <R extends MessageResponse<?>> void chatStream(Prompt<R> prompt,StreamResponseListener<R> listener){
        this.chatStream(prompt,listener,ChatOptions.DEFAULT);
    }

    default void chatStream(String prompt, StreamResponseListener<AiMessageResponse> listener) {
        this.chatStream(new TextPrompt(prompt), listener, ChatOptions.DEFAULT);
    }

    default void chatStream(String prompt, StreamResponseListener<AiMessageResponse> listener, ChatOptions options) {
        this.chatStream(new TextPrompt(prompt), listener, options);
    }
}
