package com.atom.android.bookshop.ui.bill.delivery

import android.app.AlertDialog
import androidx.core.view.isVisible
import com.atom.android.bookshop.R
import com.atom.android.bookshop.base.BaseFragment
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.data.repository.BillRepository
import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import com.atom.android.bookshop.data.source.remote.bill.BillRemoteDataSource
import com.atom.android.bookshop.databinding.FragmentBillDeliveryBinding
import com.atom.android.bookshop.ui.bill.BillFragment
import com.atom.android.bookshop.ui.bill.delivery.BillDeliveryPresenter.Companion.INDEX_OF_ITEM_LOST
import com.atom.android.bookshop.ui.bill.detail.BillDetailFragment
import com.atom.android.bookshop.utils.Constants
import com.atom.android.bookshop.utils.SharedPreferenceUtils
import com.atom.android.bookshop.utils.navigate
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
            SharedPreferenceUtils.getInstance(context)
        )
    }

    private val listAdapter = ListAdapterBillDelivery { bill, action ->
        when (action) {
            Bill.ACTION_CONFIRM -> billDeliveryPresenter.confirmDeliveryBill(context, bill)
            Bill.ACTION_CANCEL -> {
                val titleAlertDialog = context?.getString(R.string.title_alert_destroy_bill)
                val listItems = context?.resources?.getStringArray(R.array.list_items_destroy_bill)
                var selectedIndex = Constants.DEFAULT_INT
                AlertDialog.Builder(context).apply {
                    setTitle(titleAlertDialog)
                    setSingleChoiceItems(listItems, selectedIndex) { _, which ->
                        selectedIndex = which
                    }
                    setPositiveButton(context?.getText(R.string.text_confirm)) { dialog, which ->
                        val destroyOrLostBill =
                            if (selectedIndex == INDEX_OF_ITEM_LOST)
                                ApiConstants.TYPEOFBILL.LOST
                            else ApiConstants.TYPEOFBILL.DESTROY
                        billDeliveryPresenter.requestDestroyBill(
                            context,
                            bill,
                            listItems?.get(selectedIndex),
                            destroyOrLostBill
                        )
                    }

                    setNegativeButton(context?.getText(R.string.text_cancel)) { dialog, which ->
                        dialog.cancel()
                    }
                    show()
                }
            }
            Bill.ACTION_ITEM -> {
                val fragmentDetail = BillDetailFragment.newInstance(bill)
                activity?.navigate(fragmentDetail)
            }
        }
    }

    override fun initData() {
        billDeliveryPresenter.setView(this)
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
        binding?.swiperefreshlayout?.setOnRefreshListener {
            currentPage = Constants.DEFAULT_PAGE
            listAdapter.submitList(mutableListOf())
            billDeliveryPresenter.getBillDelivery(context, currentPage)
        }
    }

    override fun getBillDeliverySuccess(bill: List<Bill>) {
        if (listAdapter.currentList.isEmpty() && bill.isEmpty()) {
            visibleScreen(true)
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
            recyclerviewBillDelivery.isVisible = !isError
            progressLoadingMore.isVisible = false
            swiperefreshlayout.isRefreshing = false
        }
    }

    override fun getBillDeliveryFailed(message: String?) {
        context?.toast(message)
        visibleScreen(true)
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
        visibleScreen(false)
    }

    fun updateNewBill(bill: Bill) {
        listAdapter.addItem(bill)
        visibleScreen(false)
    }

    companion object {
        const val NAME = "Đang giao"
    }
}
