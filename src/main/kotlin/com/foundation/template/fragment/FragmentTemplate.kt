package com.foundation.template.fragment

import com.android.tools.idea.wizard.template.*
import com.android.tools.idea.wizard.template.impl.activities.common.addAllKotlinDependencies
import com.foundation.template.util.NotificationUtils

val fragmentTemplate = template {
    revision = 2
    name = "MVP Fragment"
    description = "创建mvp模式的Fragment"
    minApi = 21
    minBuildApi = 21
    category = Category.Application
    formFactor = FormFactor.Mobile
    screens = listOf(WizardUiContext.FragmentGallery, WizardUiContext.MenuEntry,
            WizardUiContext.NewProject, WizardUiContext.NewModule)
//    thumb { File("test.png") }

    val fragmentNameParam = stringParameter {
        name = "FragmentName"
        default = "Fragment"
        help = "Fragment名"
        constraints = listOf(Constraint.NONEMPTY, Constraint.CLASS, Constraint.UNIQUE)
    }

    val layoutCheckParam = booleanParameter {
        name = "是否创建layout"
        default = true
        help = "不勾选的话不会创建layout"
    }

    val layoutNameParam = stringParameter {
        name = "Layout Name"
        default = "fragment_"
        visible = { layoutCheckParam.value }
        help = "创建Fragment的xml资源名"
        constraints = listOf(Constraint.LAYOUT, Constraint.UNIQUE, Constraint.NONEMPTY)
        suggest = { fragmentToLayout(fragmentNameParam.value) }
    }

    val fragmentPackageParam = stringParameter {
        name = "Package name"
        default = "com.foundation.base"
        constraints = listOf(Constraint.PACKAGE)
        suggest = { packageName }
    }

    widgets(TextFieldWidget(fragmentNameParam),
            CheckBoxWidget(layoutCheckParam),
            TextFieldWidget(layoutNameParam),
            PackageNameWidget(fragmentPackageParam))

    NotificationUtils.notify("测试")
    recipe = { data: TemplateData ->
        NotificationUtils.notify("测试2")
        val moduleData = data as ModuleTemplateData
        val fragmentPackage = fragmentPackageParam.value
        val simpleName = fragmentNameParam.value.replace("Fragment", "")
        val layoutName = layoutNameParam.value

        val (_, srcDir, resDir, manifestDir) = moduleData

        addAllKotlinDependencies(moduleData)

        val packageName = fragmentPackage.split(".").subList(0, 3).joinToString(separator = ".")

        save(createFragmentClass(packageName, fragmentPackage, simpleName,
                if (layoutCheckParam.value) underlinesToCamelCase(layoutName) else "ViewData"),
                srcDir.resolve("${simpleName}Fragment.kt"))
        save(createContractClass(fragmentPackage, simpleName), srcDir.resolve("${simpleName}Contract.kt"))
        save(createPresenterClass(fragmentPackage, simpleName), srcDir.resolve("${simpleName}Presenter.kt"))
        if (layoutCheckParam.value) {
            save(createLayoutXml(), resDir.resolve("layout/${layoutName}.xml"))
        }
    }
}