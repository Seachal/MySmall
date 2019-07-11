package com.laka.libutils.listener;

/**
 * @Author:summer
 * @Date:2019/7/11
 * @Description:缺少系统权限的回调
 */
public interface IPermissionQuestCallback {
    /**
     * 需要权限
     *
     * @param permissions 所需权限列表
     */
    void questPermissions(String[] permissions);
}
