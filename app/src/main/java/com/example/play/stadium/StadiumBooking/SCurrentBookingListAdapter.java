package com.example.play.stadium.StadiumBooking;

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

public class SCurrentBookingListAdapter extends RecyclerView.Adapter<SCurrentBookingListAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<SCurrentBookingListDataModel> dataModelArrayList;
    Context c;
    String image;

    public SCurrentBookingListAdapter(Context ctx, ArrayList<SCurrentBookingListDataModel> dataModelArrayList) {
        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
        this.c = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.s_current_booking_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SCurrentBookingListAdapter.MyViewHolder holder, int position) {

        image = dataModelArrayList.get(position).getBookuserimage();

        holder.txtuser.setText(dataModelArrayList.get(position).getBookusername());
        holder.txtgame.setText(dataModelArrayList.get(position).getGametype());
        holder.txtcourt.setText(dataModelArrayList.get(position).getCourttype());
        holder.txtphone.setText(dataModelArrayList.get(position).getBookuserphone());
        holder.txtbookdate.setText(dataModelArrayList.get(position).getBooking_date());
        holder.txtbooktime.setText(dataModelArrayList.get(position).getBooking_time());
        holder.txtpaytype.setText(dataModelArrayList.get(position).getPayment_type());
        Picasso.get().load(Config.imageUrl+image).into(holder.txtimage);


    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtuser,txtgame,txtcourt,txtphone,txtbookdate,txtbooktime,txtpaytype;
        ImageView txtimage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtuser = itemView.findViewById(R.id.bookUserName);
            txtgame = itemView.findViewById(R.id.bookGame);
            txtcourt = itemView.findViewById(R.id.bookcourttype);
            txtphone = itemView.findViewById(R.id.bookUserPhone);
            txtbookdate = itemView.findViewById(R.id.bookDate);
            txtbooktime = itemView.findViewById(R.id.bookTime);
            txtpaytype = itemView.findViewById(R.id.bookPaymentType);
            txtimage = itemView.findViewById(R.id.bookUserImage);
        }
    }
}
