package com.atom.android.bookshop.ui.discount

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.atom.android.bookshop.R
import com.atom.android.bookshop.base.BaseFragment
import com.atom.android.bookshop.data.model.Discount
import com.atom.android.bookshop.data.repository.DiscountRepository
import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import com.atom.android.bookshop.data.source.remote.discount.DiscountRemoteDataSource
import com.atom.android.bookshop.databinding.FragmentDiscountBinding
import com.atom.android.bookshop.ui.discount.adddiscount.CreateDiscountFragment
import com.atom.android.bookshop.utils.Constants.DEFAULT_PAGE
import com.atom.android.bookshop.utils.SharedPreferenceUtils
import com.atom.android.bookshop.utils.navigate
import com.atom.android.bookshop.utils.toast

typealias DefaultSource = com.google.android.material.R.layout

class DiscountFragment : BaseFragment<FragmentDiscountBinding>(FragmentDiscountBinding::inflate),
    DiscountContract.View {

    private var currentPage = DEFAULT_PAGE
    private var type = ApiConstants.TYPEOFDISCOUNT.RUNNING
    private val discountPresenter by lazy {
        DiscountPresenter.getInstance(
            DiscountRepository.getInstance(
                DiscountRemoteDataSource.getInstance()
            ),
            SharedPreferenceUtils.getInstance(context)
        )
    }

    val listAdapter = ListAdapterDiscount { discount: Discount ->
        {}
    }

    override fun initData() {
        discountPresenter.setView(this)
    }


    override fun initView() {
        binding?.recyclerviewDiscount?.adapter = listAdapter
        listAdapter.loadMore(binding?.recyclerviewDiscount) {
            currentPage += 1
            binding?.progressLoadingMore?.isVisible = true
            discountPresenter.getDiscount(context, currentPage, type)
        }
        context?.let {
            val listOptionDropDownType: Array<String> =
                it.resources.getStringArray(R.array.list_type_of_discount)
            val spinnerAdapter = ArrayAdapter<String>(
                requireContext(),
                DefaultSource.support_simple_spinner_dropdown_item,
                listOptionDropDownType
            )
            spinnerAdapter.setDropDownViewResource(DefaultSource.support_simple_spinner_dropdown_item)
            binding?.spinnerChooseType?.adapter = spinnerAdapter
        }

    }

    override fun initEvent() {
        binding?.spinnerChooseType?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {
                    type = position
                    currentPage = DEFAULT_PAGE
                    listAdapter.submitList(mutableListOf())
                    discountPresenter.getDiscount(context, currentPage, type)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // late impl
                }
            }
        binding?.imgAdd?.setOnClickListener {
            val fragment = CreateDiscountFragment()
            activity?.navigate(fragment)
        }
        binding?.refreshLayout?.setOnRefreshListener {
            currentPage = DEFAULT_PAGE
            listAdapter.submitList(mutableListOf())
            discountPresenter.getDiscount(context, currentPage, type)
        }
    }

    override fun getDiscountSuccess(discounts: List<Discount>) {
        if (listAdapter.currentList.isEmpty() && discounts.isEmpty()) {
            visibleError()
            binding?.textViewGetDiscountFailed?.text =
                context?.getString(R.string.mess_list_discount_empty)
        } else {
            val newList = listAdapter.currentList.toMutableList()
            newList.addAll(discounts)
            listAdapter.submitList(newList)
            binding?.apply {
                progressLoadingMore.isVisible = false
                textViewGetDiscountFailed.isVisible = false
                recyclerviewDiscount.isVisible = true
            }
        }
        binding?.refreshLayout?.isRefreshing = false;
    }

    override fun getDiscountFailed(message: String?) {
        context?.toast(message)
        visibleError()
        binding?.refreshLayout?.isRefreshing = false;
    }

    private fun visibleError() {
        binding?.apply {
            textViewGetDiscountFailed.isVisible = true
            recyclerviewDiscount.isVisible = false
            progressLoadingMore.isVisible = false
        }
    }
}
