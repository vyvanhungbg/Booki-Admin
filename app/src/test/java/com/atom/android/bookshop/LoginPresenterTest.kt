package com.atom.android.bookshop

import android.content.Context
import com.atom.android.bookshop.data.repository.LoginRepository
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject
import com.atom.android.bookshop.ui.authentication.login.LoginContract
import com.atom.android.bookshop.ui.authentication.login.LoginPresenter
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class LoginPresenterTest {

    private val view = mockk<LoginContract.View>(relaxed = true)
    private val repository = mockk<LoginRepository>()
    private val presenter = LoginPresenter(repository)
    private val callback = slot<IRequestCallback<ResponseObject<String>>>()
    private var context = mockk<Context>(relaxed = true)
    private val email = "vyvanhung2882001bg@gmail.com"
    private val password = "abc"

    @Before
    fun setView() {
        presenter.setView(view)
    }

    @Test
    fun `login success`() {
        val responseObjectString = ResponseObject<String>(true, "", "")
        every {
            repository.login(email, password, capture(callback))
        } answers {
            callback.captured.onSuccess(responseObjectString)
        }
        presenter.login(context, email, password)
        verify {
            view.loginSuccess(responseObjectString.data)
        }
    }

    @Test
    fun `login failed`() {
        val responseObjectString = ResponseObject<String>(true, "", "")
        every {
            repository.login(email, password, capture(callback))
        } answers {
            callback.captured.onFailed(responseObjectString.message)
        }

        presenter.login(context, email, password)

        verify {
            view.loginFailed(responseObjectString.message)
        }
    }
}