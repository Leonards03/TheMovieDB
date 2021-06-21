package com.dicoding.bfaa.tmdb.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import app.cash.turbine.test
import com.dicoding.bfaa.tmdb.utils.DummyDataGenerator
import com.dicoding.bfaa.tmdb.utils.TestCoroutineRule
import com.dicoding.tmdb.core.data.Constants
import com.dicoding.tmdb.core.data.Constants.DEFAULT_PAGE_SIZE
import com.dicoding.tmdb.core.data.RepositoryImpl
import com.dicoding.tmdb.core.data.mapper.mapToDomain
import com.dicoding.tmdb.core.data.mapper.mapToEntity
import com.dicoding.tmdb.core.data.source.local.LocalDataSource
import com.dicoding.tmdb.core.data.source.local.dao.TMDBDao
import com.dicoding.tmdb.core.data.source.local.entity.MovieEntity
import com.dicoding.tmdb.core.data.source.local.entity.TvShowEntity
import com.dicoding.tmdb.core.data.source.remote.RemoteDataSource
import com.dicoding.tmdb.core.data.source.remote.network.TMDBServices
import com.dicoding.tmdb.core.data.states.Resource
import com.dicoding.tmdb.core.domain.model.Movie
import com.dicoding.tmdb.core.domain.model.TvShow
import com.dicoding.tmdb.core.presentation.paging.MoviePagingSource
import com.dicoding.tmdb.core.presentation.paging.TvShowPagingSource
import io.kotest.assertions.asClue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.*
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
class RepositoryImplTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val tmdbServices = mockk<TMDBServices>()
    private val tmdbDao = mockk<TMDBDao>()

    /**
     *  @SUTs
     */
    private lateinit var repositoryImpl: RepositoryImpl
    private val remoteDataSource = RemoteDataSource(tmdbServices)
    private val localDataSource = LocalDataSource(tmdbDao)

    @Before
    fun setup() {
        repositoryImpl = RepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            dispatcher = testCoroutineRule.testDispatcher
        )
    }

    /**
     *  For fetching PagingData test replaced by testing PagingSource test
     *  source:
     *  https://developer.android.com/topic/libraries/architecture/paging/test
     */

    @Test
    fun `movie paging source should return page when successful load data`() =
        testCoroutineRule.runBlockingTest {
            // Given
            val dummyResponse = DummyDataGenerator.generateListResponseMovie()
            coEvery { tmdbServices.getMovies() } returns dummyResponse
            val pagingSource = MoviePagingSource(remoteDataSource)

            // When
            val actual = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = DEFAULT_PAGE_SIZE,
                    placeholdersEnabled = false
                )
            )

            val expected = PagingSource.LoadResult.Page(
                data = DummyDataGenerator.generateMovies(),
                prevKey = null,
                nextKey = null
            )

            // Then
            actual.asClue {
                it shouldNotBe null
                it shouldBe expected
            }
            coVerify { tmdbServices.getMovies() }
        }

    @Test
    fun `movie paging source should return error`() = testCoroutineRule.runBlockingTest {
        // Given
        val exception = Exception()
        coEvery { tmdbServices.getMovies() } throws exception
        val pagingSource = MoviePagingSource(remoteDataSource)

        // When
        val actual = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = DEFAULT_PAGE_SIZE,
                placeholdersEnabled = false
            )
        )
        val expected = PagingSource.LoadResult.Error<Int, Movie>(exception)

        // Then
        actual shouldNotBe null
        actual shouldBe expected
        coVerify { tmdbServices.getMovies() }
    }

    @Test
    fun `tvshow paging source should return page when successful load data`() =
        testCoroutineRule.runBlockingTest {
            val dummyResponse = DummyDataGenerator.generateListResponseTvShow()
            // Mock return
            coEvery { tmdbServices.getTvShows() } returns dummyResponse
            val pagingSource = TvShowPagingSource(remoteDataSource)

            val actual = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = DEFAULT_PAGE_SIZE,
                    placeholdersEnabled = false
                )
            )

            val expected = PagingSource.LoadResult.Page(
                data = DummyDataGenerator.generateTvShows(),
                prevKey = null,
                nextKey = null
            )

            actual.asClue {
                it shouldNotBe null
                it shouldBe expected
            }
        }

    @Test
    fun `tvshow paging source should return error`() = testCoroutineRule.runBlockingTest {
        // Given
        val exception = Exception()
        coEvery { tmdbServices.getTvShows() } throws exception
        val pagingSource = TvShowPagingSource(remoteDataSource)

        // When
        val actual = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = DEFAULT_PAGE_SIZE,
                placeholdersEnabled = false
            )
        )
        val expected = PagingSource.LoadResult.Error<Int, TvShow>(exception)

        // Then
        actual shouldNotBe null
        actual shouldBe expected
        coVerify { tmdbServices.getTvShows() }
    }

    @Test
    fun `should return movie list from db`() = testCoroutineRule.runBlockingTest {
        // Given
        val expected = DummyDataGenerator.generateMovies()
        coEvery { tmdbDao.getFavoriteMovies() } returns flowOf(expected.map { it.mapToEntity() })

        // When
        repositoryImpl.getFavoriteMovies().test {
            expectItem().asClue {
                it shouldNotBe null
                it shouldBe expected
            }
            expectComplete()
        }

        coVerify { tmdbDao.getFavoriteMovies() }
    }

    @Test
    fun `should return tvshow list from db`() = testCoroutineRule.runBlockingTest {
        // Given
        val expected = DummyDataGenerator.generateTvShows()
        coEvery { tmdbDao.getFavoriteTvShows() } returns flowOf(expected.map { it.mapToEntity() })

        // When
        repositoryImpl.getFavoriteTvShows().test {
            expectItem().asClue {
                it shouldNotBe null
                it shouldBe expected
            }
            expectComplete()
        }

        coVerify { tmdbDao.getFavoriteTvShows() }
    }

    @Test
    fun `should fetch movie from network`() = testCoroutineRule.runBlockingTest {
        // Given
        val movieId = 615457
        val expected = DummyDataGenerator.getMovieResponse(movieId)
        coEvery { tmdbDao.getFavoriteMovie(movieId) } returns flow<MovieEntity?> { emit(null) }
        coEvery {
            tmdbServices.getMovie(movieId, Constants.appendToResponse)
        } returns expected
        var asserted = false

        // When
        repositoryImpl.fetchMovie(movieId).test {
            // Then
            // Expect first emitted item should be a loading resource
            expectItem() shouldBe Resource.Loading<Resource<Movie>>()

            // Expecting a result in the next item
            when (val resource = expectItem()) {
                is Resource.Error -> throw Error("Resource Error")
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    resource.data.asClue {
                        it shouldNotBe null
                        it shouldBe expected.mapToDomain()
                    }
                    asserted = true
                }
            }
            // after item emitted, flow should be completed by this time
            expectComplete()
        }

        coVerifyOrder {
            tmdbDao.getFavoriteMovie(movieId)
            tmdbServices.getMovie(movieId, Constants.appendToResponse)
        }
        println("should fetch movie from network all asserted: $asserted")
    }

    @Test
    fun `should fetch movie from db`() = testCoroutineRule.runBlockingTest {
        // Given
        val movieId = 615457
        val expected = DummyDataGenerator.getMovieEntity(movieId)
        coEvery { tmdbDao.getFavoriteMovie(movieId) } returns flowOf(expected)
        var asserted = false

        // When
        repositoryImpl.fetchMovie(movieId).test {
            // Then
            // Expect first emitted item should be a loading resource
            expectItem() shouldBe Resource.Loading<Resource<Movie>>()

            // Expecting a result in the next item
            when (val resource = expectItem()) {
                is Resource.Error -> throw Error("Resource Error")
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    resource.data.asClue {
                        it shouldNotBe null
                        it shouldBe expected.mapToDomain()
                    }
                    asserted = true
                }
            }
            // after item emitted, flow should be completed by this time
            expectComplete()
        }

        coVerify { tmdbDao.getFavoriteMovie(movieId) }
        println("should fetch movie from db all asserted: $asserted")
    }

    @Test
    fun `should fetch tvshow from network`() = testCoroutineRule.runBlockingTest {
        // Given
        val tvShowId = 1402
        val expected = DummyDataGenerator.getTvShowResponse(tvShowId)
        coEvery { tmdbDao.getFavoriteTvShow(tvShowId) } returns flow<TvShowEntity?> { emit(null) }
        coEvery { tmdbServices.getTvShow(tvShowId) } returns expected
        var asserted = false

        // When
        repositoryImpl.fetchTvShow(tvShowId).test {
            // Then
            // Expect first emitted item should be a loading resource
            expectItem() shouldBe Resource.Loading<Resource<TvShow>>()
            when (val resource = expectItem()) {
                is Resource.Error -> throw Error(resource.exception.message)
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    // Then
                    resource.data.asClue {
                        it shouldNotBe null
                        it shouldBe expected.mapToDomain()
                    }
                    asserted = true
                }
            }
            // after item emitted, flow should be completed by this time
            expectComplete()
        }

        coVerifyOrder {
            tmdbDao.getFavoriteTvShow(tvShowId)
            tmdbServices.getTvShow(any())
        }
        println("should fetch tvshow from network all asserted: $asserted")
    }

    @Test
    fun `should fetch tvshow from db`() = testCoroutineRule.runBlockingTest {
        // Given
        val tvShowId = 1402
        val expected = DummyDataGenerator.getTvShowEntity(tvShowId)
        coEvery { tmdbDao.getFavoriteTvShow(tvShowId) } returns flowOf(expected)
        var asserted = false

        // When
        repositoryImpl.fetchTvShow(tvShowId).test {
            // Then
            // Expect first emitted item should be a loading resource
            expectItem() shouldBe Resource.Loading<Resource<TvShow>>()

            // Expecting a result in the next item
            when (val resource = expectItem()) {
                is Resource.Error -> throw Error("Resource Error")
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    resource.data.asClue {
                        it shouldNotBe null
                        it shouldBe expected.mapToDomain()
                    }
                    asserted = true
                }
            }
            // after item emitted, flow should be completed by this time
            expectComplete()
        }

        coVerify { tmdbDao.getFavoriteTvShow(tvShowId) }
        println("should fetch tvshow from db all asserted: $asserted")
    }

    @Test
    fun `set movie favorite state to true and false`() = testCoroutineRule.runBlockingTest {
        // Given
        val movieId = 615457
        val data = DummyDataGenerator.getMovieEntity(movieId)
        coEvery { tmdbDao.addToFavorite(data) } returns 1
        coEvery { tmdbDao.removeFromFavorite(data) } returns 1

        // When
        with(repositoryImpl) {
            val domain = data.mapToDomain()
            setFavorite(domain, true)
            setFavorite(domain, false)
        }

        // Then
        coVerifyOrder {
            tmdbDao.addToFavorite(data)
            tmdbDao.removeFromFavorite(data)
        }
    }

    @Test
    fun `set tvshow favorite state to true and false`() = testCoroutineRule.runBlockingTest {
        // Given
        val tvShowId = 1402
        val data = DummyDataGenerator.getTvShowEntity(tvShowId)
        coEvery { tmdbDao.addToFavorite(data) } returns 1
        coEvery { tmdbDao.removeFromFavorite(data) } returns 1

        // When
        with(repositoryImpl) {
            val domain = data.mapToDomain()
            setFavorite(domain, true)
            setFavorite(domain, false)
        }

        // Then
        coVerifyOrder {
            tmdbDao.addToFavorite(data)
            tmdbDao.removeFromFavorite(data)
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}