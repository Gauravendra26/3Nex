package com.mynexmy.nex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mynexmy.nex.Activitys.Order_Summary_Activity;
import com.mynexmy.nex.R;

public class ProductDetailPage extends AppCompatActivity {

    View view;
    ImageView imageSlider, btn_back, imgrev, imgrev1,btn_Share;
    String name, image, rating, accc, sale;
    TextView pname, saleprice, rating1, tvAdd, actprice, tvrev, tvdes;
    LinearLayout lldes, llRev;
    RelativeLayout placeorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_page);
        Utils.blackIconStatusBar(ProductDetailPage.this, R.color.white);
        init();
        Intent intent = getIntent();
        name = intent.getStringExtra("pname");
        image = intent.getStringExtra("pimage");
        sale = intent.getStringExtra("psale");
        accc = intent.getStringExtra("paccu");
        rating = intent.getStringExtra("rating");

        pname.setText(name);
        rating1.setText(rating);
        saleprice.setText("\u20B9" + sale);

        String text = "<strike><font color=\'#757575\'>\u20B9" + accc + "</font></strike>";
        actprice.setText(Html.fromHtml(text));
        Glide.with(getApplicationContext()).load(image).into(imageSlider);
        Glide.with(getApplicationContext()).load(image).into(imgrev);
        Glide.with(getApplicationContext()).load(image).into(imgrev1);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                String text="Check out this Application : ";
                i.putExtra(Intent.EXTRA_TEXT,text+"https://play.google.com/store/apps/details?id=com.mynexmy.nex");
                startActivity(Intent.createChooser(i,"Share Via"));
            }
        });
        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Order_Summary_Activity.class);
                startActivity(i);
            }
        });

        tvrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lldes.setVisibility(View.GONE);
                llRev.setVisibility(View.VISIBLE);
                tvrev.setTextColor(getResources().getColor(R.color.main));
                tvdes.setTextColor(getResources().getColor(R.color.black));
            }

        });
        tvdes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lldes.setVisibility(View.VISIBLE);
                llRev.setVisibility(View.GONE);
                tvrev.setTextColor(getResources().getColor(R.color.black));
                tvdes.setTextColor(getResources().getColor(R.color.main));

            }

        });

    }

    void init() {
        imageSlider = findViewById(R.id.imageSlider);
        pname = findViewById(R.id.pname);
        btn_Share = findViewById(R.id.btn_Share);
        actprice = findViewById(R.id.actprice);
        saleprice = findViewById(R.id.saleprice);
        btn_back = findViewById(R.id.btn_back);
        tvAdd = findViewById(R.id.tvAdd);
        rating1 = findViewById(R.id.rating1);
        lldes = findViewById(R.id.lldes);
        llRev = findViewById(R.id.llRev);
        tvrev = findViewById(R.id.tvrev);
        tvdes = findViewById(R.id.tvdes);
        imgrev = findViewById(R.id.imgrev);
        placeorder = findViewById(R.id.placeorder);
        imgrev1 = findViewById(R.id.imgrev1);
    }
}