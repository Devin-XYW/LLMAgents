package com.llm.saprk;

import com.llm.agents.core.llm.BaseLlm;
import com.llm.agents.core.llm.ChatContext;
import com.llm.agents.core.llm.ChatOptions;
import com.llm.agents.core.llm.MessageResponse;
import com.llm.agents.core.llm.StreamResponseListener;
import com.llm.agents.core.llm.client.BaseLlmClientListener;
import com.llm.agents.core.llm.client.LlmClient;
import com.llm.agents.core.llm.client.LlmClientListener;
import com.llm.agents.core.llm.client.http.HttpClient;
import com.llm.agents.core.llm.client.http.WebSocketClient;
import com.llm.agents.core.llm.response.AbstractBaseMessageResponse;
import com.llm.agents.core.llm.response.AiMessageResponse;
import com.llm.agents.core.llm.response.FunctionMessageResponse;
import com.llm.agents.core.message.ai.AiMessage;
import com.llm.agents.core.message.func.FunctionMessage;
import com.llm.agents.core.message.parser.AiMessageParser;
import com.llm.agents.core.message.parser.FunctionMessageParser;
import com.llm.agents.core.prompt.Prompt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * @Author Devin
 * @Date 2024/12/2 22:49
 * @Description:
 **/
public class SparkLlm extends BaseLlm<SparkLlmConfig> {
    private static final Logger logger = LoggerFactory.getLogger(SparkLlm.class);
    public AiMessageParser aiMessageParser = SparkLlmUtil.getAiMessageParser();
    public FunctionMessageParser functionMessageParser = SparkLlmUtil.getFunctionMessageParser();

    private final HttpClient httpClient = new HttpClient();

    public SparkLlm(SparkLlmConfig config) {
        super(config);
    }

    @Override
    public <R extends MessageResponse<?>> R chat(Prompt<R> prompt, ChatOptions options) {
        CountDownLatch latch = new CountDownLatch(1);
        Throwable[] failureThrowable = new Throwable[1];
        AbstractBaseMessageResponse<?>[] messageResponse = {null};

        waitResponse(prompt, options, messageResponse, latch, failureThrowable);

        AbstractBaseMessageResponse<?> response = messageResponse[0];
        if (response == null) {
            return null;
        }
        Throwable fialureThrowable = failureThrowable[0];
        if (null == response.getMessage() || fialureThrowable != null) {
            response.setError(true);
            if (fialureThrowable != null) {
                response.setErrorMessage(fialureThrowable.getMessage());
            }
        } else {
            response.setError(false);
        }

        return (R) response;
    }


    private <R extends MessageResponse<?>> void waitResponse(Prompt<R> prompt
            , ChatOptions options
            , AbstractBaseMessageResponse<?>[] messageResponse
            , CountDownLatch latch
            , Throwable[] failureThrowable) {
        chatStream(prompt, new StreamResponseListener<R>() {
            @Override
            public void onMessage(ChatContext context, R response) {
                if (response.getMessage() instanceof FunctionMessage) {
                    messageResponse[0] = (FunctionMessageResponse) response;
                } else {
                    AiMessage aiMessage = new AiMessage();
                    aiMessage.setContent(response.getMessage().getFullContent());
                    messageResponse[0] = new AiMessageResponse(aiMessage);
                }
            }

            @Override
            public void onStop(ChatContext context) {
                StreamResponseListener.super.onStop(context);
                latch.countDown();
            }

            @Override
            public void onFailure(ChatContext context, Throwable throwable) {
                logger.error(throwable.toString(), throwable);
                failureThrowable[0] = throwable;
            }
        }, options);

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public <R extends MessageResponse<?>> void chatStream(Prompt<R> prompt, StreamResponseListener<R> listener, ChatOptions options) {
        LlmClient llmClient = new WebSocketClient();
        String url = SparkLlmUtil.createURL(config);

        String payload = SparkLlmUtil.promptToPayload(prompt, config, options);

        LlmClientListener clientListener = new BaseLlmClientListener(this, llmClient, listener, prompt, aiMessageParser, functionMessageParser);
        llmClient.start(url, null, payload, clientListener, config);
    }

}
