package com.llm.llmagents.agenttest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.opengl.GLDebugHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.llm.agents.core.agent.Agent;
import com.llm.agents.core.agent.Output;
import com.llm.agents.core.chain.Chain;
import com.llm.agents.core.chain.ChainEvent;
import com.llm.agents.core.chain.ChainEventListener;
import com.llm.agents.core.chain.ChainOutputListener;
import com.llm.agents.core.chain.impl.ParallelChain;
import com.llm.agents.core.chain.impl.SequentialChain;
import com.llm.agents.core.llm.LLM;
import com.llm.llmagents.R;
import com.llm.llmagents.agenttest.agent.llmAgent.ChatLLmAgent;
import com.llm.llmagents.agenttest.agent.sqlAgent.SQLCodeLlmAgent;
import com.llm.llmagents.agenttest.agent.sqlAgent.SQLTableLlmAgent;
import com.llm.llmagents.util.KeyUtil;
import com.llm.qwen.QwenLLm;
import com.llm.qwen.QwenLLmConfig;

import java.util.HashMap;
import java.util.Map;

public class AgentActivity extends AppCompatActivity {

    private final static String TAG = "AgentActivity";
    private EditText mEditText;
    private TextView mResultView;

    private Handler handler = new Handler(Looper.getMainLooper());

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);

        mEditText = findViewById(R.id.queryEditAgent);
        mResultView = findViewById(R.id.resultAgent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        findViewById(R.id.chatTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testChatGptAgent();
            }
        });

        findViewById(R.id.sqlAgentTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testSQLAgent();
            }
        });

        findViewById(R.id.sqlAgentChainTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testSQLAgentChain();
            }
        });

        findViewById(R.id.chatAgentTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                syncChatAgentChain();
            }
        });
    }

    private void testChatGptAgent(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                QwenLLmConfig config = new QwenLLmConfig();
                config.setApiKey(KeyUtil.getInstance().getKeyObject().keys.get("qwen").apiKey);
                config.setModel("qwen-turbo");
                LLM llm = new QwenLLm(config);

                ChatLLmAgent chatLLmAgent = new ChatLLmAgent(llm);
                Map<String, Object> variables = new HashMap<>();
                variables.put("askText", mEditText.getText().toString());

                SequentialChain chain = new SequentialChain();
                chain.addNode(chatLLmAgent);

                chain.registerOutputListener(new ChainOutputListener() {
                    @Override
                    public void onOutput(Chain chain, Agent agent, Object outputMessage) {
                        String result = outputMessage.toString();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mResultView.setText(result);
                            }
                        });
                    }
                });

                chain.execute(variables);

            }
        }).start();

    }

    private void testSQLAgent(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                QwenLLmConfig config = new QwenLLmConfig();
                config.setApiKey(KeyUtil.getInstance().getKeyObject().keys.get("qwen").apiKey);
                config.setModel("qwen-turbo");
                LLM llm = new QwenLLm(config);

                SQLTableLlmAgent agent = new SQLTableLlmAgent(llm);

                String ddlInfo = "表名 student，字段 id,name,age,club,data";
                String index = "id";

                Map<String, Object> variables = new HashMap<>();
                variables.put("ddlInfo", ddlInfo);
                variables.put("index",index);

                Output output = agent.execute(variables, null);

                String result = output.get(SQLTableLlmAgent.resultKey).toString();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (result != null){
                            mResultView.setText(result);
                        }else {
                            mResultView.setText("未查询到相关结果");
                        }
                    }
                });
            }
        }).start();
    }

    public void testSQLAgentChain(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                QwenLLmConfig config = new QwenLLmConfig();
                config.setApiKey(KeyUtil.getInstance().getKeyObject().keys.get("qwen").apiKey);
                config.setModel("qwen-turbo");
                LLM llm = new QwenLLm(config);

                SQLTableLlmAgent sqlTableAgent = new SQLTableLlmAgent(llm);
                SQLCodeLlmAgent sqlCodeAgent = new SQLCodeLlmAgent(llm);

                SequentialChain chain = new SequentialChain();
                chain.addNode(sqlTableAgent);
                chain.addNode(sqlCodeAgent);

                String ddlInfo = "表名 student，字段 id,name,age,club,data";
                String index = "id";
                String option = "查询表中年龄为18的学生id";

                Map<String, Object> variables = new HashMap<>();
                variables.put("ddlInfo", ddlInfo);
                variables.put("index",index);
                variables.put("option",option);

                chain.registerEventListener(new ChainEventListener() {
                    @Override
                    public void onEvent(ChainEvent event, Chain chain) {
                        Log.i(TAG,"testSQLAgentChain onEvent="+event);
                    }
                });

                chain.registerOutputListener(new ChainOutputListener() {
                    @Override
                    public void onOutput(Chain chain, Agent onOutput, Object outputMessage) {
                        Log.i(TAG,"testSQLAgentChain onOutput="+onOutput+",outputMessage="+outputMessage);
                    }
                });

                chain.execute(variables);

                String result = chain.getMemory().get(SQLCodeLlmAgent.resultKey).toString();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (result != null){
                            mResultView.setText(result);
                        }else {
                            mResultView.setText("未查询到相关结果");
                        }
                    }
                });
            }
        }).start();

    }

    private void syncChatAgentChain(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                QwenLLmConfig config = new QwenLLmConfig();
                config.setApiKey(KeyUtil.getInstance().getKeyObject().keys.get("qwen").apiKey);
                config.setModel("qwen-turbo");
                LLM llm = new QwenLLm(config);

                ChatLLmAgent agent1 = new ChatLLmAgent(llm);
                ChatLLmAgent agent2 = new ChatLLmAgent(llm);

                ParallelChain parallelChain = new ParallelChain();
                parallelChain.addNode(agent1);
                parallelChain.addNode(agent2);

                Map<String, Object> variables = new HashMap<>();
                variables.put("askText", mEditText.getText().toString());

                parallelChain.registerEventListener(new ChainEventListener() {
                    @Override
                    public void onEvent(ChainEvent event, Chain chain) {
                        Log.i(TAG,"testSQLAgentChain onEvent="+event);
                    }
                });
                StringBuilder result = new StringBuilder();

                parallelChain.registerOutputListener(new ChainOutputListener() {
                    @Override
                    public void onOutput(Chain chain, Agent onOutput, Object outputMessage) {
                        Log.i(TAG,"testSQLAgentChain onOutput="+onOutput+",outputMessage="+outputMessage);
                        result.append(outputMessage.toString());
                        result.append("\n");
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (result != null){
                                    mResultView.setText(result);
                                }else {
                                    mResultView.setText("未查询到相关结果");
                                }
                            }
                        });
                    }
                });

                parallelChain.execute(variables);


            }
        }).start();
    }

}