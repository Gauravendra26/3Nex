package com.mynexmy.nex.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mynexmy.nex.Adapters.Selectaddress_Adapter;
import com.mynexmy.nex.Models.Selectaddress_Model;
import com.mynexmy.nex.R;


import com.mynexmy.nex.Utils;

import java.util.ArrayList;
import java.util.List;

public class SelectAddress_MyCartActivity extends AppCompatActivity implements Selectaddress_Adapter.ProductPageClick {
    RelativeLayout rlnewadress, rlConfirm, tvedit,rlHome,rlHome2,rlOffice,rlOffice2;
    ImageView btnback;
    RecyclerView rvAddress;
    Selectaddress_Adapter selectaddress_adapter;
    List<Selectaddress_Model> selectaddress_models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address_my_cart);
        Utils.blackIconStatusBar(SelectAddress_MyCartActivity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        init();


         getdata();


//        AddressDataBase Address = Room.databaseBuilder(getApplicationContext(),
//                AddressDataBase.class, "cart_Address").allowMainThreadQueries().build();
//        AddressDao addressDao=Address.AddressDao();
//        rvAddress.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        List<com.example.nex.Room.Address> addresses = addressDao.getallproduct();



        rlnewadress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Manage_Account_Activity.class);
                startActivity(intent);
                finish();
            }

        });

//        tvedit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), AddNewAddress.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        rlOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlHome.setVisibility(View.GONE);
                rlHome2.setVisibility(View.VISIBLE);
                rlOffice2.setVisibility(View.VISIBLE);
                rlOffice.setVisibility(View.GONE);
            }
        });
        rlHome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlHome.setVisibility(View.VISIBLE);
                rlHome2.setVisibility(View.GONE);
                rlOffice.setVisibility(View.VISIBLE);
                rlOffice2.setVisibility(View.GONE);
            }
        });


        rlConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void init() {

        rlConfirm = findViewById(R.id.rlConfirm);
        btnback = findViewById(R.id.btnback);
        rvAddress = findViewById(R.id.rvAddress);
//        tvedit = findViewById(R.id.tvedit);
        rlnewadress = findViewById(R.id.rlnewadress);
        rlHome = findViewById(R.id.rlHome);
        rlHome2 = findViewById(R.id.rlHome2);
        rlOffice = findViewById(R.id.rlOffice);
        rlOffice2 = findViewById(R.id.rlOffice2);

    }

  public void getdata(){

      selectaddress_models = new ArrayList<Selectaddress_Model>();
      selectaddress_adapter = new Selectaddress_Adapter(getApplicationContext(), selectaddress_models);

      LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
              LinearLayoutManager.VERTICAL, true);
      rvAddress.setLayoutManager(layoutManager);
      rvAddress.setItemAnimator(new DefaultItemAnimator());
      rvAddress.setAdapter(selectaddress_adapter);

  }


    @Override
    public void productClick(int position,int cid, int c_mobile, int c_pin, String c_name,
                             String c_email, String c_address, String c_locality,
                             String c_city, String c_state) {
    }
}