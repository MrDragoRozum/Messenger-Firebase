package com.example.messenger

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordViewModel : ViewModel() {

    private val success: MutableLiveData<Boolean> = MutableLiveData()
    private val error: MutableLiveData<String> = MutableLiveData()
    private val auth = FirebaseAuth.getInstance()

    val isSuccess: LiveData<Boolean> get() = success
    val getError: LiveData<String> get() = error

    fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
                .addOnSuccessListener { success.value = true }
                .addOnFailureListener { error.value = it.message }
    }
}