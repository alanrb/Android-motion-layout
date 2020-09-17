package dev.tuongnt.tinder.presentation.ui.favourite

import android.view.ViewGroup
import dev.tuongnt.tinder.domain.model.UserModel
import dev.tuongnt.tinder.presentation.common.AutoUpdatableAdapter

class UserAdapter : AutoUpdatableAdapter<UserModel, UserViewHolder>() {

    override fun compareItem(oldItem: UserModel, newItem: UserModel) = oldItem.md5 == newItem.md5

    override fun compareContent(oldItem: UserModel, newItem: UserModel) =
        oldItem.email == newItem.email
                && oldItem.picture == newItem.picture

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(parent)

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(items[position])
    }
}