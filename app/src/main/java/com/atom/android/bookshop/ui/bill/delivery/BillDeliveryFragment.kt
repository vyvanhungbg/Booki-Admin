package com.atom.android.bookshop.ui.bill.delivery

import androidx.core.view.isVisible
import com.atom.android.bookshop.R
import com.atom.android.bookshop.base.BaseFragment
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.data.repository.BillRepository
import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import com.atom.android.bookshop.data.source.remote.bill.BillRemoteDataSource
import com.atom.android.bookshop.databinding.FragmentBillDeliveryBinding
import com.atom.android.bookshop.ui.bill.BillFragment
import com.atom.android.bookshop.ui.bill.detail.BillDetailFragment
import com.atom.android.bookshop.utils.toast

class BillDeliveryFragment :
    BaseFragment<FragmentBillDeliveryBinding>(FragmentBillDeliveryBinding::inflate),
    BillDeliveryContract.View {

    private var currentPage = 1
    private val billDeliveryPresenter by lazy {
        BillDeliveryPresenter.getInstance(
            BillRepository.getInstance(
                BillRemoteDataSource.getInstance()
            ),
            this
        )
    }

    private val listAdapter = ListAdapterBillDelivery { bill, action ->
        when (action) {
            Bill.ACTION_CONFIRM -> billDeliveryPresenter.confirmDeliveryBill(context, bill)
            Bill.ACTION_CANCEL -> billDeliveryPresenter.destroyBill(context, bill)
            Bill.ACTION_ITEM -> navigateToDetailsFragment(bill)
        }
    }

    override fun initData() {
        billDeliveryPresenter.getBillDelivery(context, currentPage)
    }

    override fun initView() {
        binding?.recyclerviewBillDelivery?.adapter = listAdapter
    }

    override fun initEvent() {
        listAdapter.loadMore(binding?.recyclerviewBillDelivery) {
            currentPage += 1
            binding?.progressLoadingMore?.isVisible = true
            billDeliveryPresenter.getBillDelivery(context, currentPage)
        }
    }

    override fun getBillDeliverySuccess(bill: List<Bill>) {
        if (listAdapter.currentList.isEmpty() && bill.isEmpty()) {
            visibleError()
        } else {
            val newList = listAdapter.currentList.toMutableList()
            newList.addAll(bill)
            listAdapter.submitList(newList)
            binding?.progressLoadingMore?.isVisible = false
        }
    }

    private fun visibleError() {
        binding?.apply {
            textViewGetBillFailed?.isVisible = true
            recyclerviewBillDelivery?.isVisible = false
            progressLoadingMore?.isVisible = false
        }
    }

    override fun getBillDeliveryFailed(message: String?) {
        visibleError()
        binding?.textViewGetBillFailed?.text = context?.getString(R.string.text_get_bill_failed)
    }

    override fun requestFailed(message: String?) {
        context?.toast(message)
    }

    override fun requestSuccess(oldBill: Bill, newBill: Bill?, message: String?) {
        newBill?.let {
            val billFragment = parentFragment as BillFragment
            billFragment.updateStatusBill(it, ApiConstants.TYPEOFBILL.SUCCESS)
        }
        val newList = listAdapter.currentList.toMutableList()
        newList.remove(oldBill)
        listAdapter.submitList(newList)
        context?.toast(message)
    }

    private fun navigateToDetailsFragment(bill: Bill) {
        val fragmentDetail = BillDetailFragment.newInstance(bill)
        val beginTransaction = activity?.supportFragmentManager?.beginTransaction()
        beginTransaction?.replace(R.id.fragment_container, fragmentDetail)
            ?.addToBackStack(null)?.commit()
    }

    fun updateNewBill(bill: Bill) {
        listAdapter.addItem(bill)
    }

    companion object {
        const val NAME = "Đang giao"
    }
}
