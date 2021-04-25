package com.foundation.template.temp

import com.android.tools.idea.wizard.template.*

val mviSetupTemplate
    get() = template {
        revision = 2
        name = "MVP Activity"
        description = "创建mvp模式的Activity"
        minApi = 21
        minBuildApi = 21
        category = Category.Other
        formFactor = FormFactor.Mobile
        screens = listOf(WizardUiContext.FragmentGallery, WizardUiContext.MenuEntry,
                WizardUiContext.NewProject, WizardUiContext.NewModule)

        val packageNameParam = defaultPackageNameParameter
        val entityName = stringParameter {
            name = "ActivityName"
            default = "Activity"
            help = "创建的Activity"
            constraints = listOf(Constraint.NONEMPTY, Constraint.ACTIVITY)
        }

        val layoutName = stringParameter {
            name = "Layout Name"
            default = "layout_"
            help = "创建Activity的xml资源名"
            constraints = listOf(Constraint.LAYOUT, Constraint.UNIQUE, Constraint.NONEMPTY)
            suggest = { activityToLayout(entityName.value) }
        }

        widgets(
                TextFieldWidget(entityName),
                TextFieldWidget(layoutName),
                PackageNameWidget(packageNameParam)
        )

        recipe = { data: TemplateData ->
            mviSetup(
                    data as ModuleTemplateData,
                    packageNameParam.value,
                    entityName.value,
                    layoutName.value
            )
        }
    }

val defaultPackageNameParameter
    get() = stringParameter {
        name = "Package name"
        visible = { !isNewModule }
        default = "com.foundation.base"
        constraints = listOf(Constraint.PACKAGE)
        suggest = { packageName }
    }