package com.mynexmy.nex.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mynexmy.nex.R;
import com.mynexmy.nex.Signup;
import com.mynexmy.nex.Utils;

public class ContactUspage extends AppCompatActivity {
    ImageView btnback,imgCall,imgEmail;
    TextView tvCall,tvEmail;
    RelativeLayout rlContact;
    EditText etConMessage,etConSubject,etConEmail,etConMob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_uspage);
        Utils.blackIconStatusBar(ContactUspage.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        init();

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:18002035817"));
                startActivity(i);
            }
        });
        imgEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_EMAIL,new String[]{"info@3nex.co.in"});
                i.setType("message/rfc822");
                startActivity(Intent.createChooser(i,"Choose for send Email"));
            }
        });
        tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_EMAIL,new String[]{"info@3nex.co.in"});
                i.setType("message/rfc822");
                startActivity(Intent.createChooser(i,"Choose for send Email"));
            }
        });
        tvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:18002035817"));
                startActivity(i);
            }
        });
        rlContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (isValid()){
                   Toast.makeText(ContactUspage.this, "Request has been sent", Toast.LENGTH_SHORT).show();
                   finish();
               }
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
                rlContact.startAnimation(myAnim);
                hideDefaultKeyboard();
            }
        });
    }

    public void init() {
        btnback = findViewById(R.id.btnback);
        rlContact = findViewById(R.id.rlContact);
        imgCall = findViewById(R.id.imgCall);
        tvCall = findViewById(R.id.tvCall);
        imgEmail = findViewById(R.id.imgEmail);
       tvEmail = findViewById(R.id.tvEmail);
        etConMob = findViewById(R.id.etConMob);
        etConEmail = findViewById(R.id.etConEmail);
        etConSubject = findViewById(R.id.etConSubject);
        etConMessage = findViewById(R.id.etConMessage);
    }

    private void hideDefaultKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    boolean isValid() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String email,mobile,subject,message;

        email = etConEmail.getText().toString().trim();
        mobile = etConMob.getText().toString().trim();
        subject = etConSubject.getText().toString().trim();
        message = etConMessage.getText().toString().trim();

        if (mobile.isEmpty()) {
            etConMob.setError("Please enter Mobile Number");
            return false;
        } else if (mobile.length() < 10) {
            etConMob.setError("Enter Full Mobile Number");
            return false;
        }
        if (email.isEmpty()) {
            etConEmail.setError("Please enter Email");
            return false;
        } else if (!email.matches(emailPattern)) {
            etConEmail.setError("Please enter Valid Email");
            return false;
        }
        if (subject.isEmpty()) {
            etConSubject.setError("Please Enter Subject");
            return false;
        }
        if (message.isEmpty()) {
            etConMessage.setError("Please Enter Message");
            return false;
        }
        return true;

    }


}