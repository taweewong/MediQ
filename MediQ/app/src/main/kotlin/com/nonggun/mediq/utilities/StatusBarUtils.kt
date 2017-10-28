package com.nonggun.mediq.utilities

import android.os.Build
import android.view.Window
import android.view.WindowManager

class StatusBarUtils {
    companion object {
        fun fullScreenLayout(window: Window) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            }
        }
    }
}
