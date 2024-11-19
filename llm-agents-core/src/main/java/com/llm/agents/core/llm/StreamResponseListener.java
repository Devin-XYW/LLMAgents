package com.llm.agents.core.llm;

public interface StreamResponseListener<R extends MessageResponse<?>> {

    default void onStart(ChatContext context) {
    }

    void onMessage(ChatContext context, R response);

    default void onStop(ChatContext context) {
    }

    default void onFailure(ChatContext context, Throwable throwable) {
        if (throwable != null) {
            throwable.printStackTrace();
        }
    }
}
