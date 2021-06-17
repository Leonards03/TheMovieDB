package com.dicoding.bfaa.tmdb.home.favorite.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dicoding.bfaa.tmdb.core.domain.model.TvShow
import com.dicoding.bfaa.tmdb.core.domain.usecase.FavoriteUseCase
import com.dicoding.bfaa.tmdb.utils.DummyDataGenerator
import com.dicoding.bfaa.tmdb.utils.TestCoroutineRule
import io.kotest.assertions.asClue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TvShowViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @RelaxedMockK
    private lateinit var favoriteUseCase: FavoriteUseCase

    @MockK
    private lateinit var observer: Observer<List<TvShow>>
    private lateinit var viewModel: TvShowViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { observer.onChanged(any()) } returns Unit
    }

    @Test
    fun `should return favorite tvshow list from db`() = testCoroutineRule.runBlockingTest {
        // Given
        val expected = DummyDataGenerator.generateTvShows()
        every { favoriteUseCase.getFavoriteTvShows() } returns flowOf(expected)
        viewModel = TvShowViewModel(favoriteUseCase)

        // When
        viewModel.favoriteTvShows.observeForever(observer)
        val result = viewModel.favoriteTvShows.value

        // Then
        verify { favoriteUseCase.getFavoriteTvShows() }
        verify { observer.onChanged(any()) }

        result.asClue {
            it shouldNotBe null
            it shouldBe expected
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}