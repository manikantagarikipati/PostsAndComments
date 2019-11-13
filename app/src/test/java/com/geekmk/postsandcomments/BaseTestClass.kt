package com.geekmk.postsandcomments

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.geekmk.postsandcomments.utils.NetworkState
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

open class BaseTestClass : KoinTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val networkState = mockk<NetworkState>()
    private val application: Application = mockk()

    @ExperimentalCoroutinesApi
    @Before
    open fun before() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        MockKAnnotations.init(this)


        startKoin {
            androidContext(application)
            modules(listOf(dataSourceModule, viewmodelModule))
        }

        loadKoinModules(module{
            single(override = true) { networkState }
        })

        every { application.getString(any()) } returns "Test String"
        every { networkState.isConnected() } returns true

    }

    @After
    open fun after() {
        Dispatchers.resetMain()
    }
}