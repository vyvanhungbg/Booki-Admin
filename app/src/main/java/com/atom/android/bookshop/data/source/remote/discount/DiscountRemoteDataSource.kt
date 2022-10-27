package com.atom.android.bookshop.data.source.remote.discount

import com.atom.android.bookshop.data.model.Discount
import com.atom.android.bookshop.data.model.DiscountEntity
import com.atom.android.bookshop.data.source.IDiscountDataSource
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject
import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import com.atom.android.bookshop.data.source.remote.api.ApiURL
import com.atom.android.bookshop.data.source.remote.api.convertDiscountJsonToResponseObject
import com.atom.android.bookshop.data.source.remote.api.convertDiscountsJsonToResponseObject
import com.atom.android.bookshop.utils.JSONConvertException
import com.atom.android.bookshop.utils.convertToJson
import com.atom.android.bookshop.utils.handler
import com.atom.android.bookshop.utils.httpConnection
import com.atom.android.bookshop.utils.httpConnectionSendFormData
import com.atom.android.bookshop.utils.remoteExecuteCallAPI
import org.json.JSONException
import org.json.JSONObject

class DiscountRemoteDataSource : IDiscountDataSource.Remote {

    override fun getDiscount(
        token: String?,
        page: Int?,
        type: Int?,
        callback: IRequestCallback<ResponseObject<List<Discount>>>
    ) {
        val dataForm = "?${ApiConstants.FIELD.PAGE}=$page&${ApiConstants.FIELD.TYPE}=$type"
        remoteExecuteCallAPI<ResponseObject<List<Discount>>>(
            dataForm,
            token = token,
            callback = callback,
            handle = ::getDiscountFromServer
        )
    }

    override fun createDiscount(
        token: String?,
        discount: DiscountEntity,
        callback: IRequestCallback<ResponseObject<Discount>>
    ) {
        val dataFormBill = discount.convertToJson()
        remoteExecuteCallAPI<ResponseObject<Discount>>(
            dataFormBill,
            token,
            callback,
            ::createDiscount
        )
    }

    private fun createDiscount(
        dataFormDiscount: String?,
        token: String?,
        callback: IRequestCallback<ResponseObject<Discount>>
    ) {

        val jsonObjectResponseObject = JSONObject(
            httpConnection(
                dataFormDiscount,
                ApiURL.pathDiscount(),
                ApiConstants.Method.POST,
                token
            )
        )
        try {
            val responseObject = convertDiscountJsonToResponseObject(jsonObjectResponseObject)
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

    private fun getDiscountFromServer(
        dataForm: String?,
        token: String?,
        callback: IRequestCallback<ResponseObject<List<Discount>>>
    ) {

        val jsonObjectResponseObject = JSONObject(
            httpConnectionSendFormData(
                dataForm,
                ApiURL.pathDiscount() + dataForm,
                ApiConstants.Method.GET,
                token
            )
        )
        try {
            val responseObject = convertDiscountsJsonToResponseObject(jsonObjectResponseObject)
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
        private var instance: DiscountRemoteDataSource? = null
        fun getInstance() = synchronized(this) {
            instance ?: DiscountRemoteDataSource().also { instance = it }
        }
    }
}
