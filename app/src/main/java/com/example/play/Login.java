package com.example.play;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText email,password;
    AppCompatButton login;
    TextView forgetPassword;
    LinearLayout registration;

    String Email,Password,status,message,id,type,name,place,district,state,phone,Emailid,image,pass,typeCheck,url = Config.baseurl+"login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        forgetPassword = findViewById(R.id.forgetPassword);
        login = findViewById(R.id.login);
        registration = findViewById(R.id.logRegistrer);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Registration.class);
                startActivity(intent);
            }
        });
    }

    private void userLogin() {
        Email = email.getText().toString();
        Password = password.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject c = new JSONObject(response);
                    status = c.getString("status");
                    message = c.getString("message");
                    id = c.getString("id");
                    Emailid = c.getString("email");
                    pass = c.getString("password");
                    name = c.getString("name");
                    place = c.getString("place");
                    district = c.getString("district");
                    phone = c.getString("phone");
                    image = c.getString("image");
                    type = c.getString("type");
                    state = c.getString("state");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status.equals("1")){
                    new SessionManager(getApplicationContext()).createLoginSession(id,Emailid,pass,type,name,place,district,phone,image,state);
//                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Admin.class);
                    startActivity(intent);
                    finish();
                }
                else if (status.equals("2")){
                    new SessionManager(getApplicationContext()).createLoginSession(id,Emailid,pass,type,name,place,district,phone,image,state);
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getApplicationContext(), Stadium.class);
//                    startActivity(intent);
//                    finish();
                }
                else if (status.equals("3")){
                    new SessionManager(getApplicationContext()).createLoginSession(id,Emailid,pass,type,name,place,district,phone,image,state);
//                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    typeCheck = new SessionManager(getApplication()).getUserDetails().get("type");

                    if (typeCheck.equals("Stadium")){
                        Intent intent = new Intent(getApplicationContext(), Stadium.class);
                        startActivity(intent);
                        finish();
                    }else if (typeCheck.equals("Turf")){
                        Toast.makeText(getApplicationContext(), "Turf", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Club", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (status.equals("4")){
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
                else{
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
                map.put("email", Email);
                map.put("password", Password);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}