package dev.tuongnt.tinder.data.repo

import dev.tuongnt.tinder.data.api.ApiService
import dev.tuongnt.tinder.data.db.dao.UserDao
import dev.tuongnt.tinder.data.db.dto.UserDbDto
import dev.tuongnt.tinder.domain.Result
import dev.tuongnt.tinder.domain.map
import dev.tuongnt.tinder.domain.model.UserModel
import dev.tuongnt.tinder.domain.repo.UserRepository

class UserRepositoryImpl(
    private val apiService: ApiService,
    private val userDao: UserDao
) : UserRepository() {

    override suspend fun randomUser() = apiService.randomUser().map { result ->
        result.results.map {
            UserModel(
                md5 = it.user.md5,
                email = it.user.email,
                picture = it.user.picture,
                address = "${it.user.location.zip}, ${it.user.location.street}",
                fullName = "${it.user.name.title}. ${it.user.name.first} ${it.user.name.last}",
                mobile = it.user.phone,
                password = it.user.password,
                dob = it.user.dob
            )
        }
    }

    override suspend fun saveUser(user: UserModel): Result<Boolean> {
        userDao.insert(
            UserDbDto(
                md5 = user.md5,
                dob = user.dob,
                password = user.password,
                mobile = user.mobile,
                fullName = user.fullName,
                address = user.address,
                picture = user.picture,
                email = user.email
            )
        )
        return Result.Success(true)
    }

    override suspend fun getFavouriteUser(): Result<List<UserModel>> {
        return Result.Success(userDao.get().map {
            UserModel(
                md5 = it.md5,
                email = it.email,
                picture = it.picture,
                address = it.address,
                fullName = it.fullName,
                mobile = it.mobile,
                password = it.password,
                dob = it.dob
            )
        })
    }
}