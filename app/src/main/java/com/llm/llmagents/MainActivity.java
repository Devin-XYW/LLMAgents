package com.llm.llmagents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.llm.llmagents.agenttest.AgentActivity;
import com.llm.llmagents.llmtest.LLMTestActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.cloud).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LLMTestActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.agent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AgentActivity.class);
                startActivity(intent);
            }
        });
    }
}