package com.mgijon.baseapp.example.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mgijon.baseapp.R
import com.mgijon.baseapp.databinding.ActivityMainBinding
import com.mgijon.baseapp.example.fragment.ListCharactersFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BaseActivity {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        val fragment = ListCharactersFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fcMain, fragment, fragment.javaClass.name).commit()

        setTitleStatusBar(R.string.marvel_title)
        Glide.with(baseContext)
            .load(R.drawable.loader)
            .into(binding.ivLoader)
    }

    fun setTitleStatusBar(title: Int) {
        binding.blTop.tvTitleFragment.text = getString(title)
    }

    override fun loading(boolean: Boolean){
        binding.ivLoader.visibility = if (boolean) View.VISIBLE else View.GONE
    }


}