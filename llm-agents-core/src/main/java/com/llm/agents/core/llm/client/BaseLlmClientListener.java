package com.llm.agents.core.llm.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.llm.agents.core.functions.Function;
import com.llm.agents.core.llm.ChatContext;
import com.llm.agents.core.llm.LLM;
import com.llm.agents.core.llm.MessageResponse;
import com.llm.agents.core.llm.StreamResponseListener;
import com.llm.agents.core.llm.response.AiMessageResponse;
import com.llm.agents.core.llm.response.FunctionMessageResponse;
import com.llm.agents.core.message.ai.AiMessage;
import com.llm.agents.core.message.func.FunctionMessage;
import com.llm.agents.core.message.parser.AiMessageParser;
import com.llm.agents.core.message.parser.FunctionMessageParser;
import com.llm.agents.core.prompt.FunctionPrompt;
import com.llm.agents.core.prompt.HistoriesPrompt;
import com.llm.agents.core.prompt.Prompt;
import com.llm.agents.core.util.StringUtil;

import java.util.List;

/**
 * @Author Devin
 * @Date 2024/11/19 22:19
 * @Description:
 **/
public class BaseLlmClientListener implements LlmClientListener{

    private final StreamResponseListener streamResponseListener;
    private final Prompt prompt;
    private final AiMessageParser messageParser;
    private final FunctionMessageParser functionMessageParser;
    private final StringBuilder fullMessage = new StringBuilder();
    private AiMessage lastAiMessage;
    private boolean isFunctionCalling = false;
    private final ChatContext context;


    public BaseLlmClientListener(LLM llm, LlmClient client
            , StreamResponseListener streamResponseListener, Prompt prompt
            , AiMessageParser messageParser, FunctionMessageParser functionMessageParser){
        this.streamResponseListener = streamResponseListener;
        this.prompt = prompt;
        this.messageParser = messageParser;
        this.functionMessageParser = functionMessageParser;
        this.context = new ChatContext(llm, client);

        if (prompt instanceof FunctionPrompt) {
            if (functionMessageParser == null) {
                throw new IllegalArgumentException("Can not support Function Calling");
            } else {
                isFunctionCalling = true;
            }
        }
    }

    @Override
    public void onStart(LlmClient client) {
        streamResponseListener.onStart(context);
    }

    @Override
    public void onMessage(LlmClient client, String response) {
        if (StringUtil.noText(response) || "[DONE]".equalsIgnoreCase(response.trim())) {
            return;
        }
        JSONObject jsonObject = JSON.parseObject(response);
        if (isFunctionCalling) {
            FunctionMessage functionMessage = functionMessageParser.parse(jsonObject);
            List<Function> functions = ((FunctionPrompt) prompt).getFunctions();
            MessageResponse<?> r = new FunctionMessageResponse(functions, functionMessage);
            //noinspection unchecked
            streamResponseListener.onMessage(context, r);
        } else {
            lastAiMessage = messageParser.parse(jsonObject);
            fullMessage.append(lastAiMessage.getContent());
            lastAiMessage.setFullContent(fullMessage.toString());
            MessageResponse<?> r = new AiMessageResponse(lastAiMessage);
            //noinspection unchecked
            streamResponseListener.onMessage(context, r);
        }
    }

    @Override
    public void onStop(LlmClient client) {
        if (lastAiMessage != null) {
            if (this.prompt instanceof HistoriesPrompt) {
                ((HistoriesPrompt) this.prompt).addMessage(lastAiMessage);
            }
        }

        streamResponseListener.onStop(context);
    }

    @Override
    public void onFailure(LlmClient client, Throwable throwable) {
        streamResponseListener.onFailure(context, throwable);
    }
}
