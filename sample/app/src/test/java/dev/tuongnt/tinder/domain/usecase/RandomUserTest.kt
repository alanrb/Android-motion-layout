package dev.tuongnt.tinder.domain.usecase

import com.google.common.truth.Truth
import org.junit.Before

import dev.tuongnt.tinder.UnitTest
import dev.tuongnt.tinder.domain.Result
import dev.tuongnt.tinder.domain.repo.UserRepository
import dev.tuongnt.tinder.factory.UserFactory
import io.mockk.coEvery
import io.mockk.coVerifyAll
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking

import org.junit.Assert.*
import org.junit.Test

@ExperimentalCoroutinesApi
class RandomUserTest : UnitTest() {

    private val userRepository = mockk<UserRepository>()

    private lateinit var randomUser: RandomUserUseCase

    override fun setUp() {
        super.setUp()
        randomUser = RandomUserUseCase(userRepository)
    }

    @Test
    fun `should return random users list with one item`() {
        runBlocking {
            //Given
            val fakeUserList = UserFactory.fakeListOfUserModel(count = 1)
            coEvery { userRepository.randomUser() } returns
                    (Result.Success(fakeUserList))

            //When
            val result = randomUser.invoke(this, UseCase.None())

            //Then
            Truth.assertThat((result as Result.Success).data).isEqualTo(fakeUserList)
            Truth.assertThat((result as Result.Success).data?.size).isEqualTo(1)
            coVerifyAll {
                userRepository.randomUser()
            }
        }
    }
}