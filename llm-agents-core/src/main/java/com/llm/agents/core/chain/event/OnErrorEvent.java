package com.llm.agents.core.chain.event;

import com.llm.agents.core.chain.ChainEvent;

/**
 * @Author Devin
 * @Date 2024/11/24 15:10
 * @Description:
 **/
public class OnErrorEvent implements ChainEvent {
    private Exception exception;

    public OnErrorEvent(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "OnErrorEvent{" +
                "exception=" + exception +
                '}';
    }

}
