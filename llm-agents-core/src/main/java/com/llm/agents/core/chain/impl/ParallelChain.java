package com.llm.agents.core.chain.impl;

import com.llm.agents.core.chain.Chain;
import com.llm.agents.core.chain.ChainNode;

/**
 * @Author Devin
 * @Date 2024/12/8 15:10
 * @Description: 并行链
 **/
public class ParallelChain extends Chain {
    @Override
    public void addNode(ChainNode chainNode) {
        chainNode.setAsync(true);
        super.addNode(chainNode);
    }

}
