package com.example.messenger

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.messenger.pojo.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class RegistrationViewModel : ViewModel() {

    private val error = MutableLiveData<String>()
    private val user = MutableLiveData<FirebaseUser>()

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val userReference = database.getReference("Users")

    init {
        auth.addAuthStateListener { it.currentUser?.let { user.value = it } }
    }

    val getError: LiveData<String> get() = error
    val getUser: LiveData<FirebaseUser> get() = user

    fun register(email: String, password: String, name: String, lastName: String, age: Int) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val firebaseUser = it.user
                    val uid = firebaseUser?.uid ?: return@addOnSuccessListener
                    val user = User(uid, name, lastName, age, false)
                    userReference.child(uid).setValue(user)
                }
                .addOnFailureListener { error.value = it.message }
    }

}