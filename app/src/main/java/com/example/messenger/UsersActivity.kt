package com.example.messenger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.messenger.adapters.UsersAdapter
import com.example.messenger.databinding.ActivityUsersBinding

class UsersActivity : AppCompatActivity() {
    private lateinit var viewModel: UserViewModel
    private lateinit var binding: ActivityUsersBinding
    private val adapter = UsersAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        binding.recyclerViewUsers.adapter = adapter
        listenerAdapter()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.getUser.observe(this) {
            if (it == null) {
                val intent = MainActivity.newIntent(this)
                startActivity(intent)
                finish()
            }

        }
        viewModel.getUsers.observe(this) { adapter.users = it }
    }

    private fun listenerAdapter() {
        adapter.onUserClickListener = UsersAdapter.OnUserClickListener {

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemSingOut) viewModel.logout()
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onlineUserStatus(true)
    }

    override fun onStop() {
        super.onStop()
        viewModel.onlineUserStatus(false)
    }

    companion object {
        private const val EXTRA_CURRENT_USER_ID = "user_id"

        fun newIntent(context: Context, currentUserId: String): Intent {
            val intent = Intent(context, UsersActivity::class.java)
            intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId)
            return intent
        }
    }
}