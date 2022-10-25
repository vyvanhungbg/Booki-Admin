package com.atom.android.bookshop.data.source.remote.main

import com.atom.android.bookshop.data.source.IMainDataSource
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject
import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import com.atom.android.bookshop.data.source.remote.api.ApiURL
import com.atom.android.bookshop.data.source.remote.api.convertJsonToResponseObject
import com.atom.android.bookshop.utils.JSONConvertException
import com.atom.android.bookshop.utils.handler
import com.atom.android.bookshop.utils.httpConnection
import com.atom.android.bookshop.utils.remoteExecuteCallAPI
import org.json.JSONException
import org.json.JSONObject

class MainRemoteDataSource : IMainDataSource.Remote {

    override fun checkToken(token: String?, callback: IRequestCallback<ResponseObject<String>>) {
        remoteExecuteCallAPI<ResponseObject<String>>(
            dataForm = null,
            token,
            callback = callback,
            handle = ::checkTokenLive
        )
    }

    private fun checkTokenLive(
        data: String?,
        token: String?,
        callback: IRequestCallback<ResponseObject<String>>
    ) {

        val jsonObjectResponseObject = JSONObject(
            httpConnection(
                formData = data,
                ApiURL.pathCheckToken(),
                ApiConstants.Method.GET,
                token
            )
        )
        try {
            val responseObject = convertJsonToResponseObject(jsonObjectResponseObject)
            val isSuccess = responseObject.status
            if (isSuccess) {
                handler.post {
                    callback.onSuccess(responseObject)
                }
            } else {
                handler.post {
                    callback.onFailed(responseObject.message)
                }
            }
        } catch (ex: JSONException) {
            throw JSONConvertException(ex)
        } catch (ex: IllegalStateException) {
            throw JSONConvertException(ex)
        }
    }

    companion object {
        private var instance: MainRemoteDataSource? = null
        fun getInstance() = synchronized(this) {
            instance ?: MainRemoteDataSource().also { instance = it }
        }
    }
}
