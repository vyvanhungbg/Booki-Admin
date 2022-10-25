package com.atom.android.bookshop.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

typealias ActivityInflate<T> = (LayoutInflater) -> T

abstract class BaseActivity<VBinding : ViewBinding>(private val bindingLayoutInflater: ActivityInflate<VBinding>) :
    AppCompatActivity() {

    private var _binding: VBinding? = null
    protected val binding: VBinding?
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingLayoutInflater.invoke(layoutInflater)
        setContentView(binding?.root)
        initView()
        initData()
        initEvent()
    }

    abstract fun initView()
    abstract fun initData()
    abstract fun initEvent()
    abstract fun navigate(position: Int)

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
