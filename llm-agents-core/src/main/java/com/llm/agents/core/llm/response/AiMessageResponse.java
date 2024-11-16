package com.llm.agents.core.llm.response;

import com.llm.agents.core.message.ai.AiMessage;

/**
 * @Author Devin
 * @Date 2024/11/16 14:26
 * @Description:
 **/
public class AiMessageResponse extends AbstractBaseMessageResponse<AiMessage> {

    private AiMessage aiMessage;

    public AiMessageResponse(AiMessage aiMessage){
        this.aiMessage = aiMessage;
    }

    @Override
    public AiMessage getMessage() {
        return aiMessage;
    }

    @Override
    public String toString() {
        return "AiMessageResponse{" +
                "aiMessage=" + aiMessage +
                ", isError=" + error +
                ", errorMessage='" + errorMessage + '\'' +
                ", errorType='" + errorType + '\'' +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
