package com.laka.libnet.gson.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @Author:summer
 * @Date:2019/7/16
 * @Description:Gson默认Long转换类，假若后台的数字字段返回了null,"",boolean .
 * 通过当前的解析器转换为默认值0L
 */
public class LongDefaultFormatAdapter extends TypeAdapter<Long> {

    private static final String TAG = "LongDefaultFormatAdapte";

    @Override
    public void write(JsonWriter out, Long value) throws IOException {
        if (value == null) {
            value = 0L;
        }
        out.value(value);
    }

    @Override
    public Long read(JsonReader in) throws IOException {
        Long value;
        try {
            if (in.peek() == JsonToken.NULL) {
                // 假若返回Null，默认为0L
                in.nextNull();
                value = 0L;
            } else if (in.peek() == JsonToken.BOOLEAN) {
                in.nextBoolean();
                value = 0L;
            } else if (in.peek() == JsonToken.STRING) {
                String jsonStr = in.nextString();
                value = Long.parseLong(jsonStr);
            } else {
                value = in.nextLong();
            }
            //LogUtils.info(GsonUtils.TAG, "进入Long格式转换类：" + value);
        } catch (Exception e) {
            e.printStackTrace();
            value = 0L;
        }
        return value;
    }
}
