package com.example.play.stadium.StadiumFacilities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.play.Config;
import com.example.play.Login;
import com.example.play.R;
import com.example.play.admin.admin_home.ListItemClick;
import com.example.play.admin.admin_home.PlaygroundListAdapter;
import com.example.play.admin.admin_home.PlaygroundListDataModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SFacilitiesListAdapter extends RecyclerView.Adapter<SFacilitiesListAdapter.MyViewHolder>{

    private final ListItemClick listItemClick;

    private LayoutInflater inflater;
    private ArrayList<SFacilitiesListDataModel> dataModelArrayList;
    Context c;

    public SFacilitiesListAdapter( Context ctx, ArrayList<SFacilitiesListDataModel> dataModelArrayList, ListItemClick listItemClick) {
        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
        this.c = ctx;
        this.listItemClick = listItemClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.s_facilities_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view, listItemClick);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SFacilitiesListAdapter.MyViewHolder holder, int position) {

        holder.txtfacility.setText(dataModelArrayList.get(position).getFacility());
        holder.txtdescription.setText(dataModelArrayList.get(position).getDescription());
        holder.txtotime.setText(dataModelArrayList.get(position).getOpening_time());
        holder.txtctime.setText(dataModelArrayList.get(position).getClosing_time());


    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtfacility,txtdescription,txtotime,txtctime;
        AppCompatButton btnRemove;

        public MyViewHolder(@NonNull View itemView, ListItemClick listItemClick) {
            super(itemView);
            txtfacility = itemView.findViewById(R.id.facility);
            txtdescription = itemView.findViewById(R.id.facilitiesDescription);
            txtotime = itemView.findViewById(R.id.openingTime);
            txtctime = itemView.findViewById(R.id.closingTime);
            btnRemove = itemView.findViewById(R.id.sRemoveFacility);

            btnRemove.setOnClickListener(new View.OnClickListener() {
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
