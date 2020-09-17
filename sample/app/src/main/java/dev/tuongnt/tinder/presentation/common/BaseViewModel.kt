package dev.tuongnt.tinder.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

abstract class BaseViewModel : ViewModel() {

    fun <T> liveDataResult(block: suspend () -> T) = liveData { emit(block.invoke()) }
}