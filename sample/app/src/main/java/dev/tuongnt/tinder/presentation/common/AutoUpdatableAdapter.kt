package dev.tuongnt.tinder.presentation.common

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates

abstract class AutoUpdatableAdapter<V, VH> : RecyclerView.Adapter<VH>() where VH : RecyclerView.ViewHolder {

    var items: List<V> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(
            oldList,
            newList,
            { i1, i2 -> compareItem(i1, i2) },
            { i1, i2 -> compareContent(i1, i2) }
        )
    }

    abstract fun compareItem(oldItem: V, newItem: V): Boolean

    abstract fun compareContent(oldItem: V, newItem: V): Boolean

    override fun getItemCount() = items.count()

    private inline fun RecyclerView.Adapter<*>.autoNotify(
        oldList: List<V>,
        newList: List<V>,
        crossinline compareItem: (V, V) -> Boolean,
        crossinline compareContent: (V, V) -> Boolean
    ) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return compareItem(oldList[oldItemPosition], newList[newItemPosition])
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return compareContent(oldList[oldItemPosition], newList[newItemPosition])
            }

            override fun getOldListSize() = oldList.size

            override fun getNewListSize() = newList.size
        })

        diff.dispatchUpdatesTo(this)
    }
}