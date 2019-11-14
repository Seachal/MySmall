//默认创建一个文件，这里是没有package声明的，如果不声明，则导入插件的时候，会报找不到插件类的异常
package com.xhh.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println("this is my custom plugin ModifyPlugin")
        project.android.registerTransform(new MyTransform(project))
    }

}