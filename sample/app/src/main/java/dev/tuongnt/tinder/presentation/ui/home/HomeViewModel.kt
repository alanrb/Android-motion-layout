package dev.tuongnt.tinder.presentation.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.tuongnt.tinder.domain.Result
import dev.tuongnt.tinder.domain.model.UserModel
import dev.tuongnt.tinder.domain.usecase.RandomUserUseCase
import dev.tuongnt.tinder.domain.usecase.SaveFavouriteUserUseCase
import dev.tuongnt.tinder.domain.usecase.UseCase
import dev.tuongnt.tinder.presentation.common.BaseViewModel
import dev.tuongnt.tinder.presentation.common.SingleLiveEvent
import dev.tuongnt.tinder.presentation.extension.coSwitchMap

class HomeViewModel(
    private val randomUserUseCase: RandomUserUseCase,
    private val favouriteUserUseCase: SaveFavouriteUserUseCase
) : BaseViewModel() {

    private val favouriteInvoker = MutableLiveData<UserModel>()
    private val randomInvoker = SingleLiveEvent<Any>()

    val randomUserResult = randomInvoker.coSwitchMap(Result.Loading) {
        randomUserUseCase.invoke(viewModelScope, UseCase.None())
    }

    val favouriteResult = favouriteInvoker.coSwitchMap(Result.Loading) {
        favouriteUserUseCase.invoke(viewModelScope, it)
    }

    fun randomUser() {
        randomInvoker.call()
    }

    fun addFavourite(userModel: UserModel) {
        favouriteInvoker.value = userModel
    }
}