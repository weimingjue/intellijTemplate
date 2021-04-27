package com.foundation.template.fragment

fun createFragmentClass(
        packageName: String,
        fragmentPackage: String,
        simpleName: String,
        layoutHumpName: String
) = """
package $fragmentPackage

import android.os.Bundle
import android.view.View
${
    if (layoutHumpName.equals("ViewData")) "import androidx.databinding.ViewDataBinding"
    else "import ${packageName}.databinding.${layoutHumpName}Binding"
}
import com.nmm.smallfatbear.v2.mvp.MVPBaseFragment

class ${simpleName}Fragment : MVPBaseFragment<${simpleName}Contract.View, ${simpleName}Presenter, ${layoutHumpName}Binding>(),${simpleName}Contract.View {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
"""

fun createContractClass(
        fragmentPackage: String,
        simpleName: String
) = """
package $fragmentPackage

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
        fragmentPackage: String,
        simpleName: String
) = """
package $fragmentPackage

import com.nmm.smallfatbear.v2.mvp.BasePresenterImpl

class  ${simpleName}Presenter : BasePresenterImpl< ${simpleName}Contract.View>(),  ${simpleName}Contract.Presenter {
}
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