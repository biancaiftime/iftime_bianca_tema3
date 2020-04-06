package com.example.tema3.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tema3.Models.ToDo;
import com.example.tema3.Models.User;
import com.example.tema3.R;
import com.example.tema3.ToDosFragment;
import com.example.tema3.UsersFragment;

import java.util.List;

public class ListOfToDosAdapter extends RecyclerView.Adapter<ListOfToDosAdapter.ViewHolder> {

    private List<ToDo> mDataset;
    private View.OnClickListener onClickListener;

    public ListOfToDosAdapter(List<ToDo> toDos, View.OnClickListener clickListener)
    {
        onClickListener = clickListener;
        mDataset = toDos;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.todos_details, viewGroup, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.title.setText(mDataset.get(i).Title);
        viewHolder.completed.setText( mDataset.get(i).Completed  ? "Completed" : "Not completed" );
        viewHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ToDosFragment.ToDosClickListener)onClickListener).setTitle(mDataset.get(i).Title);
                onClickListener.onClick(v);
            }
        });
        viewHolder.completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ToDosFragment.ToDosClickListener)onClickListener).setTitle(mDataset.get(i).Title);
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
        public TextView title;
        public TextView completed;
        //public TextView email;

        public ViewHolder(View vCard)
        {
            super(vCard);
            title = vCard.findViewById(R.id.title);
            completed = vCard.findViewById(R.id.completed);
            //email = vCard.findViewById(R.id.email);
        }
    }

    public void addItem(ToDo toDo)
    {
        mDataset.add(toDo);
        notifyDataSetChanged();
    }
    public void clearList()
    {
        mDataset.clear();
        notifyDataSetChanged();
    }
//
//    public void deleteItem(User user)
//    {
//        for (User u : mDataset)
//        {
//            if(u.fullName.equals(user.fullName))
//            {
//                mDataset.remove(u);
//                notifyDataSetChanged();
//                break;
//            }
//        }
//    }
}
