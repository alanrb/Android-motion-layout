package dev.tuongnt.tinder.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.tuongnt.tinder.data.db.dao.UserDao
import dev.tuongnt.tinder.data.db.dto.UserDbDto

@Database(
    entities = [UserDbDto::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}