package com.laka.libutils.toast;

/**
 * @Author:summer
 * @Date:2019/7/11
 * @Description:
 */
public interface ICustomToast {

    void setText(CharSequence s);

    void show();

    void setDuration(int duration);

    void setGravity(int gravity, int xOffset, int yOffset);

}
