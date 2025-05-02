package com.mynexmy.nex.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mynexmy.nex.Home;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;

public class Congratulation_Scratch_Activity2 extends AppCompatActivity {
    RelativeLayout rlbuy, rlwish;
    TextView tvclose;
    ImageView btn_back;
    int scratch_card_payment_id,txnid;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulation_scratch2);
        Utils.blackIconStatusBar(Congratulation_Scratch_Activity2.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        init();
        Intent i=getIntent();
         scratch_card_payment_id = i.getIntExtra("scratch_card_payment_id", 0);
        txnid = i.getIntExtra("txnid", 0);

        rlwish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Otp_sharelink_Activity.class);
                intent.putExtra("scratch_card_payment_id",scratch_card_payment_id);
                 startActivity(intent);
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
                rlwish.startAnimation(myAnim);
                finish();
            }
        });

        tvclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
                tvclose.startAnimation(myAnim);
                finish();
            }
        });
//        btn_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               finish();
//            }
//        });
    }
    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
//            super.onBackPressed();
            return;
        }
//        else {
//            Toast.makeText(getBaseContext(), "Do you want to Exit ?", Toast.LENGTH_SHORT).show();
//        }

        mBackPressed = System.currentTimeMillis();
    }

    private void init() {

        rlbuy = findViewById(R.id.rlbuy);
        rlwish = findViewById(R.id.rlwish);
        tvclose = findViewById(R.id.tvclose);
        btn_back = findViewById(R.id.btn_back);


    }
}