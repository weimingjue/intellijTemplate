package com.foundation.template

import com.android.tools.idea.wizard.template.WizardTemplateProvider
import com.foundation.template.activity.activityTemplate
import com.foundation.template.fragment.fragmentTemplate

class MyWizardTemplateProvider : WizardTemplateProvider() {
    override fun getTemplates() = listOf(activityTemplate, fragmentTemplate)
}