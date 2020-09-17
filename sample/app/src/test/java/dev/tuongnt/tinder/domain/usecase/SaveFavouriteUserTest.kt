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
class SaveFavouriteUserTest : UnitTest() {

    private val userRepository = mockk<UserRepository>()

    private lateinit var saveFavouriteUser: SaveFavouriteUserUseCase

    override fun setUp() {
        super.setUp()
        saveFavouriteUser = SaveFavouriteUserUseCase(userRepository)
    }

    @Test
    fun `should return random users list with one item`() {
        runBlocking {
            //Given
            val fakeUser = UserFactory.fakeUserModel(email = "tuongnt.dev@gmail.com")
            coEvery { userRepository.saveUser(fakeUser) } returns
                    (Result.Success(true))

            //When
            val result = saveFavouriteUser.invoke(this, fakeUser)

            //Then
            Truth.assertThat((result as Result.Success).data).isEqualTo(true)
            coVerifyAll {
                userRepository.saveUser(fakeUser)
            }
        }
    }
}