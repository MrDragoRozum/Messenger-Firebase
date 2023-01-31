package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditText editTextTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewForgotPassword;
    private TextView textViewRegister;

    private MainViewModel viewModel;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        observeViewModel();
        setupClickListener();
    }

    private void observeViewModel() {
        viewModel.getError().observe(this, error -> {
                    if (error != null) {
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        viewModel.getUser().observe(this, user -> {
            if (user != null) {
                Intent intent = UsersActivity.newIntent(context);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setupClickListener() {
        buttonLogin.setOnClickListener(l -> {
            String email = getTrimmedValue(editTextTextEmail);
            String password = getTrimmedValue(editTextPassword);
            viewModel.login(email, password);
        });
        textViewRegister.setOnClickListener(l -> {
            Intent intent = RegistrationActivity.newIntent(context);
            startActivity(intent);
        });
        textViewForgotPassword.setOnClickListener(l -> {
            String email = getTrimmedValue(editTextTextEmail);
            Intent intent = ResetPasswordActivity.newIntent(context, email);
            startActivity(intent);
        });
    }

    private String getTrimmedValue(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    private void initViews() {
        editTextTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        textViewRegister = findViewById(R.id.textViewRegister);
    }
}