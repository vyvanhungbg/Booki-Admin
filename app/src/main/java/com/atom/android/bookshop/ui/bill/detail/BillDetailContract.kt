package com.atom.android.bookshop.ui.bill.detail

import android.content.Context
import android.os.Bundle
import com.atom.android.bookshop.data.model.Bill

class BillDetailContract {
    interface View {
        fun getBillSuccess(bill: Bill)
        fun getBillFailed()
    }

    interface Presenter {
        fun getBill(bundle: Bundle?)
        fun requestCall(context: Context?, phoneNumber: String?)
    }
}
