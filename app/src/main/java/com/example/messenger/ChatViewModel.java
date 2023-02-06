package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatViewModel extends ViewModel {
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> messageSent = new MutableLiveData<>();
    private final MutableLiveData<List<Message>> messages = new MutableLiveData<>();
    private final MutableLiveData<User> userOther = new MutableLiveData<>();

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference referenceUsers = database.getReference("Users");
    private DatabaseReference referenceMessages = database.getReference("Messages");

    private String currentUserId;
    private String otherUserId;

    public ChatViewModel(String currentUserId, String otherUserId) {
        this.currentUserId = currentUserId;
        this.otherUserId = otherUserId;
        referenceUsers.child(otherUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                userOther.setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        referenceMessages.child(currentUserId).child(otherUserId)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Message> messageList = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Message message = dataSnapshot.getValue(Message.class);
                    messageList.add(message);
                }
                messages.setValue(messageList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Boolean> getMessageSent() {
        return messageSent;
    }

    public LiveData<List<Message>> getMessages() {
        return messages;
    }

    public LiveData<User> getUserOther() {
        return userOther;
    }

    public void sendMessage(Message message) {
        referenceMessages
                .child(message.getSenderId())
                .child(message.getReceiverId())
                .push()
                .setValue(message)
                .addOnSuccessListener(unused ->
                        referenceMessages
                        .child(message.getReceiverId())
                        .child(message.getSenderId())
                        .push()
                        .setValue(message)
                        .addOnSuccessListener(unusedVoid -> messageSent.setValue(true))
                                .addOnFailureListener(e -> error.setValue(e.getMessage())))
                .addOnFailureListener(e -> error.setValue(e.getMessage()));
    }
}
