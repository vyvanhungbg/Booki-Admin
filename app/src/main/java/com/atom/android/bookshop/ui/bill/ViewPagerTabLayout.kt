package com.atom.android.bookshop.ui.bill

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.atom.android.bookshop.ui.bill.confirm.BillConfirmFragment
import com.atom.android.bookshop.ui.bill.delivery.BillDeliveryFragment
import com.atom.android.bookshop.ui.bill.pending.BillPendingFragment
import com.atom.android.bookshop.ui.bill.success.BillSuccessFragment

class ViewPagerTabLayout(
    private val fm: FragmentManager,
    private val behavior: Int,
    private val fragments: List<Fragment>
) :
    FragmentPagerAdapter(fm, behavior) {

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (fragments[position]) {
            is BillConfirmFragment -> BillConfirmFragment.NAME
            is BillDeliveryFragment -> BillDeliveryFragment.NAME
            is BillSuccessFragment -> BillSuccessFragment.NAME
            else -> BillPendingFragment.NAME
        }
    }
}
