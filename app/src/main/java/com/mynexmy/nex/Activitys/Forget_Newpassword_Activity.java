package com.mynexmy.nex.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.MainActivity;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class Forget_Newpassword_Activity extends AppCompatActivity {
    RelativeLayout rlChngPassword1;
    ImageView imghide1, imgshow1, imghide2, imgshow2, btn_back4;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    EditText etPass2, etPass1;
    int otp;
    String mobile, p1, p2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_newpassword);
        Utils.blackIconStatusBar(Forget_Newpassword_Activity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        init();
        Intent i = getIntent();

        mobile= i.getStringExtra("mobile");

        rlChngPassword1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideDefaultKeyboard();
                if (isValid()) {
                    changepassword();
                }
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
                rlChngPassword1.startAnimation(myAnim);
            }
        });
        btn_back4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Forget_Password_Activity.class);
                startActivity(i);
                finish();
            }
        });
        imgshow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPass1.setTransformationMethod(null);
//                etPass1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                etPass1.setSelection(etPass1.length());
                imgshow1.setVisibility(View.INVISIBLE);
                imghide1.setVisibility(View.VISIBLE);
                hideDefaultKeyboard();

            }
        });
        imgshow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPass2.setTransformationMethod(null);
//                etPass2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                etPass2.setSelection(etPass2.length());
                imgshow2.setVisibility(View.INVISIBLE);
                imghide2.setVisibility(View.VISIBLE);
                hideDefaultKeyboard();

            }
        });
        imghide2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                etPass2.setSelection(etPass2.length());
                imghide2.setVisibility(View.INVISIBLE);
                imgshow2.setVisibility(View.VISIBLE);
//                etPass2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                etPass2.setTransformationMethod(new PasswordTransformationMethod());

                hideDefaultKeyboard();
            }
        });
        imghide1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                etPass1.setSelection(etPass1.length());
                imghide1.setVisibility(View.INVISIBLE);
                imgshow1.setVisibility(View.VISIBLE);
//                etPass1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                etPass1.setTransformationMethod(new PasswordTransformationMethod());

                hideDefaultKeyboard();
            }
        });
    }

    private void hideDefaultKeyboard() {
        //  MainActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //you have got lot of methods here
        if (getCurrentFocus() != null) {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        }
    }

    private void changepassword() {
        {
            progressDialog = new ProgressDialog(this);
            progressDialog.show();
            progressDialog.setContentView(R.layout.new_progresslogo);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("mobile", mobile);
                jsonObject.put("password", etPass1.getText().toString().trim());


            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    ApiData.Resetpassword, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressDialog.dismiss();
                            try {
                                if (response.getBoolean("status") == true) {
                                Toast.makeText(Forget_Newpassword_Activity.this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                finish();
                                } else {
                                    Toast.makeText(Forget_Newpassword_Activity.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();

                                }

                            } catch (JSONException e) {

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(jsonObjectRequest);

        }

    }


    void init() {
        rlChngPassword1 = findViewById(R.id.rlChngPassword1);
        btn_back4 = findViewById(R.id.btn_back4);
        imghide1 = findViewById(R.id.imghide1);
        imgshow1 = findViewById(R.id.imgshow1);
        imghide2 = findViewById(R.id.imghide2);
        etPass2 = findViewById(R.id.etPass2);
        imgshow2 = findViewById(R.id.imgshow2);
        etPass1 = findViewById(R.id.etPass1);
    }
    boolean isValid() {

        String passwordPattern ="[a-zA-Z0-9\\!\\@\\#\\$]{8,24}";

        p1 = etPass1.getText().toString();
        p2 = etPass2.getText().toString();

        if (p1.isEmpty()) {
            etPass1.setError("Enter Password");
             return false;
        } else if (p1.length() <6) {
            etPass1.setError("Enter Full Password");
             return false;
        }
        if (!p1.matches(passwordPattern)) {
            etPass1.setError("Please enter Password in this form, Ex:abc@123");
            return false;
        }

        if (!p2.equals(p1)) {
            etPass2.setError("Password Not Match");
             return false;
        }
        return true;

    }

}