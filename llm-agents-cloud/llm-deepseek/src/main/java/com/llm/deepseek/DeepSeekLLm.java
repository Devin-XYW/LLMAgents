package com.llm.deepseek;

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
import com.llm.agents.core.message.parser.AiMessageParser;
import com.llm.agents.core.message.parser.FunctionMessageParser;
import com.llm.agents.core.prompt.FunctionPrompt;
import com.llm.agents.core.prompt.Prompt;
import com.llm.agents.core.util.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @Author Devin
 * @Date 2025/2/5 22:13
 * @Description:
 **/
public class DeepSeekLLm extends BaseLlm<DeepSeekLLmConfig> {
    private final Map<String, String> headers = new HashMap<>();
    private final HttpClient httpClient = new HttpClient();
    private final AiMessageParser aiMessageParser = DeepSeekLLmUtil.getAiMessageParser(false);
    private final AiMessageParser streamMessageParser = DeepSeekLLmUtil.getAiMessageParser(true);
    public FunctionMessageParser functionMessageParser = DeepSeekLLmUtil.getFunctionMessageParser();

    public DeepSeekLLm(DeepSeekLLmConfig config){
        super(config);
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        headers.put("Authorization", "Bearer " + getConfig().getApiKey());
    }

    public static DeepSeekLLm of(String apiKey) {
        DeepSeekLLmConfig config = new DeepSeekLLmConfig();
        config.setApiKey(apiKey);
        return new DeepSeekLLm(config);
    }

    @Override
    public <R extends MessageResponse<?>> R chat(Prompt<R> prompt, ChatOptions options){

        Consumer<Map<String, String>> headersConfig = config.getHeadersConfig();
        if (headersConfig != null) {
            headersConfig.accept(headers);
        }

        String payload = DeepSeekLLmUtil.promptToPayload(prompt,config,options,false);
        String endpoint = config.getEndpoint();
        String response = httpClient.post(endpoint + "/chat/completions", headers, payload);

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

        return (R) messageResponse;
    }

    @Override
    public <R extends MessageResponse<?>> void chatStream(Prompt<R> prompt, StreamResponseListener<R> listener, ChatOptions options) {
        LlmClient llmClient = new SseClient();
        String payload = DeepSeekLLmUtil.promptToPayload(prompt, config, options, true);
        String endpoint = config.getEndpoint();

        LlmClientListener clientListener = new BaseLlmClientListener(this, llmClient, listener, prompt, streamMessageParser, functionMessageParser);
        llmClient.start(endpoint + "/chat/completions", headers, payload, clientListener, config);
    }
}
