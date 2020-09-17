package dev.tuongnt.tinder.presentation.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.tuongnt.tinder.domain.model.UserModel
import dev.tuongnt.tinder.presentation.ui.favourite.UserViewHolder

class UserSwipeAdapter(var items: MutableList<UserModel> = mutableListOf()) :
    RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(parent)

    override fun onBindViewHolder(viewHolder: UserViewHolder, position: Int) {
        viewHolder.bind(items[position])
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount() = items.size
}