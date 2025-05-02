package com.example.nex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class Congratulations_scratch_Activity2 extends AppCompatActivity {

    RelativeLayout rlbuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulations_scratch2);
        Utils.blackIconStatusBar(Congratulations_scratch_Activity2.this, R.color.main);
init();


    }
    private void init() {

        rlbuy = findViewById(R.id.rlbuy);



    }
}