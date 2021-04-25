package com.github.weimingjue.intellijtemplate.services

import com.github.weimingjue.intellijtemplate.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
