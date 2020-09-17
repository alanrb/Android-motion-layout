package dev.tuongnt.tinder.presentation.ui.favourite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dev.tuongnt.tinder.R
import dev.tuongnt.tinder.domain.Result
import dev.tuongnt.tinder.presentation.extension.observe
import kotlinx.android.synthetic.main.activity_favourite.*
import org.koin.android.viewmodel.ext.android.viewModel

class FavouriteActivity : AppCompatActivity() {

    private val viewModel: FavouriteViewModel by viewModel()
    private val userAdapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)

        setSupportActionBar(toolbar)

        with(rvUser) {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@FavouriteActivity)
        }

        observe(viewModel.userResult, { result ->
            when (result) {
                is Result.Loading -> {
                }
                is Result.Success -> {
                    userAdapter.items = result.data ?: listOf()
                }
                is Result.Failure -> {
                }
            }
        })
    }
}