package com.llm.agents.core.message.ai;

import com.llm.agents.core.message.AbstractTextMessage;
import com.llm.agents.core.message.MessageStatus;


/**
 * @Author Devin
 * @Date 2024/11/16 13:50
 * @Description: AI Message回复消息
 **/
public class AiMessage extends AbstractTextMessage {

    private Integer index; //index下标标识
    private MessageStatus status; //标识流式结构状态
    private Integer promptTokens; //prompt token数值
    private Integer completionTokens; //结果 token数值
    private Integer totalTokens; //总token数值
    private String fullContent; //总内容

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public Integer getPromptTokens() {
        return promptTokens;
    }

    public void setPromptTokens(Integer promptTokens) {
        this.promptTokens = promptTokens;
    }

    public Integer getCompletionTokens() {
        return completionTokens;
    }

    public void setCompletionTokens(Integer completionTokens) {
        this.completionTokens = completionTokens;
    }

    public Integer getTotalTokens() {
        return totalTokens;
    }

    public void setTotalTokens(Integer totalTokens) {
        this.totalTokens = totalTokens;
    }

    public String getFullContent() {
        return fullContent;
    }

    public void setFullContent(String fullContent) {
        this.fullContent = fullContent;
    }

    @Override
    public Object getMessageContent() {
        return getFullContent();
    }

    @Override
    public String toString() {
        return "AiMessage{" +
                "index=" + index +
                ", status=" + status +
                ", totalTokens=" + totalTokens +
                ", fullContent='" + fullContent + '\'' +
                ", content='" + content + '\'' +
                ", metadataMap=" + metaDataMap +
                '}';
    }
}
