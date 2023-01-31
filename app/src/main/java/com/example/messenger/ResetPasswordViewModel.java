package com.example.messenger;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordViewModel extends ViewModel {

    private final MutableLiveData<Boolean> success = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    ;

    public LiveData<Boolean> isSuccess() {
        return success;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void resetPassword(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnSuccessListener(unused -> success.setValue(true))
                .addOnFailureListener(e -> error.setValue(e.getMessage()));
    }
}
