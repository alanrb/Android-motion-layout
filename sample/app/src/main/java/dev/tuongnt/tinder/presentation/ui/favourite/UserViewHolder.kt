package dev.tuongnt.tinder.presentation.ui.favourite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import dev.tuongnt.tinder.R
import dev.tuongnt.tinder.domain.model.UserModel
import dev.tuongnt.tinder.presentation.extension.toDateString
import kotlinx.android.synthetic.main.item_user.view.*

class UserViewHolder(
    parent: ViewGroup,
    private val rootView: View = LayoutInflater.from(parent.context).inflate(
        R.layout.item_user,
        parent,
        false
    )
) : RecyclerView.ViewHolder(rootView) {

    fun bind(user: UserModel) {

        rootView.apply {
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> {
                            tvCaption.text = context.getString(R.string.lbl_my_name)
                            tvHeader.text = user.fullName
                        }
                        1 -> {
                            tvCaption.text = context.getString(R.string.lbl_my_birthday)
                            tvHeader.text = user.dob.toDateString()
                        }
                        2 -> {
                            tvCaption.text = context.getString(R.string.lbl_my_address)
                            tvHeader.text = user.address
                        }
                        3 -> {
                            tvCaption.text = context.getString(R.string.lbl_my_mobile)
                            tvHeader.text = user.mobile
                        }
                        4 -> {
                            tvCaption.text = context.getString(R.string.lbl_my_password)
                            tvHeader.text = user.password
                        }
                        else -> {
                        }
                    }
                }

            })
            tabLayout.selectTab(tabLayout.getTabAt(0))
            tvHeader.text = user.fullName
            Glide.with(context).load(user.picture).circleCrop().into(imgAvatar)
        }
    }
}