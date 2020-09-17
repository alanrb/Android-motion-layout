package dev.tuongnt.tinder.presentation.ui.favourite

import androidx.lifecycle.viewModelScope
import dev.tuongnt.tinder.domain.usecase.GetFavouriteUserUseCase
import dev.tuongnt.tinder.domain.usecase.UseCase
import dev.tuongnt.tinder.presentation.common.BaseViewModel

class FavouriteViewModel(private val getFavouriteUserUseCase: GetFavouriteUserUseCase) :
    BaseViewModel() {

    val userResult = liveDataResult {
        getFavouriteUserUseCase.invoke(viewModelScope, UseCase.None())
    }
}