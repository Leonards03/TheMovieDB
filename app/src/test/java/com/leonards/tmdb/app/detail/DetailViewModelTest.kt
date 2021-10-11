package com.leonards.tmdb.app.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.leonards.tmdb.app.utils.TestCoroutineRule
import com.leonards.tmdb.core.data.states.ItemType
import com.leonards.tmdb.core.data.states.Resource
import com.leonards.tmdb.core.domain.model.Movie
import com.leonards.tmdb.core.domain.model.TvShow
import com.leonards.tmdb.core.domain.usecase.DetailUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @RelaxedMockK
    private lateinit var savedStateHandle: SavedStateHandle

    @RelaxedMockK
    private lateinit var detailUseCase: DetailUseCase

    @MockK
    private lateinit var favoriteObserver: Observer<Boolean>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        every { savedStateHandle.get<Int>(DetailViewModel.EXTRA_ID) } returns 0
        every { favoriteObserver.onChanged(any()) } returns Unit
    }

    @Test
    fun `should return movie details`() = testCoroutineRule.runBlockingTest {
        // Given
        val movieId = 615457
        val data = Resource.Success(DummyDataGenerator.getMovieResponse(movieId)
            .mapToDomain())
        val dummyFlow = flow {
            emit(data)
        }

        every { savedStateHandle.get<Int>(DetailViewModel.EXTRA_ID) } returns movieId
        every { savedStateHandle.get<ItemType>(DetailViewModel.EXTRA_TYPE) } returns ItemType.Movie

        every { detailUseCase.getMovieDetails(movieId) } returns dummyFlow

        val observer = mockk<Observer<Resource<Movie>>>()
        every { observer.onChanged(any()) } returns Unit

        // When
        val viewModel = DetailViewModel(savedStateHandle, detailUseCase)
        viewModel.movieDetails.observeForever(observer)
        val result = viewModel.movieDetails.value

        // Then
        verify { detailUseCase.getMovieDetails(movieId) }
        verify { observer.onChanged(data) }

        result.asClue {
            it shouldNotBe null
            it shouldBe data
        }
    }

    @Test
    fun `should return tvshow details`() = testCoroutineRule.runBlockingTest {
        // Given
        val tvShowId = 1402
        val data = Resource.Success(DummyDataGenerator.getTvShowResponse(tvShowId)
            .mapToDomain())
        val dummyFlow = flow {
            emit(data)
        }

        every { savedStateHandle.get<Int>(DetailViewModel.EXTRA_ID) } returns tvShowId
        every { savedStateHandle.get<ItemType>(DetailViewModel.EXTRA_TYPE) } returns ItemType.TvShow

        every { detailUseCase.getTvShowDetails(tvShowId) } returns dummyFlow

        val observer = mockk<Observer<Resource<TvShow>>>()
        every { observer.onChanged(any()) } returns Unit

        // When
        val viewModel = DetailViewModel(savedStateHandle, detailUseCase)
        viewModel.tvShowDetails.observeForever(observer)
        val result = viewModel.tvShowDetails.value

        // Then
        verify { detailUseCase.getTvShowDetails(tvShowId) }
        verify { observer.onChanged(data) }

        result.asClue {
            it shouldNotBe null
            it shouldBe data
        }
    }

    @Test
    fun `set tvshow favorite state to true`() = testCoroutineRule.runBlockingTest {
        // init
        every { savedStateHandle.get<ItemType>(DetailViewModel.EXTRA_TYPE) } returns ItemType.TvShow

        // Given
        val data = DummyDataGenerator.generateTvShows()[0]

        val viewModel = DetailViewModel(savedStateHandle, detailUseCase)
        viewModel.itemIsFavorite.observeForever(favoriteObserver)

        // When
        viewModel.toggleFavoriteState()
        viewModel.setFavorite(data)

        // Then
        verify { detailUseCase.setFavorite(data, true) }
        verify { favoriteObserver.onChanged(true) }
    }


    @Test
    fun `set tvshow favorite state to false`() = testCoroutineRule.runBlockingTest {
        // init
        every { savedStateHandle.get<ItemType>(DetailViewModel.EXTRA_TYPE) } returns ItemType.TvShow

        // Given
        val data = DummyDataGenerator.generateTvShows()[0]

        val viewModel = DetailViewModel(savedStateHandle, detailUseCase)
        viewModel.setFavoriteState(true)
        viewModel.itemIsFavorite.observeForever(favoriteObserver)

        // When
        viewModel.toggleFavoriteState()
        viewModel.setFavorite(data)

        // Then
        verify { detailUseCase.setFavorite(data, false) }
        verify { favoriteObserver.onChanged(false) }
    }

    @Test
    fun `set movie favorite state to true`() = testCoroutineRule.runBlockingTest {
        // init
        every { savedStateHandle.get<ItemType>(DetailViewModel.EXTRA_TYPE) } returns ItemType.Movie

        // Given
        val data = DummyDataGenerator.generateMovies()[0]

        val viewModel = DetailViewModel(savedStateHandle, detailUseCase)
        viewModel.itemIsFavorite.observeForever(favoriteObserver)

        // When
        viewModel.toggleFavoriteState()
        viewModel.setFavorite(data)

        // Then
        verify { detailUseCase.setFavorite(data, true) }
        verify { favoriteObserver.onChanged(true) }
    }

    @Test
    fun `set movie favorite state to false`() = testCoroutineRule.runBlockingTest {
        // init
        every { savedStateHandle.get<ItemType>(DetailViewModel.EXTRA_TYPE) } returns ItemType.Movie

        // Given
        val data = DummyDataGenerator.generateMovies()[0]

        val viewModel = DetailViewModel(savedStateHandle, detailUseCase)
        viewModel.setFavoriteState(true)
        viewModel.itemIsFavorite.observeForever(favoriteObserver)

        // When
        viewModel.toggleFavoriteState()
        viewModel.setFavorite(data)

        // Then
        verify { detailUseCase.setFavorite(data, false) }
        verify { favoriteObserver.onChanged(false) }
    }

    @After
    fun tearDown() = unmockkAll()
}