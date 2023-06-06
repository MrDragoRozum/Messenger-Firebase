package com.example.messenger

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainViewModel : ViewModel() {
    private val error: MutableLiveData<String> = MutableLiveData()
    private val user: MutableLiveData<FirebaseUser> = MutableLiveData()
    private val auth = FirebaseAuth.getInstance()

    init {
        auth.addAuthStateListener {
            auth.currentUser?.let {
                user.value = auth.currentUser
            }
        }
    }

    val getUser: LiveData<FirebaseUser> get() = user
    val getError: LiveData<String> get() = error

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnFailureListener { error.value = it.message }
    }
}