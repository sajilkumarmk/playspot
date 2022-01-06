package com.example.play.admin.admin_home;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.play.Config;
import com.example.play.R;
import com.example.play.databinding.FragmentPendingPlaygroundBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PendingPlaygroundFragment extends Fragment implements ListItemClick {

    private FragmentPendingPlaygroundBinding binding;
    RecyclerView recyclerview;
    private String URLstring = Config.baseurl+"admin_play_list_pending.php";
    private static ProgressDialog mProgressDialog;
    ArrayList<PlaygroundListDataModel> dataModelArrayList;
    private PlaygroundListAdapter rvAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPendingPlaygroundBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerview = binding.recyclerList;
        fetchingJSON();
        return root;
    }
    private void fetchingJSON() {
        showSimpleProgressDialog(getActivity(), "Loading...","Please wait...",false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            removeSimpleProgressDialog();

                            dataModelArrayList = new ArrayList<>();

                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject dataobj = array.getJSONObject(i);

                                dataModelArrayList.add(new PlaygroundListDataModel(
                                        dataobj.getString("id"),
                                        dataobj.getString("name"),
                                        dataobj.getString("type"),
                                        dataobj.getString("district"),
                                        dataobj.getString("place"),
                                        dataobj.getString("state"),
                                        dataobj.getString("id_proof"),
                                        dataobj.getString("phone"),
                                        dataobj.getString("email"),
                                        dataobj.getString("image")
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
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        requestQueue.add(stringRequest);
    }

    private void setupRecycler(){

        rvAdapter = new PlaygroundListAdapter(getActivity(), dataModelArrayList, this);
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
        Intent intent = new Intent(getActivity(), PendingListItemView.class);

        intent.putExtra("ID", dataModelArrayList.get(position).getId());
        intent.putExtra("TYPE", dataModelArrayList.get(position).getType());
        intent.putExtra("NAME", dataModelArrayList.get(position).getName());
        intent.putExtra("PLACE", dataModelArrayList.get(position).getPlace());
        intent.putExtra("DISTRICT", dataModelArrayList.get(position).getDistrict());
        intent.putExtra("STATE", dataModelArrayList.get(position).getState());
        intent.putExtra("IDPROOF", dataModelArrayList.get(position).getIdProof());
        intent.putExtra("PHONE", dataModelArrayList.get(position).getPhone());
        intent.putExtra("EMAIL", dataModelArrayList.get(position).getEmail());
        intent.putExtra("IMAGE", dataModelArrayList.get(position).getImage());

        startActivity(intent);
    }
}