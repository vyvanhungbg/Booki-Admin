package com.atom.android.bookshop.ui.discount

import android.content.Context
import android.content.SharedPreferences
import com.atom.android.bookshop.data.model.Discount
import com.atom.android.bookshop.data.repository.DiscountRepository
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject
import com.atom.android.bookshop.utils.getTokenLogin

class DiscountPresenter(
    private val repository: DiscountRepository,
    private val sharedPreferences: SharedPreferences?
) : DiscountContract.Presenter {

    private var view: DiscountContract.View? = null

    override fun getDiscount(context: Context?, currentPage: Int, type: Int) {
        val token = sharedPreferences?.getTokenLogin()
        repository.getDiscount(
            token,
            currentPage,
            type,
            object : IRequestCallback<ResponseObject<List<Discount>>> {
                override fun onSuccess(responseObject: ResponseObject<List<Discount>>) {
                    view?.getDiscountSuccess(responseObject.data as List<Discount>)
                }

                override fun onFailed(message: String?) {
                    view?.getDiscountFailed(message)
                }

            })
    }

    fun setView(view: DiscountContract.View) {
        this.view = view
    }

    companion object {
        private var instance: DiscountPresenter? = null
        fun getInstance(
            repository: DiscountRepository,
            sharedPreferences: SharedPreferences?,
        ) = synchronized(this) {
            this.instance ?: DiscountPresenter(repository, sharedPreferences).also {
                this.instance = it
            }
        }
    }
}
