package com.mynexmy.nex.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class Forget_Password_Activity extends AppCompatActivity {
    RelativeLayout rlgetotp1, rlcan;
    ImageView btn_back4;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    EditText etMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        Utils.blackIconStatusBar(Forget_Password_Activity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        init();


        rlgetotp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideDefaultKeyboard();
                if (etMobile.getText().toString().length()>=10)
                {
                    forgotpassword();
                }else{
                    Toast.makeText(Forget_Password_Activity.this, "Please Enter Full Mobile Number", Toast.LENGTH_SHORT).show();
                }
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                rlgetotp1.startAnimation(myAnim);
            }
        });

        btn_back4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rlcan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                rlcan.startAnimation(myAnim);
            }
        });
    }

    void init() {
        rlgetotp1 = findViewById(R.id.rlgetotp1);
        btn_back4 = findViewById(R.id.btn_back4);
        rlcan = findViewById(R.id.rlcan);
        etMobile = findViewById(R.id.etMobile);
    }

    private void forgotpassword() {

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.new_progresslogo);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        JSONObject jsonObject = new JSONObject();
        try {
             jsonObject.put("mobile", etMobile.getText().toString().trim());

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                ApiData.SendOtp,jsonObject,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        progressDialog.dismiss();

                        try {
                            if (response.getBoolean("status") == true) {
                            Toast.makeText(Forget_Password_Activity.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), Forget_OtpSubmit_Activity.class);

                            i.putExtra("mobile",etMobile.getText().toString().trim());
                            startActivity(i);
                            finish();
                            } else {
                                Toast.makeText(Forget_Password_Activity.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void hideDefaultKeyboard() {
        //  MainActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //you have got lot of methods here
        if (getCurrentFocus() != null) {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        }
    }

}

