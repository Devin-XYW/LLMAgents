package com.llm.agents.core.llm.response;

import com.llm.agents.core.functions.Function;
import com.llm.agents.core.message.ai.AiMessage;
import com.llm.agents.core.message.func.FunctionMessage;

import java.util.List;

/**
 * @Author Devin
 * @Date 2024/11/16 14:29
 * @Description: Function工具类型返回的消息体
 **/
public class FunctionMessageResponse extends AiMessageResponse{

    private final List<Function> functions;
    private final FunctionMessage functionMessage;
    private final Object functionResult;

    public FunctionMessageResponse(List<Function> functions, FunctionMessage functionMessage) {
        super(functionMessage);

        this.functions = functions;
        this.functionMessage = functionMessage;
        this.functionResult = invoke();

        if (this.functionMessage != null) {
            String messageContent = this.functionResult != null ? this.functionResult.toString() : null;
            this.functionMessage.setContent(messageContent);
        }
    }


    private Object invoke() {
        if (functionMessage == null) {
            return null;
        }
        Object result = null;
        for (Function function : functions) {
            if (function.getName().equals(functionMessage.getFunctionName())) {
                result = function.invoke(functionMessage.getArgs());
                break;
            }
        }
        return result;
    }

    @Override
    public FunctionMessage getMessage() {
        return functionMessage;
    }

    public Object getFunctionResult() {
        return functionResult;
    }

    @Override
    public String toString() {
        return "FunctionMessageResponse{" +
                "functions=" + functions +
                ", functionMessage=" + functionMessage +
                ", isError=" + error +
                ", errorMessage='" + errorMessage + '\'' +
                ", errorType='" + errorType + '\'' +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
