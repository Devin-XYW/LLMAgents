package com.llm.llmagents.llmtest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.llm.agents.core.llm.ChatContext;
import com.llm.agents.core.llm.LLM;
import com.llm.agents.core.llm.MessageResponse;
import com.llm.agents.core.llm.StreamResponseListener;
import com.llm.agents.core.llm.response.AiMessageResponse;
import com.llm.agents.core.llm.response.FunctionMessageResponse;
import com.llm.agents.core.message.ai.AiMessage;
import com.llm.agents.core.prompt.FunctionPrompt;
import com.llm.agents.core.prompt.TextPrompt;
import com.llm.chatglm.ChatglmLlm;
import com.llm.chatglm.ChatglmLlmConfig;
import com.llm.deepseek.DeepSeekLLm;
import com.llm.deepseek.DeepSeekLLmConfig;
import com.llm.llmagents.R;
import com.llm.llmagents.llmtest.function.WeatherUtil;
import com.llm.llmagents.util.KeyUtil;
import com.llm.llmagents.util.PermissionUtils;
import com.llm.qwen.QwenLLm;
import com.llm.qwen.QwenLLmConfig;
import com.llm.saprk.SparkLlm;
import com.llm.saprk.SparkLlmConfig;

public class LLMTestActivity extends AppCompatActivity {
    private final static String TAG = "LLMTestActivity";
    private EditText mEditText;
    private Button mQueryQwen;
    private TextView mResultView;

    private Handler handler = new Handler(Looper.getMainLooper());

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llmtest);

        PermissionUtils.requestReadExternalStoragePermission(this);

        mEditText = findViewById(R.id.queryEdit);
        mQueryQwen = findViewById(R.id.qwenQuery);
        mResultView = findViewById(R.id.result);

     }

    @Override
    protected void onStart() {
        super.onStart();

        mQueryQwen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryQwen();
            }
        });

        findViewById(R.id.qwenQueryHttp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryQwenHttp();
            }
        });

        findViewById(R.id.qwenFunction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                useFunctionQwen();
            }
        });

        findViewById(R.id.sparkQuery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                querySpark();
            }
        });
        findViewById(R.id.sparkQueryHttp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                querySparkHttp();
            }
        });

        findViewById(R.id.sparkFunction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                useFunctionSpark();
            }
        });

        findViewById(R.id.chatglmQuery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryChatglm();
            }
        });

        findViewById(R.id.chatglmQueryHttp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryChatglmHttp();
            }
        });

        findViewById(R.id.chatglmFunction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                useFunctionChatglm();
            }
        });

        findViewById(R.id.deepSeekQuery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryDeepSeekLLm();
            }
        });

        findViewById(R.id.deepSeekQueryHttp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryDeepSeekLLmHttp();
            }
        });

    }

    private StreamResponseListener mListener = new StreamResponseListener() {
        @Override
        public void onStart(ChatContext context) {
            Log.i(TAG,"Listener onStart");
        }

        @Override
        public void onMessage(ChatContext context, MessageResponse response) {
            AiMessage message = response.getMessage();
            Log.i(TAG,"Listener onMessage message="+message);
            String result = message.getFullContent();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mResultView.setText(result);
                }
            });
        }

        @Override
        public void onStop(ChatContext context) {
            Log.i(TAG,"Listener onStop");
        }

        @Override
        public void onFailure(ChatContext context, Throwable throwable) {
            Log.i(TAG,"Listener onFailure throwable="+throwable.getMessage());
            mResultView.post(new Runnable() {
                @Override
                public void run() {
                    mResultView.setText(throwable.getMessage());
                }
            });
        }
    };

    private void queryQwen(){
        QwenLLmConfig config = new QwenLLmConfig();
        config.setApiKey(KeyUtil.getInstance().getKeyObject().keys.get("qwen").apiKey);
        config.setModel("qwen-turbo");
        LLM llm = new QwenLLm(config);

        String query = mEditText.getText().toString();
        TextPrompt prompt = new TextPrompt(query);

        llm.chatStream(prompt, mListener);
    }

    private void queryQwenHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                QwenLLmConfig config = new QwenLLmConfig();
                config.setApiKey(KeyUtil.getInstance().getKeyObject().keys.get("qwen").apiKey);
                config.setModel("qwen-turbo");
                LLM llm = new QwenLLm(config);

                String query = mEditText.getText().toString();
                TextPrompt prompt = new TextPrompt(query);

                AiMessageResponse result = llm.chat(prompt);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (result != null){
                            mResultView.setText(result.toString());
                        }else {
                            mResultView.setText("未查询到相关结果");
                        }
                    }
                });
            }
        }).start();
    }

    private void useFunctionQwen(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                QwenLLmConfig config = new QwenLLmConfig();
                config.setApiKey(KeyUtil.getInstance().getKeyObject().keys.get("qwen").apiKey);
                config.setModel("qwen-turbo");
                LLM llm = new QwenLLm(config);

                String query = mEditText.getText().toString();

                FunctionPrompt prompt = new FunctionPrompt(query, WeatherUtil.class);
                FunctionMessageResponse response = llm.chat( prompt);
                Log.i(TAG,"useFunctionQwen response="+response);
                Object result = response.getFunctionResult();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (result != null){
                            mResultView.setText(result.toString());
                        }else {
                            mResultView.setText("未查询到相关结果");
                        }
                    }
                });
            }
        }).start();
    }

    private void querySpark(){
        SparkLlmConfig config = new SparkLlmConfig();
        config.setAppId(KeyUtil.getInstance().getKeyObject().keys.get("spark").appId);
        config.setApiKey(KeyUtil.getInstance().getKeyObject().keys.get("spark").apiKey);
        config.setApiSecret(KeyUtil.getInstance().getKeyObject().keys.get("spark").apiSecret);

        LLM llm = new SparkLlm(config);

        String query = mEditText.getText().toString();
        TextPrompt prompt = new TextPrompt(query);

        llm.chatStream(prompt, mListener);

    }

    private void querySparkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SparkLlmConfig config = new SparkLlmConfig();
                config.setAppId(KeyUtil.getInstance().getKeyObject().keys.get("spark").appId);
                config.setApiKey(KeyUtil.getInstance().getKeyObject().keys.get("spark").apiKey);
                config.setApiSecret(KeyUtil.getInstance().getKeyObject().keys.get("spark").apiSecret);

                LLM llm = new SparkLlm(config);

                String query = mEditText.getText().toString();
                TextPrompt prompt = new TextPrompt(query);

                AiMessageResponse result = llm.chat(prompt);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (result != null){
                            mResultView.setText(result.toString());
                        }else {
                            mResultView.setText("未查询到相关结果");
                        }
                    }
                });
            }
        }).start();
    }

    private void useFunctionSpark(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SparkLlmConfig config = new SparkLlmConfig();
                config.setAppId(KeyUtil.getInstance().getKeyObject().keys.get("spark").appId);
                config.setApiKey(KeyUtil.getInstance().getKeyObject().keys.get("spark").apiKey);
                config.setApiSecret(KeyUtil.getInstance().getKeyObject().keys.get("spark").apiSecret);

                LLM llm = new SparkLlm(config);

                String query = mEditText.getText().toString();

                FunctionPrompt prompt = new FunctionPrompt(query, WeatherUtil.class);
                FunctionMessageResponse response = llm.chat( prompt);
                Log.i(TAG,"useFunctionQwen response="+response);
                Object result = response.getFunctionResult();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (result != null){
                            mResultView.setText(result.toString());
                        }else {
                            mResultView.setText("未查询到相关结果");
                        }
                    }
                });
            }
        }).start();
    }

    private void queryChatglm(){
        ChatglmLlmConfig config = new ChatglmLlmConfig();
        config.setApiKey(KeyUtil.getInstance().getKeyObject().keys.get("chatglm").apiKey);

        LLM llm = new ChatglmLlm(config);

        String query = mEditText.getText().toString();
        TextPrompt prompt = new TextPrompt(query);

        llm.chatStream(prompt, mListener);

    }

    private void queryChatglmHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ChatglmLlmConfig config = new ChatglmLlmConfig();
                config.setApiKey(KeyUtil.getInstance().getKeyObject().keys.get("chatglm").apiKey);

                LLM llm = new ChatglmLlm(config);

                String query = mEditText.getText().toString();
                TextPrompt prompt = new TextPrompt(query);

                AiMessageResponse result = llm.chat(prompt);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (result != null){
                            mResultView.setText(result.toString());
                        }else {
                            mResultView.setText("未查询到相关结果");
                        }
                    }
                });
            }
        }).start();
    }

    private void useFunctionChatglm(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ChatglmLlmConfig config = new ChatglmLlmConfig();
                config.setApiKey(KeyUtil.getInstance().getKeyObject().keys.get("chatglm").apiKey);

                LLM llm = new ChatglmLlm(config);

                String query = mEditText.getText().toString();

                FunctionPrompt prompt = new FunctionPrompt(query, WeatherUtil.class);
                FunctionMessageResponse response = llm.chat( prompt);
                Log.i(TAG,"useFunctionQwen response="+response);
                Object result = response.getFunctionResult();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (result != null){
                            mResultView.setText(result.toString());
                        }else {
                            mResultView.setText("未查询到相关结果");
                        }
                    }
                });
            }
        }).start();
    }

    private void queryDeepSeekLLm(){
        DeepSeekLLmConfig config = new DeepSeekLLmConfig();
        config.setApiKey(KeyUtil.getInstance().getKeyObject().keys.get("deepseek").apiKey);

        LLM llm = new DeepSeekLLm(config);

        String query = mEditText.getText().toString();
        TextPrompt prompt = new TextPrompt(query);

        llm.chatStream(prompt, mListener);

    }

    private void queryDeepSeekLLmHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                DeepSeekLLmConfig config = new DeepSeekLLmConfig();
                config.setApiKey(KeyUtil.getInstance().getKeyObject().keys.get("deepseek").apiKey);

                LLM llm = new DeepSeekLLm(config);

                String query = mEditText.getText().toString();
                TextPrompt prompt = new TextPrompt(query);

                AiMessageResponse result = llm.chat(prompt);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (result != null){
                            mResultView.setText(result.toString());
                        }else {
                            mResultView.setText("未查询到相关结果");
                        }
                    }
                });
            }
        }).start();
    }
}