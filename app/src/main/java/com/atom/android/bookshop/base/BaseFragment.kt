package com.atom.android.bookshop.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

typealias Inflate<T> = (LayoutInflater) -> T

abstract class BaseFragment<VBinding : ViewBinding>(private val bindingLayoutInflater: Inflate<VBinding>) :
    Fragment() {

    private var _binding: VBinding? = null
    protected val binding: VBinding? get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingLayoutInflater(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
    }

    abstract fun initData()

    abstract fun initView()

    abstract fun initEvent()
}
