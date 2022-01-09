package com.example.play.admin.admin_profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.play.Config;
import com.example.play.Login;
import com.example.play.R;
import com.example.play.SessionManager;
import com.example.play.databinding.FragmentAdminprofileBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdminProfileFragment extends Fragment {


    private FragmentAdminprofileBinding binding;
    EditText email,password;
    ImageButton logout;
    AppCompatButton update;
    String adminId,adminEmail,adminPassword,emailAdmin,passwordAdmin,Status,Message;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAdminprofileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        email = binding.email;
        password = binding.password;
        logout = binding.logout;
        update = binding.update;

        adminId = new SessionManager(getActivity()).getUserDetails().get("id");
        adminEmail = new SessionManager(getActivity()).getUserDetails().get("email");
        adminPassword = new SessionManager(getActivity()).getUserDetails().get("password");

        email.setText(adminEmail);
        password.setText(adminPassword);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDetails();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SessionManager(getActivity()).logoutUser();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "Logout", Toast.LENGTH_SHORT).show();
                getActivity().finish();

            }
        });

        return root;
    }

    private void updateDetails() {

//        idAdmin = new SessionManager(getActivity()).getUserDetails().get("id");
        emailAdmin = email.getText().toString();
        passwordAdmin = password.getText().toString();

        if (TextUtils.isEmpty(emailAdmin)) {
            email.requestFocus();
            email.setError( "Required Field" );
            return;
        }
        if (TextUtils.isEmpty(passwordAdmin)) {
            password.requestFocus();
            password.setError( "Required Field" );
            return;
        }

        String url= Config.baseurl+"admin_profile_update.php";
        StringRequest stringRequest = new StringRequest( Request.Method.POST, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject c = new JSONObject( response );
                            Status = c.getString( "status" );
                            Message = c.getString( "message" );
                            if (Status.equals("0")) {
                                Toast.makeText(getContext(), "Failed To Add", Toast.LENGTH_SHORT ).show();
                            } else {
                                Toast.makeText(getContext(), "Profile updated", Toast.LENGTH_SHORT).show();
                                //Intent i = new Intent(BuyProducts.this, LoginActivity.class);
                                //startActivity(i);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), String.valueOf( error ), Toast.LENGTH_SHORT ).show();
                    }
                } ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put( "id",adminId);
                params.put( "email",emailAdmin);
                params.put( "password", passwordAdmin );

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add( stringRequest );
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}