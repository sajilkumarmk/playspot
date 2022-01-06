package com.example.play.admin.admin_home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.play.Config;
import com.example.play.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PendingListItemView extends AppCompatActivity {
    TextView tType,tName,tPlace,tDistrict,tState,tPhone,tEmail;
    ImageView timage,tidproof;
    AppCompatButton approve, reject;
    String p_id,type,name,place,district,state,idProof,phone,email,image,status,message,
            approveurl = Config.baseurl+"admin_approve_play.php",
            rejecturl = Config.baseurl+"admin_reject_play.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pending_list_item_view);

        p_id = getIntent().getStringExtra("ID");
        type = getIntent().getStringExtra("TYPE");
        name = getIntent().getStringExtra("NAME");
        place = getIntent().getStringExtra("PLACE");
        district = getIntent().getStringExtra("DISTRICT");
        state = getIntent().getStringExtra("STATE");
        idProof = getIntent().getStringExtra("IDPROOF");
        phone = getIntent().getStringExtra("PHONE");
        email = getIntent().getStringExtra("EMAIL");
        image = getIntent().getStringExtra("IMAGE");

        tType = findViewById(R.id.pType);
        tName = findViewById(R.id.pName);
        tPlace = findViewById(R.id.pPlace);
        tDistrict = findViewById(R.id.pDistrict);
        tState = findViewById(R.id.pState);
        tPhone = findViewById(R.id.pPhone);
        tEmail = findViewById(R.id.pEmail);
        timage = findViewById(R.id.playProfile);
        tidproof = findViewById(R.id.idProof);
        approve = findViewById(R.id.approve);
        reject = findViewById(R.id.reject);

        tType.setText(type);
        tName.setText(name);
        tPlace.setText(place);
        tDistrict.setText(district);
        tState.setText(state);
        tPhone.setText(phone);
        tEmail.setText(email);
        Picasso.get().load(Config.imageUrl+image).into(timage);
        Picasso.get().load(Config.imageUrl+idProof).into(tidproof);

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check for permission
                if (ContextCompat.checkSelfPermission(PendingListItemView.this, Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED){
                    playApprove();
                }
                else {
                    // when permission is not granted request for it
                    ActivityCompat.requestPermissions(PendingListItemView.this,new String[]{Manifest.permission.SEND_SMS},
                            100);
                }

            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(PendingListItemView.this, Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED){
                    playReject();
                }
                else {
                    // when permission is not granted request for it
                    ActivityCompat.requestPermissions(PendingListItemView.this,new String[]{Manifest.permission.SEND_SMS},
                            100);
                }

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //checking condition
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void playApprove() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, approveurl, new Response.Listener<String>() {
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
                    sendApproveMessege();
                    Intent intent = new Intent(getApplicationContext(), PendingPlaygroundFragment.class);
                    startActivity(intent);
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
                map.put("id", p_id);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void sendApproveMessege() {
        String msg = "Your registration request for PLATSPOT is approved";
        //Initialize SmsManager
        SmsManager smsManager = SmsManager.getDefault();
        //Send text messege
        smsManager.sendTextMessage(phone,null,msg,null,null);
    }

    private void playReject() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, rejecturl, new Response.Listener<String>() {
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
                    sendRejectMessege();
                    Intent intent = new Intent(getApplicationContext(), PendingPlaygroundFragment.class);
                    startActivity(intent);
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
                map.put("id", p_id);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
    private void sendRejectMessege() {
        String msg = "Your registration request for PLATSPOT is rejected";
        //Initialize SmsManager
        SmsManager smsManager = SmsManager.getDefault();
        //Send text messege
        smsManager.sendTextMessage(phone,null,msg,null,null);
    }
}
