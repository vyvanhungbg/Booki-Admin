package com.atom.android.bookshop.ui.bill.confirm

import android.content.Context
import com.atom.android.bookshop.base.BasePresenter
import com.atom.android.bookshop.data.model.Bill

class BillConfirmContract {
    interface View {
        fun getBillConfirmSuccess(bill: List<Bill>)
        fun getBillConfirmFailed(message: String?)
        fun requestFailed(message: String?)
        fun requestSuccess(old: Bill, newBill: Bill?, message: String?)
    }

    interface Presenter : BasePresenter<BillConfirmContract.View> {
        fun getBillConfirm(context: Context?, currentPage: Int)
        fun confirmShippingBill(context: Context?, bill: Bill)
        fun destroyBill(context: Context?, bill: Bill)
    }
}
