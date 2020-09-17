package dev.tuongnt.tinder.di

import dev.tuongnt.tinder.domain.usecase.GetFavouriteUserUseCase
import dev.tuongnt.tinder.domain.usecase.RandomUserUseCase
import dev.tuongnt.tinder.domain.usecase.SaveFavouriteUserUseCase
import org.koin.dsl.module


val useCaseModule = module {
    factory { GetFavouriteUserUseCase(get()) }
    factory { RandomUserUseCase(get()) }
    factory { SaveFavouriteUserUseCase(get()) }
}