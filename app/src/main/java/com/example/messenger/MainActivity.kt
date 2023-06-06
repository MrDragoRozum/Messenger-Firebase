package com.example.messenger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.messenger.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        observeViewModel()
        setupClickListener()
    }

    private fun setupClickListener() {
        with(binding) {
            buttonLogin.setOnClickListener {
                val email = editTextEmail.getTrimmedValue()
                val password = editTextPassword.getTrimmedValue()
                viewModel.login(email, password)
            }
            textViewRegister.setOnClickListener {

            }
            textViewForgotPassword.setOnClickListener {
                val email = editTextEmail.getTrimmedValue()
                val intent = ResetPasswordActivity.newIntent(this@MainActivity, email)
                startActivity(intent)
            }
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            getError.observe(this@MainActivity) {
                it?.let { Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show() }
            }
            getUser.observe(this@MainActivity) {
                it?.let {

                }
            }
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    private fun EditText.getTrimmedValue() = this.text.toString().trim()
}