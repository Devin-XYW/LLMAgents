package com.llm.agents.core.message.func;

import com.llm.agents.core.message.ai.AiMessage;

import java.util.Map;

/**
 * @Author Devin
 * @Date 2024/11/16 14:16
 * @Description: 用于Function工具类型消息
 **/
public class FunctionMessage extends AiMessage {
    private String functionName;
    private Map<String,Object> args;

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public void setArgs(Map<String, Object> args) {
        this.args = args;
    }

    public String toString() {
        return "FunctionMessage{" +
                "functionName='" + functionName + '\'' +
                ", args=" + args +
                ", content='" + content + '\'' +
                ", metadataMap=" + metaDataMap +
                '}';
    }
}
