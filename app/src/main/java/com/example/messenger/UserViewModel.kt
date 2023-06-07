package com.example.messenger

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.messenger.pojo.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserViewModel : ViewModel() {
    private val user = MutableLiveData<FirebaseUser>()
    private val users = MutableLiveData<List<User>>()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val userReference: DatabaseReference = database.getReference("Users")

    init {
        auth.addAuthStateListener { user.value = it.currentUser }
        userReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userList = mutableListOf<User>()
                if (auth.currentUser != null) return

                for (item in snapshot.children)
                    userList.add(item.getValue(User::class.java) ?: return)
                users.value = userList
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    val getUser: LiveData<FirebaseUser> get() = user
    val getUsers: LiveData<List<User>> get() = users

    fun logout() {
        auth.signOut()
        onlineUserStatus(false)
    }

    fun onlineUserStatus(isOnline: Boolean) {
        userReference
            .child(auth.currentUser?.uid ?: return)
            .child("online")
            .setValue(isOnline)
    }
}