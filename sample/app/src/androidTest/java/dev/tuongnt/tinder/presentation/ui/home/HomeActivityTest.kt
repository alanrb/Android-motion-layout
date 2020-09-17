package dev.tuongnt.tinder.presentation.ui.home

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dev.tuongnt.tinder.R
import dev.tuongnt.tinder.domain.Result
import dev.tuongnt.tinder.domain.model.UserModel
import dev.tuongnt.tinder.factory.UserFactory
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeActivityTest {

    private lateinit var scenario: ActivityScenario<HomeActivity>
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomUserResult: MutableLiveData<Result<List<UserModel>>>
    private lateinit var module: Module

    @Before
    fun before() {
        viewModel = mockk(relaxed = true)
        randomUserResult = MutableLiveData()

        module = module {
            viewModel { viewModel }
        }
        loadKoinModules(module)

        every {
            viewModel.randomUserResult
        } returns randomUserResult

        val intent = Intent(ApplicationProvider.getApplicationContext(), HomeActivity::class.java)
        scenario = launchActivity(intent)
    }

    @After
    fun tearDown() {
        scenario.close()
        unloadKoinModules(module)
    }

    @Test
    fun testResultLoadingState() {
        randomUserResult.postValue(Result.Loading)
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
    }

    @Test
    fun testEmptyResult() {
        randomUserResult.postValue(Result.Success(listOf()))
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.rvUser)).check(matches(hasChildCount(0)))
    }

    @Test
    fun testNoneEmptyResult() {
        val fakeUser = UserFactory.fakeUserModel("tuongnt.dev@gmail.com")
        randomUserResult.postValue(Result.Success(listOf(fakeUser)))
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.rvUser)).check(matches(hasChildCount(1)))
    }

    @Test
    fun testSwipeRight() {
        val fakeUser = UserFactory.fakeUserModel("tuongnt.dev@gmail.com")
        randomUserResult.postValue(Result.Success(listOf(fakeUser)))

        onView(withId(R.id.rvUser)).perform(ViewActions.swipeRight())
    }

}