package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ChatViewModel viewModel;
    private ChatViewModelFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initViews();
        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        otherUserId = getIntent().getStringExtra(EXTRA_OTHER_USER_ID);
        adapter = new MessageAdapter(currentUserId);
        recyclerViewMessages.setAdapter(adapter);
        factory = new ChatViewModelFactory(currentUserId, otherUserId);
        viewModel = new ViewModelProvider(this, factory).get(ChatViewModel.class);
        observeViewModel();
        imageViewSendMessage.setOnClickListener(l -> {
            String text = editTextMessage.getText().toString().trim();
            Message message = new Message(text, currentUserId, otherUserId);
            viewModel.sendMessage(message);
        });
    }

    private void observeViewModel() {
        viewModel.getError().observe(this, error ->
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show());

        viewModel.getMessages().observe(this,messages ->
                adapter.setMessageList(messages));

        viewModel.getMessageSent().observe(this, isBoolean -> {
            if(isBoolean) {
                editTextMessage.setText("");
            }
        });

        viewModel.getUserOther().observe(this, user -> {
            String userInfo = String.format("%s %s", user.getName(), user.getLastName());
            textViewTitle.setText(userInfo);
        });
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