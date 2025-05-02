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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RelativeLayout rlsignin;
    EditText etUser, etPass;
    TextView tvSign;
    ImageView imghide,imgshow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        tvSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideDefaultKeyboard();
                Intent i=new Intent(getApplicationContext(),Signup.class);
                startActivity(i);
                finish();

            }
        });
        rlsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideDefaultKeyboard();
                Intent i=new Intent(getApplicationContext(),Home.class);
                startActivity(i);
                finish();

            }
        });



        etPass.setTransformationMethod(new PasswordTransformationMethod());
        imgshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                etPass.setSelection(etPass.length());
                imgshow.setVisibility(View.INVISIBLE);
                imghide.setVisibility(View.VISIBLE);
                hideDefaultKeyboard();

            }
        });
        imghide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPass.setSelection(etPass.length());
                imghide.setVisibility(View.INVISIBLE);
                imgshow.setVisibility(View.VISIBLE);
                etPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                hideDefaultKeyboard();

            }
        });
    }


    void init(){

        rlsignin=findViewById(R.id.rlSignin);
        etUser=findViewById(R.id.etUser);
        etPass=findViewById(R.id.etPass);
        tvSign=findViewById(R.id.tvSign);
        imghide=findViewById(R.id.imghide);
        imgshow=findViewById(R.id.imgshow);


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