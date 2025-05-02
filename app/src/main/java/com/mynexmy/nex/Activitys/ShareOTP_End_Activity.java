package com.mynexmy.nex.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import java.util.HashMap;
import java.util.Map;

public class ShareOTP_End_Activity extends AppCompatActivity {
    RelativeLayout rlClaim1,rlwa,rlwa1;
    ImageView btn_back3;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_otp_end);
        Utils.blackIconStatusBar(ShareOTP_End_Activity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        init();
        rlClaim1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Scratch_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rlwa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referralcode();
            }
        });
        rlwa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referralcode();
            }
        });

    }

    private void init() {

        rlClaim1 = findViewById(R.id.rlClaim1);
        btn_back3 = findViewById(R.id.btn_back3);
        rlwa = findViewById(R.id.rlwa);
        rlwa1 = findViewById(R.id.rlwa1);
    }
    void referralcode() {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.new_progresslogo);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


// Create a new request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, ApiData.Scratchcards_referralcode, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressDialog.dismiss();

                            String reffer=response.getString("referral_code");
                            Toast.makeText(ShareOTP_End_Activity.this, ""+reffer, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {

                        }
//
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error
                        // Display an error message or retry request
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Set the token in the headers
                SharedPreferences sharedPreferences =
                        getSharedPreferences("MySharedPref", MODE_PRIVATE);
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + sharedPreferences.getString("Login_Token",""));
                return headers;
            }
        };

// Add the request to the Volley request queue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

}