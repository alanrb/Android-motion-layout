package dev.tuongnt.tinder.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.tuongnt.tinder.data.db.dto.UserDbDto

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: UserDbDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<UserDbDto>)

    @Query("SELECT * FROM user")
    suspend fun get(): List<UserDbDto>

    @Query("DELETE FROM user")
    suspend fun deleteAll()
}