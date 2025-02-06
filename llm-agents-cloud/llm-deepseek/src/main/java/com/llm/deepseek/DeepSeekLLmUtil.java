package com.llm.deepseek;

import com.alibaba.fastjson.JSON;
import com.llm.agents.core.llm.ChatOptions;
import com.llm.agents.core.llm.LlmConfig;
import com.llm.agents.core.message.Message;
import com.llm.agents.core.message.MessageStatus;
import com.llm.agents.core.message.human.HumanMessage;
import com.llm.agents.core.parser.AiMessageParser;
import com.llm.agents.core.parser.FunctionMessageParser;
import com.llm.agents.core.parser.impl.DefaultAiMessageParser;
import com.llm.agents.core.parser.impl.DefaultFunctionMessageParser;
import com.llm.agents.core.prompt.DefaultPromptFormat;
import com.llm.agents.core.prompt.Prompt;
import com.llm.agents.core.prompt.PromptFormat;
import com.llm.agents.core.util.CollectionUtil;
import com.llm.agents.core.util.Maps;

import java.util.List;
import java.util.Map;

/**
 * @Author Devin
 * @Date 2025/2/5 22:14
 * @Description:
 **/
public class DeepSeekLLmUtil {
    private static final PromptFormat promptFormat = new DefaultPromptFormat();

    public static AiMessageParser getAiMessageParser(boolean isStream){
        DefaultAiMessageParser aiMessageParser = new DefaultAiMessageParser();
        if (isStream) {
            aiMessageParser.setContentPath("$.choices[0].delta.content");
        } else {
            aiMessageParser.setContentPath("$.choices[0].message.content");
        }
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

    public static String promptToPayload(Prompt<?> prompt, DeepSeekLLmConfig config, ChatOptions options, boolean withStream) {
        Maps.Builder root = Maps.of("model", config.getModel())
                .put("messages", promptFormat.toMessagesJsonObject(prompt))
                .putIfNotEmpty("tools", promptFormat.toFunctionsJsonObject(prompt))
                .putIf(withStream,"stream", true)
                .putIf(map -> !map.containsKey("tools") && options.getTemperature() > 0, "temperature", options.getTemperature())
                .putIf(map -> !map.containsKey("tools") && options.getMaxTokens() != null, "max_tokens", options.getMaxTokens())
                .putIfNotNull("top_p", options.getTopP())
                .putIfNotEmpty("stop", options.getStop());

        return JSON.toJSONString(root.build());
    }


}
