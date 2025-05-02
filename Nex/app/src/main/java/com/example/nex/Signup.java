package com.example.nex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Signup extends AppCompatActivity {
    EditText etEmail,etPass2, etPass3;
    ImageView imghide1,imgshow1,imghide2,imgshow2;
    TextView tvsign1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        init();
        tvsign1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideDefaultKeyboard();
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();

            }
        });

        imgshow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etPass2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                etPass2.setSelection(etPass2.length());
                imgshow1.setVisibility(View.INVISIBLE);
                imghide1.setVisibility(View.VISIBLE);
                hideDefaultKeyboard();

            }
        });
        imghide1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPass2.setSelection(etPass2.length());
                imghide1.setVisibility(View.INVISIBLE);
                imgshow1.setVisibility(View.VISIBLE);
                etPass2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                hideDefaultKeyboard();
            }
    });


    }
    void init(){

        etEmail=findViewById(R.id.etEmail);
        etPass2=findViewById(R.id.etPass2);
        etPass3=findViewById(R.id.etPass3);
        imghide1=findViewById(R.id.imghide1);
        imgshow1=findViewById(R.id.imgshow1);
        imghide2=findViewById(R.id.imghide2);

        imgshow2=findViewById(R.id.imgshow2);
        tvsign1=findViewById(R.id.tvSign1);


    }
    private void hideDefaultKeyboard() {
        //  MainActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //you have got lot of methods here
        if(getCurrentFocus()!=null) {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        }
    }

}

