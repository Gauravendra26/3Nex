package com.mynexmy.nex.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;

public class NewManageAddress_Activity extends Activity {
    TextView tv_messages,mobleno,tvhouseno,tvufirst,tvlastname,tvpincode,tvcity,tvstate;
    RelativeLayout changeaddress, placeorder1,changeaddress1,rlbot;
    ImageView btn_back;
    String email,first_name,last_name,mobile,pincode,created_at,updated_at,referral_code,
            city,country,state,postal_code,address_phone,address_line;
    LinearLayout bottom,llAddress;
    String House ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_manage_address);
        Utils.blackIconStatusBar(NewManageAddress_Activity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);



        if ( House.isEmpty()) {
            House = tvhouseno.getText().toString().trim();
            llAddress.setVisibility(View.GONE);
            changeaddress.setVisibility(View.VISIBLE);
            changeaddress1.setVisibility(View.GONE);
            Toast.makeText(this, "Please Add Address", Toast.LENGTH_SHORT).show();
        } else {

            llAddress.setVisibility(View.VISIBLE);
            changeaddress.setVisibility(View.GONE);
            changeaddress1.setVisibility(View.VISIBLE);
        }

        changeaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Manage_Account_Activity.class);
                startActivity(i);

            }
        });

        changeaddress1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Manage_Account_Activity.class);
                startActivity(i);

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
