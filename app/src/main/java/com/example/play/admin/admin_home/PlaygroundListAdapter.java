package com.example.play.admin.admin_home;

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

public class PlaygroundListAdapter extends RecyclerView.Adapter<PlaygroundListAdapter.MyViewHolder>{

    private final ListItemClick listItemClick;

    private LayoutInflater inflater;
    private ArrayList<PlaygroundListDataModel> dataModelArrayList;
    Context c;
    String image;

    public PlaygroundListAdapter(Context ctx, ArrayList<PlaygroundListDataModel> dataModelArrayList, ListItemClick listItemClick){
        this.c = ctx;
        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
        this.listItemClick = listItemClick;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.admin_playground_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view, listItemClick);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        image = dataModelArrayList.get(position).getImage();

        holder.textName.setText(dataModelArrayList.get(position).getName());
        holder.textType.setText(dataModelArrayList.get(position).getType());
        holder.textPlace.setText(dataModelArrayList.get(position).getPlace());
        holder.textDistrict.setText(dataModelArrayList.get(position).getDistrict());

        Picasso.get().load(Config.imageUrl+image).into(holder.playImage);

    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textType,textName,textPlace,textDistrict;
        ImageView playImage;

        public MyViewHolder(@NonNull View itemView, ListItemClick listItemClick) {
            super(itemView);
            textType = itemView.findViewById(R.id.textType);
            textName = itemView.findViewById(R.id.textName);
            textPlace = itemView.findViewById(R.id.textPlace);
            textDistrict = itemView.findViewById(R.id.textDistrict);
            playImage = itemView.findViewById(R.id.playimage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listItemClick != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            listItemClick.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
