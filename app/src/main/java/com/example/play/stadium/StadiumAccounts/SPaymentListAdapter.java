package com.example.play.stadium.StadiumAccounts;

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

public class SPaymentListAdapter extends RecyclerView.Adapter<SPaymentListAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<SPaymentListDataModel> dataModelArrayList;
    Context c;
    String image;

    public SPaymentListAdapter(Context ctx, ArrayList<SPaymentListDataModel> dataModelArrayList) {
        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
        this.c = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.s_payment_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        image = dataModelArrayList.get(position).getUserimage();

        holder.txttype.setText(dataModelArrayList.get(position).getType());
        holder.txtamount.setText(dataModelArrayList.get(position).getAmount());
        holder.txtusername.setText(dataModelArrayList.get(position).getUsername());
        Picasso.get().load(Config.imageUrl+image).into(holder.txtuserimage);
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txttype,txtamount,txtusername;
        ImageView txtuserimage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txttype = itemView.findViewById(R.id.paymentType);
            txtamount = itemView.findViewById(R.id.paymentAmount);
            txtusername = itemView.findViewById(R.id.payUserName);
            txtuserimage = itemView.findViewById(R.id.payUserImage);
        }
    }
}
