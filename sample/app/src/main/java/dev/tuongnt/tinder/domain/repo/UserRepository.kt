package dev.tuongnt.tinder.domain.repo

import dev.tuongnt.tinder.domain.Result
import dev.tuongnt.tinder.domain.model.UserModel

abstract class UserRepository {

    abstract suspend fun randomUser(): Result<List<UserModel>>
    abstract suspend fun saveUser(user: UserModel): Result<Boolean>
    abstract suspend fun getFavouriteUser(): Result<List<UserModel>>
}