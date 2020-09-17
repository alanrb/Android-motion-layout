package dev.tuongnt.tinder.data.repo

import com.google.common.truth.Truth.assertThat
import dev.tuongnt.tinder.UnitTest
import dev.tuongnt.tinder.data.api.ApiService
import dev.tuongnt.tinder.data.api.dto.ResultApiDto
import dev.tuongnt.tinder.data.api.dto.UserResultApiDto
import dev.tuongnt.tinder.data.db.dao.UserDao
import dev.tuongnt.tinder.domain.Result
import dev.tuongnt.tinder.domain.model.UserModel
import dev.tuongnt.tinder.factory.UserFactory
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

@ExperimentalCoroutinesApi
class UserRepositoryTest : UnitTest() {

    private val apiService = mockk<ApiService>()

    private val userDao = mockk<UserDao>()

    private val repository = UserRepositoryImpl(apiService, userDao)

    @Test
    fun `should return user list`() {
        runBlocking {
            //Given
            coEvery { apiService.randomUser() } returns Result.Success(
                ResultApiDto(
                    results = listOf(
                        UserResultApiDto(
                            user = UserFactory.fakeUserApiDto("tuongnt.dev@gmail.com")
                        )
                    )
                )
            )

            //When
            val result = repository.randomUser()

            //Then
            assertThat(result).isInstanceOf(Result.Success::class.java)
            assertThat((result as Result.Success).data!!.size).isEqualTo(1)
            assertThat(result.data!!).isEqualTo(listOf(UserFactory.fakeUserModel("tuongnt.dev@gmail.com")))
            coVerify(exactly = 1) { apiService.randomUser() }
        }
    }

    @Test
    fun `can save user to local`() {
        runBlocking {
            val fakeUser = UserFactory.fakeUserModel("tuongnt.dev@gmail.com")

            //Given
            coEvery { userDao.insert(any()) } just Runs

            //When
            val saveResult = repository.saveUser(fakeUser)

            //Then
            assertThat(saveResult).isInstanceOf(Result.Success::class.java)
        }
    }

    @Test
    fun `should return favourite list`() {
        runBlocking {

            //Given
            coEvery { userDao.get() } returns listOf(UserFactory.fakeUserDbDto("tuongnt.dev@gmail.com"))

            //When
            val result = repository.getFavouriteUser()

            //Then
            assertThat(result).isInstanceOf(Result.Success::class.java)
            assertThat((result as Result.Success).data!!).isEqualTo(
                listOf(
                    UserFactory.fakeUserModel(
                        "tuongnt.dev@gmail.com"
                    )
                )
            )
            assertThat(result.data!![0]).isInstanceOf(UserModel::class.java)
        }
    }
}