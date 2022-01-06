package com.example.play.admin.admin_home;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.play.Config;
import com.example.play.R;
import com.squareup.picasso.Picasso;

public class ApprovedListItemView extends AppCompatActivity {

    TextView tType,tName,tPlace,tDistrict,tState,tPhone,tEmail;
    ImageView timage,tidproof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.approved_list_item_view);

        String p_id = getIntent().getStringExtra("ID");
        String type = getIntent().getStringExtra("TYPE");
        String name = getIntent().getStringExtra("NAME");
        String place = getIntent().getStringExtra("PLACE");
        String district = getIntent().getStringExtra("DISTRICT");
        String state = getIntent().getStringExtra("STATE");
        String idProof = getIntent().getStringExtra("IDPROOF");
        String phone = getIntent().getStringExtra("PHONE");
        String email = getIntent().getStringExtra("EMAIL");
        String image = getIntent().getStringExtra("IMAGE");

        tType = findViewById(R.id.pType);
        tName = findViewById(R.id.pName);
        tPlace = findViewById(R.id.pPlace);
        tDistrict = findViewById(R.id.pDistrict);
        tState = findViewById(R.id.pState);
        tPhone = findViewById(R.id.pPhone);
        tEmail = findViewById(R.id.pEmail);
        timage = findViewById(R.id.playProfile);
        tidproof = findViewById(R.id.idProof);

        tType.setText(type);
        tName.setText(name);
        tPlace.setText(place);
        tDistrict.setText(district);
        tState.setText(state);
        tPhone.setText(phone);
        tEmail.setText(email);
        Picasso.get().load(Config.imageUrl+image).into(timage);
        Picasso.get().load(Config.imageUrl+idProof).into(tidproof);

    }

}
