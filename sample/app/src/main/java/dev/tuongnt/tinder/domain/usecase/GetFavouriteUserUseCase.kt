package dev.tuongnt.tinder.domain.usecase

import dev.tuongnt.tinder.domain.Result
import dev.tuongnt.tinder.domain.model.UserModel
import dev.tuongnt.tinder.domain.repo.UserRepository

class GetFavouriteUserUseCase(private val userRepo: UserRepository) :
    UseCase<Result<List<UserModel>>, UseCase.None>() {

    override suspend fun run(params: None) = userRepo.getFavouriteUser()
}