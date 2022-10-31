package com.atom.android.bookshop

import android.content.Context
import android.content.SharedPreferences
import com.atom.android.bookshop.data.model.Discount
import com.atom.android.bookshop.data.repository.DiscountRepository
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject
import com.atom.android.bookshop.ui.discount.DiscountContract
import com.atom.android.bookshop.ui.discount.DiscountPresenter
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class DiscountPresenterTest {

    private val view = mockk<DiscountContract.View>(relaxed = true)
    private val repository = mockk<DiscountRepository>()
    private val sharedPreferences = mockk<SharedPreferences>(relaxed = true)
    private val presenter = DiscountPresenter(repository, sharedPreferences)
    private val callback = slot<IRequestCallback<ResponseObject<List<Discount>>>>()
    private var context = mockk<Context>(relaxed = true)

    @Before
    fun setView() {
        presenter.setView(view)
    }

    @Test
    fun `get discount success`() {
        val responseObject = ResponseObject<List<Discount>>(true, "", mutableListOf<Discount>())
        val token = ""
        val page = 1
        val type = 1
        every {
            repository.getDiscount(token, page, type, capture(callback))
        } answers {
            callback.captured.onSuccess(responseObject)
        }
        presenter.getDiscount(context, page, type)
        verify(exactly = 1) {
            view.getDiscountSuccess(responseObject.data as List<Discount>)
        }
    }

    @Test
    fun `get discount failed`() {
        val responseObject = ResponseObject<List<Discount>>(true, "", mutableListOf<Discount>())
        val token = ""
        val page = 1
        val type = 1
        every {
            repository.getDiscount(token, page, type, capture(callback))
        } answers {
            callback.captured.onFailed(responseObject.message)
        }
        presenter.getDiscount(context, page, type)
        verify(exactly = 1) {
            view.getDiscountFailed(responseObject.message)
        }
    }
}
