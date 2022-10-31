package com.atom.android.bookshop.ui

import android.app.Application
import com.atom.android.bookshop.utils.registerNetwork

class BookApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        registerNetwork()
    }

    companion object {
        var isConnectedInternet = false
    }
}
