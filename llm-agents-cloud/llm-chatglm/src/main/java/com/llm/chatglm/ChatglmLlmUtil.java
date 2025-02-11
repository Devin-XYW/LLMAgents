package com.llm.chatglm;

import com.alibaba.fastjson.JSON;
import com.llm.agents.core.llm.ChatOptions;
import com.llm.agents.core.message.MessageStatus;
import com.llm.agents.core.message.parser.AiMessageParser;
import com.llm.agents.core.message.parser.FunctionMessageParser;
import com.llm.agents.core.message.parser.impl.DefaultAiMessageParser;
import com.llm.agents.core.message.parser.impl.DefaultFunctionMessageParser;
import com.llm.agents.core.prompt.DefaultPromptFormat;
import com.llm.agents.core.prompt.Prompt;
import com.llm.agents.core.prompt.PromptFormat;
import com.llm.agents.core.util.Maps;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;

/**
 * @Author Devin
 * @Date 2024/12/3 23:05
 * @Description:
 **/
public class ChatglmLlmUtil {

    private static final PromptFormat promptFormat = new DefaultPromptFormat();

    private static final String id = "HS256";
    private static final String jcaName = "HmacSHA256";
    private static final MacAlgorithm macAlgorithm;

    static {
        try {
            //create a custom MacAlgorithm with a custom minKeyBitLength
            int minKeyBitLength = 128;
            Class<?> c = Class.forName("io.jsonwebtoken.impl.security.DefaultMacAlgorithm");
            Constructor<?> ctor = c.getDeclaredConstructor(String.class, String.class, int.class);
            ctor.setAccessible(true);
            macAlgorithm = (MacAlgorithm) ctor.newInstance(id, jcaName, minKeyBitLength);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


    public static String createAuthorizationToken(ChatglmLlmConfig config) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "HS256");
        headers.put("sign_type", "SIGN");

        long nowMillis = System.currentTimeMillis();
        String[] idAndSecret = config.getApiKey().split("\\.");

        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("api_key", idAndSecret[0]);
        payloadMap.put("exp", nowMillis + 3600000);
        payloadMap.put("timestamp", nowMillis);
        String payloadJsonString = JSON.toJSONString(payloadMap);

        byte[] bytes = idAndSecret[1].getBytes();
        SecretKey secretKey = new SecretKeySpec(bytes, jcaName);

        JwtBuilder builder = Jwts.builder()
                .content(payloadJsonString)
                .header().add(headers).and()
                .signWith(secretKey, macAlgorithm);
        return builder.compact();
    }

    public static AiMessageParser getAiMessageParser(boolean isStream) {
        DefaultAiMessageParser aiMessageParser = new DefaultAiMessageParser();
        if (isStream) {
            aiMessageParser.setContentPath("$.choices[0].delta.content");
        } else {
            aiMessageParser.setContentPath("$.choices[0].message.content");
        }

        aiMessageParser.setIndexPath("$.choices[0].index");
        aiMessageParser.setStatusPath("$.choices[0].finish_reason");
        aiMessageParser.setStatusParser(content -> parseMessageStatus((String) content));
        aiMessageParser.setTotalTokensPath("$.usage.total_tokens");
        aiMessageParser.setPromptTokensPath("$.usage.prompt_tokens");
        aiMessageParser.setCompletionTokensPath("$.usage.completion_tokens");
        return aiMessageParser;
    }


    public static FunctionMessageParser getFunctionMessageParser() {
        DefaultFunctionMessageParser functionMessageParser = new DefaultFunctionMessageParser();
        functionMessageParser.setFunctionNamePath("$.choices[0].message.tool_calls[0].function.name");
        functionMessageParser.setFunctionArgsPath("$.choices[0].message.tool_calls[0].function.arguments");
        functionMessageParser.setFunctionArgsParser(JSON::parseObject);
        return functionMessageParser;
    }


    public static String promptToPayload(Prompt<?> prompt, ChatglmLlmConfig config, boolean withStream, ChatOptions options) {
        Maps.Builder builder = Maps.of("model", config.getModel())
                .put("messages", promptFormat.toMessagesJsonObject(prompt))
                .putIf(withStream, "stream", true)
                .putIfNotEmpty("tools", promptFormat.toFunctionsJsonObject(prompt))
                .putIfContainsKey("tools", "tool_choice", "auto")
                .putIfNotNull("top_p", options.getTopP())
                .putIfNotEmpty("stop", options.getStop())
                .putIf(map -> !map.containsKey("tools") && options.getTemperature() > 0, "temperature", options.getTemperature())
                .putIf(map -> !map.containsKey("tools") && options.getMaxTokens() != null, "max_tokens", options.getMaxTokens());
        return JSON.toJSONString(builder.build());

    }


    public static MessageStatus parseMessageStatus(String status) {
        return "stop".equals(status) ? MessageStatus.END : MessageStatus.MIDDLE;
    }

}
