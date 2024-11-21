package com.llm.agents.core.chain;

/**
 * @Author Devin
 * @Date 2024/11/19 23:29
 * @Description:
 **/
public class ChainContext {
    private static final ThreadLocal<Chain> TL_CHAIN = new ThreadLocal<>();

    private static final ThreadLocal<ChainNode> TL_NODE = new ThreadLocal<>();

    public static Chain getCurrentChain() {
        return TL_CHAIN.get();
    }

    public static ChainNode getCurrentNode() {
        return TL_NODE.get();
    }

    static void setChain(Chain chain) {
        TL_CHAIN.set(chain);
    }

    static void clearChain() {
        TL_CHAIN.remove();
    }

    static void setNode(ChainNode node) {
        TL_NODE.set(node);
    }

    static void clearNode() {
        TL_NODE.remove();
    }

}
