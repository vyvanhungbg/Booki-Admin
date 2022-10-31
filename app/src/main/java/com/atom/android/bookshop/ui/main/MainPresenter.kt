package com.atom.android.bookshop.ui.main

import android.content.Context
import com.atom.android.bookshop.data.repository.MainRepository
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject
import com.atom.android.bookshop.utils.SharedPreferenceUtils
import com.atom.android.bookshop.utils.getTokenLogin

class MainPresenter(
    private val mainRepository: MainRepository
) : MainContract.Presenter {

    private var mainView: MainContract.View? = null
    override fun checkToken(context: Context?) {
        val token = SharedPreferenceUtils.getInstance(context)?.getTokenLogin()
        mainRepository.checkToken(token, object : IRequestCallback<ResponseObject<String>> {
            override fun onSuccess(responseObject: ResponseObject<String>) {
                mainView?.checkTokenSuccess(responseObject.message)
            }

            override fun onFailed(message: String?) {
                mainView?.checkTokenFailed(message)
            }
        })
    }

    override fun setView(view: MainContract.View) {
        mainView = view
    }

    companion object {
        private var instance: MainPresenter? = null
        fun getInstance(repository: MainRepository) = synchronized(this) {
            instance ?: MainPresenter(repository).also {
                instance = it
            }
        }
    }
}
