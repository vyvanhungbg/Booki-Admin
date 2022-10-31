package com.atom.android.bookshop.ui.bill.success

import androidx.core.view.isVisible
import com.atom.android.bookshop.R
import com.atom.android.bookshop.base.BaseFragment
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.data.repository.BillRepository
import com.atom.android.bookshop.data.source.remote.bill.BillRemoteDataSource
import com.atom.android.bookshop.databinding.FragmentBillSuccessBinding
import com.atom.android.bookshop.ui.bill.detail.BillDetailFragment
import com.atom.android.bookshop.utils.Constants
import com.atom.android.bookshop.utils.SharedPreferenceUtils
import com.atom.android.bookshop.utils.toast

class BillSuccessFragment :
    BaseFragment<FragmentBillSuccessBinding>(FragmentBillSuccessBinding::inflate),
    BillSuccessContract.View {

    private var currentPage = 1
    private val billSuccessPresenter by lazy {
        BillSuccessPresenter.getInstance(
            BillRepository.getInstance(
                BillRemoteDataSource.getInstance()
            ),
            SharedPreferenceUtils.getInstance(context)
        )
    }

    private val listAdapter = ListAdapterBillSuccess { bill: Bill ->
        run {
            navigateToDetailsFragment(bill)
        }
    }

    override fun initData() {
        billSuccessPresenter.setView(this)
        billSuccessPresenter.getBillSuccess(context, currentPage)
    }

    override fun initView() {
        binding?.recyclerviewBillSuccess?.adapter = listAdapter
    }

    override fun initEvent() {
        listAdapter.loadMore(binding?.recyclerviewBillSuccess) {
            currentPage += 1
            binding?.progressLoadingMore?.isVisible = true
            billSuccessPresenter.getBillSuccess(context, currentPage)
        }
        binding?.swiperefreshlayout?.setOnRefreshListener {
            currentPage = Constants.DEFAULT_PAGE
            listAdapter.submitList(mutableListOf())
            billSuccessPresenter.getBillSuccess(context, currentPage)
        }
    }

    override fun getBillSuccess(bill: List<Bill>) {
        if (listAdapter.currentList.isEmpty() && bill.isEmpty()) {
            visibleScreen(true)
            binding?.textViewGetBillFailed?.text = context?.getString(R.string.mess_list_bill_empty)
        } else {
            val newList = listAdapter.currentList.toMutableList()
            newList.addAll(bill)
            listAdapter.submitList(newList)
            visibleScreen(false)
        }
    }

    private fun navigateToDetailsFragment(bill: Bill) {
        val fragmentDetail = BillDetailFragment.newInstance(bill)
        val beginTransaction = activity?.supportFragmentManager?.beginTransaction()
        beginTransaction?.replace(R.id.fragment_container, fragmentDetail)
            ?.addToBackStack(null)?.commit()
    }

    private fun visibleScreen(isError: Boolean) {
        binding?.apply {
            textViewGetBillFailed.isVisible = isError
            recyclerviewBillSuccess.isVisible = !isError
            progressLoadingMore.isVisible = false
            swiperefreshlayout.isRefreshing = false
        }
    }

    override fun getBillFailed(message: String?) {
        context?.toast(message)
        visibleScreen(true)
    }

    fun updateNewBill(bill: Bill) {
        listAdapter.addItem(bill)
        visibleScreen(false)
    }

    companion object {
        const val NAME = "Đã giao"
    }
}
