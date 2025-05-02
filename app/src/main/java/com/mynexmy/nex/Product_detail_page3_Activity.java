package com.mynexmy.nex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mynexmy.nex.R;

public class Product_detail_page3_Activity extends AppCompatActivity {
    ProgressDialog progressDialog;
    RatingBar ratingbar;
    ImageView imageSlider, btn_back;
    String  image,  desc,name;
    TextView pname, saleprice;
    RatingBar simpleRatingBar;
    TextView text_view;
int sale,bucket_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_page3);
        Utils.blackIconStatusBar(Product_detail_page3_Activity.this, R.color.white);
        init();

        Intent intent = getIntent();
        bucket_id = intent.getIntExtra("bucket_id",0);

        name = intent.getStringExtra("bucket_product_title");
        image = intent.getStringExtra("bucket_product_image");
        desc = intent.getStringExtra("bucket_product_description");
        sale = intent.getIntExtra("bucket_product_price", 0);


        pname.setText(name);
        text_view.setText(desc);
        saleprice.setText("\u20B9" + sale);
        Glide.with(getApplicationContext()).load(image).into(imageSlider);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    void init() {
        imageSlider = findViewById(R.id.imageSlider);
        pname = findViewById(R.id.pname);
        saleprice = findViewById(R.id.saleprice);
        btn_back = findViewById(R.id.btn_back);
//        text_view = findViewById(R.id.text_view);
    }

}