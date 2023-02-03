package com.example.messenger;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationViewModel extends ViewModel {

    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    private final FirebaseAuth auth;
    private final FirebaseDatabase database;
    private final DatabaseReference userReference;

    public RegistrationViewModel() {
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(userFB -> {
            if (userFB.getCurrentUser() != null) {
                user.setValue(userFB.getCurrentUser());
            }
        });
        database = FirebaseDatabase.getInstance();
        userReference = database.getReference("Users");
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public void register(String email, String password, String name, String lastName, int age) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser firebaseUser = authResult.getUser();
                    if(firebaseUser == null) {
                        return;
                    }
                    String uid = firebaseUser.getUid();
                    User user = new User(
                            uid,
                            name,
                            lastName,
                            age,
                            false
                    );
                    userReference.child(uid).setValue(user);
                })
                .addOnFailureListener(e -> error.setValue(e.getMessage()));
    }
}
