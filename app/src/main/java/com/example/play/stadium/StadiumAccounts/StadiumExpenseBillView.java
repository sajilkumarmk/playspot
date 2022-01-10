package com.example.play.stadium.StadiumAccounts;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.play.Config;
import com.example.play.R;
import com.squareup.picasso.Picasso;

public class StadiumExpenseBillView extends AppCompatActivity {

    ImageView bill;
    String billImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_expense_bill_view);

        billImage = getIntent().getStringExtra("EBILL");

        bill = findViewById(R.id.bill);

        Picasso.get().load(Config.imageUrl+billImage).into(bill);


    }
}
