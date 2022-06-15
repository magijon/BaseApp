package com.mgijon.baseapp.example.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mgijon.baseapp.R
import com.mgijon.baseapp.base.BaseActivity
import com.mgijon.baseapp.databinding.ActivityMainBinding
import com.mgijon.baseapp.example.util.gone
import com.mgijon.baseapp.example.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BaseActivity<ActivityMainBinding> {

    override lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitleStatusBar(R.string.marvel_title)
        Glide.with(baseContext)
            .load(R.drawable.loader)
            .into(binding.ivLoader)
    }

    fun setTitleStatusBar(title: Int) {
        binding.blTop.tvTitleFragment.text = getString(title)
    }

    override fun loading(boolean: Boolean) {
        binding.lyLoader.visibility = if (boolean) View.VISIBLE else View.GONE
    }

    override fun setTitleStatusBar(title: String) {
        binding.blTop.tvTitleFragment.text = title
    }

    override fun showTitle() {
        binding.blTop.tvTitleFragment.visible()
    }

    override fun hideTitle() {
        binding.blTop.tvTitleFragment.gone()
    }

}