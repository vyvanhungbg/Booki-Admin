package com.atom.android.bookshop.utils

import android.os.Handler
import android.os.Looper
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

fun <T> remoteExecuteCallAPI(
    dataForm: String? = null,
    token: String? = null,
    callback: IRequestCallback<T>,
    handle: (String?, String?, IRequestCallback<T>) -> Unit
) {
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

val handler = Handler(Looper.getMainLooper())
