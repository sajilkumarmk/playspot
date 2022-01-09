package com.example.play.stadium.StadiumFacilities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class StadiumFacilitiesAdd extends AppCompatActivity {

    EditText facility, description;
    TextView openingTime, closingTime;
    AppCompatButton add, openingbtn, closingbtn;
    int cHour, cMinute, sHour, sMinute;
    String id, faci, open, close, desc, status, message, url = Config.baseurl+"stadium_add_facilities.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_facilities_add);

        facility = findViewById(R.id.sAddFacility);
        openingTime = findViewById(R.id.sAddFacilityOpeningTime);
        closingTime = findViewById(R.id.sAddFacilityClosingTime);
        description = findViewById(R.id.sAddFacilityDescription);
        openingbtn = findViewById(R.id.sAddFacilityOpeningTimebtn);
        closingbtn = findViewById(R.id.sAddFacilityClosingTimebtn);
        add = findViewById(R.id.sFacilityAdd);

        Calendar calender = Calendar.getInstance();
        cHour = calender.get(Calendar.HOUR_OF_DAY);
        cMinute = calender.get(Calendar.MINUTE);


        openingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(openingTime);
            }
        });
        closingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(closingTime);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFacility();
            }
        });


    }

    private void showTimeDialog(TextView time) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                StadiumFacilitiesAdd.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                sHour = hourOfDay;
                sMinute = minute;
                Calendar calendar = Calendar.getInstance();
//                        String sDate = openingTime.getText().toString().trim();
//                        String[] strings = sDate.split("-");
//                        int sDay = Integer.parseInt(strings[0]);
//                        calendar.set(Calendar.DAY_OF_MONTH,sDay);
                calendar.set(Calendar.HOUR_OF_DAY, sHour);
                calendar.set(Calendar.MINUTE, sMinute);
                time.setText(DateFormat.format("hh:mm aa", calendar));
            }
        }, cHour, cMinute, false
        );
        timePickerDialog.show();
    }

    private void addFacility() {

        id = new SessionManager(getApplicationContext()).getUserDetails().get("id");
        faci = facility.getText().toString();
        open = openingTime.getText().toString();
        close = closingTime.getText().toString();
        desc = description.getText().toString();

        if (TextUtils.isEmpty(faci)) {
            facility.setError("Required field");
            facility.requestFocus();
            return;
        }else if (open.equals("Opening Time")) {
            openingTime.setError("Required field");
            description.requestFocus();
            return;
        }else if (close.equals("Closing Time")) {
            openingTime.setError("Required field");
            description.requestFocus();
            return;
        } else if (TextUtils.isEmpty(desc)) {
            description.setError("Required field");
            description.requestFocus();
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
                    Intent intent = new Intent(getApplicationContext(), SFacilitiesFragment.class);
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
                map.put("facility", faci);
                map.put("open", open);
                map.put("close", close);
                map.put("description", desc);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}

