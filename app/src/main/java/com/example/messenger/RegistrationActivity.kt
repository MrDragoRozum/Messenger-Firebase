package com.example.messenger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.messenger.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var viewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]
        setupClickListener()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.getError.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.getUser.observe(this) {
            it?.let {

            }
        }
    }

    private fun setupClickListener() {
        binding.buttonSignUp.setOnClickListener {
            with(binding) {
                val email = editTextTextEmailAddress.getTrimmedValue()
                val password = editTextTextPassword.getTrimmedValue()
                val name = editTextName.getTrimmedValue()
                val lastName = editTextLastName.getTrimmedValue()
                val age = editTextAge.getTrimmedValue().toInt()

                viewModel.register(email, password, name, lastName, age)
            }
        }
    }

    private fun EditText.getTrimmedValue() = this.text.toString().trim()

    companion object {
        fun newIntent(context: Context) = Intent(context, RegistrationActivity::class.java)
    }
}