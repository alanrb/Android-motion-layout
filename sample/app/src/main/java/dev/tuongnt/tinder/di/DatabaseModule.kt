package dev.tuongnt.tinder.di

import androidx.room.Room
import dev.tuongnt.tinder.data.db.AppDatabase
import org.koin.dsl.module


val databaseModule = module {

    single { Room.databaseBuilder(get(), AppDatabase::class.java, "Tinder").build() }
    single { get<AppDatabase>().userDao() }
}