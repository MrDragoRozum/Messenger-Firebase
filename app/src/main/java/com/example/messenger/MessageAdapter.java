package com.example.messenger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private static final int VIEW_TYPE_MY_MESSAGE = 100;
    private static final int VIEW_TYPE_OTHER_MESSAGE = 101;
    private String currentUserId;

    public MessageAdapter(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    List<Message> messageList = new ArrayList<>();

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutResId;
        if(viewType == VIEW_TYPE_MY_MESSAGE) {
            layoutResId = R.layout.my_message_item;
        } else {
            layoutResId = R.layout.other_message_item;
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutResId, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        if(message.getSenderId().equals(currentUserId)) {
            return VIEW_TYPE_MY_MESSAGE;
        } else {
            return VIEW_TYPE_OTHER_MESSAGE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.textViewMessage.setText(message.getText());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewMessage;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.textViewMessage);
        }
    }
}
