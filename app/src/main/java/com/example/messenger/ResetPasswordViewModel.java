package com.example.messenger;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordViewModel extends ViewModel {

    private final MutableLiveData<String> sentMessage = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final FirebaseAuth auth;

    public ResetPasswordViewModel() {
        auth = FirebaseAuth.getInstance();
    }

    public LiveData<String> getSentMessage() {
        return sentMessage;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void resetPassword(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnSuccessListener(unused -> sentMessage
                        .setValue("The reset message has been sent!"))
                .addOnFailureListener(e -> error.setValue(e.getMessage()));
    }
}
