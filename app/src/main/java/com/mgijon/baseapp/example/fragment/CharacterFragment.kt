package com.mgijon.baseapp.example.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.mgijon.baseapp.R
import com.mgijon.baseapp.base.BaseFragment
import com.mgijon.baseapp.databinding.FragmentCharacterBinding
import com.mgijon.baseapp.example.activity.MainActivity
import com.mgijon.baseapp.example.model.StateBase
import com.mgijon.baseapp.example.model.TitleFragmentType
import com.mgijon.baseapp.example.viewmodel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint

@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class CharacterFragment : BaseFragment<CharacterViewModel, FragmentCharacterBinding, MainActivity>() {

    override val viewModel: CharacterViewModel by viewModels()

    override val title: Int = TitleFragmentType.DYNAMIC_TITLE.value

    override fun getViewBinding(): FragmentCharacterBinding = FragmentCharacterBinding.inflate(layoutInflater)

    override fun initViews() {}

    override fun initObservers() {
        viewModel.state.let {
            runGenericState(it) {
                binding.apply {
                    setTitle((it as LiveData<StateBase.CharacterState>).value?.character?.name)
                    Glide.with(requireContext())
                        .load(it.value?.character?.image)
                        .placeholder(R.drawable.iron_man)
                        .into(ivLargeCharacter)
                    tvDescription.text = it.value?.character?.description.let {
                            description -> if (description?.isNotEmpty() == true) description else tvDescription.text }
                }
            }
        }
    }
}