package com.atom.android.bookshop.ui.bill.delivery

import android.content.Context
import com.atom.android.bookshop.data.model.Bill

class BillDeliveryContract {
    interface View {
        fun getBillDeliverySuccess(bill: List<Bill>)
        fun getBillDeliveryFailed(message: String?)
        fun requestFailed(message: String?)
        fun requestSuccess(oldBill: Bill, newBill: Bill?, message: String?)
    }

    interface Presenter {
        fun getBillDelivery(context: Context?, currentPage: Int)
        fun confirmDeliveryBill(context: Context?, bill: Bill)
        fun destroyBill(context: Context?, bill: Bill)
    }
}
