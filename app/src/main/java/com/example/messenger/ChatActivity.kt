package com.example.messenger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.messenger.adapters.MessageAdapter
import com.example.messenger.databinding.ActivityChatBinding
import com.example.messenger.pojo.Message

class ChatActivity: AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var viewModel: ChatViewModel
    private lateinit var adapter: MessageAdapter
    private lateinit var factory: ChatViewModelFactory
    private var currentUserId = ""
    private var otherUserId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        otherUserId = intent.getStringExtra(EXTRA_OTHER_USER_ID) ?: return
        currentUserId = intent.getStringExtra(EXTRA_CURRENT_USER_ID) ?: return
        adapter = MessageAdapter(currentUserId)
        binding.recyclerViewMessages.adapter = adapter
        factory = ChatViewModelFactory(currentUserId, otherUserId)
        viewModel = ViewModelProvider(this, factory)[ChatViewModel::class.java]
        observeViewModel()
        binding.imageViewSendMessage.setOnClickListener {
            val text = binding.editTextMessage.text.toString().trim()
            val message = Message(text, currentUserId, otherUserId)
            viewModel.sendMessage(message)
        }
    }

    private fun observeViewModel() {
        viewModel.getError.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.getMessages.observe(this) { adapter.messageList = it }
        viewModel.getMessageSent.observe(this) {
            if(it) binding.editTextMessage.setText("")
        }
        viewModel.getUserOther.observe(this) {
            val userInfo = String.format("%s %s", it.name, it.lastname)
            binding.textViewTitle.text = userInfo
            val backgroundId = if(it.online) R.drawable.circle_green else R.drawable.circle_red
            val background = ContextCompat.getDrawable(this, backgroundId)
            binding.viewStatus.background = background
        }
    }

    companion object {
        private const val EXTRA_CURRENT_USER_ID = "user_id"
        private const val EXTRA_OTHER_USER_ID = "user_other_id"

        fun newIntent(context: Context, currentUserId: String, otherUserId: String): Intent {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId)
            intent.putExtra(EXTRA_OTHER_USER_ID, otherUserId)
            return intent
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.onlineUserStatus(false)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onlineUserStatus(true)
    }
}