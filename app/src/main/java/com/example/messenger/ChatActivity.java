package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private TextView textViewTitle;
    private View viewStatus;
    private RecyclerView recyclerViewMessages;
    private EditText editTextMessage;
    private ImageView imageViewSendMessage;
    private MessageAdapter adapter;
    private static final String EXTRA_CURRENT_USER_ID = "user_id";
    private static final String EXTRA_OTHER_USER_ID = "user_other_id";
    private String currentUserId;
    private String otherUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initViews();
        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        otherUserId = getIntent().getStringExtra(EXTRA_OTHER_USER_ID);
        adapter = new MessageAdapter(currentUserId);
        recyclerViewMessages.setAdapter(adapter);
        List<Message> messageArrayList = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            Message message = new Message("Text " + i, otherUserId,  currentUserId);
            messageArrayList.add(message);
        }
        for(int i = 0; i < 25; i++) {
            Message message = new Message( "Купи гараж, надоел, за " + i*32 ,currentUserId, otherUserId);
            messageArrayList.add(message);
        }
        adapter.setMessageList(messageArrayList);
    }

    public static Intent newIntent(Context context, String currentUserId, String otherUserId) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId);
        intent.putExtra(EXTRA_OTHER_USER_ID, otherUserId);
        return intent;
    }

    private void initViews() {
        textViewTitle = findViewById(R.id.textViewTitle);
        viewStatus = findViewById(R.id.viewStatus);
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
        editTextMessage = findViewById(R.id.editTextMessage);
        imageViewSendMessage = findViewById(R.id.imageViewSendMessage);
    }
}