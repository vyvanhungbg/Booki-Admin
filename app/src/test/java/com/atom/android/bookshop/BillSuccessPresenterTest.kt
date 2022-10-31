package com.atom.android.bookshop

import android.content.Context
import android.content.SharedPreferences
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.data.repository.BillRepository
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject
import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import com.atom.android.bookshop.ui.bill.success.BillSuccessContract
import com.atom.android.bookshop.ui.bill.success.BillSuccessPresenter
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class BillSuccessPresenterTest {

    private val view = mockk<BillSuccessContract.View>(relaxed = true)
    private val repository = mockk<BillRepository>()
    private val sharedPreferences = mockk<SharedPreferences>(relaxed = true)
    private val presenter = BillSuccessPresenter(repository, sharedPreferences)
    private val callback = slot<IRequestCallback<ResponseObject<List<Bill>>>>()
    private var context = mockk<Context>(relaxed = true)
    private val token = ""
    private val page = 1
    private val type = ApiConstants.TYPEOFBILL.SUCCESS
    private val idBill = 1

    @Before
    fun setView() {
        presenter.setView(view)
    }

    @Test
    fun `get bill success`() {
        val responseObject = ResponseObject<List<Bill>>(true, "", mutableListOf())
        println(repository)
        every {
            repository.getBill(token, page, type, capture(callback))
        } answers {
            callback.captured.onSuccess(responseObject)
        }
        presenter.getBillSuccess(context, page)
        verify(exactly = 1) {
            view.getBillSuccess(responseObject.data as List<Bill>)
        }
    }

    @Test
    fun `get bill failed`() {
        val responseObject = ResponseObject<List<Bill>>(true, "", null)
        every {
            repository.getBill(token, page, type, capture(callback))
        } answers {
            callback.captured.onFailed(responseObject.message)
        }
        presenter.getBillSuccess(context, page)
        verify(exactly = 1) {
            view.getBillFailed(responseObject.message)
        }
    }
}
