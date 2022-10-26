package com.atom.android.bookshop.ui.bill.detail

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.utils.Constants

class BillDetailPresenter() :
    BillDetailContract.Presenter {

    private var view: BillDetailContract.View? = null

    override fun getBill(bundle: Bundle?) {
        val bill: Bill? = bundle?.getParcelable<Bill>(BillDetailFragment.EXTRA_BILL)
        if (bill != null) {
            view?.getBillSuccess(bill)
        } else {
            view?.getBillFailed()
        }
    }

    override fun requestCall(context: Context?, phoneNumber: String?) {
        phoneNumber?.let {
            try {
                context?.startActivity(
                    Intent(
                        Intent.ACTION_DIAL,
                        Uri.fromParts(Constants.SCHEME_ACTION_CALL, it, null)
                    )
                )
            } catch (ex: ActivityNotFoundException) {
                view?.requestCallFailed(ex.message)
            }
        }
    }

    fun setView(view: BillDetailContract.View) {
        this.view = view
    }

    companion object {
        private var instance: BillDetailPresenter? = null
        fun getInstance() = synchronized(this) {
            instance ?: BillDetailPresenter().also {
                instance = it
            }
        }
    }
}
