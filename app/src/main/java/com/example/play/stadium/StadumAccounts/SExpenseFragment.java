package com.example.play.stadium.StadumAccounts;

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
import android.widget.TextView;
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
import com.example.play.databinding.FragmentSCourtBinding;
import com.example.play.databinding.FragmentSExpenseBinding;
import com.example.play.stadium.StadiumFacilities.SCourtListAdapter;
import com.example.play.stadium.StadiumFacilities.SCourtListDataModel;
import com.example.play.stadium.StadiumFacilities.StadiumCourtAdd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SExpenseFragment extends Fragment {

    private FragmentSExpenseBinding binding;
    TextView totalExpense;
    RecyclerView recyclerview;
    FloatingActionButton add;
    private String URLstring = Config.baseurl+"stadium_expense_list.php";
    private static ProgressDialog mProgressDialog;
    ArrayList<SExpenseListDataModel> dataModelArrayList;
    private SExpenseListAdapter rvAdapter;
    String id,total;
    int ttl,tot = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSExpenseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerview = binding.recyclerSExpenseList;
        add = binding.sExpenseAdd;
        totalExpense = binding.sTotalExpense;
        fetchingJSON();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StadiumCourtAdd.class);
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
                                dataModelArrayList.add(new SExpenseListDataModel(
                                        dataobj.getString("e_id"),
                                        dataobj.getString("type"),
                                        dataobj.getString("date"),
                                        dataobj.getString("amount"),
                                        dataobj.getString("description"),
                                        dataobj.getString("bill")
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
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        requestQueue.add(stringRequest);
    }

    private void setupRecycler(){

        rvAdapter = new SExpenseListAdapter(getActivity(), dataModelArrayList);
        recyclerview.setHasFixedSize(true);
        recyclerview.setAdapter(rvAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        getTotalPrice();
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
    private void getTotalPrice() {
        for (int i=0;i<dataModelArrayList.size();i++){
            total=dataModelArrayList.get(i).getAmount();
            ttl=Integer.parseInt(total);
            tot=tot+ttl;

        }
        totalExpense.setText( String.valueOf( tot ) );
    }
}