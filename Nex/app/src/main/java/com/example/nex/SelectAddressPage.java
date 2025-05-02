package com.example.nex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SelectAddressPage extends AppCompatActivity {

    RelativeLayout rlConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectaddresspage);
        Utils.blackIconStatusBar(SelectAddressPage.this, R.color.white);




    }
    private void init() {

        rlConfirm =   findViewById(R.id.rlConfirm);


    }
}