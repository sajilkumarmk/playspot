package com.example.play.stadium.StadumAccounts;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.play.databinding.FragmentSExpenseBinding;
import com.example.play.databinding.FragmentSPaymentBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SPaymentFragment extends Fragment {

    private FragmentSPaymentBinding binding;
    TextView totalExpense;
    RecyclerView recyclerview;
    private String URLstring = Config.baseurl+"stadium_payment_list.php";
    private static ProgressDialog mProgressDialog;
    ArrayList<SPaymentListDataModel> dataModelArrayList;
    private SPaymentListAdapter rvAdapter;
    String id,total;
    int ttl,tot = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSPaymentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerview = binding.recyclerSPaymentList;
        totalExpense = binding.sTotalPayment;
        fetchingJSON();

        return  root;
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
                                dataModelArrayList.add(new SPaymentListDataModel(
                                        dataobj.getString("pay_id"),
                                        dataobj.getString("type"),
                                        dataobj.getString("amount"),
                                        dataobj.getString("username"),
                                        dataobj.getString("userimage")
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

        rvAdapter = new SPaymentListAdapter(this.getActivity(), dataModelArrayList);
        recyclerview.setHasFixedSize(true);
        recyclerview.setAdapter(rvAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
//        Toast.makeText(getActivity(),"Success", Toast.LENGTH_SHORT).show();
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