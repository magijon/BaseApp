package com.mgijon.baseapp.example.fragment

import androidx.fragment.app.viewModels
import com.mgijon.baseapp.base.BaseFragment
import com.mgijon.baseapp.databinding.FragmentListCharacterBinding
import com.mgijon.baseapp.example.adapter.AdapterListCharacter
import com.mgijon.baseapp.example.viewmodel.ListCharactersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListCharactersFragment : BaseFragment<ListCharactersViewModel, FragmentListCharacterBinding>() {

    override val viewModel: ListCharactersViewModel by viewModels()

    override fun getViewBinding(): FragmentListCharacterBinding = FragmentListCharacterBinding.inflate(layoutInflater)

    private val adapter: AdapterListCharacter = AdapterListCharacter()

    override fun initViews() {
        binding.apply {
            rvCharacters.adapter = adapter
        }
    }

    override fun initObservers() {
        viewModel.state.let {
            runGenericState(it) {
                adapter.updateCharacters(it.value?.characters?.toMutableList())
            }
        }
    }
}