package com.llm.agents.core.chain.node;

import com.llm.agents.core.chain.Chain;
import com.llm.agents.core.chain.ChainNode;

import java.util.Map;

/**
 * @Author Devin
 * @Date 2024/11/21 22:45
 * @Description:
 **/
public class EndNode extends ChainNode {
    private boolean normal = true;
    private String message;

    public boolean isNormal() {
        return normal;
    }

    public void setNormal(boolean normal) {
        this.normal = normal;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EndNode() {
        this.name = "end";
    }

    @Override
    protected Map<String, Object> execute(Chain chain) {
        //TODO 实现stop操作
//        if (normal) {
//            chain.stopNormal(message);
//        } else {
//            chain.stopError(message);
//        }
        return null;
    }
}
