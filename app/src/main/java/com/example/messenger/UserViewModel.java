package com.example.messenger;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserViewModel extends ViewModel {

    private final FirebaseAuth auth;
    private final FirebaseDatabase database;
    private final DatabaseReference userReference;

    private final MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<User>> users = new MutableLiveData<>();

    public UserViewModel() {
        database = FirebaseDatabase.getInstance();
        userReference = database.getReference("Users");
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(firebaseAuth -> user.setValue(firebaseAuth.getCurrentUser()));
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<User> userList = new ArrayList<>();
                FirebaseUser currentUser = auth.getCurrentUser();
                if(currentUser == null) {
                    return;
                }

                for (DataSnapshot dataSnapshot:
                     snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if(user == null) {
                        return;
                    }
//                    if(!user.getId().equals(currentUser.getUid())) {
                        userList.add(user);
//                    }
                }
                users.setValue(userList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public LiveData<ArrayList<User>> getUsers() {
        return users;
    }

    public void logout() {
        auth.signOut();
        onlineUserStatus(false);
    }

    public void onlineUserStatus(boolean isOnline) {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if(firebaseUser == null) {
            return;
        }
        userReference.child(firebaseUser.getUid()).child("online").setValue(isOnline);
    }
}
