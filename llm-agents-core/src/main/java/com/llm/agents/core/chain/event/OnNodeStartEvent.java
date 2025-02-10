package com.llm.agents.core.chain.event;

import com.llm.agents.core.chain.Chain;
import com.llm.agents.core.chain.ChainNode;

/**
 * @Author Devin
 * @Date 2024/11/24 15:16
 * @Description:
 **/
public class OnNodeStartEvent extends BaseChainEvent {

    private ChainNode node;

    public OnNodeStartEvent(Chain chain, ChainNode node) {
        super(chain);
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
