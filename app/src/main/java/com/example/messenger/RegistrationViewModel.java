package com.example.messenger;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationViewModel extends ViewModel {

    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    private final FirebaseAuth auth;

    public RegistrationViewModel() {
        auth = FirebaseAuth.getInstance();
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public void Register(String email, String password, String name, String lastName, int age) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> user.setValue(authResult.getUser()))
                .addOnFailureListener(e -> error.setValue(e.getMessage()));
    }
}
