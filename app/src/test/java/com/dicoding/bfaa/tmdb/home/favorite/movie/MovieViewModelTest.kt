package com.dicoding.bfaa.tmdb.home.favorite.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.domain.usecase.FavoriteUseCase
import com.dicoding.bfaa.tmdb.home.favorite.tvshow.TvShowViewModel
import com.dicoding.bfaa.tmdb.utils.DummyDataGenerator
import com.dicoding.bfaa.tmdb.utils.TestCoroutineRule
import io.kotest.assertions.asClue
import io.kotest.assertions.until.until
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

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @RelaxedMockK
    private lateinit var favoriteUseCase: FavoriteUseCase
    @MockK
    private lateinit var observer: Observer<List<Movie>>
    private lateinit var viewModel: MovieViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { observer.onChanged(any()) } returns Unit
    }

    @Test
    fun `should return favorite movie list from db`() = testCoroutineRule.runBlockingTest {
        // Given
        val expected = DummyDataGenerator.generateMovies()
        every { favoriteUseCase.getFavoriteMovies() } returns flowOf(expected)
        viewModel = MovieViewModel(favoriteUseCase)

        // When
        viewModel.favoriteMovies.observeForever(observer)
        val result = viewModel.favoriteMovies.value

        // Then
        verify { favoriteUseCase.getFavoriteMovies() }
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