package com.atom.android.bookshop.data.source.remote.account

import com.atom.android.bookshop.data.model.User
import com.atom.android.bookshop.data.source.IAccountDataSource
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject
import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import com.atom.android.bookshop.data.source.remote.api.ApiURL
import com.atom.android.bookshop.data.source.remote.api.convertUserJsonToResponseObject
import com.atom.android.bookshop.utils.JSONConvertException
import com.atom.android.bookshop.utils.handler
import com.atom.android.bookshop.utils.httpConnection
import com.atom.android.bookshop.utils.remoteExecuteCallAPI
import org.json.JSONException
import org.json.JSONObject

class AccountRemoteDataSource : IAccountDataSource.Remote {

    override fun getUser(token: String?, callback: IRequestCallback<ResponseObject<User>>) {
        remoteExecuteCallAPI<ResponseObject<User>>(
            dataForm = null,
            token,
            callback = callback,
            handle = ::fetchUser,
        )
    }

    private fun fetchUser(
        data: String?,
        token: String?,
        callback: IRequestCallback<ResponseObject<User>>
    ) {

        val jsonObjectResponseObject = JSONObject(
            httpConnection(
                data,
                ApiURL.pathGetUser(),
                ApiConstants.Method.GET,
                token
            )
        )
        try {
            val responseObject = convertUserJsonToResponseObject(jsonObjectResponseObject)
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
        private var instance: AccountRemoteDataSource? = null
        fun getInstance() = synchronized(this) {
            instance ?: AccountRemoteDataSource().also { instance = it }
        }
    }
}
