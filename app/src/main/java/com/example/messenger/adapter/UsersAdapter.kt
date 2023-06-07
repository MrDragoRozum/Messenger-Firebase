package com.example.messenger.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.R
import com.example.messenger.databinding.UserItemBinding
import com.example.messenger.pojo.User

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    var users = listOf<User>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onUserClickListener: OnUserClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = users[position]
        val userInfo = String.format("%s %s %s", user.name, user.lastname, user.age)
        val backgroundId = if (user.online) R.drawable.circle_green else R.drawable.circle_red
        val background = ContextCompat.getDrawable(holder.itemView.context, backgroundId)
        with(holder.binding) {
            textViewUser.text = userInfo
            viewStatus.background = background
            textViewUser.setOnClickListener { onUserClickListener?.userClickListener(user) }
        }
    }

    override fun getItemCount() = users.size

    inner class UsersViewHolder(val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun interface OnUserClickListener {
        fun userClickListener(user: User)
    }
}