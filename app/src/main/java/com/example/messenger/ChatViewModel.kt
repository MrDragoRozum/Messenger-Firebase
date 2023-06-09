package com.example.messenger

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.messenger.pojo.Message
import com.example.messenger.pojo.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatViewModel(val currentUserId: String, val otherUserId: String) : ViewModel() {
    private val error = MutableLiveData<String>()
    private val messageSent = MutableLiveData<Boolean>()
    private val messages = MutableLiveData<List<Message>>()
    private val userOther = MutableLiveData<User>()

    private val database = FirebaseDatabase.getInstance()
    private val referenceUsers = database.getReference("Users")
    private val referenceMessages = database.getReference("Messages")

    init {
        referenceUsers.child(otherUserId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userOther.value = snapshot.getValue(User::class.java)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        referenceMessages.child(otherUserId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val messageList = mutableListOf<Message>()
                for (item in snapshot.children) {
                    val message = item.getValue(Message::class.java) ?: return
                    messageList.add(message)
                }
                messages.value = messageList
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun onlineUserStatus(isOnline: Boolean) {
        referenceUsers.child(currentUserId).child("online").setValue(isOnline)
    }

    val getError: LiveData<String> get() = error
    val getMessageSent: LiveData<Boolean> get() = messageSent
    val getMessages: LiveData<List<Message>> get() = messages
    val getUserOther: LiveData<User> get() = userOther

    fun sendMessage(message: Message) {
        with(message) {
            referenceMessages
                .child(senderId)
                .child(receiverId)
                .push()
                .setValue(this)
                .addOnSuccessListener {
                    referenceMessages
                        .child(receiverId)
                        .child(senderId)
                        .push()
                        .setValue(this)
                }
        }
    }
}