package dev.tuongnt.tinder.data.api.dto

data class UserApiDto(
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val username: String,
    val registered: String,
    val password: String,
    val md5: String,
    val dob: String,
    val phone: String,
    val cell: String,
    val picture: String
) {
    data class Name(
        val title: String,
        val first: String,
        val last: String
    )

    data class Location(
        val street: String,
        val city: String,
        val state: String,
        val zip: String
    )
}