package com.laka.libutils.system;

/**
 * @Author:summer
 * @Date:2019/7/11
 * @Description:app基本信息分装类，通过SystemHelper进行获取具体的versionName、versionCode等
 */
public class AppInfo {

    public String versionName;
    public int versionCode;
    public String marketChannel;
    public String packageName;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("versionName : ").append(versionName).append("\n");
        builder.append("versionCode : ").append(versionCode).append("\n");
        builder.append("marketChannel : ").append(marketChannel).append("\n");
        return builder.toString();
    }

    public String getVersionName() {
        if(versionName == null){
            return "";
        }
        return versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public String getMarketChannel() {
        if(marketChannel == null){
            return "";
        }
        return marketChannel;
    }

    public String getPackageName() {
        return packageName;
    }
}
