package com.example.play.stadium.StadiumFacilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.play.Config;
import com.example.play.R;
import com.example.play.SessionManager;
import com.example.play.admin.admin_home.ListItemClick;
import com.example.play.admin.admin_home.PlaygroundListAdapter;
import com.example.play.admin.admin_home.PlaygroundListDataModel;
import com.example.play.databinding.FragmentPendingPlaygroundBinding;
import com.example.play.databinding.FragmentSFacilitiesBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SFacilitiesFragment extends Fragment implements ListItemClick {

    private FragmentSFacilitiesBinding binding;
    RecyclerView recyclerview;
    FloatingActionButton add;
    private String URLstring = Config.baseurl+"stadium_facilities_list.php";
    private static ProgressDialog mProgressDialog;
    ArrayList<SFacilitiesListDataModel> dataModelArrayList;
    private SFacilitiesListAdapter rvAdapter;
    String id, url = Config.baseurl+"stadium_remove_facilities.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSFacilitiesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerview = binding.recyclerSFacilitiesList;
        add = binding.sFacilitiesAdd;
        fetchingJSON();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StadiumFacilitiesAdd.class);
                startActivity(intent);
            }
        });
        return root;
    }
    private void fetchingJSON() {

        id = new SessionManager(getActivity()).getUserDetails().get("id");

        showSimpleProgressDialog(getActivity(), "Loading...","Please wait...",false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            removeSimpleProgressDialog();
                            dataModelArrayList = new ArrayList<>();
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject dataobj = array.getJSONObject(i);
                                dataModelArrayList.add(new SFacilitiesListDataModel(
                                        dataobj.getString("f_id"),
                                        dataobj.getString("facility"),
                                        dataobj.getString("opening"),
                                        dataobj.getString("closing"),
                                        dataobj.getString("description")
                                ));
                            }
                            setupRecycler();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Log", "inside onErrorResponse");
                        //displaying the error in toast if occurs
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", id);
                return map;
            }
        };

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this.requireActivity());
        requestQueue.add(stringRequest);
    }

    private void setupRecycler(){

        rvAdapter = new SFacilitiesListAdapter(getActivity(), dataModelArrayList, this);
        recyclerview.setHasFixedSize(true);
        recyclerview.setAdapter(rvAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            Log.e("Log", "inside catch IllegalArgumentException");
            ie.printStackTrace();

        } catch (RuntimeException re) {
            Log.e("Log", "inside catch RuntimeException");
            re.printStackTrace();
        } catch (Exception e) {
            Log.e("Log", "Inside catch Exception");
            e.printStackTrace();
        }

    }

    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(int position) {

        id = dataModelArrayList.get(position).getId();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String status = null,message = null;
                try {
                    JSONObject c = new JSONObject(response);
                    status = c.getString("status");
                    message = c.getString("message");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status.equals("1")){
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    fetchingJSON();
//                    startActivity(new Intent(getActivity(),StadiumFacilitiesFragment.class));
//                    getActivity().finish();
                } else{
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", id);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}