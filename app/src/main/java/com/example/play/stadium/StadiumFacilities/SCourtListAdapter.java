package com.example.play.stadium.StadiumFacilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.play.R;
import com.example.play.admin.admin_home.ListItemClick;
import com.example.play.admin.admin_home.PlaygroundListAdapter;
import com.example.play.admin.admin_home.PlaygroundListDataModel;

import java.util.ArrayList;

public class SCourtListAdapter extends RecyclerView.Adapter<SCourtListAdapter.MyViewHolder>{

    private final ListItemClick listItemClick;

    private LayoutInflater inflater;
    private ArrayList<SCourtListDataModel> dataModelArrayList;
    Context c;

    public SCourtListAdapter(Context ctx, ArrayList<SCourtListDataModel> dataModelArrayList, ListItemClick listItemClick){
        this.c = ctx;
        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
        this.listItemClick = listItemClick;

    }

    @NonNull
    @Override
    public SCourtListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.s_court_list_item, parent, false);
        SCourtListAdapter.MyViewHolder holder = new SCourtListAdapter.MyViewHolder(view, listItemClick);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.textGameType.setText(dataModelArrayList.get(position).getGameType());
        holder.textCourtType.setText(dataModelArrayList.get(position).getCourtType());
        holder.textCourtLength.setText(dataModelArrayList.get(position).getCourtLength());
        holder.textCourtWidth.setText(dataModelArrayList.get(position).getCourtWidth());
        holder.textCourtAmount.setText(dataModelArrayList.get(position).getCourtAmount());

    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textGameType,textCourtType,textCourtLength,textCourtWidth,textCourtAmount;

        public MyViewHolder(@NonNull View itemView, ListItemClick listItemClick) {
            super(itemView);

            textGameType = itemView.findViewById(R.id.sGameType);
            textCourtType = itemView.findViewById(R.id.sCourtType);
            textCourtLength = itemView.findViewById(R.id.sCourtLength);
            textCourtWidth = itemView.findViewById(R.id.sCourtWidth);
            textCourtAmount = itemView.findViewById(R.id.sCourtAmount);

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
