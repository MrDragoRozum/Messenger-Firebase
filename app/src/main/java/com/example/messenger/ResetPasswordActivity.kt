package com.example.messenger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.messenger.databinding.ActivityResetPasswordBinding

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private lateinit var viewModel: ResetPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListener()
        observeViewModel()
        binding.editTextEmail.setText(intent.getStringExtra(EXTRA_EMAIL))
    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this)[ResetPasswordViewModel::class.java]

        viewModel.getError.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.isSuccess.observe(this) {
            if (it) Toast.makeText(this, R.string.reset_link_sent, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupClickListener() {
        binding.buttonResetPassword.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            viewModel.resetPassword(email)
        }
    }

    companion object {

        private const val EXTRA_EMAIL = "email"
        fun newIntent(context: Context, email: String): Intent {
            val intent = Intent(context, ResetPasswordActivity::class.java)
            intent.putExtra(EXTRA_EMAIL, email)
            return intent
        }
    }
}