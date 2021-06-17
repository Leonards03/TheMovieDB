package com.dicoding.bfaa.tmdb.detail

import android.content.Intent
import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.dicoding.bfaa.tmdb.R
import com.dicoding.bfaa.tmdb.core.data.states.ItemType
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.domain.model.TvShow
import com.dicoding.bfaa.tmdb.core.utils.EspressoIdlingResource
import com.dicoding.bfaa.tmdb.detail.DetailViewModel.Companion.EXTRA_ID
import com.dicoding.bfaa.tmdb.detail.DetailViewModel.Companion.EXTRA_TYPE
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailActivityTest {
    private val dummyMovie = Movie(
        id = 615457,
        title = "Nobody",
        backdrop = "/6zbKgwgaaCyyBXE4Sun4oWQfQmi.jpg",
        poster = "/oBgWY00bEFeZ9N25wWVyuQddbAo.jpg",
        genres = "Crime,Action",
        overview = "Hutch Mansell, a suburban dad, overlooked husband, nothing neighbor — a \\\"nobody.\\\" When two thieves break into his home one night, Hutch's unknown long-simmering rage is ignited and propels him on a brutal path that will uncover dark secrets he fought to leave behind.",
        voteAverage = 8.4,
        voteCount = 1845,
        runtime = 92,
        releaseDate = "2021-03-26",
        director = "Ilya Naishuller"
    )

    private val dummyTvShow = TvShow(
        id = 1402,
        title = "The Walking Dead",
        backdrop = "/uro2Khv7JxlzXtLb8tCIbRhkb9E.jpg",
        poster = "/rqeYMLryjcawh2JeRpCVUDXYM5b.jpg",
        overview = "Sheriff's deputy Rick Grimes awakens from a coma to find a post-apocalyptic world dominated by flesh-eating zombies. He sets out to find his family and encounters many other survivors along the way.",
        voteAverage = 8.1,
        voteCount = 10909,
        firstAirDate = "2010-10-31",
        lastAirDate = "",
        numberOfEpisodes = 0,
        numberOfSeasons = 0,
        genres = "Action & Adventure,Drama",
        status = ""
    )

    private val intent =
        Intent(InstrumentationRegistry.getInstrumentation().targetContext,
            DetailActivity::class.java)
    lateinit var scenario: ActivityScenario<DetailActivity>

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    fun wait(delay: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()

            override fun getDescription(): String = "UI delayed for $delay milliseconds"

            override fun perform(uiController: UiController, view: View) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }

    @Test
    fun loadMovie() {
        scenario = launchActivity(intent.apply {
            putExtra(EXTRA_ID, dummyMovie.id)
            putExtra(EXTRA_TYPE, ItemType.Movie)
        })

        onView(isRoot()).perform(wait(1000L))

        onView(withId(R.id.collapsing_toolbar)).perform(swipeUp())

        onView(withId(R.id.tv_title))
            .check(matches(isDisplayed()))
            .check(matches(withText(dummyMovie.title)))

        onView(withId(R.id.tv_genre))
            .check(matches(isDisplayed()))
            .check(matches(withText(dummyMovie.genres)))

        onView(withId(R.id.tv_rating)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_runtime))
            .check(matches(isDisplayed()))
            .check(matches(withText(String.format("%d min", dummyMovie.runtime))))


        onView(withId(R.id.tv_description)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()))

        onView(withId(R.id.btn_favorite))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(com.google.android.material.R.id.snackbar_text)).check(matches(isDisplayed()))
    }

    @Test
    fun loadTvShow() {
        scenario = launchActivity(intent.apply {
            putExtra(EXTRA_ID, dummyTvShow.id)
            putExtra(EXTRA_TYPE, ItemType.TvShow)
        })

        onView(isRoot()).perform(wait(1000L))

        onView(withId(R.id.collapsing_toolbar)).perform(swipeUp())

        onView(withId(R.id.tv_title))
            .check(matches(isDisplayed()))
            .check(matches(withText(dummyTvShow.title)))

        onView(withId(R.id.tv_genre))
            .check(matches(isDisplayed()))
            .check(matches(withText(dummyTvShow.genres)))

        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()))

        onView(withId(R.id.btn_favorite))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
        scenario.close()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }
}