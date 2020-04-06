package com.example.tema3.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tema3.Models.User;
import com.example.tema3.R;
import com.example.tema3.ToDosFragment;
import com.example.tema3.UsersFragment;

import java.util.List;

public class ListOfUsersAdapter extends RecyclerView.Adapter<ListOfUsersAdapter.ViewHolder> {

    private List<User> mDataset;
    private View.OnClickListener onClickListener;

    public ListOfUsersAdapter(List<User> users, View.OnClickListener onClickListener)
    {
        mDataset = users;
        this.onClickListener = onClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_details, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
        viewHolder.name.setText(mDataset.get(i).Name);
        viewHolder.username.setText(mDataset.get(i).Username);
        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UsersFragment.MyClickListener)onClickListener).setId(mDataset.get(i).Id);
                onClickListener.onClick(v);
            }
        });
        viewHolder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UsersFragment.MyClickListener)onClickListener).setId(mDataset.get(i).Id);
                onClickListener.onClick(v);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name;
        public TextView username;
        //public TextView email;

        public ViewHolder(View vCard)
        {
            super(vCard);
            name = vCard.findViewById(R.id.name);
            username = vCard.findViewById(R.id.username);
            //email = vCard.findViewById(R.id.email);
        }
    }

    public void addItem(User user)
    {
        mDataset.add(user);
        notifyDataSetChanged();
    }
//
    public void clearList()
    {
        mDataset.clear();
        notifyDataSetChanged();
    }
    public User find(int position)
    {
        return mDataset.get(position);
    }
}
