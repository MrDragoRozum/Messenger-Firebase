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

    }

    private fun setupClickListeners() {
        with(binding) {
            buttonLogin.setOnClickListener {
                val email = getTrimmedValue(editTextEmail)
                val password = getTrimmedValue(editTextPassword)
                viewModel.login(email, password)
            }
            textViewRegister.setOnClickListener {

            }
            textViewForgotPassword.setOnClickListener {
                val email = getTrimmedValue(editTextEmail)

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

    private fun getTrimmedValue(editText: EditText) = editText.text.toString().trim()
}