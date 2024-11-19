package com.llm.agents.core.chain;

/**
 * @Author Devin
 * @Date 2024/11/19 23:00
 * @Description:
 **/
public interface ChainEventListener {
    void onEvent(ChainEvent event, Chain chain);
}
