package dev.tuongnt.tinder.di

import dev.tuongnt.tinder.data.repo.UserRepositoryImpl
import dev.tuongnt.tinder.domain.repo.UserRepository
import org.koin.dsl.module

val dataModule = module {
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
}