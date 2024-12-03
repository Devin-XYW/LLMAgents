package com.llm.chatglm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
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
import com.llm.agents.core.parser.AiMessageParser;
import com.llm.agents.core.parser.FunctionMessageParser;
import com.llm.agents.core.prompt.FunctionPrompt;
import com.llm.agents.core.prompt.Prompt;
import com.llm.agents.core.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Devin
 * @Date 2024/12/3 23:10
 * @Description: 智谱大模型
 * API文档：https://open.bigmodel.cn/dev/api/normal-model/glm-4
 **/
public class ChatglmLlm extends BaseLlm<ChatglmLlmConfig> {
    private HttpClient httpClient = new HttpClient();
    public AiMessageParser aiMessageParser = ChatglmLlmUtil.getAiMessageParser(false);
    public AiMessageParser aiStreamMessageParser = ChatglmLlmUtil.getAiMessageParser(true);
    public FunctionMessageParser functionMessageParser = ChatglmLlmUtil.getFunctionMessageParser();


    public ChatglmLlm(ChatglmLlmConfig config) {
        super(config);
    }

    @Override
    public <R extends MessageResponse<?>> R chat(Prompt<R> prompt, ChatOptions options) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", ChatglmLlmUtil.createAuthorizationToken(config));

        String endpoint = config.getEndpoint();
        String payload = ChatglmLlmUtil.promptToPayload(prompt, config, false, options);
        String response = httpClient.post(endpoint + "/api/paas/v4/chat/completions", headers, payload);

        if (config.isDebug()) {
            System.out.println(">>>>receive payload:" + response);
        }

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
        headers.put("Authorization", ChatglmLlmUtil.createAuthorizationToken(config));

        String payload = ChatglmLlmUtil.promptToPayload(prompt, config, true, options);

        String endpoint = config.getEndpoint();
        LlmClientListener clientListener = new BaseLlmClientListener(this, llmClient, listener, prompt, aiStreamMessageParser, functionMessageParser);
        llmClient.start(endpoint + "/api/paas/v4/chat/completions", headers, payload, clientListener, config);
    }

}
