package com.mynexmy.nex.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.mynexmy.nex.Home;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;

public class OrderPlacedMsg extends AppCompatActivity {
    TextView tv1;
    int check=1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed_msg);
        Utils.blackIconStatusBar(OrderPlacedMsg.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        tv1 = findViewById(R.id.tv1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        Animation animation,animation1;
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.my_anim
        );

        tv1.setAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(getApplicationContext(), Orders_Activity.class);
                intent.putExtra("check",check);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}