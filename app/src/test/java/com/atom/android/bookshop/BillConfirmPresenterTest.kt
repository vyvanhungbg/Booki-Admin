package com.atom.android.bookshop

import android.content.Context
import android.content.SharedPreferences
import com.atom.android.bookshop.data.model.Bill
import com.atom.android.bookshop.data.repository.BillRepository
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject
import com.atom.android.bookshop.data.source.remote.api.ApiConstants
import com.atom.android.bookshop.ui.bill.confirm.BillConfirmContract
import com.atom.android.bookshop.ui.bill.confirm.BillConfirmPresenter
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class BillConfirmPresenterTest {

    private val view = mockk<BillConfirmContract.View>(relaxed = true)
    private val repository = mockk<BillRepository>()
    private val sharedPreferences = mockk<SharedPreferences>(relaxed = true)
    private val presenter = BillConfirmPresenter(repository, sharedPreferences)
    private val callback = slot<IRequestCallback<ResponseObject<List<Bill>>>>()
    private val callbackUpdate = slot<IRequestCallback<ResponseObject<Bill>>>()
    private var context = mockk<Context>(relaxed = true)
    private val token = ""
    private val page = 1
    private val type = ApiConstants.TYPEOFBILL.ACCEPT
    private val status = ApiConstants.TYPEOFBILL.DELIVERY
    private val idBill = 0
    private val reason = ""
    private val bill = mockk<Bill>(relaxed = true)

    @Before
    fun setView() {
        presenter.setView(view)
    }

    @Test
    fun `get bill pending success`() {
        val responseObject = ResponseObject<List<Bill>>(true, "", mutableListOf())
        println(repository)
        every {
            repository.getBill(token, page, type, capture(callback))
        } answers {
            callback.captured.onSuccess(responseObject)
        }
        presenter.getBillConfirm(context, page)
        verify(exactly = 1) {
            view.getBillConfirmSuccess(responseObject.data as List<Bill>)
        }
    }

    @Test
    fun `get bill pending failed`() {
        val responseObject = ResponseObject<List<Bill>>(true, "", null)
        every {
            repository.getBill(token, page, type, capture(callback))
        } answers {
            callback.captured.onFailed(responseObject.message)
        }
        presenter.getBillConfirm(context, page)
        verify(exactly = 1) {
            view.getBillConfirmFailed(responseObject.message)
        }
    }

    @Test
    fun `request confirm bill failed`() {
        val responseObject = ResponseObject<Bill>(true, "", null)
        every {
            repository.updateStatusBill(token, idBill, status, reason, capture(callbackUpdate))
        } answers {
            callbackUpdate.captured.onFailed(responseObject.message)
        }
        presenter.confirmShippingBill(context, bill)
        verify(exactly = 1) {
            view.requestFailed(responseObject.message)
        }
    }

    @Test
    fun `request confirm bill success`() {
        val responseObject = ResponseObject<Bill>(true, "", bill)
        every {
            repository.updateStatusBill(token, idBill, status, reason, capture(callbackUpdate))
        } answers {
            callbackUpdate.captured.onSuccess(responseObject)
        }
        presenter.confirmShippingBill(context, bill)
        verify(exactly = 1) {
            view.requestSuccess(bill, responseObject.data as Bill, responseObject.message)
        }
    }

    /*@Test
    fun `request destroy bill success`() {
        val responseObject = ResponseObject<Bill>(true, "", bill)
        every {
            repository.updateStatusBill(
                token,
                idBill,
                ApiConstants.TYPEOFBILL.DESTROY,
                reason,
                capture(callbackUpdate)
            )
        } answers {
            callbackUpdate.captured.onSuccess(responseObject)
        }

        presenter.destroyBill(context, bill)
        verify(exactly = 1) {
            view.requestSuccess(bill, responseObject.data as Bill, responseObject.message)
        }
    }

    @Test
    fun `request destroy bill failed`() {
        val responseObject = ResponseObject<Bill>(true, "", bill)
        every {
            repository.updateStatusBill(
                token,
                idBill,
                ApiConstants.TYPEOFBILL.DESTROY,
                reason,
                capture(callbackUpdate)
            )
        } answers {
            callbackUpdate.captured.onFailed(responseObject.message)
        }

        presenter.destroyBill(context, bill)
        verify(exactly = 1) {
            view.requestFailed(responseObject.message)
        }
    }*/
}
