package com.mynexmy.nex.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.Home;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Scratch_Delivery_Activity extends AppCompatActivity {

    RelativeLayout rlContinue;
    TextView tvhide, tvshow;
    Animation leftanimation, rightanimation, bottomanimation;
    ProgressDialog progressDialog;
    String email, first_name, last_name, mobile, pincode, created_at, updated_at, referral_code,
            city, country, state, postal_code, address_line, address_phone, city1, state1, postal_code1, address_line1, address_phone1;
    int customer_id, is_verified, address_id, is_default, scratch_card_payment_id, scratchIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch_delivery);
        Utils.blackIconStatusBar(Scratch_Delivery_Activity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        init();
        Intent i = getIntent();
        scratch_card_payment_id = i.getIntExtra("scratch_card_payment_id", 0);
        scratchIndicator = i.getIntExtra("scratchIndicator", 0);

// Log the values with their names
        Log.d("ScratchCardInfo", "scratch_card_payment_id: " + scratch_card_payment_id);
        Log.d("ScratchCardInfo", "scratchIndicator: " + scratchIndicator);
        if (scratchIndicator == 1) {
            tvshow.setText("You can collect your gift in the stall");
        } else   {
            tvshow.setText("Your Gift will be delivery within 3 to 5 working Days");
        }
        leftanimation = AnimationUtils.loadAnimation(this, R.anim.left_anim);
        rightanimation = AnimationUtils.loadAnimation(this, R.anim.right_anim);
        bottomanimation = AnimationUtils.loadAnimation(this, R.anim.bottomanimation);

        tvhide.setAnimation(leftanimation);
        tvshow.setAnimation(rightanimation);
        rlContinue.setAnimation(bottomanimation);
        getDataProfile();

        rlContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scratchIndicator == 1) {
                    scratchAddress();
                } else if (scratchIndicator == 2) {
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                    finish();
                }
                SharedPreferences sharedPreferencesnew = getSharedPreferences("scratchIndicatorCheck", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferencesnew.edit();
                myEdit.clear();
                myEdit.apply();

                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                rlContinue.startAnimation(myAnim);
                finish();
            }
        });
    }

    void getDataProfile() {
        Log.d("ApiRequest", "Request started to fetch profile data from: " + ApiData.Profile);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, ApiData.Profile, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("ApiResponse", "Response received: " + response.toString());

                try {
                    if (response.getBoolean("status") == true) {
                        Log.d("ApiResponse", "Status: true");

                        JSONObject jsonObject1 = response.getJSONObject("data");
                        JSONObject jsonObject = jsonObject1.getJSONObject("details");
                        JSONArray addre = jsonObject1.getJSONArray("address");

                        Log.d("ApiResponse", "Extracting user details...");

                        customer_id = jsonObject.optInt("customer_id");
                        email = jsonObject.optString("email");
                        first_name = jsonObject.optString("first_name");
                        last_name = jsonObject.optString("last_name");
                        mobile = jsonObject.optString("mobile");
                        pincode = jsonObject.optString("pincode");
                        is_verified = jsonObject.optInt("is_verified");
                        referral_code = jsonObject.optString("referral_code");
                        created_at = jsonObject.optString("created_at");
                        updated_at = jsonObject.optString("updated_at");

                        String logMessage = String.format("Customer Info - customer_id: %d, email: %s, first_name: %s, last_name: %s, mobile: %s, pincode: %s, is_verified: %d, referral_code: %s, created_at: %s, updated_at: %s",
                                customer_id, email, first_name, last_name, mobile, pincode, is_verified, referral_code, created_at, updated_at);

                        Log.d("UserDetails", logMessage);

                        if (addre.length() > 0) {
                            for (int i = 0; i < addre.length(); i++) {
                                JSONObject user = addre.getJSONObject(i);

                                is_default = user.optInt("is_default");
                                city1 = user.optString("city");
                                country = user.optString("country");
                                state = user.optString("state");
                                postal_code = user.optString("postal_code");
                                address_id = user.optInt("address_id");
                                address_line = user.optString("address_line");
                                address_phone = user.optString("address_phone");

                                if (is_default == 1) {
                                    String addressLogMessage = String.format("Address Info - city: %s, country: %s, state: %s, postal_code: %s, address_id: %d, address_line: %s, address_phone: %s, is_default: %d",
                                            city1, country, state, postal_code, address_id, address_line, address_phone, is_default);
                                    Log.d("UserDetails", addressLogMessage);
                                }
                            }
                        } else {
                            Log.d("UserDetails", "No address data available.");
                        }

                    } else {
                        Log.d("ApiResponse", "Status: false - Profile data not found.");
                    }

                } catch (JSONException e) {
                    Log.e("ApiError", "JSONException occurred: " + e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ApiError", "Error: " + error.getMessage());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
                HashMap<String, String> headers = new HashMap<String, String>();
                String token = sharedPreferences.getString("Login_Token", "");
                headers.put("Authorization", "Bearer " + token);
                Log.d("ApiRequest", "Authorization header: " + "Bearer " + token);
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(req);
        Log.d("ApiRequest", "Request added to the queue.");
    }

    void scratchAddress() {
        Log.d("scratchAddress", "Method called");

        progressDialog = new ProgressDialog(this);
        Log.d("scratchAddress", "ProgressDialog initialized");

        progressDialog.show();
        progressDialog.setContentView(R.layout.new_progresslogo);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Log.d("scratchAddress", "ProgressDialog displayed with custom layout");

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("scratch_card_payment_id", scratch_card_payment_id);
            requestBody.put("address_line", "");
            requestBody.put("city", "");
            requestBody.put("state", "");
            requestBody.put("postal_code", pincode);
            requestBody.put("phone", mobile);
            requestBody.put("is_stall", true);

            Log.d("scratchAddress", "Request Body: " + requestBody.toString());
        } catch (JSONException e) {
            Log.e("scratchAddress", "JSON exception: " + e.getMessage());
            e.printStackTrace();
        }

        Log.d("Scratchcheck", "Request Body before sending: " + requestBody.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                ApiData.Scratchcards_addshippingaddress, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("scratchAddress", "API Response received");
                        try {
                            progressDialog.dismiss();
                            Log.d("scratchAddress", "ProgressDialog dismissed");

                            if (response.getBoolean("status")) {
                                Log.d("scratchAddress", "API response status: success");
                                Toast.makeText(Scratch_Delivery_Activity.this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Log.d("scratchAddress", "API response status: failure");
                                Toast.makeText(Scratch_Delivery_Activity.this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Log.e("scratchAddress", "Error parsing response: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("scratchAddress", "Error in API request: " + error.getMessage());
                        // Handle the error - log or show a message to the user
                        Toast.makeText(Scratch_Delivery_Activity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                Map<String, String> headers = new HashMap<>();
                String token = sharedPreferences.getString("Login_Token", "");
                headers.put("Authorization", "Bearer " + token);
                Log.d("scratchAddress", "Authorization header: Bearer " + token);
                return headers;
            }
        };

        Log.d("scratchAddress", "Sending request to API: " + ApiData.Scratchcards_addshippingaddress);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Log.d("scratchAddress", "API request queued with retry policy set.");
    }

    private void init() {
        rlContinue = findViewById(R.id.rlContinue);
        tvhide = findViewById(R.id.tvhide);
        tvshow = findViewById(R.id.tvshow);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Ensure the ProgressDialog is dismissed to avoid window leak
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
