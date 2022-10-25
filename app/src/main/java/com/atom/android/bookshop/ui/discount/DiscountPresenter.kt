package com.atom.android.bookshop.ui.discount

import android.content.Context
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.data.model.Discount
import com.atom.android.bookshop.data.model.DiscountEntity
import com.atom.android.bookshop.data.repository.DiscountRepository
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject
import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import com.atom.android.bookshop.utils.SharedPreferenceUtils
import com.atom.android.bookshop.utils.getTokenLogin

class DiscountPresenter(
    private val repository: DiscountRepository,
    private val view: DiscountContract.View
) : DiscountContract.Presenter {

    override fun getDiscount(context: Context?, currentPage: Int, type: Int) {
        val token = SharedPreferenceUtils.getInstance(context)?.getTokenLogin()
        repository.getDiscount(
            token,
            currentPage,
            type,
            object : IRequestCallback<ResponseObject<List<Discount>>> {
                override fun onSuccess(responseObject: ResponseObject<List<Discount>>) {
                    view.getDiscountSuccess(responseObject.data as List<Discount>)
                }

                override fun onFailed(message: String?) {
                    view.getDiscountFailed(message)
                }

            })
    }

    override fun createDiscount(context: Context?, discount: DiscountEntity) {

    }

    companion object {
        private var instance: DiscountPresenter? = null
        fun getInstance(
            repository: DiscountRepository,
            view: DiscountContract.View
        ) = synchronized(this) {
            instance ?: DiscountPresenter(repository, view).also {
                instance = it
            }
        }
    }
}
