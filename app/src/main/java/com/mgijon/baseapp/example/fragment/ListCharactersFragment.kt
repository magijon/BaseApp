package com.mgijon.baseapp.example.fragment

import android.text.Editable
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.mgijon.baseapp.R
import com.mgijon.baseapp.base.BaseFragment
import com.mgijon.baseapp.databinding.FragmentListCharacterBinding
import com.mgijon.baseapp.example.activity.MainActivity
import com.mgijon.baseapp.example.adapter.AdapterListCharacter
import com.mgijon.baseapp.example.model.StateBase
import com.mgijon.baseapp.example.model.StateBase.CharacterListState
import com.mgijon.baseapp.example.util.ConstantsNavigation.CHARACTER_ID
import com.mgijon.baseapp.example.util.animateTranslationY
import com.mgijon.baseapp.example.viewmodel.ListCharactersViewModel
import dagger.hilt.android.AndroidEntryPoint


@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class ListCharactersFragment : BaseFragment<ListCharactersViewModel, FragmentListCharacterBinding, MainActivity>() {

    override val viewModel: ListCharactersViewModel by viewModels()

    override val title: Int = R.string.marvel_title

    override fun getViewBinding(): FragmentListCharacterBinding = FragmentListCharacterBinding.inflate(layoutInflater)

    private lateinit var adapter: AdapterListCharacter

    private var searchBarisVisible = false

    private val openDuration = 500
    private val closeDuration = 500
    private val destiny = 130

    private val navigateTo: (String) -> Unit = { id ->
        if (!isLoading()) {
            hideSearchBar()
            view?.let {
                Navigation.findNavController(it)
                    .navigate(
                        R.id.action_listCharactersFragment_to_characterFragment,
                        bundleOf(CHARACTER_ID to id)
                    )
            }
        }
    }

    private val remove: (String) -> Unit = { id ->
        if (!isLoading()) {
            viewModel.removeVisibility(id)
        }
    }

    override fun initViews() {
        adapter = AdapterListCharacter(navigateTo, remove)
        binding.apply {
            rvCharacters.adapter = adapter
            rvCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == SCROLL_STATE_IDLE && !recyclerView.canScrollVertically(1) && !viewModel.isFilter) {
                        viewModel.getCharacters()
                    }
                    if (newState == SCROLL_STATE_IDLE && !recyclerView.canScrollVertically(-1)) {
                        showSearchBar()
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0 && !viewModel.isFilter)
                        hideSearchBar()
                }
            })
            btSearch.setOnClickListener {
                viewModel.filterCharacters(etSearch.text.toString())
            }
            btClear.setOnClickListener {
                binding.etSearch.text = Editable.Factory().newEditable("")
                viewModel.filterCharacters("")
            }
        }
    }

    private fun showSearchBar() {
        if (!searchBarisVisible) {
            binding.lySearch.animateTranslationY(openDuration, binding.lySearch.height)
            binding.rvCharacters.animateTranslationY(openDuration, binding.lySearch.height)
            searchBarisVisible = true
        }
    }

    private fun hideSearchBar() {
        if (searchBarisVisible) {
            binding.lySearch.animateTranslationY(closeDuration)
            binding.rvCharacters.animateTranslationY(closeDuration)
            binding.etSearch.apply {
                clearFocus()
                text = Editable.Factory().newEditable("")
            }
            searchBarisVisible = false
            hideKeyboard(requireActivity(), binding.etSearch)
        }
    }

    override fun initObservers() {
        viewModel.state.let {
            runGenericState(it) {
                when (it.value) {
                    is CharacterListState -> {
                        adapter.setCharacters((it as LiveData<CharacterListState>).value?.characters?.filter { character -> character.visible }
                            ?.toMutableList())
                    }
                    is StateBase.NewCharacterListState -> {
                        adapter.updateCharacters((it as LiveData<StateBase.NewCharacterListState>).value?.characters?.toMutableList())
                    }
                    else -> {}
                }
            }
        }
    }
}
