package com.atom.android.bookshop.ui.bill.success

import android.content.Context
import android.content.SharedPreferences
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.data.repository.BillRepository
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject
import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import com.atom.android.bookshop.utils.getTokenLogin

class BillSuccessPresenter(
    private val repository: BillRepository,
    private val sharedPreferences: SharedPreferences?
) : BillSuccessContract.Presenter {

    private var view: BillSuccessContract.View? = null

    override fun getBillSuccess(context: Context?, currentPage: Int) {
        val token = sharedPreferences?.getTokenLogin()
        repository.getBill(
            token,
            currentPage,
            ApiConstants.TYPEOFBILL.SUCCESS,
            object : IRequestCallback<ResponseObject<List<Bill>>> {
                override fun onSuccess(responseObject: ResponseObject<List<Bill>>) {
                    view?.getBillSuccess(responseObject.data as List<Bill>)
                }

                override fun onFailed(message: String?) {
                    view?.getBillFailed(message)
                }

            })
    }

    override fun setView(view: BillSuccessContract.View) {
        this.view = view
    }

    companion object {
        private var instance: BillSuccessPresenter? = null
        fun getInstance(
            repository: BillRepository,
            sharedPreferences: SharedPreferences?
        ) = synchronized(this) {
            instance ?: BillSuccessPresenter(repository, sharedPreferences).also {
                instance = it
            }
        }
    }
}
