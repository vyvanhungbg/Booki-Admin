package com.atom.android.bookshop.ui.discount

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atom.android.bookshop.R
import com.atom.android.bookshop.base.BaseFragment
import com.atom.android.bookshop.data.model.Discount
import com.atom.android.bookshop.data.repository.DiscountRepository
import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import com.atom.android.bookshop.data.source.remote.discount.DiscountRemoteDataSource
import com.atom.android.bookshop.databinding.FragmentDiscountBinding

typealias DefaultSource = com.google.android.material.R.layout

class DiscountFragment : BaseFragment<FragmentDiscountBinding>(FragmentDiscountBinding::inflate),
    DiscountContract.View {

    private var currentPage = 1
    private var type = ApiConstants.TYPEOFDISCOUNT.RUNNING
    private val discountPresenter by lazy {
        DiscountPresenter.getInstance(
            DiscountRepository.getInstance(
                DiscountRemoteDataSource.getInstance()
            ),
            this
        )
    }

    val listAdapter = ListAdapterDiscount { discount: Discount ->
        {}

    }

    override fun initData() {
        discountPresenter.getDiscount(context, currentPage, type);
    }


    override fun initView() {
        binding?.recyclerviewDiscount?.apply {
            adapter = listAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                    val sizeData = listAdapter.itemCount - 1
                    if (linearLayoutManager != null &&
                        linearLayoutManager.findLastCompletelyVisibleItemPosition() == sizeData
                    ) {
                        currentPage += 1
                        binding?.progressLoadingMore?.isVisible = true
                        discountPresenter.getDiscount(context, currentPage, type)
                    }
                }
            })
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
                    currentPage = 1
                    listAdapter.clearAll()
                    discountPresenter.getDiscount(context, currentPage, type)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
    }

    override fun getDiscountSuccess(discounts: List<Discount>) {
        if (listAdapter.currentList.isEmpty() && discounts.isEmpty()) {
            visibleError()
            binding?.textViewGetDiscountFailed?.text =
                context?.getString(R.string.mess_list_discount_empty)
        } else {
            listAdapter.addList(discounts)
            binding?.apply {
                progressLoadingMore.isVisible = false
                textViewGetDiscountFailed.isVisible = false
                recyclerviewDiscount.isVisible = true
            }
        }
    }

    override fun getDiscountFailed(message: String?) {
        visibleError()
    }

    override fun createDiscountSuccess(discount: Discount) {
        //late impl
    }

    override fun createDiscountFailed(message: String?) {
        //late impl
    }

    private fun visibleError() {
        binding?.apply {
            textViewGetDiscountFailed?.isVisible = true
            recyclerviewDiscount?.isVisible = false
            progressLoadingMore?.isVisible = false
        }
    }

}
