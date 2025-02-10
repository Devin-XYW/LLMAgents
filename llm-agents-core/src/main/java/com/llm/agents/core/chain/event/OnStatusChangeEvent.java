package com.llm.agents.core.chain.event;

import com.llm.agents.core.chain.Chain;
import com.llm.agents.core.chain.ChainStatus;

/**
 * @Author Devin
 * @Date 2024/11/24 15:17
 * @Description:
 **/
public class OnStatusChangeEvent extends BaseChainEvent {
    private ChainStatus status;
    private ChainStatus before;

    public OnStatusChangeEvent(Chain chain, ChainStatus status, ChainStatus before) {
        super(chain);
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
