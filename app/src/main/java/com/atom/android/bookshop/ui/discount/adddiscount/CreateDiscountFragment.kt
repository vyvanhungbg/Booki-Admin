package com.atom.android.bookshop.ui.discount.adddiscount

import com.atom.android.bookshop.base.BaseFragment
import com.atom.android.bookshop.data.model.Discount
import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import com.atom.android.bookshop.databinding.FragmentCreateDiscountBinding
import com.atom.android.bookshop.utils.Constants
import com.atom.android.bookshop.utils.convertDataTimeToInstant
import com.atom.android.bookshop.utils.convertStringToDate
import com.atom.android.bookshop.utils.pickDateTime
import org.apache.commons.lang3.RandomStringUtils
import java.text.SimpleDateFormat
import java.util.TimeZone


class CreateDiscountFragment :
    BaseFragment<FragmentCreateDiscountBinding>(FragmentCreateDiscountBinding::inflate),
    CreateDiscountContract.View {

    override fun initData() {

    }

    override fun initView() {

    }

    override fun initEvent() {
        binding?.apply {
            editTextTimeStartDiscount.setOnClickListener {
                context?.pickDateTime { year, month, day, hour, minute ->
                    val instant = convertDataTimeToInstant(year, month, day, hour, minute)
                    val inputFormat = SimpleDateFormat(Constants.FORMAT_DATE_TIME_INPUT) // UTC time
                    val outputFormat = SimpleDateFormat(Constants.FORMAT_DATE_TIME) // local time
                    val date = inputFormat.parse(instant)
                    val string = outputFormat.format(date).toString()
                    editTextTimeStartDiscount.setText(string)
                }
            }
            editTextTimeEndDiscount.setOnClickListener {
                context?.pickDateTime { year, month, day, hour, minute ->
                    val instant = convertDataTimeToInstant(year, month, day, hour, minute)
                    editTextTimeEndDiscount.setText(convertStringToDate(instant))
                }
            }

            textViewRandomDiscount.setOnClickListener {
                val code = RandomStringUtils.randomAlphanumeric(Constants.LENGTH_CODE).uppercase()
                editTextCodeDiscount.setText(code)
            }
        }
    }

    override fun createDiscountSuccess(discount: Discount) {

    }

    override fun createDiscountFailed(message: String?) {

    }


}
