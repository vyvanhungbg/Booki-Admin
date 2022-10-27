package com.atom.android.bookshop.ui.bill.delivery

import android.content.Context
import com.atom.android.bookshop.R
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.data.repository.BillRepository
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject
import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import com.atom.android.bookshop.utils.Constants
import com.atom.android.bookshop.utils.SharedPreferenceUtils
import com.atom.android.bookshop.utils.getTokenLogin


class BillDeliveryPresenter(
    private val repository: BillRepository,
    private val view: BillDeliveryContract.View
) : BillDeliveryContract.Presenter {

    override fun getBillDelivery(context: Context?, currentPage: Int) {
        val token = SharedPreferenceUtils.getInstance(context)?.getTokenLogin()
        repository.getBill(
            token,
            currentPage,
            ApiConstants.TYPEOFBILL.DELIVERY,
            object : IRequestCallback<ResponseObject<List<Bill>>> {
                override fun onSuccess(responseObject: ResponseObject<List<Bill>>) {
                    view.getBillDeliverySuccess(responseObject.data as List<Bill>)
                }

                override fun onFailed(message: String?) {
                    view.getBillDeliveryFailed(message)
                }

            })
    }

    override fun confirmDeliveryBill(context: Context?, bill: Bill) {
        val token = SharedPreferenceUtils.getInstance(context)?.getTokenLogin()
        repository.updateStatusBill(
            token,
            bill.id,
            ApiConstants.TYPEOFBILL.SUCCESS,
            reason = Constants.DEFAULT_STRING,
            object : IRequestCallback<ResponseObject<Bill>> {
                override fun onSuccess(responseObject: ResponseObject<Bill>) {
                    view.requestSuccess(
                        bill,
                        responseObject.data as Bill,
                        context?.getString(R.string.text_bill_delivery_done_success, bill.id)
                    )
                }

                override fun onFailed(message: String?) {
                    view.requestFailed(context?.getString(R.string.text_confirm_failed))
                }
            })
    }

    override fun requestDestroyBill(
        context: Context?,
        bill: Bill,
        reasonForDestroy: String?,
        status: Int
    ) {
        val token = SharedPreferenceUtils.getInstance(context)?.getTokenLogin()
        repository.updateStatusBill(
            token,
            bill.id,
            status,
            reasonForDestroy,
            object : IRequestCallback<ResponseObject<Bill>> {
                override fun onSuccess(responseObject: ResponseObject<Bill>) {
                    view.requestSuccess(
                        bill,
                        newBill = null,
                        context?.getString(R.string.text_destroy_success, bill.id)
                    )
                }

                override fun onFailed(message: String?) {
                    view.requestFailed(context?.getString(R.string.text_destroy_failed))
                }
            })
    }

    companion object {
        private var instance: BillDeliveryPresenter? = null
        fun getInstance(
            repository: BillRepository,
            view: BillDeliveryContract.View
        ) = synchronized(this) {
            instance ?: BillDeliveryPresenter(repository, view).also {
                instance = it
            }
        }

        const val INDEX_OF_ITEM_LOST = 1
    }
}
