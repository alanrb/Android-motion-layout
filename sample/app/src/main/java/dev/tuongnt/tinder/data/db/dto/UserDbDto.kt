package dev.tuongnt.tinder.data.db.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserDbDto(

    @PrimaryKey
    val email: String,

    val md5: String,
    val fullName: String,
    val dob: String,
    val address: String,
    val mobile: String,
    val password: String,
    val picture: String
)