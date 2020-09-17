package dev.tuongnt.tinder.domain.model

data class UserModel(
    val md5: String,
    val fullName: String,
    val email: String,
    val dob: String,
    val address: String,
    val mobile: String,
    val password: String,
    val picture: String
)