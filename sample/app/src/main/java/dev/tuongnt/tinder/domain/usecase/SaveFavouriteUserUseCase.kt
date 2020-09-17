package dev.tuongnt.tinder.domain.usecase

import dev.tuongnt.tinder.domain.Result
import dev.tuongnt.tinder.domain.model.UserModel
import dev.tuongnt.tinder.domain.repo.UserRepository

class SaveFavouriteUserUseCase(private val userRepo: UserRepository) :
    UseCase<Result<Boolean>, UserModel>() {

    override suspend fun run(params: UserModel) = userRepo.saveUser(params)

}