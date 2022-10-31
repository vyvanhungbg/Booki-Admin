package com.atom.android.bookshop.ui.authentication.login

import android.app.Dialog
import android.content.Intent
import com.atom.android.bookshop.base.BaseFragment
import com.atom.android.bookshop.data.repository.LoginRepository
import com.atom.android.bookshop.data.source.local.LoginLocalDataSource
import com.atom.android.bookshop.data.source.remote.login.LoginRemoteDataSource
import com.atom.android.bookshop.databinding.FragmentLoginBinding
import com.atom.android.bookshop.ui.authentication.AuthenticationActivity
import com.atom.android.bookshop.ui.main.MainActivity
import com.atom.android.bookshop.utils.start
import com.atom.android.bookshop.utils.toast

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate),
    LoginContract.View {

    private val loginPresenter by lazy {
        LoginPresenter.getInstance(
            LoginRepository.getInstance(
                LoginLocalDataSource.getInstance(),
                LoginRemoteDataSource.getInstance()
            )
        )
    }

    private val progessBar by lazy {
        Dialog(requireContext())
    }

    override fun initData() {
        loginPresenter.setView(this)
    }

    override fun initView() {
        // TODO implement later
    }

    override fun initEvent() {
        binding?.textViewForgotPassword?.setOnClickListener {
            val authenticationActivity = activity as AuthenticationActivity
            authenticationActivity.navigate(AuthenticationActivity.POSITION_FRAGMENT_FORGOT_PASSWORD)
        }
        binding?.btnLogin?.setOnClickListener {
            progessBar.start(true)
            val email = binding?.textInputLayoutEmail?.editText?.text.toString()
            val password = binding?.textInputLayoutPassword?.editText?.text.toString()
            loginPresenter.login(context, email, password)
        }
    }

    override fun loginSuccess(token: String?) {
        loginPresenter.saveToken(context, token)
        progessBar.dismiss()
        navigateToMainActivity()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    override fun loginFailed(message: String?) {
        progessBar.dismiss()
        context?.toast(message)
    }
}
