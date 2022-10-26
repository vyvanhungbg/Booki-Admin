package com.atom.android.bookshop.ui.discount.adddiscount

import android.content.Context
import com.atom.android.bookshop.data.model.Discount
import com.atom.android.bookshop.data.model.DiscountEntity
import com.atom.android.bookshop.data.repository.DiscountRepository
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject
import com.atom.android.bookshop.utils.SharedPreferenceUtils
import com.atom.android.bookshop.utils.getTokenLogin

class CreateDiscountPresenter(private val repository: DiscountRepository) :
    CreateDiscountContract.Presenter {

    private var view: CreateDiscountContract.View? = null

    override fun createDiscount(context: Context?, discount: DiscountEntity) {
        val token = SharedPreferenceUtils.getInstance(context)?.getTokenLogin()
        repository.createDiscount(
            token,
            discount,
            object : IRequestCallback<ResponseObject<Discount>> {
                override fun onSuccess(responseObject: ResponseObject<Discount>) {
                    view?.createDiscountSuccess(responseObject.data as Discount)
                }

                override fun onFailed(message: String?) {
                    view?.createDiscountFailed(message)
                }

            })
    }

    fun setView(view: CreateDiscountContract.View) {
        this.view = view
    }

    companion object {
        private var instance: CreateDiscountPresenter? = null
        fun getInstance(
            repository: DiscountRepository,
        ) = synchronized(this) {
            instance ?: CreateDiscountPresenter(repository).also {
                instance = it
            }
        }
    }
}
