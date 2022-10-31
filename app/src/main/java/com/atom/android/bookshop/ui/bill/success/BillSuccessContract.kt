package com.atom.android.bookshop.ui.bill.success

import android.content.Context
import com.atom.android.bookshop.base.BasePresenter
import com.atom.android.bookshop.data.model.Bill

class BillSuccessContract {
    interface View {
        fun getBillSuccess(bill: List<Bill>)
        fun getBillFailed(message: String?)
    }

    interface Presenter : BasePresenter<BillSuccessContract.View> {
        fun getBillSuccess(context: Context?, currentPage: Int)
    }
}
