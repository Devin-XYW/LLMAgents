package com.llm.qwen;

import com.alibaba.fastjson.JSON;
import com.llm.agents.core.llm.ChatOptions;
import com.llm.agents.core.message.MessageStatus;
import com.llm.agents.core.parser.AiMessageParser;
import com.llm.agents.core.parser.FunctionMessageParser;
import com.llm.agents.core.parser.impl.DefaultAiMessageParser;
import com.llm.agents.core.parser.impl.DefaultFunctionMessageParser;
import com.llm.agents.core.prompt.DefaultPromptFormat;
import com.llm.agents.core.prompt.Prompt;
import com.llm.agents.core.prompt.PromptFormat;
import com.llm.agents.core.util.Maps;

/**
 * @Author Devin
 * @Date 2024/11/26 22:38
 * @Description: 阿里千问协议文档：https://help.aliyun.com/zh/dashscope/developer-reference/api-details?spm=a2c4g.11186623.0.0.1ff6fa70jCgGRc#b8ebf6b25eul6
 **/
public class QwenLLmUtil {
    private static final PromptFormat promptFormat = new DefaultPromptFormat();

    public static AiMessageParser getAiMessageParser(){
        DefaultAiMessageParser aiMessageParser = new DefaultAiMessageParser();
        aiMessageParser.setContentPath("$.output.choices[0].message.content");
        aiMessageParser.setStatusPath("$.output.choices[0].finish_reason");
        aiMessageParser.setTotalTokensPath("$.usage.total_tokens");
        aiMessageParser.setStatusParser(content -> parseMessageStatus((String) content));
        aiMessageParser.setTotalTokensPath("$.usage.total_tokens");
        aiMessageParser.setPromptTokensPath("$.usage.input_tokens");
        aiMessageParser.setCompletionTokensPath("$.usage.output_tokens");
        return aiMessageParser;
    }

    public static FunctionMessageParser getFunctionMessageParser(){
        DefaultFunctionMessageParser functionMessageParser = new DefaultFunctionMessageParser();
        functionMessageParser.setFunctionNamePath("$.output.choices[0].message.tool_calls[0].function.name");
        functionMessageParser.setFunctionArgsPath("$.output.choices[0].message.tool_calls[0].function.arguments");
        functionMessageParser.setFunctionArgsParser(JSON::parseObject);
        return functionMessageParser;
    }

    public static MessageStatus parseMessageStatus(String status) {
        return "stop".equals(status) ? MessageStatus.END : MessageStatus.MIDDLE;
    }

    public static String promptToPayload(Prompt<?> prompt, QwenLLmConfig config, ChatOptions options) {
        Maps.Builder root = Maps.of("model", config.getModel())
                .put("input", Maps.of("messages", promptFormat.toMessagesJsonObject(prompt)))
                .put("parameters", Maps.of("result_format", "message")
                        .putIfNotEmpty("tools", promptFormat.toFunctionsJsonObject(prompt))
                        .putIf(map -> !map.containsKey("tools") && options.getTemperature() > 0, "temperature", options.getTemperature())
                        .putIf(map -> !map.containsKey("tools") && options.getMaxTokens() != null, "max_tokens", options.getMaxTokens())
                        .putIfNotNull("top_p", options.getTopP())
                        .putIfNotNull("top_k", options.getTopK())
                        .putIfNotEmpty("stop", options.getStop())
                );


        return JSON.toJSONString(root.build());
    }

    public static String createEmbedURL(QwenLLmConfig config) {
        return "https://dashscope.aliyuncs.com/api/v1/services/embeddings/text-embedding/text-embedding";
    }
}
