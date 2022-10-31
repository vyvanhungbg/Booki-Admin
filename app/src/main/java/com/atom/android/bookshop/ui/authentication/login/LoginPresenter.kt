package com.atom.android.bookshop.ui.authentication.login

import android.content.Context
import com.atom.android.bookshop.R
import com.atom.android.bookshop.data.repository.LoginRepository
import com.atom.android.bookshop.data.source.remote.IRequestCallback
import com.atom.android.bookshop.data.source.remote.ResponseObject
import com.atom.android.bookshop.utils.Constants
import com.atom.android.bookshop.utils.SharedPreferenceUtils
import com.atom.android.bookshop.utils.isEmail
import com.atom.android.bookshop.utils.putStringApply

class LoginPresenter(private val repository: LoginRepository) : LoginContract.Presenter {
    private var loginView: LoginContract.View? = null
    override fun login(
        context: Context?,
        email: String,
        password: String
    ) {

        if (validateEmail(context, email) && validatePassword(context, password)) {
            repository.login(email, password, object : IRequestCallback<ResponseObject<String>> {
                override fun onSuccess(responseObject: ResponseObject<String>) {
                    loginView?.loginSuccess(responseObject.data)
                }

                override fun onFailed(message: String?) {
                    loginView?.loginFailed(message)
                }
            })
        }
    }

    private fun validateEmail(context: Context?, email: String?): Boolean {
        if (!isEmail(email) || email.isNullOrEmpty()) {
            val errorValidateEmail = context?.getString(R.string.text_error_validate_email)
            loginView?.loginFailed(errorValidateEmail)
            return false
        }
        return true
    }

    private fun validatePassword(context: Context?, password: String?): Boolean {
        if (password.isNullOrEmpty()) {
            val errorValidatePassword = context?.getString(R.string.text_error_validate_password)
            loginView?.loginFailed(errorValidatePassword)
            return false
        }
        return true
    }

    override fun saveToken(context: Context?, token: String?) {
        SharedPreferenceUtils.getInstance(context)
            ?.putStringApply(Constants.SHARED_PREF_TOKEN_LOGIN, token)
    }

    override fun setView(view: LoginContract.View) {
        loginView = view
    }

    companion object {
        private var instance: LoginPresenter? = null
        fun getInstance(repository: LoginRepository) = synchronized(this) {
            instance ?: LoginPresenter(repository).also {
                instance = it
            }
        }
    }
}
