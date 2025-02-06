package com.llm.deepseek;

import org.junit.Test;

import static org.junit.Assert.*;

import com.llm.agents.core.llm.LLM;
import com.llm.agents.core.message.ai.AiMessage;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        DeepSeekLLmConfig config = new DeepSeekLLmConfig();
        config.setApiKey("***");
        config.setModel("deepseek-chat");

        LLM llm = new DeepSeekLLm(config);
        llm.chatStream("请写一个小兔子战胜大灰狼的故事", (context, response) -> {
            AiMessage message = response.getMessage();
            System.out.println(">>>> " + message.getContent());
        });
    }
}