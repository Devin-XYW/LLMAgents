package com.llm.agents.core.chain.event;

import com.llm.agents.core.chain.Chain;

/**
 * @Author Devin
 * @Date 2024/11/24 15:10
 * @Description:
 **/
public class OnChainStartEvent extends BaseChainEvent {

    public OnChainStartEvent(Chain chain) {
        super(chain);
    }

    @Override
    public String toString() {
        return "OnChainStartEvent{" +
                "chain=" + chain +
                '}';
    }

}
