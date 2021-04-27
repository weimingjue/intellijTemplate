package com.foundation.template.util

import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationListener
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.impl.IdeFrameImpl
import java.awt.Component
import java.awt.KeyboardFocusManager

object NotificationUtils {
    private val ng = NotificationGroup.logOnlyGroup("Template")

    /**
     * 默认提示
     */
    fun notify(content: Any?) {
        ng.createNotification(content.toString(), NotificationType.INFORMATION)
                .notify(getCurrentProject())
    }

    fun notify(title: Any?, content: Any?, listener: NotificationListener?) {
        ng.createNotification(title.toString(), content.toString(), NotificationType.INFORMATION, listener)
                .notify(getCurrentProject())
    }

    fun getCurrentProject(): Project? {
//        不太准：IdeFrameImpl.getActiveFrame()
        val root = findRootComponent(KeyboardFocusManager.getCurrentKeyboardFocusManager().focusOwner)
        if (root is IdeFrameImpl) {
            val project = root.getData(CommonDataKeys.PROJECT.name)
            return if (project is Project) project else null
        }
        return null
    }

    private fun findRootComponent(c: Component?): Component? {
        if (c == null) {
            return null
        }
        return if (c.parent == null) c else findRootComponent(c.parent)
    }
}