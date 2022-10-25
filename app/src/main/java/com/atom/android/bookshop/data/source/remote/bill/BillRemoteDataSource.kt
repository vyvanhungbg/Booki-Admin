package com.atom.android.bookshop.data.source.remote.bill

import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.data.source.IBillDataSource
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject
import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import com.atom.android.bookshop.data.source.remote.api.ApiURL
import com.atom.android.bookshop.data.source.remote.api.convertBillJsonToResponseObject
import com.atom.android.bookshop.data.source.remote.api.convertBillsJsonToResponseObject
import com.atom.android.bookshop.utils.JSONConvertException
import com.atom.android.bookshop.utils.handler
import com.atom.android.bookshop.utils.httpConnectionSendFormData
import com.atom.android.bookshop.utils.remoteExecuteCallAPI
import org.json.JSONException
import org.json.JSONObject

class BillRemoteDataSource : IBillDataSource.Remote {

    override fun getBill(
        token: String?,
        page: Int?,
        type: Int?,
        callback: IRequestCallback<ResponseObject<List<Bill>>>
    ) {
        val dataForm = "?${ApiConstants.FIELD.PAGE}=$page&${ApiConstants.FIELD.TYPE}=$type"
        remoteExecuteCallAPI<ResponseObject<List<Bill>>>(
            dataForm,
            token = token,
            callback = callback,
            handle = ::getBillFromServer
        )
    }

    override fun updateStatusBill(
        token: String?,
        idBill: Int,
        status: Int,
        reason: String?,
        callback: IRequestCallback<ResponseObject<Bill>>
    ) {
        val dataForm =
            "?${Bill.ID}=$idBill&${ApiConstants.FIELD.TYPE}=$status&${ApiConstants.FIELD.REASON}=$reason"
        remoteExecuteCallAPI<ResponseObject<Bill>>(
            dataForm,
            token = token,
            callback = callback,
            handle = ::requestUpdateStatusBill
        )
    }

    private fun requestUpdateStatusBill(
        dataForm: String?,
        token: String?,
        callback: IRequestCallback<ResponseObject<Bill>>
    ) {
        val jsonObjectResponseObject = JSONObject(
            httpConnectionSendFormData(
                formData = null,
                ApiURL.pathUpdateStatusBill() + dataForm,
                ApiConstants.Method.POST,
                token
            )
        )
        try {
            val responseObject = convertBillJsonToResponseObject(jsonObjectResponseObject)
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

    private fun getBillFromServer(
        dataForm: String?,
        token: String?,
        callback: IRequestCallback<ResponseObject<List<Bill>>>
    ) {

        val jsonObjectResponseObject = JSONObject(
            httpConnectionSendFormData(
                dataForm,
                ApiURL.pathOrder() + dataForm,
                ApiConstants.Method.GET,
                token
            )
        )
        try {
            val responseObject = convertBillsJsonToResponseObject(jsonObjectResponseObject)
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
        private var instance: BillRemoteDataSource? = null
        fun getInstance() = synchronized(this) {
            instance ?: BillRemoteDataSource().also { instance = it }
        }
    }
}
