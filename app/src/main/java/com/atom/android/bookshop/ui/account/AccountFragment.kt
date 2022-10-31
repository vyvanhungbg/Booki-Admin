package com.atom.android.bookshop.ui.account

import android.content.Intent
import androidx.core.net.toUri
import com.atom.android.bookshop.R
import com.atom.android.bookshop.base.BaseFragment
import com.atom.android.bookshop.data.model.User
import com.atom.android.bookshop.data.repository.AccountRepository
import com.atom.android.bookshop.data.source.remote.account.AccountRemoteDataSource
import com.atom.android.bookshop.databinding.FragmentAccountBinding
import com.atom.android.bookshop.ui.authentication.AuthenticationActivity
import com.atom.android.bookshop.utils.loadUserImage
import com.atom.android.bookshop.utils.toast

class AccountFragment : BaseFragment<FragmentAccountBinding>(FragmentAccountBinding::inflate),
    AccountContract.View {

    private val accountPresenter by lazy {
        AccountPresenter.getInstance(
            AccountRepository.getInstance(
                AccountRemoteDataSource.getInstance()
            )
        )
    }

    override fun initData() {
        accountPresenter.setView(this)
        accountPresenter.getUser(context)
    }


    override fun initView() {
        // TODO implement later
    }

    override fun initEvent() {
        binding?.btnLogout?.setOnClickListener {
            accountPresenter.destroyToken(context)
            navigateAuthenticationActivity()
        }
    }

    private fun navigateAuthenticationActivity() {
        val intent = Intent(activity, AuthenticationActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    override fun getUserSuccess(mUser: User) {
        binding?.textViewNameUser?.text = mUser.name
        binding?.imageUser?.loadUserImage(mUser.image.toUri())
    }

    override fun getUserFailed(message: String?) {
        context?.toast(message)
        binding?.btnLogout?.text = getString(R.string.text_login)
    }
}
