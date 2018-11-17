package com.sunmi.paring.util

import android.os.Build
import android.support.annotation.RequiresApi
import android.view.View
import android.view.Window
import android.view.WindowManager

object StatusBarUtil {

    @RequiresApi(Build.VERSION_CODES.M)
    fun setStatusBarIconColor(window: Window, visible: Boolean) {
        window.decorView.systemUiVisibility =
                if (visible)
                    View.SYSTEM_UI_FLAG_VISIBLE
                else
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    fun setStatusBarColor(window: Window, color: Int) {
        window.run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                statusBarColor = color
            }
        }
    }
}
