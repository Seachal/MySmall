package com.laka.libnet.gson.factory;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.laka.libnet.gson.adapter.BooleanDefaultFormatAdapter;
import com.laka.libnet.gson.adapter.DoubleDefaultFormatAdapter;
import com.laka.libnet.gson.adapter.FloatDefaultFormatAdapter;
import com.laka.libnet.gson.adapter.IntegerDefaultFormatAdapter;
import com.laka.libnet.gson.adapter.LongDefaultFormatAdapter;
import com.laka.libnet.gson.adapter.StringDefaultFormatAdapter;

/**
 * @Author:summer
 * @Date:2019/7/16
 * @Description:GSON针对后端Number字段,String字段，null转换工厂类
 */
public class GsonFormatTypeAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();
        if (rawType == Integer.class || rawType == int.class) {
            return (TypeAdapter<T>) new IntegerDefaultFormatAdapter();
        } else if (rawType == Long.class || rawType == long.class) {
            return (TypeAdapter<T>) new LongDefaultFormatAdapter();
        } else if (rawType == Double.class || rawType == double.class) {
            return (TypeAdapter<T>) new DoubleDefaultFormatAdapter();
        } else if (rawType == Float.class || rawType == float.class) {
            return (TypeAdapter<T>) new FloatDefaultFormatAdapter();
        } else if (rawType == String.class) {
            return (TypeAdapter<T>) new StringDefaultFormatAdapter();
        } else if (rawType == Boolean.class || rawType == boolean.class) {
            return (TypeAdapter<T>) new BooleanDefaultFormatAdapter();
        } else {
            return null;//返回null，由其他Factory创建TypeAdapter
        }
    }
}