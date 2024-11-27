package com.llm.qwen;

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
    public void addition_isCorrect() throws InterruptedException {
        QwenLLmConfig config = new QwenLLmConfig();
        config.setApiKey("sk-3b52d74fcbc94c9191311e0678a826af");
        config.setModel("qwen-turbo");

        LLM llm = new QwenLLm(config);
        llm.chatStream("请写一个小兔子战胜大灰狼的故事", (context, response) -> {
            AiMessage message = response.getMessage();
            System.out.println(">>>> " + message.getContent());
        });
//        String chat = llm.chat("你叫什么名字？");
//        System.out.println(chat);
        Thread.sleep(10000);
    }
}