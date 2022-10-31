package com.atom.android.bookshop.ui.bill.delivery

import android.content.Context
import com.atom.android.bookshop.base.BasePresenter
import com.atom.android.bookshop.data.model.Bill

class BillDeliveryContract {
    interface View {
        fun getBillDeliverySuccess(bill: List<Bill>)
        fun getBillDeliveryFailed(message: String?)
        fun requestFailed(message: String?)
        fun requestSuccess(oldBill: Bill, newBill: Bill?, message: String?)
    }

    interface Presenter :BasePresenter<BillDeliveryContract.View> {
        fun getBillDelivery(context: Context?, currentPage: Int)
        fun confirmDeliveryBill(context: Context?, bill: Bill)
        fun requestDestroyBill(
            context: Context?,
            bill: Bill,
            reasonForDestroy: String?,
            status: Int
        )
    }
}
