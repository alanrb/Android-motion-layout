package dev.tuongnt.tinder.domain.usecase

import com.google.common.truth.Truth
import dev.tuongnt.tinder.UnitTest
import dev.tuongnt.tinder.domain.Result
import dev.tuongnt.tinder.domain.repo.UserRepository
import dev.tuongnt.tinder.factory.UserFactory
import io.mockk.coEvery
import io.mockk.coVerifyAll
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

@ExperimentalCoroutinesApi
class GetFavouriteUserTest : UnitTest() {

    private val userRepository = mockk<UserRepository>()

    private lateinit var getFavouriteUser: GetFavouriteUserUseCase

    override fun setUp() {
        super.setUp()
        getFavouriteUser = GetFavouriteUserUseCase(userRepository)
    }

    @Test
    fun `should return favourite users list`() {
        runBlocking {
            //Given
            val fakeUserList = UserFactory.fakeListOfUserModel()
            coEvery { userRepository.getFavouriteUser() } returns
                    (Result.Success(fakeUserList))

            //When
            val result = getFavouriteUser.invoke(this, UseCase.None())

            //Then
            Truth.assertThat((result as Result.Success).data).isEqualTo(fakeUserList)
            coVerifyAll {
                userRepository.getFavouriteUser()
            }
        }
    }
}