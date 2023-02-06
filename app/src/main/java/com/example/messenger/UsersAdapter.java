package com.example.messenger;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private ArrayList<User> users = new ArrayList<>();
    private onUserClickListener onUserClickListener;

    public void setOnUserClickListener(UsersAdapter.onUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item,parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        User user = users.get(position);
        String userInfo = String
                .format("%s %s, %s", user.getName(), user.getLastName(), user.getAge());
        holder.textViewUser.setText(userInfo);

        int backgroundId;
        if(user.isOnline()) {
            backgroundId = R.drawable.circle_green;
        } else {
            backgroundId = R.drawable.circle_red;
        }
        Drawable background = ContextCompat.getDrawable(holder.itemView.getContext(),backgroundId);
        holder.viewUserOnline.setBackground(background);

        holder.textViewUser.setOnClickListener(l -> {
            if(onUserClickListener != null) {
                onUserClickListener.userClickListener(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    interface onUserClickListener {
        void userClickListener(User user);
    }

    static class UsersViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewUser;
        private final View viewUserOnline;
        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUser = itemView.findViewById(R.id.textViewUser);
            viewUserOnline = itemView.findViewById(R.id.viewStatus);
        }
    }
}
