package com.llm.agents.core.chain.event;

import com.llm.agents.core.chain.Chain;

/**
 * @Author Devin
 * @Date 2024/11/24 15:10
 * @Description:
 **/
public class OnErrorEvent extends BaseChainEvent {
    private Exception exception;

    public OnErrorEvent(Chain chain, Exception exception) {
        super(chain);
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
