package dev.tuongnt.tinder.factory

import dev.tuongnt.tinder.data.api.dto.UserApiDto
import dev.tuongnt.tinder.data.db.dto.UserDbDto
import dev.tuongnt.tinder.domain.model.UserModel

class UserFactory {

    companion object {

        fun fakeUserModel(email: String) = UserModel(
            email = email,
            picture = "picture of $email",
            address = "339696, street of $email",
            fullName = "mr. first name $email last name $email",
            mobile = "mobile of $email",
            password = "password of $email",
            dob = "361714905",
            md5 = "md5 of $email"
        )

        fun fakeUserDbDto(email: String) = UserDbDto(
            email = email,
            picture = "picture of $email",
            address = "339696, street of $email",
            fullName = "mr. first name $email last name $email",
            mobile = "mobile of $email",
            password = "password of $email",
            dob = "361714905",
            md5 = "md5 of $email"
        )

        fun fakeUserApiDto(email: String) = UserApiDto(
            email = email,
            picture = "picture of $email",
            password = "password of $email",
            dob = "361714905",
            md5 = "md5 of $email",
            name = UserApiDto.Name(
                title = "mr",
                first = "first name $email",
                last = "last name $email"
            ),
            cell = "cell of $email",
            gender = "male",
            location = UserApiDto.Location(
                state = "state of $email",
                city = "city of $email",
                street = "street of $email",
                zip = "339696"
            ),
            phone = "mobile of $email",
            registered = "1358264167",
            username = "username of $email"
        )

        fun fakeListOfUserDbDto(count: Int = 5): List<UserDbDto> {
            val userList = mutableListOf<UserDbDto>()
            for (i in 1..count) {
                userList.add(fakeUserDbDto(i.toString()))
            }
            return userList
        }

        fun fakeListOfUserModel(count: Int = 5): List<UserModel> {
            val userList = mutableListOf<UserModel>()
            for (i in 1..count) {
                userList.add(fakeUserModel(i.toString()))
            }
            return userList
        }

        fun fakeListOfUserApiDto(count: Int = 5): List<UserApiDto> {
            val userList = mutableListOf<UserApiDto>()
            for (i in 1..count) {
                userList.add(fakeUserApiDto(i.toString()))
            }
            return userList
        }
    }
}