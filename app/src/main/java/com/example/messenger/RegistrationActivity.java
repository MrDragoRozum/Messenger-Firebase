package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextTextEmailAddress;
    private EditText editTextTextPassword;
    private EditText editTextName;
    private EditText editTextLastName;
    private EditText editTextAge;
    private Button buttonSignUp;
    private RegistrationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initViews();
        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        setupClickListener();
        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getError().observe(this, error ->
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show());

        viewModel.getUser().observe(this, user -> {
            if (user != null) {
                Intent intent = UsersActivity.newIntent(RegistrationActivity.this, user.getUid());
                startActivity(intent);
                finish();
            }
        });
    }


    private void setupClickListener() {
        buttonSignUp.setOnClickListener(l -> {
            String email = getTrimmedValue(editTextTextEmailAddress);
            String password = getTrimmedValue(editTextTextPassword);
            String name = getTrimmedValue(editTextName);
            String lastName = getTrimmedValue(editTextLastName);
            int age = Integer.parseInt(getTrimmedValue(editTextAge));

            viewModel.register(email, password,
                    name, lastName, age);
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }

    private String getTrimmedValue(EditText editText) {
        return editText.getText().toString().trim();
    }

    private void initViews() {
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAge = findViewById(R.id.editTextAge);
        buttonSignUp = findViewById(R.id.buttonSignUp);
    }
}