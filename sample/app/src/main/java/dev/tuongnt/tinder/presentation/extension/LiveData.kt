package dev.tuongnt.tinder.presentation.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap

inline fun <X, Y> LiveData<X>.coSwitchMap(
    initialValue: Y? = null,
    crossinline transform: suspend (X) -> Y
): LiveData<Y> = this.switchMap {
    liveData {
        if (initialValue != null) emit(initialValue!!)
        emit(transform(it))
    }
}
