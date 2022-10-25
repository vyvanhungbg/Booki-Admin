package com.atom.android.bookshop.ui.bill.pending

import android.content.Context
import com.atom.android.bookshop.data.model.Bill

class BillPendingContract {
    interface View {
        fun getBillPendingSuccess(bill: List<Bill>)
        fun getBillPendingFailed(message: String?)
        fun requestFailed(message: String?)
        fun requestSuccess(oldBill: Bill, newBill: Bill?, message: String?)
    }

    interface Presenter {
        fun getBillPending(context: Context?, page: Int)
        fun confirmBill(context: Context?, bill: Bill)
        fun destroyBill(context: Context?, bill: Bill)
    }
}
