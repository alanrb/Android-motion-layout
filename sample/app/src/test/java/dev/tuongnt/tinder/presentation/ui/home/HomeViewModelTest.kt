package dev.tuongnt.tinder.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.google.common.truth.Truth.assertThat
import dev.tuongnt.tinder.UnitTest
import dev.tuongnt.tinder.domain.Result
import dev.tuongnt.tinder.domain.usecase.RandomUserUseCase
import dev.tuongnt.tinder.domain.usecase.SaveFavouriteUserUseCase
import dev.tuongnt.tinder.factory.UserFactory
import dev.tuongnt.tinder.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest : UnitTest() {

    private val randomUserUseCase = mockk<RandomUserUseCase>()
    private val favouriteUserUseCase = mockk<SaveFavouriteUserUseCase>()

    private val viewModel = HomeViewModel(randomUserUseCase, favouriteUserUseCase)

    @Test
    fun `should see user list when initialize`() {
        runBlocking {

            //Given
            val fakeUserList = listOf(UserFactory.fakeUserModel("tuongnt.dev@gmail.com"))
            coEvery {
                randomUserUseCase.invoke(
                    viewModel.viewModelScope,
                    any()
                )
            } returns Result.Success(fakeUserList)

            //When
            viewModel.randomUser()

            //Then
            viewModel.randomUserResult.getOrAwaitValue().let {
                assertThat(it).isInstanceOf(Result.Loading::class.java)
            }

            viewModel.randomUserResult.getOrAwaitValue().let {
                assertThat(it).isInstanceOf(Result.Success::class.java)
                assertThat((it as Result.Success).data).isEqualTo(fakeUserList)
            }
        }
    }

    @Test
    fun `can add an user to favourite`() {
        runBlocking {
            //Given
            val fakeUser = UserFactory.fakeUserModel("tuongnt.dev@gmail.com")
            coEvery {
                favouriteUserUseCase.invoke(
                    viewModel.viewModelScope,
                    fakeUser
                )
            } returns Result.Success(true)

            //When
            viewModel.addFavourite(fakeUser)

            //Then
            viewModel.favouriteResult.getOrAwaitValue().let {
                assertThat(it).isInstanceOf(Result.Loading::class.java)
            }

            viewModel.favouriteResult.getOrAwaitValue().let {
                assertThat(it).isInstanceOf(Result.Success::class.java)
                assertThat((it as Result.Success).data).isEqualTo(true)
            }
        }
    }
}