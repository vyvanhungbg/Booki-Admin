package com.atom.android.bookshop.ui.bill.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.utils.Constants

class BillDetailPresenter(private val view: BillDetailContract.View) :
    BillDetailContract.Presenter {

    override fun getBill(bundle: Bundle?) {
        val bill: Bill? = bundle?.getParcelable<Bill>(BillDetailFragment.EXTRA_BILL)
        if (bill != null) {
            view.getBillSuccess(bill)
        } else {
            view.getBillFailed()
        }
    }

    override fun requestCall(context: Context?, phoneNumber: String?) {
        phoneNumber?.let {
            context?.startActivity(
                Intent(
                    Intent.ACTION_DIAL,
                    Uri.fromParts(Constants.SCHEME_ACTION_CALL, it, null)
                )
            )
        }
    }

    companion object {
        private var instance: BillDetailPresenter? = null
        fun getInstance(
            view: BillDetailContract.View
        ) = synchronized(this) {
            instance ?: BillDetailPresenter(view).also {
                instance = it
            }
        }
    }
}
