package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class UsersActivity extends AppCompatActivity {

    private UserViewModel viewModel;
    private RecyclerView recyclerViewUsers;
    private UsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        initViews();
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        observeViewModel();

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
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, UsersActivity.class);
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