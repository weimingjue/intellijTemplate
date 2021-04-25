package com.foundation.template.temp

import com.android.tools.idea.wizard.template.*
import com.android.tools.idea.wizard.template.impl.activities.common.addAllKotlinDependencies
import java.io.File

val mviSetupTemplate
    get() = template {
        revision = 2
        name = "MVP Activity"
        description = "创建mvp模式的Activity"
        minApi = 21
        minBuildApi = 21
        category = Category.Activity
        formFactor = FormFactor.Mobile
        screens = listOf(WizardUiContext.FragmentGallery, WizardUiContext.MenuEntry,
                WizardUiContext.NewProject, WizardUiContext.NewModule)

        val activitySimpleNameParam = stringParameter {
            name = "ActivityName"
            default = "Main"
            help = "不用输入Activity单词"
            constraints = listOf(Constraint.NONEMPTY, Constraint.ACTIVITY, Constraint.UNIQUE)
        }

        val layoutNameParam = stringParameter {
            name = "Layout Name"
            default = "activity_"
            help = "创建Activity的xml资源名"
            constraints = listOf(Constraint.LAYOUT, Constraint.UNIQUE, Constraint.NONEMPTY)
            suggest = { activityToLayout(activitySimpleNameParam.value) }
        }

        val packageNameParam = stringParameter {
            name = "Package name"
            visible = { !isNewModule }
            default = "com.foundation.base"
            constraints = listOf(Constraint.PACKAGE)
            suggest = { packageName }
        }

        widgets(TextFieldWidget(activitySimpleNameParam),
                TextFieldWidget(layoutNameParam),
                PackageNameWidget(packageNameParam))

        recipe = { data: TemplateData ->
            val moduleData = data as ModuleTemplateData
            val packageName = packageNameParam.value
            val asm = activitySimpleNameParam.value
            val activityName = if (asm.endsWith("Activity")) asm else "${asm}Activity"
            val layoutName = layoutNameParam.value

            val (_, srcDir, resDir, manifestDir) = moduleData

            addAllKotlinDependencies(moduleData)

            save(activityTemplate(packageName, activityName, layoutName), srcDir.resolve("${activityName}Activity.kt"))

            mergeXml(manifestTemplate(packageName, activityName), File(manifestDir, "AndroidManifest.xml"))

            save(layoutTemplate(), resDir.resolve("layout/${layoutName}.xml"))
        }
    }