package com.atom.android.bookshop.ui.authentication.forgotpassword

import android.content.Context
import com.atom.android.bookshop.R
import com.atom.android.bookshop.data.repository.ForgotPasswordRepository
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject
import com.atom.android.bookshop.utils.isEmail

class ForgotPasswordPresenter(
    private val repository: ForgotPasswordRepository,
    private val forgotPasswordView: ForgotPasswordContract.View
) : ForgotPasswordContract.Presenter {

    override fun requestForgotPassword(context: Context?, email: String) {

        if (validateEmail(context, email)) {
            forgotPasswordView.showProgressBar()
            repository.requestForgotPassword(
                email,
                object : IRequestCallback<ResponseObject<String>> {
                    override fun onSuccess(responseObject: ResponseObject<String>) {
                        forgotPasswordView.hideProgressBar()
                        forgotPasswordView.requestSuccess(responseObject.message)
                    }

                    override fun onFailed(message: String?) {
                        forgotPasswordView.hideProgressBar()
                        forgotPasswordView.requestFailed(message)
                    }
                }
            )
        }
    }

    private fun validateEmail(context: Context?, email: String?): Boolean {
        if (!isEmail(email) || email.isNullOrEmpty()) {
            val errorValidateEmail = context?.getString(R.string.text_error_validate_email)
            forgotPasswordView.requestFailed(errorValidateEmail)
            return false
        }
        return true
    }

    override fun onStart() {
        // TODO implement later
    }

    override fun onStop() {
        // TODO implement later
    }

    companion object {
        private var instance: ForgotPasswordPresenter? = null
        fun getInstance(
            repository: ForgotPasswordRepository,
            forgotPasswordView: ForgotPasswordContract.View
        ) = synchronized(this) {
            instance ?: ForgotPasswordPresenter(repository, forgotPasswordView).also {
                instance = it
            }
        }
    }
}
