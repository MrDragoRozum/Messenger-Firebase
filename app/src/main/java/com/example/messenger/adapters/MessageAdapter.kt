package com.example.messenger.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.R
import com.example.messenger.pojo.Message

class MessageAdapter(private val currentUserId: String) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

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
            Log.d("MessageAdapter", "onCreateViewHolder: $viewType")
            val view =
               LayoutInflater.from(parent.context).inflate(R.layout.my_message_item, parent, false)
            ViewHolder(view)
        } else {
            Log.d("MessageAdapter", "onCreateViewHolder: $viewType")
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.other_message_item, parent, false)
            ViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messageList[position]
        holder.textViewMessage.text = message.text
    }

    override fun getItemCount() = messageList.size

    override fun getItemViewType(position: Int) =
        if (messageList[position].senderId == currentUserId)
            VIEW_TYPE_MY_MESSAGE
        else VIEW_TYPE_OTHER_MESSAGE

   inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val textViewMessage: TextView = itemView.findViewById(R.id.textViewMessage)
   }
}