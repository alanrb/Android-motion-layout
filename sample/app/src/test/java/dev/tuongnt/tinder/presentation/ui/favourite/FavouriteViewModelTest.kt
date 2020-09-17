package dev.tuongnt.tinder.presentation.ui.favourite

import androidx.lifecycle.viewModelScope
import com.google.common.truth.Truth
import dev.tuongnt.tinder.UnitTest
import dev.tuongnt.tinder.domain.Result
import dev.tuongnt.tinder.domain.usecase.GetFavouriteUserUseCase
import dev.tuongnt.tinder.factory.UserFactory
import dev.tuongnt.tinder.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

@ExperimentalCoroutinesApi
class FavouriteViewModelTest : UnitTest() {

    private val getFavouriteUserUseCase = mockk<GetFavouriteUserUseCase>()

    @Test
    fun `should see favourite list when initialize`() {
        runBlocking {

            val viewModel = FavouriteViewModel(getFavouriteUserUseCase)

            //Given
            val fakeUserList = UserFactory.fakeListOfUserModel()
            coEvery {
                getFavouriteUserUseCase.invoke(
                    viewModel.viewModelScope,
                    any()
                )
            } returns Result.Success(fakeUserList)

            //Then
            viewModel.userResult.getOrAwaitValue().let {
                Truth.assertThat(it).isInstanceOf(Result.Success::class.java)
                Truth.assertThat((it as Result.Success).data).isEqualTo(fakeUserList)
            }
        }
    }
}