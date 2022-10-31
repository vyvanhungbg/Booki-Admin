package com.atom.android.bookshop.data.source.remote.forgotpassword

import com.atom.android.bookshop.data.source.IForgotPasswordDataSource
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject
import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import com.atom.android.bookshop.data.source.remote.api.ApiURL
import com.atom.android.bookshop.data.source.remote.api.convertJsonToResponseObject
import com.atom.android.bookshop.utils.JSONConvertException
import com.atom.android.bookshop.utils.handler
import com.atom.android.bookshop.utils.httpConnectionSendFormData
import com.atom.android.bookshop.utils.remoteExecuteCallAPI
import org.json.JSONException
import org.json.JSONObject

class ForgotPasswordRemoteDataSource : IForgotPasswordDataSource.Remote {

    override fun requestForgotPassword(
        email: String,
        callback: IRequestCallback<ResponseObject<String>>
    ) {
        val dataFormEmail = "${ApiConstants.FIELD.EMAIL}=$email"
        remoteExecuteCallAPI<ResponseObject<String>>(
            dataFormEmail,
            token = null,
            callback = callback,
            handle = ::sendForgotPassword
        )
    }

    private fun sendForgotPassword(
        dataFormEmail: String?,
        token: String?,
        callback: IRequestCallback<ResponseObject<String>>
    ) {

        val jsonObjectResponseObject = JSONObject(
            httpConnectionSendFormData(
                dataFormEmail,
                ApiURL.pathForgotEmail(),
                ApiConstants.Method.POST,
                token,
                ApiConstants.ATTRIBUTE.APPLICATION_JSON_FORM
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
        private var instance: ForgotPasswordRemoteDataSource? = null
        fun getInstance() = synchronized(this) {
            instance ?: ForgotPasswordRemoteDataSource().also { instance = it }
        }
    }
}
