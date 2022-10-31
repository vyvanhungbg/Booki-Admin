package com.atom.android.bookshop.utils

import android.os.Handler
import android.os.Looper
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import com.atom.android.bookshop.ui.BookApplication
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

fun <T> remoteExecuteCallAPI(
    dataForm: String? = null,
    token: String? = null,
    callback: IRequestCallback<T>,
    handle: (String?, String?, IRequestCallback<T>) -> Unit
) {
    if (!BookApplication.isConnectedInternet) {
        handler.post {
            callback.onFailed(ApiConstants.ERROR.ERROR_MESSAGE_NETWORK)
        }
    } else {
        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        executor.submit(object : Runnable {
            override fun run() {
                try {
                    handle(dataForm, token, callback)
                } catch (ex: HttpConnectionException) {
                    handler.post {
                        callback.onFailed(ex.message)
                    }
                } catch (ex: JSONConvertException) {
                    handler.post {
                        callback.onFailed(ex.message)
                    }
                }
            }
        })
        executor.shutdown()
    }
}

val handler = Handler(Looper.getMainLooper())
