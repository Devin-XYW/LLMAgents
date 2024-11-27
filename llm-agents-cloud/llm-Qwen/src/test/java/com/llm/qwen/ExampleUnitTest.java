package com.llm.qwen;

import org.junit.Test;
import com.llm.agents.core.chain.Chain;
import com.llm.agents.core.chain.ChainEvent;
import com.llm.agents.core.chain.ChainEventListener;
import com.llm.agents.core.chain.impl.SequentialChain;
import com.llm.agents.core.functions.Parameter;
import com.llm.agents.core.llm.LLM;
import com.llm.agents.core.message.ai.AiMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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

    @Test
    public void testChain(){
        SimpleAgent1 agent1 = new SimpleAgent1();
        SimpleAgent2 agent2 = new SimpleAgent2();

        Chain chain = new SequentialChain();
        chain.addNode(agent1);
        chain.addNode(agent2);
        chain.registerEventListener(new ChainEventListener() {
            @Override
            public void onEvent(ChainEvent event, Chain chain) {
                System.out.println(event);
            }
        });

        chain.execute(new HashMap<>());

        for (Map.Entry<String, Object> entry : chain.getMemory().getAll().entrySet()) {
            System.out.println("执行结果" + entry);
        }
    }
}