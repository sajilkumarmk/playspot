package com.example.play.stadium.StadiumProfile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StadiumUpiUpdate extends AppCompatActivity {

    EditText upi;
    AppCompatButton update;
    String id,upiId,upi_id,status,message,url = Config.baseurl+"get_upiId.php";
    String url1 = Config.baseurl+"set_upiId.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stadium_upi_update);

        upi = findViewById(R.id.sUpiId);
        update = findViewById(R.id.sUpiUpdate);

        id = new SessionManager(getApplication()).getUserDetails().get("id");

        getUpiId();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUpi();
            }
        });

    }

    private void updateUpi() {
        upi_id = upi.getText().toString();

//        Validating the values and highlight errors is any
        if (TextUtils.isEmpty(upi_id)){
            upi.setError("Required field");
            upi.requestFocus();
            return;
        }
        else if (!validateUPI(upi_id)){
            upi.setError("Invalid upi id");
            upi.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
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
                map.put("id", id);
                map.put("upi", upi_id);
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
//    upi validation method
    public static boolean validateUPI(String upi){
        final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^(.+)@(.+)$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(upi);
        return matcher.find();
    }

    private void getUpiId() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject c = new JSONObject(response);
                    status = c.getString("status");
                    message = c.getString("message");
                    upiId = c.getString("upi");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status.equals("1")){
//                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    setUpiID();
                    Intent intent = new Intent(getApplicationContext(), StadiumProfileFragment.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            }

            private void setUpiID() {
                String missing = "Please provide upi id";
                if (upiId.equals("empty")){
                    upi.setText(missing);
                }else{
                    upi.setText(upiId);
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
                map.put("id", id);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
