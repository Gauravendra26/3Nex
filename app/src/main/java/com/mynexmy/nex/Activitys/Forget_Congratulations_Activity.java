package com.mynexmy.nex.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mynexmy.nex.MainActivity;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;

public class Forget_Congratulations_Activity extends AppCompatActivity {
    RelativeLayout rlDone1;
    ImageView btn_back4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_congratulations);
        Utils.blackIconStatusBar(Forget_Congratulations_Activity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        init();
        rlDone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        btn_back4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Forget_Newpassword_Activity.class);
                startActivity(i);
                finish();
            }
        });
    }

    void init() {
        rlDone1 = findViewById(R.id.rlDone1);
        btn_back4 = findViewById(R.id.btn_back4);
    }
}