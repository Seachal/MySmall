package com.laka.libnet.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.internal.ConstructorConstructor;
import com.laka.libnet.anno.AnnotationExclusion;
import com.laka.libnet.gson.factory.CollectionDefaultTypeAdapterFactory;
import com.laka.libnet.gson.factory.GsonFormatTypeAdapterFactory;
import com.laka.libnet.gson.factory.DeserializeActionAdapterFactory;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author:summer
 * @Date:2019/7/16
 * @Description:Gson工具类
 */
public class GsonUtils implements IParseUtil {

    public static final String TAG = "GsonUtils";

    private static Gson sGson;

    /**
     * description:创建GSON的规则，String默认null转空字符串
     * Gson默认使用Expose注解，集合为null时解析为长度为0的集合
     **/
    static {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapterFactory(new GsonFormatTypeAdapterFactory())
                .registerTypeAdapterFactory(new DeserializeActionAdapterFactory())
                .setExclusionStrategies(new AnnotationExclusion())
                .addSerializationExclusionStrategy(new AnnotationExclusion())
                .addDeserializationExclusionStrategy(new AnnotationExclusion())
                .serializeNulls();
        //通过反射获取instanceCreators属性
        try {
            Class builder = gsonBuilder.getClass();
            Field f = builder.getDeclaredField("instanceCreators");
            f.setAccessible(true);
            Map<Type, InstanceCreator<?>> val = (Map<Type, InstanceCreator<?>>) f.get(gsonBuilder);//得到此属性的值
            //注册数组的处理器
            gsonBuilder.registerTypeAdapterFactory(new CollectionDefaultTypeAdapterFactory(new ConstructorConstructor(val)));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        sGson = gsonBuilder.create();
    }

    @Override
    public <T> T parseJson(String json, Class<T> tClass) {
        return GsonUtils.convertJson2Bean(json, tClass);
    }

    @Override
    public <T> T parseJson(String json, Type type) {
        return GsonUtils.convertJson2Bean(json, type);
    }

    @Override
    public String toJson(Object object) {
        return GsonUtils.convertObject2Json(object);
    }

    /**
     * Converts a json string into a T bean.
     */
    public static <T> T convertJson2Bean(String json, Class<T> tClass) {
        return sGson.fromJson(json, tClass);
    }

    /**
     * Converts a json string into a T bean.
     */
    public static <T> T convertJson2Bean(String json, Type type) {
        return sGson.fromJson(json, type);
    }

    /**
     * Converts an object into a string.
     */
    public static String convertObject2Json(Object object) {
        return sGson.toJson(object);
    }


    @Override
    public <T> List<T> parseJsonToList(String json, String key, final Class<T> clazz) {

        try {
            Type type = new ParameterizedType() {
                @Override
                public Type getRawType() {
                    return ArrayList.class;
                }

                @Override
                public Type getOwnerType() {
                    return null;
                }

                @Override
                public Type[] getActualTypeArguments() {
                    Type[] type = new Type[1];
                    type[0] = clazz;
                    return type;
                }
            };

            String data = new JSONObject(json).optString(key);
            return sGson.fromJson(data, type);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public <T> List<T> parseJsonToList(String json, final Class<T> clazz) {

        try {
            Type type = new ParameterizedType() {
                @Override
                public Type getRawType() {
                    return ArrayList.class;
                }

                @Override
                public Type getOwnerType() {
                    return null;
                }

                @Override
                public Type[] getActualTypeArguments() {
                    Type[] type = new Type[1];
                    type[0] = clazz;
                    return type;
                }
            };
            return sGson.fromJson(json, type);
        } catch (Exception e) {
//            LogUtils.log("parse=" + e.toString());
            return null;
        }

    }

    @Override
    public <T> T parseJsonToType(String json, Type typeOfT) {
        try {
            return sGson.fromJson(json, typeOfT);
        } catch (Exception e) {
            return null;
        }
    }

    public static Gson getDefaultGson() {
        return sGson;
    }
}