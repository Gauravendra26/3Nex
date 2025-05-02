package com.mynexmy.nex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp_OtpSbmit_Activity extends AppCompatActivity {
    RelativeLayout rlsubmit2;
    ImageView btn_back4;
    String mobile;
    PinView pinview;
    ProgressDialog progressDialog;
    TextView tvRese;
    String email, name, lname, p1, p2, pin, Token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_otp_sbmit);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        Boolean Login_Status = sh.getBoolean("Login_Status", false);
        if (Login_Status) {
            Intent i = new Intent(getApplicationContext(), Home.class);
            startActivity(i);
            finish();
        }
        init();

        Intent intent = getIntent();
        mobile = intent.getStringExtra("mobile");


        rlsubmit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pinview.getText().toString().length() >= 4) {
                    verifyOtp();
//                    Intent i = new Intent(getApplicationContext(),Signup.class);
//
//                    startActivity(i);
                } else {
                    Toast.makeText(SignUp_OtpSbmit_Activity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                }
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                rlsubmit2.startAnimation(myAnim);

            }
        });
        btn_back4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                btn_back4.startAnimation(myAnim);
            }
        });
        tvRese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOtp();
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                tvRese.startAnimation(myAnim);
            }
        });
    }

    void init() {
        rlsubmit2 = findViewById(R.id.rlsubmit2);
        btn_back4 = findViewById(R.id.btn_back4);
        tvRese = findViewById(R.id.tvRese);
        pinview = findViewById(R.id.pinview);
    }

    private void getOtp() {
        {
            progressDialog = new ProgressDialog(this);
            progressDialog.show();
            progressDialog.setContentView(R.layout.new_progresslogo);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("mobile", mobile);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiData.get_otp,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressDialog.dismiss();
                            try {
                                if (response.getBoolean("status") == true) {

                                    Toast.makeText(SignUp_OtpSbmit_Activity.this, "" + response.getString("message"),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignUp_OtpSbmit_Activity.this, "" + response.getString("message"),
                                            Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
    }

    private void verifyOtp() {
        {
            progressDialog = new ProgressDialog(this);
            progressDialog.show();
            progressDialog.setContentView(R.layout.new_progresslogo);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("mobile", mobile);
                jsonObject.put("otp", pinview.getText().toString().trim());

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiData.verify_otp,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                progressDialog.dismiss();

                                if (response.getBoolean("status") == true) {

                                    Toast.makeText(SignUp_OtpSbmit_Activity.this,
                                            "" + response.getString("message"), Toast.LENGTH_SHORT).show();

                                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                    myEdit.putBoolean("Login_Status", true);
                                    myEdit.putString("Login_Token",""+response.getString("token"));
                                    myEdit.apply();
                                    myEdit.commit();

                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);

                                    startActivity(i);

                                } else {
                                    Toast.makeText(SignUp_OtpSbmit_Activity.this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();
                                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                    myEdit.putBoolean("Login_Status", false);
                                    myEdit.apply();
                                    myEdit.commit();
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

}