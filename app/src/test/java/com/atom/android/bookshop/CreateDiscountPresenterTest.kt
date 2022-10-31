package com.atom.android.bookshop

import android.content.Context
import android.content.SharedPreferences
import com.atom.android.bookshop.data.model.Discount
import com.atom.android.bookshop.data.model.DiscountEntity
import com.atom.android.bookshop.data.repository.DiscountRepository
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject
import com.atom.android.bookshop.ui.discount.adddiscount.CreateDiscountContract
import com.atom.android.bookshop.ui.discount.adddiscount.CreateDiscountPresenter
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class CreateDiscountPresenterTest {

    private val view = mockk<CreateDiscountContract.View>(relaxed = true)
    private val repository = mockk<DiscountRepository>()
    private val sharedPreferences = mockk<SharedPreferences>(relaxed = true)
    private val presenter = CreateDiscountPresenter(repository, sharedPreferences)
    private val callback = slot<IRequestCallback<ResponseObject<Discount>>>()
    private var context = mockk<Context>(relaxed = true)

    @Before
    fun setView() {
        presenter.setView(view)
    }

    @Test
    fun `create discount success`() {
        val discount = mockk<Discount>()
        val discountEntity = mockk<DiscountEntity>()
        val responseObject = ResponseObject<Discount>(true, "", discount)
        val token = ""
        every {
            repository.createDiscount(token, discountEntity, capture(callback))
        } answers {
            callback.captured.onSuccess(responseObject)
        }
        presenter.createDiscount(context, discountEntity)
        verify(exactly = 1) {
            view.createDiscountSuccess(responseObject.data as Discount)
        }
    }

    @Test
    fun `create discount failed`() {
        val discount = mockk<Discount>()
        val discountEntity = mockk<DiscountEntity>()
        val responseObject = ResponseObject<Discount>(true, "", discount)
        val token = ""
        every {
            repository.createDiscount(token, discountEntity, capture(callback))
        } answers {
            callback.captured.onFailed(responseObject.message)
        }
        presenter.createDiscount(context, discountEntity)
        verify(exactly = 1) {
            view.createDiscountFailed(responseObject.message)
        }
    }

    /*@Test
    fun `validate discount failed`() {
        val discountEntity = DiscountEntity(
            "123", 123.0, "123", 12, "", "2022-10-23T10:35:58Z", "2022-10-23T10:35:58Z", 1,
            mutableListOf<Int>()
        )
        val fakeDiscount = spyk(discountEntity)
        every {
            presenter.validateDiscount(context, discountEntity)
        } answers {
            view.validateDiscountFailed("")
        }
        presenter.validateDiscount(context, discountEntity)
        verify(exactly = 1) {
            view.validateDiscountFailed("")
        }
    }*/

}
