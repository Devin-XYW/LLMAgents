package com.llm.agents.core.chain.event;

import com.llm.agents.core.chain.Chain;
import com.llm.agents.core.chain.ChainEvent;

/**
 * @Author Devin
 * @Date 2025/2/10 22:03
 * @Description:
 **/
public class BaseChainEvent implements ChainEvent {
    protected final Chain chain;

    public BaseChainEvent(Chain chain) {
        this.chain = chain;
    }

    public Chain getChain() {
        return chain;
    }

}
