package com.atom.android.bookshop

import android.content.Context
import com.atom.android.bookshop.data.repository.ForgotPasswordRepository
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject
import com.atom.android.bookshop.ui.authentication.forgotpassword.ForgotPasswordContract
import com.atom.android.bookshop.ui.authentication.forgotpassword.ForgotPasswordPresenter
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class ForgotPasswordTest {

    private val view = mockk<ForgotPasswordContract.View>(relaxed = true)
    private val repository = mockk<ForgotPasswordRepository>()
    private val presenter = ForgotPasswordPresenter(repository)
    private val callback = slot<IRequestCallback<ResponseObject<String>>>()
    private var context = mockk<Context>(relaxed = true)
    private val email = "vyvanhung2882001bg@gmail.com"

    @Before
    fun setView() {
        presenter.setView(view)
    }

    @Test
    fun `forgot password success`() {
        val responseObjectString = ResponseObject<String>(true, "", "")
        every {
            repository.requestForgotPassword(email, capture(callback))
        } answers {
            callback.captured.onSuccess(responseObjectString)
        }
        presenter.requestForgotPassword(context, email)
        verify {
            view.requestSuccess(responseObjectString.data)
        }
    }

    @Test
    fun `forgot password failed`() {
        val responseObjectString = ResponseObject<String>(true, "", "")
        every {
            repository.requestForgotPassword(email, capture(callback))
        } answers {
            callback.captured.onFailed(responseObjectString.message)
        }
        presenter.requestForgotPassword(context, email)
        verify {
            view.requestFailed(responseObjectString.message)
        }
    }

    @Test
    fun `forgot password success show progress bar`() {
        val responseObjectString = ResponseObject<String>(true, "", "")
        every {
            repository.requestForgotPassword(email, capture(callback))
        } answers {
            callback.captured.onSuccess(responseObjectString)
        }
        presenter.requestForgotPassword(context, email)
        verify {
            view.showProgressBar()
        }
    }

    @Test
    fun `forgot password failed show progress bar`() {
        val responseObjectString = ResponseObject<String>(true, "", "")
        every {
            repository.requestForgotPassword(email, capture(callback))
        } answers {
            callback.captured.onFailed(responseObjectString.message)
        }
        presenter.requestForgotPassword(context, email)
        verify {
            view.showProgressBar()
        }
    }

    @Test
    fun `forgot password success hide progress bar`() {
        val responseObjectString = ResponseObject<String>(true, "", "")
        every {
            repository.requestForgotPassword(email, capture(callback))
        } answers {
            callback.captured.onSuccess(responseObjectString)
        }
        presenter.requestForgotPassword(context, email)
        verify {
            view.hideProgressBar()
        }
    }

    @Test
    fun `forgot password failed hide progress bar`() {
        val responseObjectString = ResponseObject<String>(true, "", "")
        every {
            repository.requestForgotPassword(email, capture(callback))
        } answers {
            callback.captured.onFailed(responseObjectString.message)
        }
        presenter.requestForgotPassword(context, email)
        verify {
            view.hideProgressBar()
        }
    }
}
