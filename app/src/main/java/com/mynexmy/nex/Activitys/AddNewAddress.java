package com.mynexmy.nex.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddNewAddress extends AppCompatActivity {

    RelativeLayout rladdadd;
    ImageView btnback;
    ProgressDialog progressDialog;
    EditText etMobile, etFName, etEmail, etAddress, etLocal, etCity, etPincode, etState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_address);
        Utils.blackIconStatusBar(AddNewAddress.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        init();

        rladdadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideDefaultKeyboard();
                if (isValid()) {
                    verifyAddress();
                    final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
                    rladdadd.startAnimation(myAnim);
                }
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

        rladdadd = findViewById(R.id.rlAddadd);
        btnback = findViewById(R.id.btnback);

        etMobile = findViewById(R.id.etMobile);

        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);

        etCity = findViewById(R.id.etCity);
        etPincode = findViewById(R.id.etPincode);
        etState = findViewById(R.id.etState);

    }

    private void verifyAddress() {

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.new_progress);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        JSONObject jsonObject=new JSONObject();
        try {
//                jsonObject.put("token",token);
            SharedPreferences sharedPreferences =
                    getSharedPreferences("MySharedPref", MODE_PRIVATE);

            jsonObject.put("token",sharedPreferences.getString("Login_Token",""));
            jsonObject.put("address",etAddress.getText().toString().trim());
            jsonObject.put("city",etCity.getText().toString().trim());
            jsonObject.put("country","India");
            jsonObject.put("state",etState.getText().toString().trim());
            jsonObject.put("pincode",etPincode.getText().toString().trim());
            jsonObject.put("mobile",etMobile.getText().toString().trim());

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                ApiData.addressupdate, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            if (response.getBoolean("status") == true) {

                                Toast.makeText(AddNewAddress.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), Order_Summary_Activity.class);
                                startActivity(i);
                                finish();

                            } else {
                                Toast.makeText(AddNewAddress.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", "Error: " + error.getMessage());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                SharedPreferences sharedPreferences =
                        getSharedPreferences("MySharedPref", MODE_PRIVATE);
                HashMap<String, String> headers = new HashMap<String, String>();

                headers.put("Authorization", "Bearer " + sharedPreferences.getString("Login_Token", ""));
                return headers;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonObjectRequest);

    }

    boolean isValid() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if (etMobile.getText().toString().isEmpty()) {
            etMobile.setError("Please Enter Mobile Number");
            Toast.makeText(this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
            return false;
        }
//        if (etFName.getText().toString().isEmpty()) {
//            etFName.setError("Please Enter Full Name");
//            Toast.makeText(this, "Please Enter Full Name", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (etEmail.getText().toString().isEmpty()) {
//            etEmail.setError("Please Enter Email");
//            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
//
//            return false;
//        } else if (!etEmail.getText().toString().matches(emailPattern)) {
//            etEmail.setError("Please Enter Valid Email");
//            Toast.makeText(this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
//
//            return false;
//        }
        if (etAddress.getText().toString().isEmpty()) {
            etAddress.setError("Please Enter Address");
            Toast.makeText(this, "Please Enter Address", Toast.LENGTH_SHORT).show();

            return false;
        }
//        if (etLocal.getText().toString().isEmpty()) {
//            etLocal.setError("Please Enter Locality");
//            Toast.makeText(this, "Please Enter Locality", Toast.LENGTH_SHORT).show();
//
//            return false;
//        }
        if (etCity.getText().toString().isEmpty()) {
            etCity.setError("Please Enter City");
            Toast.makeText(this, "Please Enter City", Toast.LENGTH_SHORT).show();

            return false;
        }
        if (etPincode.getText().toString().isEmpty()) {
            etPincode.setError("Please Enter Pincode");
            Toast.makeText(this, "Please Enter Pincode", Toast.LENGTH_SHORT).show();

            return false;
        } else if (etPincode.length() < 6) {
            etPincode.setError("Please Enter Full Pincode");
            Toast.makeText(this, "Please Enter Full Pincode", Toast.LENGTH_SHORT).show();

            return false;
        }
        if (etState.getText().toString().isEmpty()) {
            etState.setError("Please Enter City");
            Toast.makeText(this, "Please Enter City", Toast.LENGTH_SHORT).show();

            return false;
        }

        return true;

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