package com.llm.llmagents.sub;

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
import com.llm.agents.core.llm.response.FunctionMessageResponse;
import com.llm.agents.core.message.ai.AiMessage;
import com.llm.agents.core.prompt.FunctionPrompt;
import com.llm.agents.core.prompt.Prompt;
import com.llm.agents.core.prompt.TextPrompt;
import com.llm.llmagents.R;
import com.llm.llmagents.function.WeatherUtil;
import com.llm.qwen.QwenLLm;
import com.llm.qwen.QwenLLmConfig;

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

        findViewById(R.id.qwenFunction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                useFunctionQwen();
            }
        });

    }

    private StreamResponseListener mQwenListener = new StreamResponseListener() {
        @Override
        public void onStart(ChatContext context) {
            Log.i(TAG,"QwenListener onStart");
        }

        @Override
        public void onMessage(ChatContext context, MessageResponse response) {
            AiMessage message = response.getMessage();
            Log.i(TAG,"QwenListener onMessage message="+message);
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
            Log.i(TAG,"QwenListener onStop");
        }

        @Override
        public void onFailure(ChatContext context, Throwable throwable) {
            Log.i(TAG,"QwenListener onFailure throwable="+throwable.getMessage());
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
        config.setApiKey("sk-3b52d74fcbc94c9191311e0678a826af");
        config.setModel("qwen-turbo");
        LLM llm = new QwenLLm(config);

        String query = mEditText.getText().toString();
        TextPrompt prompt = new TextPrompt(query);

        llm.chatStream(prompt,mQwenListener);
    }

    private void useFunctionQwen(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                QwenLLmConfig config = new QwenLLmConfig();
                config.setApiKey("sk-3b52d74fcbc94c9191311e0678a826af");
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
}