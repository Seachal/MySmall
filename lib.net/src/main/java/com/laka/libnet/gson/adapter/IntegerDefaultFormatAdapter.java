package com.laka.libnet.gson.adapter;

import android.text.TextUtils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @Author:summer
 * @Date:2019/7/16
 * @Description:Gson默认Integer转换类，假若后台的数字字段返回了null,"",boolean .
 * 通过当前的解析器转换为默认值0
 */
public class IntegerDefaultFormatAdapter extends TypeAdapter<Integer> {

    private static final String TAG = "IntegerDefaultFormatAda";

    @Override
    public void write(JsonWriter out, Integer value) throws IOException {
        if (value == null) {
            value = 0;
        }
        out.value(value);
    }

    @Override
    public Integer read(JsonReader in) throws IOException {
        Integer value;
        try {
            if (in.peek() == JsonToken.NULL) {
                // 假若返回Null，默认为0
                in.nextNull();
                value = 0;
            } else if (in.peek() == JsonToken.BOOLEAN) {
                in.nextBoolean();
                value = 0;
            } else if (in.peek() == JsonToken.STRING) {
                String jsonStr = in.nextString();
                value = isNumber(jsonStr) ? Integer.parseInt(jsonStr) : 0;
            } else {
                value = in.nextInt();
            }
        } catch (Exception e) {
            e.printStackTrace();
            value = 0;
        }
        return value;
    }

    public static boolean isNumber(String string) {
        return TextUtils.isDigitsOnly(string);
    }

}
