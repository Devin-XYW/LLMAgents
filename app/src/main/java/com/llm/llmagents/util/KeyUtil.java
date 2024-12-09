package com.llm.llmagents.util;

import android.os.Environment;
import android.util.Log;

import com.llm.llmagents.bean.KeyObject;
import com.llm.llmagents.bean.LLMKeys;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
 * @Author Devin
 * @Date 2024/12/9 21:27
 * @Description:
 **/
public class KeyUtil {

    private LLMKeys mKeys = new LLMKeys();

    private KeyUtil(){}

    private static class SingletonInstance{
        private static final KeyUtil instance = new KeyUtil();
    }

    public static final KeyUtil getInstance(){
        return SingletonInstance.instance;
    }

    public LLMKeys getKeyObject(){
        if(!mKeys.keys.isEmpty()){
            return mKeys;
        }

        File sdCard = Environment.getExternalStorageDirectory();
        File data = new File(sdCard,"llmkey/key.json");

        try {
            FileReader fileReader = new FileReader(data);
            BufferedReader reader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                stringBuilder.append(line);
            }
            reader.close();
            fileReader.close();
            String jsonData = stringBuilder.toString();

            JSONObject jsonObject = new JSONObject(jsonData);
            Iterator<String> jsonKeys = jsonObject.keys();
            while (jsonKeys.hasNext()){
                String key = jsonKeys.next();
                String valueObject = jsonObject.getString(key);
                JSONObject keyJsonObject = new JSONObject(valueObject);
                KeyObject keyObject = new KeyObject();
                keyObject.apiKey = keyJsonObject.getString("apiKey");
                keyObject.appId = keyJsonObject.getString("appId");
                keyObject.apiSecret = keyJsonObject.getString("apiSecret");
                mKeys.keys.put(key,keyObject);
            }

        }catch (Exception e){
            Log.e("KeyUtil","file not found");
        }
        return mKeys;
    }
    
}
