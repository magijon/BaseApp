package com.mgijon.baseapp

import com.mgijon.baseapp.example.model.StateBase
import com.mgijon.baseapp.example.viewmodel.CharacterViewModel
import com.mgijon.domain.common.Resource
import com.mgijon.domain.model.marvel.Character
import com.mgijon.usecase.marvel.GetOneCharacterUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.Mock

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class CharacterViewModelTest : BaseViewModelTest<CharacterViewModel>() {

    @Mock
    lateinit var getOneCharacterUseCase: GetOneCharacterUseCase

    @Mock
    lateinit var flow: Flow<Resource<Character?>>

    override val initFunc: () -> Unit = {
        whenever(getOneCharacterUseCase.invoke(any())).thenReturn(flow)
        viewmodel = CharacterViewModel(getOneCharacterUseCase, dispatcher)
    }


    @Test
    fun `verify call`() {
        val id = "id"
        whenever(bundle.getString(any())).thenReturn(id)

        runTest {
            viewmodel.startLogic(bundle)
        }

        verify(getOneCharacterUseCase, times(1)).invoke(any())
    }

    @Test
    fun `loading get one character`() {
        val id = "id"
        whenever(bundle.getString(any())).thenReturn(id)

        viewmodel.state.observeForever {
            assert(it is StateBase.LoadingStateBase)
        }

        runTest {
            whenever(flow.collect(any())).then {
                launch {
                    (it.arguments[0] as FlowCollector<Resource<Character>>).emit(Resource.Loading())
                }
            }
            viewmodel.startLogic(bundle)
        }
    }


    @Test
    fun `success get one character`() {
        val id = "id"
        whenever(bundle.getString(any())).thenReturn(id)

        viewmodel.state.observeForever {
            assert((it as StateBase.CharacterState).character?.id == id)
        }
        runTest {
            whenever(flow.collect(any())).then {
                launch {
                    (it.arguments[0] as FlowCollector<Resource<Character>>).emit(Resource.Success(Character(id, "name", "", "")))
                }
            }
            viewmodel.startLogic(bundle)
        }
    }

    @Test
    fun `error get one character`() {
        val id = "id"
        val message = "message"
        whenever(bundle.getString(any())).thenReturn(id)

        viewmodel.state.observeForever {
            assert((it as StateBase.ErrorStateBase).error?.message == message)
        }
        runTest {
            whenever(flow.collect(any())).then {
                launch {
                    (it.arguments[0] as FlowCollector<Resource<Character>>).emit(Resource.Error(message))
                }
            }
            viewmodel.startLogic(bundle)
        }
    }

}