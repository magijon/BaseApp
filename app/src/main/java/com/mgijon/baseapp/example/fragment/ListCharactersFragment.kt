package com.mgijon.baseapp.example.fragment

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mgijon.baseapp.R
import com.mgijon.baseapp.base.BaseFragment
import com.mgijon.baseapp.databinding.FragmentListCharacterBinding
import com.mgijon.baseapp.example.activity.MainActivity
import com.mgijon.baseapp.example.adapter.AdapterListCharacter
import com.mgijon.baseapp.example.model.StateBase.CharacterListState
import com.mgijon.baseapp.example.model.StateBase.NewCharacterListState
import com.mgijon.baseapp.example.util.ConstantsNavigation.CHARACTER_ID
import com.mgijon.baseapp.example.viewmodel.ListCharactersViewModel
import dagger.hilt.android.AndroidEntryPoint


@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class ListCharactersFragment : BaseFragment<ListCharactersViewModel, FragmentListCharacterBinding, MainActivity>() {

    override val viewModel: ListCharactersViewModel by viewModels()

    override val title: Int = R.string.marvel_title

    override fun getViewBinding(): FragmentListCharacterBinding = FragmentListCharacterBinding.inflate(layoutInflater)

    private lateinit var adapter: AdapterListCharacter

    private val navigateTo: (String) -> Unit = { id ->
        if (!isLoading()) {
            binding.rvCharacters.hideSearchBar()
            hideKeyboard(requireActivity(), binding.rvCharacters.getEditText())
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

    private val endList: () -> Unit = {
        if (!viewModel.isFilter)
            viewModel.getNewCharacters()
    }

    private val startList: () -> Unit = {
        binding.rvCharacters.showSearchBar()
    }

    private val moveDownList: () -> Unit = {
        if (!viewModel.isFilter) {
            binding.rvCharacters.hideSearchBar()
            hideKeyboard(requireActivity(), binding.rvCharacters.getEditText())
        }
    }

    override fun initViews() {
        adapter = AdapterListCharacter(navigateTo, remove)
        binding.rvCharacters.setAdapter(
            adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>,
            endList,
            startList,
            moveDownList,
            { viewModel.filterCharacters(binding.rvCharacters.getSearchText()) },
            { viewModel.filterCharacters("") }
        )
    }

    override fun initObservers() {
        viewModel.state.let {
            runGenericState(it) {
                when (it.value) {
                    is CharacterListState -> {
                        adapter.setCharacters((it as LiveData<CharacterListState>).value?.characters?.toMutableList())
                    }
                    is NewCharacterListState -> {
                        adapter.updateCharacters((it as LiveData<NewCharacterListState>).value?.characters?.toMutableList())
                    }
                    else -> {}
                }
            }
        }
    }
}
