package com.dicoding.bfaa.tmdb.home.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import app.cash.turbine.test
import com.dicoding.bfaa.tmdb.utils.DummyDataGenerator
import com.dicoding.bfaa.tmdb.utils.TestCoroutineRule
import com.dicoding.bfaa.tmdb.utils.collectDataForTest
import com.dicoding.tmdb.app.home.tvshow.TvShowViewModel
import com.dicoding.tmdb.core.domain.model.Movie
import com.dicoding.tmdb.core.domain.model.TvShow
import com.dicoding.tmdb.core.domain.usecase.TvShowUseCase
import io.kotest.assertions.asClue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class TvShowViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @RelaxedMockK
    private lateinit var tvShowUseCase: TvShowUseCase
    private lateinit var viewModel: TvShowViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = TvShowViewModel(tvShowUseCase)
    }

    @Test
    fun `get TvShowStream from UseCase`() = testCoroutineRule.runBlockingTest {
        val dummyData = DummyDataGenerator.generateTvShows()
        // Create a dummy flow that emits PagingData from the generated data
        val dummyFlowPagingData = flowOf(PagingData.from(dummyData))

        // mock return data from the movieUseCase
        every { tvShowUseCase.fetchTvShows() } returns dummyFlowPagingData

        var itemIsAsserted = false
        viewModel.tvShowsStream.test {
            val tvShow = expectItem().collectDataForTest(testCoroutineRule.testDispatcher)

            tvShow.asClue {
                it shouldNotBe null
                it shouldBe dummyData
            }

            itemIsAsserted = true
            expectComplete()
        }

        // Wait until dispatchers resolve the data
        advanceUntilIdle()

        verify { tvShowUseCase.fetchTvShows() }

        println("All Asserted : $itemIsAsserted")
    }

    @Test
    fun `get tvshowstream from usecase but empty`() = testCoroutineRule.runBlockingTest {
        // Given
        // Create a dummy flow that emits PagingData from the generated data
        val dummyFlowPagingData = flow { emit(PagingData.empty<TvShow>()) }
        // mock return data from the movieUseCase
        every { tvShowUseCase.fetchTvShows() } returns dummyFlowPagingData
        val expected = arrayListOf<Movie>()

        var itemIsAsserted = false
        viewModel.tvShowsStream.test {
            // Then
            expectItem().collectDataForTest(testCoroutineRule.testDispatcher).asClue {
                it shouldNotBe null
                it shouldBe expected
            }
            itemIsAsserted = true
            expectComplete()
        }

        verify { tvShowUseCase.fetchTvShows() }
        println("All Asserted : $itemIsAsserted")
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}