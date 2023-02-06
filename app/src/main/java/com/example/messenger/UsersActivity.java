package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;


public class UsersActivity extends AppCompatActivity {

    private UserViewModel viewModel;
    private RecyclerView recyclerViewUsers;
    private UsersAdapter adapter;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference("Users");
    private static final String EXTRA_CURRENT_USER_ID = "user_id";
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        initViews();
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        observeViewModel();
        adapter.setOnUserClickListener(otherUserId -> {
            currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
            Intent intent = ChatActivity
                    .newIntent(UsersActivity.this, currentUserId, otherUserId.getId());
            startActivity(intent);
        });
    }

    private void initViews() {
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        adapter = new UsersAdapter();
        recyclerViewUsers.setAdapter(adapter);
    }

    private void observeViewModel() {
        viewModel.getUser().observe(this, user -> {
            if(user == null) {
                Intent intent = MainActivity.newIntent(this);
                startActivity(intent);
                finish();
            }
        });
        viewModel.getUsers().observe(this, users -> adapter.setUsers(users));
    }

    public static Intent newIntent(Context context, String currentUserId) {
        Intent intent =  new Intent(context, UsersActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID,currentUserId);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.itemSingOut) {
            viewModel.logout();
        }
        return super.onOptionsItemSelected(item);
    }
}