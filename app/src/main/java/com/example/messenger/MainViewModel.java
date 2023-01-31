package com.example.messenger;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private final FirebaseAuth auth;

    public MainViewModel() {
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(firebaseAuth -> {
            if(auth.getCurrentUser() != null) {
                user.setValue(auth.getCurrentUser());
            }
        });
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void login(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnFailureListener(e -> error.setValue(e.getMessage()));
    }
}
