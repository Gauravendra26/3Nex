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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.mynexmy.nex.MyAccount_Activity2;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class SelectAddressPage extends AppCompatActivity {

    RelativeLayout changeaddress, rlConfirm, changeaddress1;
    RequestQueue requestQueue;
    ImageView btnback;
    ProgressDialog progressDialog;
    TextView tv_messages, mobleno, tvhouseno, tvufirst, tvlastname, tvpincode, tvcity, tvstate,tvHeading,tvOR;

    LinearLayout bottom, llAddress;
    String email, first_name, last_name, mobile, pincode, created_at, updated_at, referral_code,
            city, country, state, postal_code, address_line, address_phone, city1,  state1, postal_code1, address_line1, address_phone1;
    Integer customer_id, is_verified, address_id, is_default;
    String houseno;
    int scratch_card_payment_id,scratchIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectaddresspage);
        Utils.blackIconStatusBar(SelectAddressPage.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        init();
        getDataProfile();
        Intent i = getIntent();
        scratch_card_payment_id = i.getIntExtra("scratch_card_payment_id", 0);
        scratchIndicator = i.getIntExtra("scratchIndicator", 0);

        houseno = tvhouseno.getText().toString().trim();
        //        if (  houseno.isEmpty()) {
//            rlConfirm.setVisibility(View.GONE);
//            llAddress.setVisibility(View.GONE);
//            changeaddress.setVisibility(View.VISIBLE);
//            changeaddress1.setVisibility(View.GONE);
//            Toast.makeText(this, "Please Add Address", Toast.LENGTH_SHORT).show();
//        } else {
//            rlConfirm.setVisibility(View.VISIBLE);
//            llAddress.setVisibility(View.VISIBLE);
//            changeaddress.setVisibility(View.GONE);
//            changeaddress1.setVisibility(View.VISIBLE);
//        }

        changeaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MyAccount_Activity2.class);
                startActivity(i);
                SharedPreferences sharedPreferences = getSharedPreferences("AddressDetailsScratch",
                        MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.clear();
                myEdit.apply();
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                changeaddress.startAnimation(myAnim);
            }
        });

        changeaddress1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MyAccount_Activity2.class);
                startActivity(i);
                SharedPreferences sharedPreferences = getSharedPreferences("AddressDetailsScratch",
                        MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.clear();
                myEdit.apply();
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                changeaddress1.startAnimation(myAnim);
            }
        });
        rlConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                scratchAddress();
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                rlConfirm.startAnimation(myAnim);

            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("AddressDetailsScratch",
                        MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.clear();
                myEdit.apply();
                finish();
            }
        });

    }

    private void init() {

        rlConfirm = findViewById(R.id.rlConfirm);
        btnback = findViewById(R.id.btnback);
        changeaddress = findViewById(R.id.changeaddress);
        changeaddress1 = findViewById(R.id.changeaddress1);
        llAddress = findViewById(R.id.llAddress);
        tv_messages = findViewById(R.id.tv_messages);
        mobleno = findViewById(R.id.mobleno);
        tvhouseno = findViewById(R.id.tvhouseno);
        tvufirst = findViewById(R.id.tvufirst);
        tvlastname = findViewById(R.id.tvlastname);
        tvpincode = findViewById(R.id.tvpincode);
        tvcity = findViewById(R.id.tvcity);
        tvstate = findViewById(R.id.tvstate);
        tvHeading = findViewById(R.id.tvHeading);
        tvOR = findViewById(R.id.tvOR);
    }

    void getDataProfile() {


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, ApiData.Profile,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                try {


                    if (response.getBoolean("status") == true) {
                        JSONObject jsonObject1 = response.getJSONObject("data");
                        JSONObject jsonObject = jsonObject1.getJSONObject("details");
                        JSONArray addre = jsonObject1.getJSONArray("address");
                        for (int i = 0; i < addre.length(); i++) {
                            JSONObject user = addre.getJSONObject(i);


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

                            city = user.optString("city");
                            country = user.optString("country");
                            state = user.optString("state");
                            postal_code = user.optString("postal_code");
                            address_id = user.optInt("address_id");
                            address_line = user.optString("address_line");
                            address_phone = user.optString("address_phone");
                            is_default = user.optInt("is_default");
//                            if (is_default==1){
//                                tvufirst.setText(first_name);
//                                tvlastname.setText(last_name);
//                                tvcity.setText(city);
//                                tvpincode.setText(postal_code);
//                                mobleno.setText(""+address_phone);
//                                tvstate.setText(state);
//                                tvhouseno.setText(address_line);
//                            }


//                        is_default= jsonObject.getInt("is_default");

                        }
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
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(req);
    }

    void getDataProfileONresume() {

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.new_progresslogo);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, ApiData.Profile,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                try {
                    progressDialog.dismiss();

                    if (response.getBoolean("status") == true) {
                        JSONObject data = response.getJSONObject("data");
                        JSONObject details = data.getJSONObject("details");
                        customer_id = details.optInt("customer_id");
                        email = details.optString("email");
                        first_name = details.optString("first_name");
                        last_name = details.optString("last_name");
                        mobile = details.optString("mobile");
                        pincode = details.optString("pincode");
                        is_verified = details.optInt("is_verified");
                        JSONArray addre = data.getJSONArray("address");
                        for (int i = 0; i < addre.length(); i++) {
                            JSONObject user = addre.getJSONObject(i);
                            address_id = user.optInt("address_id");
                            address_line = user.optString("address_line");
                            city = user.optString("city");
                            country = user.optString("country");
                            state = user.optString("state");
                            postal_code = user.optString("postal_code");
                            address_phone = user.optString("address_phone");
                            is_default = user.optInt("is_default");
                            if (is_default == 1) {
                                SharedPreferences sharedPreferences = getSharedPreferences("AddressDetailsScratch", MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.putString("address_phone", address_phone);
                                myEdit.putString("city",city);
                                myEdit.putString("postal_code",postal_code);
                                myEdit.putString("address_line",address_line);
                                myEdit.putString("state",state);
                                myEdit.apply();
                                myEdit.commit();

                                tvufirst.setText(first_name);
                                tvlastname.setText(last_name);
                                tvcity.setText(city);
                                tvpincode.setText(postal_code);
                                mobleno.setText(address_phone);
                                tvstate.setText(state);
                                tvhouseno.setText(address_line);

                                Log.e("asddressLog",address_line+" "+city+" "+state+" "+postal_code+" "+ address_phone);


                                llAddress.setVisibility(View.VISIBLE);
                                tvHeading.setVisibility(View.VISIBLE);
                                tvOR.setVisibility(View.VISIBLE);
                                changeaddress1.setVisibility(View.VISIBLE);
                                changeaddress.setVisibility(View.GONE);
                                rlConfirm.setVisibility(View.VISIBLE);
//                            llAddress.setVisibility(View.GONE);
//                            changeaddress1.setVisibility(View.GONE);
//                            changeaddress.setVisibility(View.VISIBLE);
//                                rlbot.setVisibility(View.GONE);
                            }


//                        is_default= jsonObject.getInt("is_default");

                        }
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
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(req);
    }

    void scratchAddress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.new_progresslogo);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        JSONObject requestBody = new JSONObject();
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("AddressDetailsScratch", MODE_PRIVATE);
            address_phone1 = sharedPreferences.getString("address_phone", "");
                city1 = sharedPreferences.getString("city", "");
            postal_code1 = sharedPreferences.getString("postal_code", "");
            address_line1 = sharedPreferences.getString("address_line", "");
            state1 = sharedPreferences.getString("state", "");

            requestBody.put("scratch_card_payment_id", scratch_card_payment_id);
            requestBody.put("address_line", address_line1);
            requestBody.put("city", city1);
            requestBody.put("state", state1);
            requestBody.put("postal_code", postal_code1);
            requestBody.put("phone", address_phone1);
            Log.e("innerLogAPI",scratch_card_payment_id+" "+address_line1+" "+city1+" "+state1+" "+postal_code1+" "+ address_phone1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("Scratchcheck", "" + requestBody);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                ApiData.Scratchcards_addshippingaddress, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            progressDialog.dismiss();
                            if (response.getBoolean("status") == true) {

                                Toast.makeText(SelectAddressPage.this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPreferences = getSharedPreferences("AddressDetailsScratch",
                                        MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.clear();
                                myEdit.apply();

                                Intent intent = new Intent(getApplicationContext(), Scratch_Delivery_Activity.class);
                                intent.putExtra("scratchIndicator", scratchIndicator);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(SelectAddressPage.this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();

                            }


                        } catch (JSONException e) {

                        }

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
                headers.put("Authorization", "Bearer " + sharedPreferences.getString("Login_Token", ""));
                return headers;
            }
        };

// Add the request to the Volley request queue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataProfileONresume();
    }

}