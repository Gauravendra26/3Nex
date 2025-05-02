package com.example.nex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Payment_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Utils.blackIconStatusBar(Payment_Activity.this, R.color.white);
    }
}