package com.llm.agents.core.llm.response;

import com.llm.agents.core.llm.MessageResponse;
import com.llm.agents.core.message.ai.AiMessage;

/**
 * @Author Devin
 * @Date 2024/11/16 14:21
 * @Description: 大模型返回结果抽象类
 **/
public abstract class AbstractBaseMessageResponse<T extends AiMessage>
        implements MessageResponse<T> {
    protected boolean error = false;
    protected String errorMessage;
    protected String errorType;
    protected String errorCode;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
