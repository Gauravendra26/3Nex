package com.mynexmy.nex.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mynexmy.nex.Home;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;

public class VerifyMobile extends AppCompatActivity {

    RelativeLayout rlgetotp, rlcan;
    ImageView btn_back3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mobile);
        Utils.blackIconStatusBar(VerifyMobile.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        init();

        rlgetotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), OtpSubmit.class);
                startActivity(i);
            }
        });

        rlcan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Home.class);
                startActivity(i);
            }
        });
        btn_back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    void init() {
        rlgetotp = findViewById(R.id.rlgetotp);
        rlcan = findViewById(R.id.rlcan);
        btn_back3 = findViewById(R.id.btn_back3);

    }
}