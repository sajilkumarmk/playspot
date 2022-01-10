package com.example.play.stadium.StadiumAccounts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.play.R;
import com.example.play.admin.admin_home.ListItemClick;

import java.util.ArrayList;

public class SExpenseListAdapter extends RecyclerView.Adapter<SExpenseListAdapter.MyViewHolder>{

    private final ListItemClick listItemClick;

    private LayoutInflater inflater;
    private ArrayList<SExpenseListDataModel> dataModelArrayList;
    Context c;

    public SExpenseListAdapter(Context ctx, ArrayList<SExpenseListDataModel> dataModelArrayList, ListItemClick listItemClick) {
        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
        this.listItemClick = listItemClick;
        this.c = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.s_expense_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view, listItemClick);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txtexpense.setText(dataModelArrayList.get(position).getType());
        holder.txtdate.setText(dataModelArrayList.get(position).getDate());
        holder.txtdescription.setText(dataModelArrayList.get(position).getDescription());
        holder.txtamount.setText(dataModelArrayList.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtexpense,txtdescription,txtdate,txtamount;

        public MyViewHolder(@NonNull View itemView, ListItemClick listItemClick) {
            super(itemView);
            txtexpense = itemView.findViewById(R.id.sExpenseType);
            txtdate = itemView.findViewById(R.id.sExpenseDate);
            txtdescription = itemView.findViewById(R.id.sExpenseDescription);
            txtamount = itemView.findViewById(R.id.sExpenseAmount);

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
