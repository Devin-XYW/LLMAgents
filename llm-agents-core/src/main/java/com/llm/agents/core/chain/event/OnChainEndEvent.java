package com.llm.agents.core.chain.event;

import com.llm.agents.core.chain.Chain;

/**
 * @Author Devin
 * @Date 2024/11/24 15:14
 * @Description:
 **/
public class OnChainEndEvent extends BaseChainEvent {
    public OnChainEndEvent(Chain chain) {
        super(chain);
    }

    @Override
    public String toString() {
        return "OnChainEndEvent{" +
                "chain=" + chain +
                '}';
    }
}
