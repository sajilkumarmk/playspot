package com.example.play.stadium.StadiumFacilities;

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

public class StadiumCourtUpdate extends AppCompatActivity {

    EditText gametype, courttype, courtlength,courtwidth, courtamount;
    AppCompatButton update;
    String cID,gameTYPE, courtTYPE, courtLENGTH, courtWIDTH, courtAMOUNT;
    String id, gtype, ctype, clength, cwidth, camount, status, message, url = Config.baseurl+"stadium_update_court.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_court_update);

        cID = getIntent().getStringExtra("CID");
        gameTYPE = getIntent().getStringExtra("GAMETYPE");
        courtTYPE = getIntent().getStringExtra("COURTTYPE");
        courtLENGTH = getIntent().getStringExtra("COURTLENGTH");
        courtWIDTH = getIntent().getStringExtra("COURTWIDTH");
        courtAMOUNT = getIntent().getStringExtra("COURTAMOUNT");

        gametype = findViewById(R.id.sUpdateGameType);
        courttype = findViewById(R.id.sUpdateCourtType);
        courtlength = findViewById(R.id.sUpdateCourtLength);
        courtwidth = findViewById(R.id.sUpdateCourtWidth);
        courtamount = findViewById(R.id.sUpdateCourtAmount);
        update = findViewById(R.id.sCourtUpdate);

        gametype.setText(gameTYPE);
        courttype.setText(courtTYPE);
        courtlength.setText(courtLENGTH);
        courtwidth.setText(courtWIDTH);
        courtamount.setText(courtAMOUNT);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCourt();
            }
        });
    }

    private void updateCourt() {

        id = new SessionManager(getApplicationContext()).getUserDetails().get("id");
        gtype = gametype.getText().toString();
        ctype = courttype.getText().toString();
        clength = courtlength.getText().toString();
        cwidth = courtwidth.getText().toString();
        camount = courtamount.getText().toString();

        if (TextUtils.isEmpty(gtype)) {
            gametype.setError("Required field");
            gametype.requestFocus();
            return;
        } else if (TextUtils.isEmpty(ctype)) {
            courttype.setError("Required field");
            courttype.requestFocus();
            return;
        } else if (TextUtils.isEmpty(clength)) {
            courtlength.setError("Required field");
            courtlength.requestFocus();
            return;
        } else if (TextUtils.isEmpty(cwidth)) {
            courtwidth.setError("Required field");
            courtwidth.requestFocus();
            return;
        }else if (TextUtils.isEmpty(camount)) {
            courtamount.setError("Required field");
            courtamount.requestFocus();
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
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), SCourtFragment.class);
                    startActivity(intent);
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
                map.put("cid",cID);
                map.put("gametype", gtype);
                map.put("courttype", ctype);
                map.put("courtlength", clength);
                map.put("courtwidth", cwidth);
                map.put("amount", camount);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
