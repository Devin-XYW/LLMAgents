package com.llm.agents.core.prompt;

import com.llm.agents.core.functions.Function;
import com.llm.agents.core.functions.Functions;
import com.llm.agents.core.llm.response.FunctionMessageResponse;
import com.llm.agents.core.memory.ChatMemory;
import com.llm.agents.core.memory.DefaultChatMemory;
import com.llm.agents.core.message.Message;
import com.llm.agents.core.message.human.HumanMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author Devin
 * @Date 2024/11/17 13:36
 * @Description:
 **/
public class FunctionPrompt extends Prompt<FunctionMessageResponse>{
    private final ChatMemory memory = new DefaultChatMemory();
    private final List<Function> functions = new ArrayList<>();

    public FunctionPrompt(String prompt, Collection<Function> functions) {
        memory.addMessage(new HumanMessage(prompt));
        this.functions.addAll(functions);
    }

    public FunctionPrompt(List<Message> messages, Collection<Function> functions) {
        memory.addMessages(messages);
        this.functions.addAll(functions);
    }

    public FunctionPrompt(String prompt, Class<?> funcClass, String... methodNames) {
        memory.addMessage(new HumanMessage(prompt));
        functions.addAll(Functions.from(funcClass, methodNames));
    }

    public FunctionPrompt(List<Message> messages, Class<?> funcClass, String... methodNames) {
        memory.addMessages(messages);
        functions.addAll(Functions.from(funcClass, methodNames));
    }

    public FunctionPrompt(String prompt, Object funcObject, String... methodNames) {
        memory.addMessage(new HumanMessage(prompt));
        functions.addAll(Functions.from(funcObject, methodNames));
    }

    public FunctionPrompt(List<Message> messages, Object funcObject, String... methodNames) {
        memory.addMessages(messages);
        functions.addAll(Functions.from(funcObject, methodNames));
    }

    @Override
    public List<Message> toMessages() {
        return memory.getMessages();
    }

    public List<Function> getFunctions() {
        return functions;
    }

}
