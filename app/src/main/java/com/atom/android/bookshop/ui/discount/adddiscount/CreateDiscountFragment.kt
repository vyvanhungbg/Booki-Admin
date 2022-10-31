package com.atom.android.bookshop.ui.discount.adddiscount

import android.app.Dialog
import com.atom.android.bookshop.R
import com.atom.android.bookshop.base.BaseFragment
import com.atom.android.bookshop.data.model.Discount
import com.atom.android.bookshop.data.model.DiscountEntity
import com.atom.android.bookshop.data.repository.DiscountRepository
import com.atom.android.bookshop.data.source.remote.discount.DiscountRemoteDataSource
import com.atom.android.bookshop.databinding.FragmentCreateDiscountBinding
import com.atom.android.bookshop.ui.main.MainActivity
import com.atom.android.bookshop.utils.Constants
import com.atom.android.bookshop.utils.SharedPreferenceUtils
import com.atom.android.bookshop.utils.convertDataTimeToInstant
import com.atom.android.bookshop.utils.convertInstantToString
import com.atom.android.bookshop.utils.pickDateTime
import com.atom.android.bookshop.utils.start
import com.atom.android.bookshop.utils.toast
import org.apache.commons.lang3.RandomStringUtils


class CreateDiscountFragment :
    BaseFragment<FragmentCreateDiscountBinding>(FragmentCreateDiscountBinding::inflate),
    CreateDiscountContract.View {

    private var timeStart: String? = null
    private var timeEnd: String? = null
    private val createDiscountPresenter by lazy {
        CreateDiscountPresenter.getInstance(
            DiscountRepository.getInstance(DiscountRemoteDataSource.getInstance()),
            SharedPreferenceUtils.getInstance(context)
        )
    }
    private val progessBar by lazy {
        Dialog(requireContext())
    }

    override fun initData() {
        createDiscountPresenter.setView(this)
    }

    override fun initView() {
        (activity as? MainActivity)?.let {
            it.setVisibleNavigationBar(false)
        }
        binding?.imgBack?.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    override fun initEvent() {
        binding?.apply {
            editTextTimeStartDiscount.setOnClickListener {
                context?.pickDateTime { year, month, day, hour, minute ->
                    val instant = convertDataTimeToInstant(year, month, day, hour, minute)
                    timeStart = instant
                    val dateForHumanReadable = convertInstantToString(instant)
                    editTextTimeStartDiscount.setText(dateForHumanReadable)
                }
            }
            editTextTimeEndDiscount.setOnClickListener {
                context?.pickDateTime { year, month, day, hour, minute ->
                    val instant = convertDataTimeToInstant(year, month, day, hour, minute)
                    timeEnd = instant
                    val dateForHumanReadable = convertInstantToString(instant)
                    editTextTimeEndDiscount.setText(dateForHumanReadable)
                }
            }

            textViewRandomDiscount.setOnClickListener {
                val randomCode =
                    RandomStringUtils.randomAlphanumeric(Constants.LENGTH_CODE).uppercase()
                editTextCodeDiscount.setText(randomCode)
            }

            btnSubmit.setOnClickListener {
                val title = editTextNameDiscount.text.toString()
                val value = editTextValueDiscount.text.toString()
                val code = editTextCodeDiscount.text.toString()
                val amount = editTextAmountDiscount.text.toString()
                val visible = if (radioTypeVisibleDiscount.isChecked) 1 else 0
                val image = Constants.DEFAULT_STRING
                createDiscountPresenter.validateDiscount(
                    context,
                    DiscountEntity(
                        title,
                        if (value.isNullOrEmpty()) Constants.DEFAULT_DOUBLE else value.toDouble(),
                        code,
                        if (amount.isNullOrEmpty()) Constants.DEFAULT_INT else amount.toInt(),
                        image,
                        timeEnd,
                        timeStart,
                        visible,
                        idBook = null
                    )
                )

            }
        }
    }

    override fun createDiscountSuccess(discount: Discount) {
        context?.toast(R.string.text_discount_created)
    }

    override fun createDiscountFailed(message: String?) {
        context?.toast(message)
    }

    override fun validateDiscountSuccess(discount: DiscountEntity) {
        createDiscountPresenter.createDiscount(context, discount)
    }

    override fun validateDiscountFailed(message: String?) {
        context?.toast(message)
    }

    override fun showProgressBar() {
        progessBar.start(true)
    }

    override fun hideProgressBar() {
        progessBar.dismiss()
    }

    override fun onDestroy() {
        (activity as? MainActivity)?.let {
            it.setVisibleNavigationBar(true)
        }
        super.onDestroy()
    }
}
