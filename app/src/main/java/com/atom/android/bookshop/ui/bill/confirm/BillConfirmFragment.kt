package com.atom.android.bookshop.ui.bill.confirm

import androidx.core.view.isVisible
import com.atom.android.bookshop.R
import com.atom.android.bookshop.base.BaseFragment
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.data.repository.BillRepository
import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import com.atom.android.bookshop.data.source.remote.bill.BillRemoteDataSource
import com.atom.android.bookshop.databinding.FragmentBillConfirmBinding
import com.atom.android.bookshop.ui.bill.BillFragment
import com.atom.android.bookshop.ui.bill.detail.BillDetailFragment
import com.atom.android.bookshop.utils.navigate
import com.atom.android.bookshop.utils.toast

class BillConfirmFragment :
    BaseFragment<FragmentBillConfirmBinding>(FragmentBillConfirmBinding::inflate),
    BillConfirmContract.View {

    private var currentPage = 1
    private val billConfirmPresenter by lazy {
        BillConfirmPresenter.getInstance(
            BillRepository.getInstance(
                BillRemoteDataSource.getInstance()
            ),
            this
        )
    }

    private val listAdapter = ListAdapterBillConfirm { bill, action ->
        when (action) {
            Bill.ACTION_CONFIRM -> billConfirmPresenter.confirmShippingBill(context, bill)
            Bill.ACTION_CANCEL -> billConfirmPresenter.destroyBill(context, bill)
            Bill.ACTION_ITEM -> {
                val fragmentDetail = BillDetailFragment.newInstance(bill)
                activity?.navigate(fragmentDetail)
            }
        }
    }

    override fun initData() {
        billConfirmPresenter.getBillConfirm(context, currentPage)
    }

    override fun initView() {
        binding?.recyclerviewBillConfirm?.adapter = listAdapter
    }

    override fun initEvent() {
        listAdapter.loadMore(binding?.recyclerviewBillConfirm) {
            currentPage += 1
            binding?.progressLoadingMore?.isVisible = true
            billConfirmPresenter.getBillConfirm(context, currentPage)
        }
    }

    override fun getBillConfirmSuccess(bill: List<Bill>) {
        if (listAdapter.currentList.isEmpty() && bill.isEmpty()) {
            visibleError()
            binding?.textViewGetBillFailed?.text = context?.getString(R.string.mess_list_bill_empty)
        } else {
            val newList = listAdapter.currentList.toMutableList()
            newList.addAll(bill)
            listAdapter.submitList(newList)
            binding?.progressLoadingMore?.isVisible = false
        }
    }

    override fun getBillConfirmFailed(message: String?) {
        visibleError()
        binding?.textViewGetBillFailed?.text = context?.getString(R.string.text_get_bill_failed)
    }

    private fun visibleError() {
        binding?.apply {
            textViewGetBillFailed?.isVisible = true
            recyclerviewBillConfirm?.isVisible = false
            progressLoadingMore?.isVisible = false
        }
    }

    override fun requestFailed(message: String?) {
        context?.toast(message)
    }

    override fun requestSuccess(oldBill: Bill, newBill: Bill?, message: String?) {
        newBill?.let {
            val billFragment = parentFragment as BillFragment
            billFragment.updateStatusBill(it, ApiConstants.TYPEOFBILL.DELIVERY)
        }
        val newList = listAdapter.currentList.toMutableList()
        newList.remove(oldBill)
        listAdapter.submitList(newList)
        context?.toast(message)
    }

    fun updateNewBill(bill: Bill) {
        listAdapter.addItem(bill)
    }

    companion object {
        const val NAME = "Chuẩn bị"
    }
}
