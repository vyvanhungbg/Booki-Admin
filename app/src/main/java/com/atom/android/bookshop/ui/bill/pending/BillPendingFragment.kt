package com.atom.android.bookshop.ui.bill.pending

import androidx.core.view.isVisible
import com.atom.android.bookshop.R
import com.atom.android.bookshop.base.BaseFragment
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.data.repository.BillRepository
import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import com.atom.android.bookshop.data.source.remote.bill.BillRemoteDataSource
import com.atom.android.bookshop.databinding.FragmentBillPendingBinding
import com.atom.android.bookshop.ui.bill.BillFragment
import com.atom.android.bookshop.ui.bill.detail.BillDetailFragment
import com.atom.android.bookshop.utils.Constants
import com.atom.android.bookshop.utils.SharedPreferenceUtils
import com.atom.android.bookshop.utils.navigate
import com.atom.android.bookshop.utils.toast

class BillPendingFragment :
    BaseFragment<FragmentBillPendingBinding>(FragmentBillPendingBinding::inflate),
    BillPendingContract.View {

    private var currentPage = 1
    private val billPendingPresenter by lazy {
        BillPendingPresenter.getInstance(
            BillRepository.getInstance(
                BillRemoteDataSource.getInstance()
            ),
            SharedPreferenceUtils.getInstance(context)
        )
    }

    private val listAdapter = ListAdapterBillPending { bill, action ->
        when (action) {
            Bill.ACTION_CONFIRM -> billPendingPresenter.confirmBill(context, bill)
            Bill.ACTION_CANCEL -> billPendingPresenter.destroyBill(context, bill)
            Bill.ACTION_ITEM -> {
                val fragmentDetail = BillDetailFragment.newInstance(bill)
                activity?.navigate(fragmentDetail)
            }
        }
    }

    override fun initData() {
        billPendingPresenter.setView(this)
        billPendingPresenter.getBillPending(context, currentPage)
    }

    override fun initView() {
        binding?.recyclerviewBillPending?.adapter = listAdapter
    }

    override fun initEvent() {
        listAdapter.loadMore(binding?.recyclerviewBillPending) {
            currentPage += 1
            binding?.progressLoadingMore?.isVisible = true
            billPendingPresenter.getBillPending(context, currentPage)
        }
        binding?.swiperefreshlayout?.setOnRefreshListener {
            currentPage = Constants.DEFAULT_PAGE
            listAdapter.submitList(mutableListOf())
            billPendingPresenter.getBillPending(context, currentPage)
        }
    }

    override fun getBillPendingSuccess(bill: List<Bill>) {
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

    private fun visibleScreen(isError: Boolean) {
        binding?.apply {
            textViewGetBillFailed.isVisible = isError
            recyclerviewBillPending.isVisible = !isError
            progressLoadingMore.isVisible = false
            swiperefreshlayout.isRefreshing = false
        }
    }

    override fun getBillPendingFailed(message: String?) {
        context?.toast(message)
        visibleScreen(isError = true)
        binding?.textViewGetBillFailed?.text = context?.getString(R.string.text_get_bill_failed)
    }

    override fun requestFailed(message: String?) {
        context?.toast(message)
    }

    override fun requestSuccess(oldBill: Bill, newBill: Bill?, message: String?) {
        newBill?.let {
            val billFragment = parentFragment as BillFragment
            billFragment.updateStatusBill(it, ApiConstants.TYPEOFBILL.ACCEPT)
        }
        val newList = listAdapter.currentList.toMutableList()
        newList.remove(oldBill)
        listAdapter.submitList(newList)
        context?.toast(message)
        visibleScreen(false)
    }

    companion object {
        const val NAME = "Đơn chờ"
    }
}
