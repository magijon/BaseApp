package com.mgijon.baseapp

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExtendWith(InstantExecutorExtension::class)
@ExperimentalCoroutinesApi
abstract class BaseViewModelTest<VM : ViewModel> {

    @get:Rule
    var instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    val dispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var viewmodel: VM

    abstract val initFunc: () -> Unit

    @Mock
    lateinit var bundle: Bundle


    @BeforeEach
    fun setup() {
        Dispatchers.setMain(dispatcher)

        MockitoAnnotations.openMocks(this)

        initFunc.invoke()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}