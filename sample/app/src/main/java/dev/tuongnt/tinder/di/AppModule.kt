package dev.tuongnt.tinder.di

import dev.tuongnt.tinder.presentation.ui.favourite.FavouriteViewModel
import dev.tuongnt.tinder.presentation.ui.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { HomeViewModel(get(), get()) }
    viewModel { FavouriteViewModel(get()) }
}