package com.laka.libnet.gson.factory;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.laka.libnet.exception.ApiException;
import com.laka.libnet.gson.action.IAfterDeserializeAction;
import com.laka.libnet.gson.action.IDataValidateAction;

import java.io.IOException;

/**
 * @Author:summer
 * @Date:2019/7/16
 * @Description:
 */
public class DeserializeActionAdapterFactory implements TypeAdapterFactory {

    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        // 获取其他优先级高的Factory创建的DelegateAdapter
        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        // 如果type实现了IDeserializeAction，则返回包裹后的TypeAdapter
        if (shouldWrap(type.getRawType())) {
            return new TypeAdapter<T>() {
                public void write(JsonWriter out, T value) throws IOException {
                    delegate.write(out, value);
                }

                public T read(JsonReader in) throws IOException {
                    T bean = delegate.read(in);
                    // 验证数据是否合法，如不合法，则丢弃
                    if (bean instanceof IDataValidateAction) {
                        if (!((IDataValidateAction) bean).isDataValid()) {
                            throw new ApiException(ApiException.ILLEGALLY_DATA, ApiException.MSG_ILLEGALLY_DATA);
                        }
                    }
                    // 数据预处理
                    if (bean instanceof IAfterDeserializeAction) {
                        ((IAfterDeserializeAction) bean).doAfterDeserialize();
                    }
                    return bean;
                }
            };
        } else {
            return delegate;
        }
    }

    private boolean shouldWrap(Class clazz) {
        return IAfterDeserializeAction.class.isAssignableFrom(clazz) ||
                IDataValidateAction.class.isAssignableFrom(clazz);
    }
}