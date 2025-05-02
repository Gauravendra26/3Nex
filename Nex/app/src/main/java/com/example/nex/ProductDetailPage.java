package com.example.nex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class ProductDetailPage extends AppCompatActivity {

    View view;
    ImageView imageSlider,btn_back;
    String name,image,id,accc,sale;
    TextView pname,saleprice,actprice;


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

        pname.setText(name);

        saleprice.setText("\u20B9"+sale);
        String text = "<strike><font color=\'#757575\'>\u20B9"+accc+"</font></strike>";
        actprice.setText(Html.fromHtml(text));
        Glide.with(getApplicationContext()).load(image).into(imageSlider);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void init(){
        imageSlider=findViewById(R.id.imageSlider);
        pname=findViewById(R.id.pname);
        actprice=findViewById(R.id.actprice);
        saleprice=findViewById(R.id.saleprice);
        btn_back=findViewById(R.id.btn_back);


}
}