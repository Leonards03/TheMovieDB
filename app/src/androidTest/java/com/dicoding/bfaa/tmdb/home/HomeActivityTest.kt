package com.dicoding.bfaa.tmdb.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.dicoding.bfaa.tmdb.R
import com.dicoding.bfaa.tmdb.core.utils.EspressoIdlingResource
import com.google.android.material.tabs.TabLayout
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomeActivityTest {
    private val targetPosition = 19

    @Before
    fun setUp() {
        ActivityScenario.launch(HomeActivity::class.java)
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

    /**
     * https://stackoverflow.com/questions/49626315/how-to-select-a-specific-tab-position-in-tab-layout-using-espresso-testing
     */
    fun selectTabAtPosition(tabIndex: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription() = "with tab at index $tabIndex"

            override fun getConstraints() =
                allOf(isDisplayed(), isAssignableFrom(TabLayout::class.java))

            override fun perform(uiController: UiController, view: View) {
                val tabLayout = view as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabIndex)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index $tabIndex"))
                        .build()

                tabAtIndex.select()
            }
        }
    }

    @Test
    fun clickMovieTab() {
        onView(withId(R.id.bottom_nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_movie)).perform(click())

        onView(isRoot()).perform(wait(1000L))
        onView(withId(R.id.rv_discover_movie))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(targetPosition))
    }

    @Test
    fun clickTvShowTab() {
        onView(withId(R.id.bottom_nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_tv_show)).perform(click())

        onView(isRoot()).perform(wait(1000L))
        onView(withId(R.id.rv_discover_tv))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(targetPosition))
    }

    @Test
    fun checkFavoriteTabs() {
        onView(withId(R.id.bottom_nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_favorite)).perform(click())

        onView(isRoot()).perform(wait(1000L))
        onView(withId(R.id.tabs))
            .check(matches(isDisplayed()))
            .perform(selectTabAtPosition(0))
            .perform(selectTabAtPosition(1))
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }
}