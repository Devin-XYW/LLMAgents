package com.llm.qwen;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.llm.agents.core.llm.BaseLlm;
import com.llm.agents.core.llm.ChatOptions;
import com.llm.agents.core.llm.MessageResponse;
import com.llm.agents.core.llm.StreamResponseListener;
import com.llm.agents.core.llm.client.BaseLlmClientListener;
import com.llm.agents.core.llm.client.LlmClient;
import com.llm.agents.core.llm.client.LlmClientListener;
import com.llm.agents.core.llm.client.http.HttpClient;
import com.llm.agents.core.llm.client.http.SseClient;
import com.llm.agents.core.llm.response.AbstractBaseMessageResponse;
import com.llm.agents.core.llm.response.AiMessageResponse;
import com.llm.agents.core.llm.response.FunctionMessageResponse;
import com.llm.agents.core.message.ai.AiMessage;
import com.llm.agents.core.parser.AiMessageParser;
import com.llm.agents.core.parser.FunctionMessageParser;
import com.llm.agents.core.parser.impl.DefaultAiMessageParser;
import com.llm.agents.core.prompt.FunctionPrompt;
import com.llm.agents.core.prompt.Prompt;
import com.llm.agents.core.util.StringUtil;

import java.util.*;

/**
 * @Author Devin
 * @Date 2024/11/26 22:49
 * @Description: 阿里千问协议文档：https://help.aliyun.com/zh/dashscope/developer-reference/api-details?spm=a2c4g.11186623.0.0.1ff6fa70jCgGRc#b8ebf6b25eul6
 **/
public class QwenLLm extends BaseLlm<QwenLLmConfig> {

    HttpClient httpClient = new HttpClient();

    public AiMessageParser aiMessageParser = QwenLLmUtil.getAiMessageParser();
    public FunctionMessageParser functionMessageParser = QwenLLmUtil.getFunctionMessageParser();

    public QwenLLm(QwenLLmConfig config) {
        super(config);
    }

    public <R extends MessageResponse<?>> R chat(Prompt<R> prompt, ChatOptions options){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + getConfig().getApiKey());

        String payload = QwenLLmUtil.promptToPayload(prompt, config, options);
        String endpoint = config.getEndpoint();

        String response = httpClient.post(
                endpoint + "/api/v1/services/aigc/text-generation/generation",
                headers,
                payload
        );

        if (StringUtil.noText(response)) {
            return null;
        }

        JSONObject jsonObject = JSON.parseObject(response);
        JSONObject error = jsonObject.getJSONObject("error");

        AbstractBaseMessageResponse<?> messageResponse;

        if (prompt instanceof FunctionPrompt) {
            messageResponse = new FunctionMessageResponse(((FunctionPrompt) prompt).getFunctions()
                    , functionMessageParser.parse(jsonObject));
        } else {
            messageResponse = new AiMessageResponse(aiMessageParser.parse(jsonObject));
        }

        if (error != null && !error.isEmpty()) {
            messageResponse.setError(true);
            messageResponse.setErrorMessage(error.getString("message"));
            messageResponse.setErrorType(error.getString("type"));
            messageResponse.setErrorCode(error.getString("code"));
        }

        //noinspection unchecked
        return (R) messageResponse;
    }

    @Override
    public <R extends MessageResponse<?>> void chatStream(Prompt<R> prompt, StreamResponseListener<R> listener, ChatOptions options) {
        LlmClient llmClient = new SseClient();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + getConfig().getApiKey());
        headers.put("X-DashScope-SSE", "enable"); //stream

        String payload = QwenLLmUtil.promptToPayload(prompt, config, options);

        LlmClientListener clientListener = new BaseLlmClientListener(this, llmClient, listener, prompt, new DefaultAiMessageParser() {
            int prevMessageLength = 0;

            @Override
            public AiMessage parse(JSONObject content) {
                AiMessage aiMessage = aiMessageParser.parse(content);
                String messageContent = aiMessage.getContent();
                aiMessage.setContent(messageContent.substring(prevMessageLength));
                prevMessageLength = messageContent.length();
                return aiMessage;
            }
        }, functionMessageParser);

        String endpoint = config.getEndpoint();
        llmClient.start(endpoint + "/api/v1/services/aigc/text-generation/generation", headers, payload, clientListener, config);
    }

}
