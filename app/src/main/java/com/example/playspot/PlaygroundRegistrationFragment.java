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
import android.widget.TextView;
import android.widget.Toast;

import com.example.playspot.databinding.FragmentPlaygroundRegistrationBinding;
import com.example.playspot.databinding.FragmentUserRegistrationBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaygroundRegistrationFragment extends Fragment {

    private FragmentPlaygroundRegistrationBinding binding;
    EditText playname,playplace,playstate,playphone,playemail,playpassword;
    TextView playimagename;
    Spinner playdistrict,playtype;
    AppCompatButton playregistration,playupload;
    LinearLayout playregistrationback;

    String status,message,Name,Type,Place,District,Phone,Email,Image,Password,State;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPlaygroundRegistrationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        playname = binding.playgroundregname;
        playtype = binding.playgroundregtype;
        playplace = binding.playgroundregplace;
        playdistrict = binding.playgroundregdistrict;
        playstate = binding.playgroundregstate;
        playphone = binding.playgroundregphone;
        playemail = binding.playgroundregemail;
        playimagename = binding.playgroundregimagename;
        playupload = binding.playgroundregupload;
        playpassword = binding.playgroundregpassword;
        playregistration = binding.playgroundregbutton;
        playregistrationback = binding.playgroundregback;

        String[] typ = {"Select Type","Turf","Stadium","Club"};
        ArrayAdapter adaptertype = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, typ);
        playtype.setAdapter(adaptertype);

        String[] dis = {"Select District","Kasaragod","Kannur","Wayanad","Kozhikode","Malappuram","Palakkad",
                "Thrissur","Ernakulam","Alappuzha","Idukki","Kollam","Kottayam","Pathanamthitta","Thiruvananthapuram"};
        ArrayAdapter adapterdis = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, dis);
        playdistrict.setAdapter(adapterdis);

        playregistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playgroundreg();
            }
        });

        playregistrationback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });




        return root;
    }

    private void playgroundreg() {

        Name = playname.getText().toString();
        Type = playtype.getSelectedItem().toString();
        Place = playplace.getText().toString();
        District = playdistrict.getSelectedItem().toString();
        State = playstate.getText().toString();
        Phone = playphone.getText().toString();
        Email = playemail.getText().toString();
        Image = playimagename.getText().toString();
        Password = playpassword.getText().toString();

        if (TextUtils.isEmpty(Name)){
            playname.setError("Required field");
            playname.requestFocus();
            return;
        }
        else if (Type.equals("Select Type")){
            Toast.makeText(getActivity(), "Please select Type", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(Place)){
            playplace.setError("Required field");
            playplace.requestFocus();
            return;
        }
        else if (District.equals("Select District")){
            Toast.makeText(getActivity(), "Please select the district", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(State)){
            playstate.setError("Required field");
            playstate.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(Phone)){
            playphone.setError("Required field");
            playphone.requestFocus();
            return;
        }
        else if (!isPhoneValid(Phone)){
            playphone.setError("Invalid phone number");
            playphone.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(Email)){
            playemail.setError("Required field");
            playemail.requestFocus();
            return;
        }
        else if (!isEmailValid(Email)){
            playemail.setError("Invalid email");
            playemail.requestFocus();
            return;
        }
//        else if (TextUtils.isEmpty(Image)){
//            playimagename.setError("Required field");
//            playimagename.requestFocus();
//            return;
//        }
        else if (TextUtils.isEmpty(Password)){
            playpassword.setError("Required field");
            playpassword.requestFocus();
            return;
        }



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