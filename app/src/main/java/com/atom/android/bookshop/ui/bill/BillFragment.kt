package com.atom.android.bookshop.ui.bill

import androidx.fragment.app.FragmentStatePagerAdapter
import com.atom.android.bookshop.base.BaseFragment
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import com.atom.android.bookshop.databinding.FragmentBillBinding
import com.atom.android.bookshop.ui.bill.confirm.BillConfirmFragment
import com.atom.android.bookshop.ui.bill.delivery.BillDeliveryFragment
import com.atom.android.bookshop.ui.bill.pending.BillPendingFragment
import com.atom.android.bookshop.ui.bill.success.BillSuccessFragment

class BillFragment : BaseFragment<FragmentBillBinding>(FragmentBillBinding::inflate) {

    private val pendingFragment = BillPendingFragment()
    private val confirmFragment = BillConfirmFragment()
    private val deliveryFragment = BillDeliveryFragment()
    private val successFragment = BillSuccessFragment()

    override fun initData() {
        // TODO implement later
    }

    fun updateStatusBill(bill: Bill, status: Int) {
        when (status) {
            ApiConstants.TYPEOFBILL.ACCEPT -> confirmFragment.listAdapter.addItem(bill)
            ApiConstants.TYPEOFBILL.DELIVERY -> deliveryFragment.listAdapter.addItem(bill)
            ApiConstants.TYPEOFBILL.SUCCESS -> successFragment.listAdapter.addItem(bill)
        }
    }

    override fun initView() {
        binding?.viewPagerBill?.adapter = ViewPagerTabLayout(
            childFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
            listOf(
                pendingFragment,
                confirmFragment,
                deliveryFragment,
                successFragment
            )
        )
        binding?.tabLayoutBill?.setupWithViewPager(binding?.viewPagerBill)
    }

    override fun initEvent() {
        // TODO implement later
    }
}
