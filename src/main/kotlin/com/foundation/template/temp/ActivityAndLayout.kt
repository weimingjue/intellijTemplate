package com.foundation.template.temp

fun activityTemplate(
        packageName: String,
        activityName: String,
        layoutName: String
) = """
package $packageName

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ${activityName}sActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.${layoutName})
    }
}
"""

fun manifestTemplate(
        packageName: String,
        activityName: String) = """
<manifest xmlns:android="http://schemas.android.com/apk/res/android"> 
    <application> 
        <activity android:name="${packageName}.${activityName}"
            android:screenOrientation="portrait"/>
    </application> 
</manifest> 
"""

fun layoutTemplate() = """
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <variable
            name="activity"
            type="com.wang.merchant.MainActivity" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

    </LinearLayout>
</layout>
"""