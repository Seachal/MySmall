package com.laka.libnet.gson.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @Author:summer
 * @Date:2019/7/16
 * @Description:
 */
public class BooleanDefaultFormatAdapter extends TypeAdapter<Boolean> {

    private static final String TAG = "BooleanDefaultFormatAda";

    @Override
    public void write(JsonWriter out, Boolean value) throws IOException {
        if (value == null) {
            value = false;
        }
        out.value(value);
    }

    @Override
    public Boolean read(JsonReader in) throws IOException {
        Boolean value;
        try {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                value = false;
            } else if (in.peek() == JsonToken.STRING) {
                String jsonStr = in.nextString();
                if (jsonStr.toLowerCase().equals("true")) {
                    value = true;
                } else {
                    value = false;
                }
            } else {
                value = in.nextBoolean();
            }
        } catch (Exception e) {
            e.printStackTrace();
            value = false;
        }
        return value;
    }
}
