package com.atom.android.bookshop

import android.content.Context
import com.atom.android.bookshop.data.repository.MainRepository
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject
import com.atom.android.bookshop.ui.main.MainContract
import com.atom.android.bookshop.ui.main.MainPresenter
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class MainPresenterTest {

    private val view = mockk<MainContract.View>(relaxed = true)
    private val mainRepository = mockk<MainRepository>()
    private val mainPresenter = MainPresenter(mainRepository)
    private val callback = slot<IRequestCallback<ResponseObject<String>>>()
    private var context = mockk<Context>(relaxed = true)

    @Before
    fun setView() {
        mainPresenter.setView(view)
    }

    @Test
    fun `check token good`() {
        val responseObjectString = ResponseObject<String>(true, "", "")
        val token = ""
        every {
            mainRepository.checkToken(token, capture(callback))
        } answers {
            callback.captured.onSuccess(responseObjectString)
        }

        mainPresenter.checkToken(context)

        verify {
            view.checkTokenSuccess(responseObjectString.message)
        }
    }

    @Test
    fun `check token failed`() {
        val message = "failed"
        val token = ""
        every {
            mainRepository.checkToken(token, capture(callback))
        } answers {
            callback.captured.onFailed(message)
        }

        mainPresenter.checkToken(context)

        verify {
            view.checkTokenFailed(message)
        }
    }

}
