package com.llm.agents.core.chain.event;

import com.llm.agents.core.chain.ChainEvent;
import com.llm.agents.core.chain.ChainStatus;

/**
 * @Author Devin
 * @Date 2024/11/24 15:17
 * @Description:
 **/
public class OnStatusChangeEvent implements ChainEvent {
    private ChainStatus status;
    private ChainStatus before;

    public OnStatusChangeEvent(ChainStatus status, ChainStatus before) {
        this.status = status;
        this.before = before;
    }

    public ChainStatus getStatus() {
        return status;
    }

    public void setStatus(ChainStatus status) {
        this.status = status;
    }

    public ChainStatus getBefore() {
        return before;
    }

    public void setBefore(ChainStatus before) {
        this.before = before;
    }

    @Override
    public String toString() {
        return "OnStatusChangeEvent{" +
                "status=" + status +
                ", before=" + before +
                '}';
    }


}
