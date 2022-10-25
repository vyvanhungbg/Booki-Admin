package com.atom.android.bookshop.ui.main

import android.content.Context
import com.atom.android.bookshop.data.repository.MainRepository
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject
import com.atom.android.bookshop.utils.SharedPreferenceUtils
import com.atom.android.bookshop.utils.getTokenLogin

class MainPresenter(
    private val mainRepository: MainRepository,
    private val mainView: MainContract.View
) : MainContract.Presenter {

    override fun checkToken(context: Context?) {
        val token = SharedPreferenceUtils.getInstance(context)?.getTokenLogin()
        mainRepository.checkToken(token, object : IRequestCallback<ResponseObject<String>> {
            override fun onSuccess(responseObject: ResponseObject<String>) {
                mainView.checkTokenSuccess(responseObject.message)
            }

            override fun onFailed(message: String?) {
                mainView.checkTokenFailed(message)
            }
        })
    }

    override fun onStart() {
        // TODO implement later
    }

    override fun onStop() {
        // TODO implement later
    }

    companion object {
        private var instance: MainPresenter? = null
        fun getInstance(repository: MainRepository, view: MainContract.View) =
            synchronized(this) {
                instance ?: MainPresenter(repository, view).also {
                    instance = it
                }
            }
    }
}
