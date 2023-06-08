package com.example.messenger.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.messenger.databinding.MyMessageItemBinding
import com.example.messenger.databinding.OtherMessageItemBinding
import com.example.messenger.pojo.Message

class MessageAdapter(val currentUserId: String) : RecyclerView.Adapter<ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_MY_MESSAGE = 100
        private const val VIEW_TYPE_OTHER_MESSAGE = 101
    }

    var messageList = listOf<Message>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == VIEW_TYPE_MY_MESSAGE) {
            val view =
                MyMessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MyMessageViewHolder(view)
        } else {
            val view = OtherMessageItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            OtherMessageViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messageList[position]
        if (getItemViewType(position) == VIEW_TYPE_MY_MESSAGE) {
            val holderBinding = holder as MyMessageViewHolder
            holderBinding.binding.textViewMessage.text = message.text
        } else if(getItemViewType(position) == VIEW_TYPE_OTHER_MESSAGE) {
            val holderBinding = holder as OtherMessageViewHolder
            holderBinding.binding.textViewMessage.text = message.text
        }
    }

    override fun getItemCount() = messageList.size

    override fun getItemViewType(position: Int) =
        if (messageList[position].senderId == currentUserId)
            VIEW_TYPE_MY_MESSAGE
        else VIEW_TYPE_OTHER_MESSAGE

    private inner class MyMessageViewHolder(val binding: MyMessageItemBinding) :
        ViewHolder(binding.root)

    private inner class OtherMessageViewHolder(val binding: OtherMessageItemBinding) :
        ViewHolder(binding.root)
}