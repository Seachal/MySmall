package com.xhh.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        println("apply my plugin")
        project.getTasks().create("myTask",MyTask.class)
    }
}