package com.mynexmy.nex.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;

public class OrderSummary_Activity extends AppCompatActivity {
    RelativeLayout changeaddress1, placeorder2;
    ImageView btn_back3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        Utils.blackIconStatusBar(OrderSummary_Activity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        init();

        changeaddress1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), AddNewAddress.class);
                startActivity(i);

            }
        });



        placeorder2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Congratulation_Scratch_Activity2.class);
                startActivity(intent);
            }
        });
        btn_back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void init() {

        changeaddress1 = findViewById(R.id.changeaddress1);
        placeorder2 = findViewById(R.id.placeorder2);
        btn_back3 = findViewById(R.id.btn_back3);


    }

}