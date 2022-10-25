package com.atom.android.bookshop.ui.bill.confirm

import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atom.android.bookshop.R
import com.atom.android.bookshop.base.BaseFragment
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.data.repository.BillRepository
import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import com.atom.android.bookshop.data.source.remote.bill.BillRemoteDataSource
import com.atom.android.bookshop.databinding.FragmentBillConfirmBinding
import com.atom.android.bookshop.ui.bill.BillFragment
import com.atom.android.bookshop.ui.bill.detail.BillDetailFragment
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

    val listAdapter = ListAdapterBillConfirm(
        { bill: Bill ->
            run {
                navigateToDetailsFragment(bill)
            }
        },
        { bill: Bill -> billConfirmPresenter.confirmShippingBill(context, bill) },
        { bill: Bill -> billConfirmPresenter.destroyBill(context, bill) }
    )

    override fun initData() {
        billConfirmPresenter.getBillConfirm(context, currentPage)
    }

    override fun initView() {
        binding?.recyclerviewBillConfirm?.adapter = listAdapter
    }

    override fun initEvent() {
        binding?.recyclerviewBillConfirm?.apply {
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
                        billConfirmPresenter.getBillConfirm(context, currentPage)
                    }
                }
            })
        }
    }

    override fun getBillConfirmSuccess(bill: List<Bill>) {
        if (listAdapter.currentList.isEmpty() && bill.isEmpty()) {
            visibleError()
            binding?.textViewGetBillFailed?.text = context?.getString(R.string.mess_list_bill_empty)
        } else {
            listAdapter.addList(bill)
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
        const val NAME = "Chuẩn bị"
    }
}
