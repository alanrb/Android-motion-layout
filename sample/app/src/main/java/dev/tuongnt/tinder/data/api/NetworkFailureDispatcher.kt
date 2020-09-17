package dev.tuongnt.tinder.data.api

import dev.tuongnt.tinder.domain.Result

/**
 * Created by Tuong (Alan) on 9/13/20.
 * Copyright (c) 2020 Buuuk. All rights reserved.
 */

interface NetworkFailureDispatcher {
    fun failureFromCode(code: Int, body: Any?): Result.Failure
    fun failureFromThrowable(throwable: Throwable): Result.Failure
}