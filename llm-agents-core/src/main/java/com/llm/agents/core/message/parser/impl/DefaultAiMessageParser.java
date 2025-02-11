
package com.llm.agents.core.message.parser.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.llm.agents.core.message.MessageStatus;
import com.llm.agents.core.message.ai.AiMessage;
import com.llm.agents.core.message.parser.Parser;
import com.llm.agents.core.message.parser.AiMessageParser;
import com.llm.agents.core.util.StringUtil;


public class DefaultAiMessageParser implements AiMessageParser {

    private String contentPath;
    private String indexPath;
    private String statusPath;
    private String totalTokensPath;
    private String promptTokensPath;
    private String completionTokensPath;
    private Parser<Object, MessageStatus> statusParser;

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public String getIndexPath() {
        return indexPath;
    }

    public void setIndexPath(String indexPath) {
        this.indexPath = indexPath;
    }

    public String getStatusPath() {
        return statusPath;
    }

    public void setStatusPath(String statusPath) {
        this.statusPath = statusPath;
    }

    public String getTotalTokensPath() {
        return totalTokensPath;
    }

    public void setTotalTokensPath(String totalTokensPath) {
        this.totalTokensPath = totalTokensPath;
    }

    public String getPromptTokensPath() {
        return promptTokensPath;
    }

    public void setPromptTokensPath(String promptTokensPath) {
        this.promptTokensPath = promptTokensPath;
    }

    public String getCompletionTokensPath() {
        return completionTokensPath;
    }

    public void setCompletionTokensPath(String completionTokensPath) {
        this.completionTokensPath = completionTokensPath;
    }

    public Parser<Object, MessageStatus> getStatusParser() {
        return statusParser;
    }

    public void setStatusParser(Parser<Object, MessageStatus> statusParser) {
        this.statusParser = statusParser;
    }

    @Override
    public AiMessage parse(JSONObject rootJson) {
        AiMessage aiMessage = new AiMessage();

        if (StringUtil.hasText(this.contentPath)) {
            aiMessage.setContent((String) JSONPath.eval(rootJson, this.contentPath));
        }

        if (StringUtil.hasText(this.indexPath)) {
            aiMessage.setIndex((Integer) JSONPath.eval(rootJson, this.indexPath));
        }


        if (StringUtil.hasText(promptTokensPath)) {
            aiMessage.setPromptTokens((Integer) JSONPath.eval(rootJson, this.promptTokensPath));
        }

        if (StringUtil.hasText(completionTokensPath)) {
            aiMessage.setCompletionTokens((Integer) JSONPath.eval(rootJson, this.completionTokensPath));
        }

        if (StringUtil.hasText(this.totalTokensPath)) {
            aiMessage.setTotalTokens((Integer) JSONPath.eval(rootJson, this.totalTokensPath));
        }
        //some LLMs like Ollama not response the total tokens
        else if (aiMessage.getPromptTokens() != null && aiMessage.getCompletionTokens() != null) {
            aiMessage.setTotalTokens(aiMessage.getPromptTokens() + aiMessage.getCompletionTokens());
        }

        if (StringUtil.hasText(this.statusPath)) {
            Object statusString = JSONPath.eval(rootJson, this.statusPath);
            if (this.statusParser != null) {
                aiMessage.setStatus(this.statusParser.parse(statusString));
            }
        }


        return aiMessage;
    }
}
