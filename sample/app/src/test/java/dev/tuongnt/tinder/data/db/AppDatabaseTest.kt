package dev.tuongnt.tinder.data.db

import androidx.room.Room
import com.google.common.truth.Truth.assertThat
import dev.tuongnt.tinder.AndroidUnitTest
import dev.tuongnt.tinder.data.db.dao.UserDao
import dev.tuongnt.tinder.factory.UserFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

@ExperimentalCoroutinesApi
class AppDatabaseTest : AndroidUnitTest() {

    private lateinit var userDao: UserDao
    private lateinit var database: AppDatabase

    override fun setUp() {
        super.setUp()
        database = Room.inMemoryDatabaseBuilder(application.applicationContext, AppDatabase::class.java).build()
        userDao = database.userDao()
    }

    override fun tearDown() {
        super.tearDown()
        database.close()
    }

    @Test
    fun `insert list and query test`() {
        runBlocking(Dispatchers.IO) {
            //Given
            val fakeUserList = UserFactory.fakeListOfUserDbDto(count = 10)

            //When
            userDao.insertAll(fakeUserList)
            val userList = userDao.get()

            //Then
            assertThat(userList).isEqualTo(fakeUserList)
        }
    }

    @Test
    fun `delete list and query test`() {
        runBlocking(Dispatchers.IO) {
            //Given
            val fakeUserList = UserFactory.fakeListOfUserDbDto(count = 10)

            //When
            userDao.insertAll(fakeUserList)
            val userList = userDao.get()

            //Then
            assertThat(userList).isEqualTo(fakeUserList)

            //When
            userDao.deleteAll()

            assertThat(userDao.get().size).isEqualTo(0)
        }
    }

    @Test
    fun `insert and query test`() {
        runBlocking(Dispatchers.IO) {
            //Given
            val fakeUser = UserFactory.fakeUserDbDto("tuongnt.dev@gmail.com")

            //When
            userDao.insert(fakeUser)
            val userList = userDao.get()

            //Then
            assertThat(userList).isEqualTo(listOf(fakeUser))
        }
    }
}