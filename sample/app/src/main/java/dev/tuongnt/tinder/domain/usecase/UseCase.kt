package dev.tuongnt.tinder.domain.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Type

    suspend fun invoke(scope: CoroutineScope, params: Params): Type =
        withContext(scope.coroutineContext + Dispatchers.IO) { run(params) }

    class None
}