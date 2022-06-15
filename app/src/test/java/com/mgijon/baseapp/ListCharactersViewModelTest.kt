package com.mgijon.baseapp

import com.mgijon.baseapp.example.model.StateBase
import com.mgijon.baseapp.example.viewmodel.ListCharactersViewModel
import com.mgijon.domain.common.Resource
import com.mgijon.domain.model.marvel.Character
import com.mgijon.usecase.marvel.GetAllCharactersUseCase
import com.mgijon.usecase.marvel.GetFilterCharacterUseCase
import com.mgijon.usecase.marvel.GetNewCharactersUseCase
import com.mgijon.usecase.marvel.RemoveVisibilityUseCase
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

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class ListCharactersViewModelTest : BaseViewModelTest<ListCharactersViewModel>() {

    override val initFunc: () -> Unit = {
        whenever(character.id).thenReturn("id")
        whenever(character.name).thenReturn("name")
        whenever(character.image).thenReturn("image")
        whenever(character.description).thenReturn("description")
        whenever(getAllCharactersUseCase.invoke()).thenReturn(flow)
        whenever(getFilterCharacterUseCase.invoke(any())).thenReturn(flow)
        whenever(removeVisibilityUseCase.invoke(any())).thenReturn(flow)
        whenever(getNewCharactersUseCase.invoke(any())).thenReturn(flowNull)
        viewmodel =
            ListCharactersViewModel(getAllCharactersUseCase, getFilterCharacterUseCase, removeVisibilityUseCase, getNewCharactersUseCase, dispatcher)
    }

    @Mock
    lateinit var flow: Flow<Resource<List<Character>>>

    @Mock
    lateinit var flowNull: Flow<Resource<List<Character>?>>

    @Mock
    lateinit var character: Character

    @Mock
    lateinit var getAllCharactersUseCase: GetAllCharactersUseCase

    @Mock
    lateinit var getFilterCharacterUseCase: GetFilterCharacterUseCase

    @Mock
    lateinit var removeVisibilityUseCase: RemoveVisibilityUseCase

    @Mock
    lateinit var getNewCharactersUseCase: GetNewCharactersUseCase

    @Test
    fun `filter characters loading`() {
        runTest {
            val name = "name"

            viewmodel.state.observeForever {
                assert(it is StateBase.LoadingStateBase)
            }

            whenever(flow.collect(any())).then {
                launch {
                    (it.arguments[0] as FlowCollector<Resource<List<Character>>>).emit(Resource.Loading())
                }
            }

            viewmodel.filterCharacters(name)

            verify(getFilterCharacterUseCase, times(1)).invoke(any())
        }
    }

    @Test
    fun `filter characters success`() {
        runTest {
            val name = "name"

            viewmodel.state.observeForever {
                assert((it as StateBase.CharacterListState).characters[0].id == character.id)
            }

            whenever(flow.collect(any())).then {
                launch {
                    (it.arguments[0] as FlowCollector<Resource<List<Character>>>).emit(Resource.Success(listOf(character, character)))
                }
            }

            viewmodel.filterCharacters(name)
        }
    }

    @Test
    fun `filter characters error`() {
        runTest {
            val name = "name"
            val message = "message"
            viewmodel.state.observeForever {
                assert((it as StateBase.ErrorStateBase).error?.message == message)
            }

            whenever(flow.collect(any())).then {
                launch {
                    (it.arguments[0] as FlowCollector<Resource<List<Character>>>).emit(Resource.Error(message))
                }
            }

            viewmodel.filterCharacters(name)
        }
    }

    @Test
    fun `remove character loading`() {
        runTest {
            val id = "id"

            viewmodel.state.observeForever {
                assert(it is StateBase.LoadingStateBase)
            }

            whenever(flow.collect(any())).then {
                launch {
                    (it.arguments[0] as FlowCollector<Resource<List<Character>>>).emit(Resource.Loading())
                }
            }

            viewmodel.removeVisibility(id)

            verify(removeVisibilityUseCase, times(1)).invoke(any())
        }
    }

    @Test
    fun `remove characters success`() {
        runTest {
            val id = "id"

            viewmodel.state.observeForever {
                assert((it as StateBase.CharacterListState).characters[0].id == character.id)
            }

            whenever(flow.collect(any())).then {
                launch {
                    (it.arguments[0] as FlowCollector<Resource<List<Character>>>).emit(Resource.Success(listOf(character, character)))
                }
            }

            viewmodel.removeVisibility(id)
        }
    }

    @Test
    fun `remove characters error`() {
        runTest {
            val id = "id"
            val message = "message"
            viewmodel.state.observeForever {
                assert((it as StateBase.ErrorStateBase).error?.message == message)
            }

            whenever(flow.collect(any())).then {
                launch {
                    (it.arguments[0] as FlowCollector<Resource<List<Character>>>).emit(Resource.Error(message))
                }
            }

            viewmodel.filterCharacters(id)
        }
    }

    @Test
    fun `get character DB loading`() {
        runTest {
            viewmodel.state.observeForever {
                assert(it is StateBase.LoadingStateBase)
            }

            whenever(flow.collect(any())).then {
                launch {
                    (it.arguments[0] as FlowCollector<Resource<List<Character>>>).emit(Resource.Loading())
                }
            }

            viewmodel.getCharacters(true)

            verify(getAllCharactersUseCase, times(1)).invoke()
        }
    }

    @Test
    fun `get characters DB success`() {
        runTest {
            viewmodel.state.observeForever {
                assert((it as StateBase.CharacterListState).characters[0].id == character.id)
            }

            whenever(flow.collect(any())).then {
                launch {
                    (it.arguments[0] as FlowCollector<Resource<List<Character>>>).emit(Resource.Success(listOf(character, character)))
                }
            }

            viewmodel.getCharacters(true)
        }
    }

    @Test
    fun `get characters DB error`() {
        runTest {
            val message = "message"
            viewmodel.state.observeForever {
                assert((it as StateBase.ErrorStateBase).error?.message == message)
            }

            whenever(flow.collect(any())).then {
                launch {
                    (it.arguments[0] as FlowCollector<Resource<List<Character>>>).emit(Resource.Error(message))
                }
            }

            viewmodel.getCharacters(true)
        }
    }

    @Test
    fun `get new characters loading`() {
        runTest {
            viewmodel.state.observeForever {
                assert(it is StateBase.LoadingStateBase)
            }

            whenever(flowNull.collect(any())).then {
                launch {
                    (it.arguments[0] as FlowCollector<Resource<List<Character>>>).emit(Resource.Loading())
                }
            }

            viewmodel.getCharacters()

            verify(getNewCharactersUseCase, times(1)).invoke(any())
        }
    }

    @Test
    fun `get new characters success`() {
        runTest {
            viewmodel.state.observeForever {
                assert((it as StateBase.NewCharacterListState).characters[0].id == character.id)
            }

            whenever(flowNull.collect(any())).then {
                launch {
                    (it.arguments[0] as FlowCollector<Resource<List<Character>>>).emit(Resource.Success(listOf(character, character)))
                }
            }

            viewmodel.getCharacters()
        }
    }

    @Test
    fun `get new characters error`() {
        runTest {
            val message = "message"
            viewmodel.state.observeForever {
                assert((it as StateBase.ErrorStateBase).error?.message == message)
            }

            whenever(flowNull.collect(any())).then {
                launch {
                    (it.arguments[0] as FlowCollector<Resource<List<Character>>>).emit(Resource.Error(message))
                }
            }

            viewmodel.getCharacters()
        }
    }

    @Test
    fun `startlogic with characterList empty`(){
        runTest {
            viewmodel.startLogic(bundle)

            verify(getAllCharactersUseCase, times(1)).invoke()
        }
    }

    @Test
    fun `startlogic with characterList full`(){
        runTest {

            whenever(flow.collect(any())).then {
                launch {
                    (it.arguments[0] as FlowCollector<Resource<List<Character>>>).emit(Resource.Success(listOf(character, character, character)))
                }
            }
            viewmodel.removeVisibility("id")

        }.run {
            viewmodel.state.observeForever {
                assert(it is StateBase.CharacterListState)
                assert((it as StateBase.CharacterListState).characters.size == 3)
            }

            viewmodel.startLogic(bundle)
        }
    }
}