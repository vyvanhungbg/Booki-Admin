package com.atom.android.bookshop.ui.bill.pending

import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atom.android.bookshop.R
import com.atom.android.bookshop.base.BaseFragment
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.data.repository.BillRepository
import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import com.atom.android.bookshop.data.source.remote.bill.BillRemoteDataSource
import com.atom.android.bookshop.databinding.FragmentBillPendingBinding
import com.atom.android.bookshop.ui.bill.BillFragment
import com.atom.android.bookshop.ui.bill.detail.BillDetailFragment
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
            this
        )
    }

    private val listAdapter = ListAdapterBillPending(
        { bill: Bill ->
            run {
                navigateToDetailsFragment(bill)
            }
        },
        { bill: Bill -> billPendingPresenter.confirmBill(context, bill) },
        { bill: Bill -> billPendingPresenter.destroyBill(context, bill) }
    )

    override fun initData() {
        billPendingPresenter.getBillPending(context, currentPage)
    }

    override fun initView() {
        binding?.recyclerviewBillPending?.apply {
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
                        billPendingPresenter.getBillPending(context, currentPage)
                    }
                }
            })
        }
    }

    override fun initEvent() {
        // late impl
    }

    override fun getBillPendingSuccess(bill: List<Bill>) {
        if (listAdapter.currentList.isEmpty() && bill.isEmpty()) {
            visibleError()
            binding?.textViewGetBillFailed?.text = context?.getString(R.string.mess_list_bill_empty)
        } else {
            listAdapter.addList(bill)
            binding?.progressLoadingMore?.isVisible = false
        }
    }

    private fun visibleError() {
        binding?.apply {
            textViewGetBillFailed?.isVisible = true
            recyclerviewBillPending?.isVisible = false
            progressLoadingMore?.isVisible = false
        }
    }

    override fun getBillPendingFailed(message: String?) {
        visibleError()
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
        listAdapter.removeItem(oldBill)
        context?.toast(message)
    }

    private fun navigateToDetailsFragment(bill: Bill) {
        val fragmentDetail = BillDetailFragment.newInstance(bill)
        val beginTransaction = activity?.supportFragmentManager?.beginTransaction()
        beginTransaction?.replace(R.id.fragment_container, fragmentDetail)
            ?.addToBackStack(null)?.commit()
    }

    companion object {
        const val NAME = "Đơn chờ"
    }
}
