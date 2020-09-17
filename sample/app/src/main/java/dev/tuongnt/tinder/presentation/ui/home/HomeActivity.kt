package dev.tuongnt.tinder.presentation.ui.home

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dev.tuongnt.tinder.R
import dev.tuongnt.tinder.domain.Result
import dev.tuongnt.tinder.presentation.extension.gotoActivity
import dev.tuongnt.tinder.presentation.extension.observe
import dev.tuongnt.tinder.presentation.ui.favourite.FavouriteActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModel()

    private val userAdapter = UserSwipeAdapter()

    private lateinit var colorDrawableBackground: ColorDrawable
    private lateinit var likeIcon: Drawable
    private lateinit var nextIcon: Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)

        colorDrawableBackground =
            ColorDrawable(ContextCompat.getColor(this, R.color.colorBackground))
        likeIcon = ContextCompat.getDrawable(this, R.drawable.img_like)!!
        nextIcon = ContextCompat.getDrawable(this, R.drawable.img_next)!!

        with(rvUser) {
            setHasFixedSize(true)
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@HomeActivity)
        }
        val itemTouchHelperCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                viewHolder2: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDirection: Int) {
                when (swipeDirection) {
                    ItemTouchHelper.LEFT -> {
                        userAdapter.removeItem(viewHolder.adapterPosition)
                        viewModel.randomUser()
                    }
                    ItemTouchHelper.RIGHT -> {
                        val item = userAdapter.items[viewHolder.bindingAdapterPosition]
                        viewModel.addFavourite(userModel = item)
                        userAdapter.notifyItemChanged(viewHolder.bindingAdapterPosition)
                    }
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val iconMarginVertical =
                    (viewHolder.itemView.height - likeIcon.intrinsicHeight) / 2

                if (dX > 0) {
                    colorDrawableBackground.setBounds(
                        itemView.left,
                        itemView.top,
                        dX.toInt(),
                        itemView.bottom
                    )
                    likeIcon.setBounds(
                        itemView.left + iconMarginVertical,
                        itemView.top + iconMarginVertical,
                        itemView.left + iconMarginVertical + likeIcon.intrinsicWidth,
                        itemView.bottom - iconMarginVertical
                    )
                } else {
                    colorDrawableBackground.setBounds(
                        itemView.right + dX.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )
                    nextIcon.setBounds(
                        itemView.right - iconMarginVertical - nextIcon.intrinsicWidth,
                        itemView.top + iconMarginVertical,
                        itemView.right - iconMarginVertical,
                        itemView.bottom - iconMarginVertical
                    )
                    nextIcon.level = 0
                }

                colorDrawableBackground.draw(c)

                c.save()

                if (dX > 0) {
                    c.clipRect(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                    likeIcon.draw(c)
                } else {
                    c.clipRect(
                        itemView.right + dX.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )
                    nextIcon.draw(c)
                }

                c.restore()

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvUser)

        btnRetry.setOnClickListener {
            viewModel.randomUser()
        }

        btnFavourite.setOnClickListener {
            gotoActivity(FavouriteActivity::class)
        }

        observe(viewModel.randomUserResult, { result ->
            when (result) {
                is Result.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    btnRetry.visibility = View.GONE
                }
                is Result.Success -> {
                    progressBar.visibility = View.GONE
                    btnRetry.visibility = View.GONE
                    userAdapter.items = result.data?.toMutableList() ?: mutableListOf()
                    userAdapter.notifyDataSetChanged()
                }
                is Result.Failure -> {
                    btnRetry.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
            }
        })

        observe(viewModel.favouriteResult, { result ->
            when (result) {
                is Result.Loading -> {

                }
                is Result.Success -> {
                    Snackbar.make(rvUser, "Added to favourite", Snackbar.LENGTH_SHORT)
                        .show()
                }
                is Result.Failure -> {

                }
            }
        })
        viewModel.randomUser()
    }

}