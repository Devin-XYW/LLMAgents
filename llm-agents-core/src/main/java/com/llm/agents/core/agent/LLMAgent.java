package com.llm.agents.core.agent;

import com.llm.agents.core.chain.Chain;
import com.llm.agents.core.llm.ChatOptions;
import com.llm.agents.core.llm.LLM;
import com.llm.agents.core.llm.response.AiMessageResponse;
import com.llm.agents.core.message.ai.AiMessage;
import com.llm.agents.core.prompt.TextPrompt;
import com.llm.agents.core.prompt.template.TextPromptTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author Devin
 * @Date 2024/11/24 16:21
 * @Description: 大模型LLMAgent，其中包含大模型引擎，调用大模型能力
 **/
public class LLMAgent extends Agent{

    protected LLM llm;//Agent中的LLM大模型引擎
    protected ChatOptions chatOptions = ChatOptions.DEFAULT; //大模型配置config选项
    protected String prompt; //Prompt字段
    protected TextPromptTemplate promptTemplate; //Prompt配置器

    public LLMAgent(){

    }

    public LLMAgent(LLM llm, String prompt) {
        this.llm = llm;
        this.prompt = prompt;
        this.promptTemplate = new TextPromptTemplate(prompt);
    }

    public LLM getLlm() {
        return llm;
    }

    public void setLlm(LLM llm) {
        this.llm = llm;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
        this.promptTemplate = new TextPromptTemplate(prompt);
    }

    public ChatOptions getChatOptions(){
        return chatOptions;
    }

    public void setChatOptions(ChatOptions chatOptions){
        if(chatOptions == null){
            chatOptions = ChatOptions.DEFAULT;
        }
        this.chatOptions = chatOptions;
    }

    @Override
    protected List<Parameter> defineInputParameter() {
        if (this.promptTemplate == null) {
            return null;
        }

        Set<String> keys = this.promptTemplate.getKeys();
        if (keys == null || keys.isEmpty()) {
            return null;
        }

        List<Parameter> parameters = new ArrayList<>(keys.size());
        for (String key : keys) {
            Parameter parameter = new Parameter(key, true);
            parameters.add(parameter);
        }
        return parameters;
    }

    @Override
    public Output execute(Map<String, Object> variables, Chain chain) {
        //将Prompt可变参数进行拼接
        TextPrompt textPrompt = promptTemplate.format(variables);
        //调用大模型引擎获取结果response
        AiMessageResponse response = llm.chat(textPrompt,chatOptions);

        //将response结果存放到chain中
        if(chain != null){
            chain.output(this,response);
        }

        return response.isError()
                ? onError(response, chain)
                : onMessage(response.getMessage());
    }

    protected Output onError(AiMessageResponse response, Chain chain) {
        if (chain != null) {
            chain.stopError(response.getErrorMessage());
        }
        return null;
    }

    protected Output onMessage(AiMessage aiMessage) {
        List<OutputKey> outputKeys = getOutputKeys();
        if (outputKeys != null && outputKeys.size() == 1) {
            return Output.of(outputKeys.get(0), aiMessage.getContent());
        }

        return Output.ofDefault(aiMessage.getContent());
    }

    @Override
    public String toString() {
        return "LLMAgent{" +
                "llm=" + llm +
                ", prompt='" + prompt + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
