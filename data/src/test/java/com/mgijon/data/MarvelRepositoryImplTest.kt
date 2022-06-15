package com.mgijon.data

import com.mgijon.data.api.MarvelApi
import com.mgijon.data.model.CharacterDataContainer
import com.mgijon.data.model.CharacterDataWrapper
import com.mgijon.data.model.CharacterModel
import com.mgijon.data.model.Image
import com.mgijon.data.persistence.dao.CharacterDao
import com.mgijon.data.persistence.entity.CharacterEntity
import com.mgijon.data.repository.MarvelRepositoryImpl
import com.mgijon.data.util.MD5Tool
import com.mgijon.domain.repository.marvel.MarvelRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MarvelRepositoryImplTest {

    private lateinit var repository: MarvelRepository

    private val initFunc: () -> Unit = {
        whenever(mD5Tool.getHash()).thenReturn("hash")
        whenever(mD5Tool.getPublicKey()).thenReturn("PublicKey")
        whenever(mD5Tool.getTs()).thenReturn(1000)
        repository = MarvelRepositoryImpl(marvelApi, mD5Tool, characterDao)
    }

    @Mock
    lateinit var marvelApi: MarvelApi

    @Mock
    lateinit var mD5Tool: MD5Tool

    @Mock
    lateinit var characterDao: CharacterDao

    @Mock
    lateinit var characterDataWrapper: CharacterDataWrapper

    @Mock
    lateinit var characterDataContainer: CharacterDataContainer

    private val characterModel: CharacterModel = CharacterModel(
        1, "name", "description", "modified", "resourceURI",
        listOf(), Image("path", "ext")
    )

    private val characterEntity: CharacterEntity = CharacterEntity(
        1, "id", "name", "image", "description", true
    )

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        initFunc.invoke()
    }

    @Test
    fun `new characters`() {
        runTest {
            whenever(characterDataContainer.results).thenReturn(listOf(characterModel, characterModel, characterModel))
            whenever(characterDataWrapper.data).thenReturn(characterDataContainer)
            whenever(marvelApi.getAllCharacters(any(), any(), any(), any())).thenReturn(characterDataWrapper)

            val result = repository.getNewCharacters(20)

            assert(result!![0].id == characterModel.id.toString())
            verify(marvelApi, times(1)).getAllCharacters(any(), any(), any(), any())
            verify(characterDao, times(1)).insertAll(any())
        }
    }

    @Test
    fun `get one character`() {
        runTest {
            whenever(characterDataContainer.results).thenReturn(listOf(characterModel, characterModel, characterModel))
            whenever(characterDataWrapper.data).thenReturn(characterDataContainer)
            whenever(marvelApi.getOneCharacter(any(), any(), any(), any())).thenReturn(characterDataWrapper)

            val result = repository.getOneCharacter("id")

            assert(result!!.id == characterModel.id.toString())
            verify(marvelApi, times(1)).getOneCharacter(any(), any(), any(), any())
            verify(characterDao, times(1)).getOne(any())
        }
    }

    @Test
    fun `get all`() {
        runTest {
            whenever(characterDao.getAll())
                .thenReturn(listOf(characterEntity, characterEntity, CharacterEntity(2, "", "", "", "", false)))

            val result = repository.getAll()

            verify(characterDao, times(1)).getAll()
            assert(result.size == 2)
        }
    }

    @Test
    fun `get filter`() {
        runTest {
            whenever(characterDao.getFilter(any()))
                .thenReturn(listOf(characterEntity, characterEntity, CharacterEntity(2, "", "", "", "", false)))

            val result = repository.getFilter("name")

            verify(characterDao, times(1)).getFilter(any())
            assert(result.size == 2)
        }
    }

    @Test
    fun `remove Visibility`() {
        runTest {
            whenever(characterDao.getAll())
                .thenReturn(listOf(characterEntity, characterEntity, CharacterEntity(2, "", "", "", "", false)))

            val result = repository.removeVisibility("id")

            verify(characterDao, times(1)).getAll()
            verify(characterDao, times(1)).removeVisibility(any())
            assert(result.size == 2)
        }
    }
}