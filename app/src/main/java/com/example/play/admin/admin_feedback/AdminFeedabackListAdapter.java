package com.example.play.admin.admin_feedback;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.play.Config;
import com.example.play.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdminFeedabackListAdapter extends RecyclerView.Adapter<AdminFeedabackListAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<AdminFeedbackListDataModel> dataModelArrayList;
    String image;

    public AdminFeedabackListAdapter(Context ctx, ArrayList<AdminFeedbackListDataModel> dataModelArrayList){
        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.admin_feedback_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        image = dataModelArrayList.get(position).getUserImage();

        holder.userName.setText(dataModelArrayList.get(position).getUserName());
        holder.userMessage.setText(dataModelArrayList.get(position).getUserMessage());
        Picasso.get().load(Config.imageUrl+image).into(holder.userImage);
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView userName,userMessage;
        ImageView userImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            userMessage = itemView.findViewById(R.id.userMessege);
            userImage =itemView.findViewById(R.id.userImage);
        }
    }
}
