package com.mgijon.baseapp.example.custom

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mgijon.baseapp.R
import com.mgijon.baseapp.databinding.RecyclerviewWithSearchCustomLayoutBinding
import com.mgijon.baseapp.example.util.animateTranslationY

class RecyclerViewWithSearchCustom : ConstraintLayout {

    private val binding: RecyclerviewWithSearchCustomLayoutBinding =
        RecyclerviewWithSearchCustomLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    private var searchBarisVisible = false

    private var openDuration = 500
    private var closeDuration = 500

    constructor(context: Context) : super(context) {
        initialize(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(attrs)
    }

    @SuppressLint("Recycle")
    private fun initialize(attrs: AttributeSet?) {
        val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.RecyclerViewWithSearchCustom, 0, 0)
        if (attrs == null) {
            return
        }
        openDuration = a.getInteger(R.styleable.RecyclerViewWithSearchCustom_openDuration, 500)
        closeDuration = a.getInteger(R.styleable.RecyclerViewWithSearchCustom_closeDuration, 500)
    }

    fun setAdapter(
        adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
        endList: () -> Unit,
        startList: () -> Unit,
        moveDown: () -> Unit,
        onClickSearch: () -> Unit,
        onClickClear: () -> Unit
    ) {
        binding.apply {
            recycler.adapter = adapter
            recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && !recyclerView.canScrollVertically(1)) {
                        endList.invoke()
                    }
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && !recyclerView.canScrollVertically(-1)) {
                        startList.invoke()
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        moveDown.invoke()
                    }
                }
            })
            btSearch.setOnClickListener {
                onClickSearch.invoke()
                //viewModel.filterCharacters(etSearch.text.toString())
            }
            btClear.setOnClickListener {
                onClickClear.invoke()
                binding.etSearch.text = Editable.Factory().newEditable("")
            }
        }
    }

    fun hideSearchBar() {
        if (searchBarisVisible) {
            binding.lySearch.animateTranslationY(closeDuration)
            binding.recycler.animateTranslationY(closeDuration)
            binding.etSearch.apply {
                clearFocus()
                text = Editable.Factory().newEditable("")
            }
            searchBarisVisible = false
        }
    }

    fun showSearchBar() {
        if (!searchBarisVisible) {
            binding.lySearch.animateTranslationY(openDuration, binding.lySearch.height)
            binding.recycler.animateTranslationY(openDuration, binding.lySearch.height)
            searchBarisVisible = true
        }
    }

    fun getSearchText(): String = binding.etSearch.text.toString()

    fun getEditText(): View = binding.etSearch

}