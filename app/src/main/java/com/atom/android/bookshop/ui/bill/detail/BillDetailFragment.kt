package com.atom.android.bookshop.ui.bill.detail

import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.atom.android.bookshop.R
import com.atom.android.bookshop.base.BaseFragment
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.data.model.OrderLine
import com.atom.android.bookshop.databinding.FragmentBillDetailBinding
import com.atom.android.bookshop.ui.main.MainActivity
import com.atom.android.bookshop.utils.Constants
import com.atom.android.bookshop.utils.convertStrToMoney
import com.atom.android.bookshop.utils.toast


class BillDetailFragment :
    BaseFragment<FragmentBillDetailBinding>(FragmentBillDetailBinding::inflate),
    BillDetailContract.View {

    private var bill: Bill? = null
    private val listAdapter = ListAdapterBillDetail { orderLine: OrderLine -> {} }
    private val billDetailPresenter by lazy { BillDetailPresenter.getInstance() }
    override fun initData() {
        billDetailPresenter.setView(this)
        billDetailPresenter.getBill(arguments)
    }

    override fun initView() {
        bill?.let {
            binding?.apply {
                recyclerviewItem.adapter = listAdapter
                textViewInfoDelivery.text = it.shippingMethod.name
                textViewInfoLocation.text = it.address
                textTotalPrice.text = it.totalPriceOfItems().toString().convertStrToMoney()
                textViewTotalBill.text = it.totalBill().toString().convertStrToMoney()
                textViewPriceShip.text = it.shippingMethod.cost.toString().convertStrToMoney()
                textViewInfoPhone.text = it.phone
                if (it.getTimeOrder() != Constants.DEFAULT_STRING) {
                    layoutTimeOrder.isVisible = true
                    textViewTimeOrder.text = it.getTimeOrder()
                }
                if (it.getTimeConfirmed() != Constants.DEFAULT_STRING) {
                    layoutTimeConfirm.isVisible = true
                    textViewTimeConfirm.text = it.getTimeConfirmed()
                }
                if (it.getTimeDelivery() != Constants.DEFAULT_STRING) {
                    layoutTimeDelivery.isVisible = true
                    textViewTimeDelivery.text = it.getTimeDelivery()
                }
                if (it.getTimeDone() != Constants.DEFAULT_STRING) {
                    layoutTimeSuccess.isVisible = true
                    textViewTimeSuccess.text = it.getTimeDone()
                }
                listAdapter.submitList(it.orderLines)
            }
        }
        (activity as? MainActivity)?.let {
            it.setVisibleNavigationBar(false)
        }
    }

    override fun initEvent() {
        binding?.imgBack?.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        binding?.btnContactCustomer?.setOnClickListener {
            billDetailPresenter.requestCall(context, bill?.phone)
        }
    }

    override fun getBillSuccess(bill: Bill) {
        this.bill = bill
    }

    override fun getBillFailed() {
        binding?.textViewError?.isVisible = true
        binding?.layoutScrollView?.isVisible = false
    }

    override fun requestCallFailed(message: String?) {
        context?.toast(R.string.text_error_request_call)
    }

    override fun onDestroy() {
        (activity as? MainActivity)?.let {
            it.setVisibleNavigationBar(true)
        }
        super.onDestroy()
    }

    companion object {
        const val EXTRA_BILL = "EXTRA_BILL"
        fun newInstance(bill: Bill): BillDetailFragment {
            val fragment = BillDetailFragment()
            fragment.arguments = bundleOf(
                EXTRA_BILL to bill
            )
            return fragment
        }
    }
}
