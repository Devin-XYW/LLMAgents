package com.llm.llmagents.toolstest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.llm.llmagents.R;
import com.llm.llmagents.util.FileUtils;

import java.io.InputStream;

public class ToolsTestActivity extends AppCompatActivity {

    private TextView mResultView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools_test);
        mResultView = findViewById(R.id.resultTools);
        findViewById(R.id.poiDocTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testParserDoc();
            }
        });
    }

    private void testParserDoc(){
        InputStream inputStream = FileUtils.getInputStreamFromAssets(this.getApplicationContext(),"a.doc");
        if(inputStream != null){
//            PoiDocumentParser parser = new PoiDocumentParser();
//            Document document = parser.parse(inputStream);
//            mResultView.setText(document.toString());
        }
    }
}