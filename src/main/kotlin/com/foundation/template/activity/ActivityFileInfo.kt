package com.foundation.template.activity

fun createActivityClass(
        packageName: String,
        activityPackage: String,
        simpleName: String,
        layoutHumpName: String
) = """
package $activityPackage

import android.os.Bundle
${
    if (layoutHumpName.equals("ViewData")) "import androidx.databinding.ViewDataBinding"
    else "import ${packageName}.databinding.${layoutHumpName}Binding"
}
import com.nmm.smallfatbear.v2.mvp.MVPBaseActivity

class ${simpleName}Activity : MVPBaseActivity<${simpleName}Contract.View, ${simpleName}Presenter, ${layoutHumpName}Binding>(),${simpleName}Contract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
"""

fun createContractClass(
        activityPackage: String,
        simpleName: String
) = """
package $activityPackage

import com.nmm.smallfatbear.v2.mvp.BasePresenter
import com.nmm.smallfatbear.v2.mvp.BaseView

class ${simpleName}Contract {

    interface View : BaseView {
    }

    internal interface Presenter : BasePresenter<View> {
    }
}
"""

fun createPresenterClass(
        activityPackage: String,
        simpleName: String
) = """
package $activityPackage

import com.nmm.smallfatbear.v2.mvp.BasePresenterImpl

class  ${simpleName}Presenter : BasePresenterImpl< ${simpleName}Contract.View>(),  ${simpleName}Contract.Presenter {
}
"""

fun createManifestXml(
        activityPackage: String,
        simpleName: String) = """
<manifest xmlns:android="http://schemas.android.com/apk/res/android"> 
    <application> 
        <activity android:name="${activityPackage}.${simpleName}Activity"
            android:screenOrientation="portrait"/>
    </application> 
</manifest> 
"""

fun createLayoutXml() = """
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

    </LinearLayout>
</layout>
"""