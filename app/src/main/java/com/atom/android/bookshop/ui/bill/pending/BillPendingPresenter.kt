package com.atom.android.bookshop.ui.bill.pending

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.widget.EditText
import com.atom.android.bookshop.R
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.data.repository.BillRepository
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject
import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import com.atom.android.bookshop.utils.Constants
import com.atom.android.bookshop.utils.getTokenLogin

class BillPendingPresenter(
    private val repository: BillRepository,
    private val sharedPreferences: SharedPreferences?
) : BillPendingContract.Presenter {

    private var view: BillPendingContract.View? = null
    override fun getBillPending(context: Context?, page: Int) {
        val token = sharedPreferences?.getTokenLogin()
        repository.getBill(
            token,
            page,
            ApiConstants.TYPEOFBILL.PENDING,
            object : IRequestCallback<ResponseObject<List<Bill>>> {
                override fun onSuccess(responseObject: ResponseObject<List<Bill>>) {
                    view?.getBillPendingSuccess(responseObject.data as List<Bill>)
                }

                override fun onFailed(message: String?) {
                    view?.getBillPendingFailed(message)
                }

            })
    }

    override fun confirmBill(context: Context?, bill: Bill) {
        val token = sharedPreferences?.getTokenLogin()
        repository.updateStatusBill(
            token,
            bill.id,
            ApiConstants.TYPEOFBILL.ACCEPT,
            reason = Constants.DEFAULT_STRING,
            object : IRequestCallback<ResponseObject<Bill>> {
                override fun onSuccess(responseObject: ResponseObject<Bill>) {
                    view?.requestSuccess(
                        bill,
                        responseObject.data as Bill,
                        context?.getString(R.string.text_confirm_success, bill.id)
                    )
                }

                override fun onFailed(message: String?) {
                    view?.requestFailed(context?.getString(R.string.text_confirm_failed, message))
                }
            })
    }

    override fun destroyBill(context: Context?, bill: Bill) {
        val titleAlertDialog = context?.getString(R.string.title_alert_destroy_bill)
        val reasonDefault = context?.getString(R.string.text_reason_default_pending_destroy_bill)
        AlertDialog.Builder(context).apply {
            setTitle(titleAlertDialog)
            val input = EditText(context)
            setView(input)
            setPositiveButton(context?.getText(R.string.text_confirm)) { dialog, which ->
                val reasonForDestroy = input.text.toString()
                requestDestroyBill(context, bill, reasonForDestroy)
            }
            setNeutralButton(context?.getText(R.string.text_normal)) { dialog, which ->
                requestDestroyBill(context, bill, reasonDefault)
            }
            setNegativeButton(context?.getText(R.string.text_cancel)) { dialog, which ->
                dialog.cancel()
            }
            show()
        }


    }

    override fun setView(view: BillPendingContract.View) {
        this.view = view
    }

    private fun requestDestroyBill(
        context: Context?,
        bill: Bill,
        reasonForDestroy: String?
    ) {
        val token = sharedPreferences?.getTokenLogin()
        repository.updateStatusBill(
            token,
            bill.id,
            ApiConstants.TYPEOFBILL.DESTROY,
            reasonForDestroy,
            object : IRequestCallback<ResponseObject<Bill>> {
                override fun onSuccess(responseObject: ResponseObject<Bill>) {
                    view?.requestSuccess(
                        bill,
                        newBill = null,
                        context?.getString(R.string.text_destroy_success, bill.id)
                    )
                }

                override fun onFailed(message: String?) {
                    view?.requestFailed(context?.getString(R.string.text_destroy_failed, message))
                }
            })
    }

    companion object {
        private var instance: BillPendingPresenter? = null
        fun getInstance(
            repository: BillRepository,
            sharedPreferences: SharedPreferences?
        ) = synchronized(this) {
            this.instance ?: BillPendingPresenter(repository, sharedPreferences).also {
                this.instance = it
            }
        }
    }
}
