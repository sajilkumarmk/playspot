package com.example.playspot;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.playspot.databinding.FragmentUserRegistrationBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRegistrationFragment extends Fragment {

    private FragmentUserRegistrationBinding binding;
    EditText username,userplace,userphone,useremail,userpassword;
    Spinner userdistrict;
    AppCompatButton userregistration;
    LinearLayout userregistrationback;

    String status,message,Name,Place,District,Phone,Email,Password,url = Config.baseurl+"user_registration.php";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserRegistrationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        username = binding.userRegName;
        userplace = binding.userRegPlace;
        userdistrict = binding.userRegDistrict;
        userphone = binding.userRegPhone;
        useremail = binding.userRegEmail;
        userpassword = binding.userRegPassword;
        userregistration = binding.userRegButton;
        userregistrationback = binding.UserRegBack;

        String[] dis = {"Select District","Kasaragod","Kannur","Wayanad","Kozhikode","Malappuram","Palakkad",
                "Thrissur","Ernakulam","Alappuzha","Idukki","Kollam","Kottayam","Pathanamthitta","Thiruvananthapuram"};
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, dis);
        userdistrict.setAdapter(adapter);

        userregistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userreg();
            }
        });

        userregistrationback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return root;
    }

    private void userreg() {

        Name = username.getText().toString();
        Place = userplace.getText().toString();
        District = userdistrict.getSelectedItem().toString();
        Phone = userphone.getText().toString();
        Email = useremail.getText().toString();
        Password = userpassword.getText().toString();

        if (TextUtils.isEmpty(Name)){
            username.setError("Required field");
            username.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(Place)){
            userplace.setError("Required field");
            userplace.requestFocus();
            return;
        }
        else if (District.equals("Select District")){
            Toast.makeText(getActivity(), "Please select the district", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(Phone)){
            userphone.setError("Required field");
            userphone.requestFocus();
            return;
        }
        else if (!isPhoneValid(Phone)){
            userphone.setError("Invalid phone number");
            userphone.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(Email)){
            useremail.setError("Required field");
            useremail.requestFocus();
            return;
        }
        else if (!isEmailValid(Email)){
            useremail.setError("Invalid email");
            useremail.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(Password)){
            userpassword.setError("Required field");
            userpassword.requestFocus();
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
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(),Login.class));
                }
                else{
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("name", Name);
                map.put("place", Place);
                map.put("district", District);
                map.put("phone", Phone);
                map.put("email", Email);
                map.put("password", Password);
                return  map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }
    public static boolean isPhoneValid(String s) {
        Pattern p = Pattern.compile("(0/91)?[6-9][0-9]{9}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }
}