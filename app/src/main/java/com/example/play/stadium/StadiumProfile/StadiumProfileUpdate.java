package com.example.play.stadium.StadiumProfile;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.play.Admin;
import com.example.play.Config;
import com.example.play.R;
import com.example.play.SessionManager;
import com.example.play.Stadium;
import com.example.play.databinding.ActivityAdminBinding;
import com.example.play.databinding.FragmentStadiumProfileBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StadiumProfileUpdate extends AppCompatActivity {

    EditText name,place,state,phone,email;
    EditText district;
    AppCompatButton update;
    String  Id, Image, Pass, Name, Type, Place, District, State, Phone, Email, status, message;
    String UpdName,UpdPlace,UpdDistrict,UpdState,UpdPhone,UpdEmail,url = Config.baseurl+"stadium_details_update.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stadium_profile_update);

        name = findViewById(R.id.stName);
        place = findViewById(R.id.stPlace);
        state = findViewById(R.id.stState);
        phone = findViewById(R.id.stPhone);
        email = findViewById(R.id.stEmail);
        district = findViewById(R.id.stDistrict);
        update = findViewById(R.id.stUpdate);

        Id = new SessionManager(getApplication()).getUserDetails().get("id");
        Image = new SessionManager(getApplication()).getUserDetails().get("image");
        Name = new SessionManager(getApplication()).getUserDetails().get("name");
        Type = new SessionManager(getApplication()).getUserDetails().get("type");
        Place = new SessionManager(getApplication()).getUserDetails().get("place");
        District = new SessionManager(getApplication()).getUserDetails().get("district");
        State = new SessionManager(getApplication()).getUserDetails().get("state");
        Phone = new SessionManager(getApplication()).getUserDetails().get("phone");
        Email = new SessionManager(getApplication()).getUserDetails().get("email");
        Pass = new SessionManager(getApplication()).getUserDetails().get("password");

        name.setText(Name);
        place.setText(Place);
        district.setText(District);
        state.setText(State);
        phone.setText(Phone);
        email.setText(Email);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDetails();
            }
        });

    }

    private void updateDetails() {

        UpdName = name.getText().toString();
        UpdPlace = place.getText().toString();
        UpdDistrict = district.getText().toString();
        UpdState = state.getText().toString();
        UpdPhone = phone.getText().toString();
        UpdEmail = email.getText().toString();

//        Validating the values and highlight errors is any
        if (TextUtils.isEmpty(UpdName)){
            name.setError("Required field");
            name.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(UpdPlace)){
            place.setError("Required field");
            place.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(UpdDistrict)){
            district.setError("Required field");
            district.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(UpdState)){
            state.setError("Required field");
            state.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(UpdPhone)){
            phone.setError("Required field");
            phone.requestFocus();
            return;
        }
        else if (!isPhoneValid(UpdPhone)){
            phone.setError("Invalid phone number");
            phone.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(UpdEmail)){
            email.setError("Required field");
            email.requestFocus();
            return;
        }
        else if (!isUpiValid(UpdEmail)){
            email.setError("Invalid email");
            email.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject c = new JSONObject(response);
                    status = c.getString("status");
                    message = c.getString("message");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status.equals("1")){
                    new SessionManager(getApplicationContext()).createLoginSession(Id,UpdEmail,Pass,Type,UpdName,UpdPlace,UpdDistrict,UpdPhone,Image,UpdState);
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getApplicationContext(), Admin.class);
//                    startActivity(intent);
//                    finish();
                } else{
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", Id);
                map.put("name", UpdName);
                map.put("place", UpdPlace);
                map.put("district", UpdDistrict);
                map.put("state", UpdState);
                map.put("phone", UpdPhone);
                map.put("email", UpdEmail);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    //   Phone validation method for pattern matching
    public static boolean isPhoneValid(String s) {
        Pattern p = Pattern.compile("(0/91)?[6-9][0-9]{9}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

//        Email validation method for pattern matching
    public static boolean isUpiValid(String upi) {
        String upiRegex = "^(.+)@(.+)$";

        Pattern pat = Pattern.compile(upiRegex);
        return pat.matcher(upi).matches();
    }

}
