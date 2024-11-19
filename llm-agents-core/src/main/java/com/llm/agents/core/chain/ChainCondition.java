package com.llm.agents.core.chain;

import com.llm.agents.core.memory.ContextMemory;

/**
 * @Author Devin
 * @Date 2024/11/19 23:01
 * @Description:
 **/
public interface ChainCondition {
    boolean check(Chain chain, ContextMemory memory);
}
