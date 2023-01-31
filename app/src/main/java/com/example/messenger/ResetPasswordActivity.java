package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPasswordActivity extends AppCompatActivity {

    private Button buttonResetPassword;
    private EditText editTextEmail;
    private static final String EXTRA_EMAIL = "email";
    private ResetPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initViews();
        setupClickListener();
        observeViewModel();
        editTextEmail.setText(getIntent().getStringExtra(EXTRA_EMAIL));
    }

    private void observeViewModel() {
        viewModel = new ViewModelProvider(this).get(ResetPasswordViewModel.class);

        viewModel.getError().observe(this, error ->
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show());

        viewModel.isSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(this, R.string.reset_link_sent, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupClickListener() {
        buttonResetPassword.setOnClickListener(l -> {
            String email = editTextEmail.getText().toString().trim();
            viewModel.resetPassword(email);
        });
    }

    private void initViews() {
        buttonResetPassword = findViewById(R.id.buttonResetPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
    }

    public static Intent newIntent(Context context, String email) {
        Intent intent = new Intent(context, ResetPasswordActivity.class);
        intent.putExtra(EXTRA_EMAIL, email);
        return intent;
    }
}