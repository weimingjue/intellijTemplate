package com.foundation.template.activity

import com.android.tools.idea.wizard.template.*
import com.android.tools.idea.wizard.template.impl.activities.common.addAllKotlinDependencies
import com.foundation.template.util.NotificationUtils
import java.io.File

val activityTemplate = template {
    revision = 2
    name = "MVP Activity"
    description = "创建mvp模式的Activity"
    minApi = 21
    minBuildApi = 21
    category = Category.Application
    formFactor = FormFactor.Mobile
    screens = listOf(WizardUiContext.FragmentGallery, WizardUiContext.MenuEntry,
            WizardUiContext.NewProject, WizardUiContext.NewModule)
//    thumb { File("test.png") }

    val activityNameParam = stringParameter {
        name = "ActivityName"
        default = "Activity"
        help = "Activity名"
        constraints = listOf(Constraint.NONEMPTY, Constraint.ACTIVITY, Constraint.UNIQUE)
    }

    val layoutCheckParam = booleanParameter {
        name = "是否创建layout"
        default = true
        help = "不勾选的话不会创建layout"
    }

    val layoutNameParam = stringParameter {
        name = "Layout Name"
        default = "activity_"
        visible = { layoutCheckParam.value }
        help = "创建Activity的xml资源名"
        constraints = listOf(Constraint.LAYOUT, Constraint.UNIQUE, Constraint.NONEMPTY)
        suggest = { activityToLayout(activityNameParam.value) }
    }

    val activityPackageParam = stringParameter {
        name = "Package name"
        default = "com.foundation.base"
        constraints = listOf(Constraint.PACKAGE)
        suggest = { packageName }
    }

    widgets(TextFieldWidget(activityNameParam),
            CheckBoxWidget(layoutCheckParam),
            TextFieldWidget(layoutNameParam),
            PackageNameWidget(activityPackageParam))

    NotificationUtils.notify("测试")
    recipe = { data: TemplateData ->
        NotificationUtils.notify("测试2")
        val moduleData = data as ModuleTemplateData
        val activityPackage = activityPackageParam.value
        val simpleName = activityNameParam.value.replace("Activity", "")
        val layoutName = layoutNameParam.value

        val (_, srcDir, resDir, manifestDir) = moduleData

        addAllKotlinDependencies(moduleData)

        val packageName = activityPackage.split(".").subList(0, 3).joinToString(separator = ".")

        val source = createActivityClass(packageName, activityPackage, simpleName,
                if (layoutCheckParam.value) underlinesToCamelCase(layoutName) else "ViewData")
        save(source,
                srcDir.resolve("${simpleName}Activity.kt"))
        save(createContractClass(activityPackage, simpleName), srcDir.resolve("${simpleName}Contract.kt"))
        save(createPresenterClass(activityPackage, simpleName), srcDir.resolve("${simpleName}Presenter.kt"))
        mergeXml(createManifestXml(activityPackage, simpleName), File(manifestDir, "AndroidManifest.xml"))
        if (layoutCheckParam.value) {
            save(createLayoutXml(), resDir.resolve("layout/${layoutName}.xml"))
        }
    }
}