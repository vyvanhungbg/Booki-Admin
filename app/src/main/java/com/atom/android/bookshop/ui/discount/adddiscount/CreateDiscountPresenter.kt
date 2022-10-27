package com.atom.android.bookshop.ui.discount.adddiscount

import android.content.Context
import com.atom.android.bookshop.R
import com.atom.android.bookshop.data.model.Discount
import com.atom.android.bookshop.data.model.DiscountEntity
import com.atom.android.bookshop.data.repository.DiscountRepository
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject
import com.atom.android.bookshop.utils.Constants
import com.atom.android.bookshop.utils.SharedPreferenceUtils
import com.atom.android.bookshop.utils.getTokenLogin
import java.text.SimpleDateFormat

class CreateDiscountPresenter(private val repository: DiscountRepository) :
    CreateDiscountContract.Presenter {

    private var view: CreateDiscountContract.View? = null

    override fun createDiscount(context: Context?, discount: DiscountEntity) {
        val token = SharedPreferenceUtils.getInstance(context)?.getTokenLogin()
        view?.showProgressBar()
        repository.createDiscount(
            token,
            discount,
            object : IRequestCallback<ResponseObject<Discount>> {
                override fun onSuccess(responseObject: ResponseObject<Discount>) {
                    view?.hideProgressBar()
                    view?.createDiscountSuccess(responseObject.data as Discount)
                }

                override fun onFailed(message: String?) {
                    view?.hideProgressBar()
                    view?.createDiscountFailed(message)
                }

            })
    }

    override fun validateDiscount(
        context: Context?,
        discount: DiscountEntity
    ) {

        if (discount.name.isEmpty() || discount.name.isBlank()) {
            view?.validateDiscountFailed(context?.getString(R.string.text_validate_name_discount_failed))
        } else if (discount.value <= Constants.MIN_VALUE_DISCOUNT) {
            view?.validateDiscountFailed(context?.getString(R.string.text_validate_value_discount_failed))
        } else if (discount.code.isEmpty() || discount.code.isBlank() || discount.code.length < Constants.LENGTH_CODE) {
            view?.validateDiscountFailed(context?.getString(R.string.text_validate_code_discount_failed))
        } else if (discount.amount <= 0) {
            view?.validateDiscountFailed(context?.getString(R.string.text_validate_amount_discount_failed))
        } else if (discount.timeStart.isNullOrEmpty()) {
            view?.validateDiscountFailed(context?.getString(R.string.text_validate_time_start_discount_failed))
        } else if (discount.timeEnd.isNullOrEmpty()) {
            view?.validateDiscountFailed(context?.getString(R.string.text_validate_time_end_discount_failed))
        } else if (!checkEndDateAfterStartDate(discount.timeStart, discount.timeEnd)) {
            view?.validateDiscountFailed(context?.getString(R.string.text_validate_time_discount_failed))
        } else {
            view?.validateDiscountSuccess(
                discount
            )
        }
    }


    fun setView(view: CreateDiscountContract.View) {
        this.view = view
    }

    private fun checkEndDateAfterStartDate(startTime: String?, endDate: String?): Boolean {
        val inputFormat = SimpleDateFormat(Constants.FORMAT_DATE_TIME_INPUT)
        val startDate = inputFormat.parse(startTime)
        val endDate = inputFormat.parse(endDate)
        return endDate.after(startDate)
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
