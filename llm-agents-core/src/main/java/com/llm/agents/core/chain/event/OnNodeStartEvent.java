package com.llm.agents.core.chain.event;

import com.llm.agents.core.chain.ChainEvent;
import com.llm.agents.core.chain.ChainNode;

/**
 * @Author Devin
 * @Date 2024/11/24 15:16
 * @Description:
 **/
public class OnNodeStartEvent implements ChainEvent {

    private ChainNode node;

    public OnNodeStartEvent(ChainNode node) {
        this.node = node;
    }

    public ChainNode getNode() {
        return node;
    }

    public void setNode(ChainNode node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return "OnNodeStartEvent{" +
                "node=" + node +
                '}';
    }

}
