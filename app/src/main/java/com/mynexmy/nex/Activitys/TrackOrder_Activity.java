package com.mynexmy.nex.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mynexmy.nex.Adapters.OrderAdapter;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.Models.Order_model;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrackOrder_Activity extends AppCompatActivity {
    String  order_id, order_number, pickup_date, status, status_code, location, event_time,
            message, awb, state, postal_code;
    Integer customer_id, is_verified, address_id, address_phone, is_default;
    TextView  tv_Status1,tv_Location1,tv_Message1;
    ImageView btn_back1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        Utils.blackIconStatusBar(TrackOrder_Activity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        init();
        Intent i=getIntent();
        awb=i.getStringExtra("awb" );

        getDataTrackOrder();


        btn_back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    void init() {
        btn_back1 = findViewById(R.id.btn_back1);
         tv_Status1 = findViewById(R.id.tv_Status1);
        tv_Location1 = findViewById(R.id.tv_Location1);
        tv_Message1 = findViewById(R.id.tv_Message1);

    }
    void getDataTrackOrder() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.new_progresslogo);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("awb", awb);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, ApiData.Scratchcards_trackorder,
                requestBody, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    progressDialog.dismiss();
                    if (response.getBoolean("status") == true) {
                        JSONObject jsonObject1 = response.getJSONObject("data");
                        order_id = jsonObject1.optString("order_id");
                        order_number = jsonObject1.optString("order_number");
                        pickup_date = jsonObject1.optString("pickup_date");
                        status = jsonObject1.optString("status");
                        tv_Status1.setText(status);

                        JSONArray addre = jsonObject1.getJSONArray("history");
                         if (addre.toString().isEmpty()){
                              tv_Location1.setVisibility(View.GONE);
                             tv_Message1.setVisibility(View.GONE);
                         } else {
                             for (int i = 0; i < addre.length(); i++) {
                                 JSONObject user = addre.getJSONObject(i);

                                 status_code = user.optString("status_code");
                                 location = user.optString("location");
                                 event_time = user.optString("event_time");
                                 message = user.optString("message");

                                 tv_Location1.setText(location);
                                 tv_Message1.setText(message);
                         }

                        }
                    }else {
                        Toast.makeText(TrackOrder_Activity.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
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

                SharedPreferences sharedPreferences = getApplicationContext().
                        getSharedPreferences("MySharedPref", MODE_PRIVATE);
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + sharedPreferences.getString("Login_Token", ""));


                return headers;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(req);
    }



}