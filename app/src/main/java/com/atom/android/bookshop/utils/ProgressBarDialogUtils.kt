package com.atom.android.bookshop.utils

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import com.atom.android.bookshop.R
import com.atom.android.bookshop.data.source.remote.api.ApiConstants

fun Dialog.start(stopFlag: Boolean) { // stop progress
    this.let {
        it.setContentView(R.layout.dl_progress_bar)
        val window: Window = it.getWindow() ?: return
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAttributes: WindowManager.LayoutParams = window.getAttributes()
        windowAttributes.y = ApiConstants.DIALOGCONFIG.MARGIN_Y
        windowAttributes.gravity = Gravity.CENTER
        window.setAttributes(windowAttributes)
        it.setCancelable(stopFlag)
        it.show()
    }
}
