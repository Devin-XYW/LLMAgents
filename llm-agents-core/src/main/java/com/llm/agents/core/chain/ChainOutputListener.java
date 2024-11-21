package com.llm.agents.core.chain;

import com.llm.agents.core.agent.Agent;

/**
 * @Author Devin
 * @Date 2024/11/19 23:28
 * @Description:
 **/
public interface ChainOutputListener {
    void onOutput(Chain chain, Agent agent, Object outputMessage);
}
