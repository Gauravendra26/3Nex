package com.mynexmy.nex.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;

public class OtpSubmit extends AppCompatActivity {

    RelativeLayout rlsubmit;
    TextView tvRe;
    ImageView btn_back3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_submit);
        Utils.blackIconStatusBar(OtpSubmit.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        init();

        rlsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CongratulationsPage.class);
                startActivity(i);
            }
        });

        tvRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OtpSubmit.this, "OTP Sent", Toast.LENGTH_SHORT).show();
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

        rlsubmit = findViewById(R.id.rlsubmit);
        tvRe = findViewById(R.id.tvRe);
        btn_back3 = findViewById(R.id.btn_back3);
    }
}