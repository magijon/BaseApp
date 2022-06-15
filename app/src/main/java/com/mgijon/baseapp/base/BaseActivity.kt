package com.mgijon.baseapp.base

import com.mgijon.baseapp.databinding.ActivityMainBinding

interface BaseActivity<B> {
    fun loading(boolean: Boolean)
    var binding: ActivityMainBinding

    fun setTitleStatusBar(title: String)
    fun showTitle()
    fun hideTitle()
}
