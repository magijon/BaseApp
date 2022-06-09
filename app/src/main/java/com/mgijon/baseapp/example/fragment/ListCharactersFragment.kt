package com.mgijon.baseapp.example.fragment

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
import com.mgijon.baseapp.example.util.ConstantsNavigation.CHARACTER_ID
import com.mgijon.baseapp.example.viewmodel.ListCharactersViewModel
import dagger.hilt.android.AndroidEntryPoint

@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class ListCharactersFragment : BaseFragment<ListCharactersViewModel, FragmentListCharacterBinding, MainActivity>(R.string.marvel_title) {

    override val viewModel: ListCharactersViewModel by viewModels()

    override fun getViewBinding(): FragmentListCharacterBinding = FragmentListCharacterBinding.inflate(layoutInflater)

    private lateinit var adapter: AdapterListCharacter

    private lateinit var navigateTo: (id: String) -> Unit

    override fun initViews() {
        navigateTo = { id ->
            view?.let {
                Navigation.findNavController(it)
                    .navigate(
                        R.id.action_listCharactersFragment_to_characterFragment,
                        bundleOf(CHARACTER_ID to id)
                    )
            }
        }
        adapter = AdapterListCharacter(navigateTo)
        binding.apply {
            rvCharacters.adapter = adapter
            rvCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == SCROLL_STATE_IDLE && !recyclerView.canScrollVertically(1))
                        viewModel.getCharacters()
                }
            })
        }
    }

    override fun initObservers() {
        viewModel.state.let {
            runGenericState(it) {
                adapter.updateCharacters((it as LiveData<StateBase.CharacterListState>).value?.characters?.toMutableList())
            }
        }
    }
}