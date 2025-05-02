package com.mynexmy.nex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mynexmy.nex.Activitys.Manage_Account_Activity;
import com.mynexmy.nex.Adapters.Address_Adapter;
import com.mynexmy.nex.Adapters.HomeCategory_Adapter;
import com.mynexmy.nex.Adapters.HomeProductAdapter1;
import com.mynexmy.nex.Fragments.HomeFragment;
import com.mynexmy.nex.Models.Address_model;
import com.mynexmy.nex.Models.CategoryModel;
import com.mynexmy.nex.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAccount_Activity2 extends AppCompatActivity implements Address_Adapter.ProductPageClick {
    TextView tv_messages,mobleno,tvhouseno,tvufirst,tvlastname,tvpincode,tv_message,tvcity,tvstate;
    RelativeLayout changeaddress, placeorder1,changeaddress1,rlbot;
    ImageView btn_back;
    RecyclerView rvAddresses;
    ProgressDialog progressDialog;
    String email,first_name,last_name,created_at,updated_at,referral_code,address_phone,
            city,country,state,mobile,pincode,postal_code, address_line;
    int customer_id, customer_id1,
            is_verified, address_id1,address_id,  is_default;
    LinearLayout bottom ;
    String House ;

    Address_Adapter address_adapter;
    List<Address_model> address_models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account2);
        Utils.blackIconStatusBar(MyAccount_Activity2.this, R.color.white);
        init();
        getDataProfile();


        changeaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Manage_Account_Activity.class);
                startActivity(i);
                finish();
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
                changeaddress.startAnimation(myAnim);
            }
        });

        changeaddress1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Manage_Account_Activity.class);
                startActivity(i);
                finish();
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
                changeaddress1.startAnimation(myAnim);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void init() {
        changeaddress = findViewById(R.id.changeaddress);
        changeaddress1 = findViewById(R.id.changeaddress1);
        rvAddresses = findViewById(R.id.rvAddresses);

        btn_back = findViewById(R.id.btn_back);
        rlbot = findViewById(R.id.rlbot);
        tv_messages = findViewById(R.id.tv_messages);
        bottom = findViewById(R.id.bottom);
        mobleno = findViewById(R.id.mobleno);
        tvhouseno = findViewById(R.id.tvhouseno);
        tvufirst = findViewById(R.id.tvufirst);
        tvlastname = findViewById(R.id.tvlastname);
        tvpincode = findViewById(R.id.tvpincode);
        tv_message = findViewById(R.id.tv_message);
        tvcity = findViewById(R.id.tvcity);
        tvstate = findViewById(R.id.tvstate);

    }

    void setAddressDefault(int customer_id1,int address_id1) {

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.new_progresslogo);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("user_id",customer_id1);
            jsonObject.put("address_id",address_id1);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                ApiData.defaultaddress,
                jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    progressDialog.dismiss();
                    if (response.getBoolean("status") == true) {

                        Toast.makeText(MyAccount_Activity2.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                        getDataProfile();
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

                headers.put("Authorization", "Bearer " + sharedPreferences.getString("Login_Token",""));
                return headers;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(req);
    }

    void getDataProfile() {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.new_progresslogo);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,
                ApiData.Profile,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progressDialog.dismiss();

                    if (response.getBoolean("status") == true) {
                        address_models = new ArrayList<Address_model>();
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
                            address_line= user.optString("address_line");
                            city = user.optString("city");
                            country = user.optString("country");
                            state = user.optString("state");
                            postal_code = user.optString("postal_code");
                            address_phone = user.optString("address_phone");
                            is_default = user.optInt("is_default");

                            Log.e("DataofProfile",address_id+" "+address_line+" "+city+" "+country
                                    +" "+state+" "+postal_code+" "+address_phone+" "+is_default);
                            address_models.add(new Address_model(email, first_name,last_name,created_at,updated_at,
                                    address_line,city,country,state,referral_code,customer_id,mobile,
                                    pincode,is_verified,address_id,postal_code,address_phone,is_default));

                        }

                        address_adapter = new Address_Adapter(getApplicationContext(), address_models);
                        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext(),
                                LinearLayoutManager.VERTICAL, true);
                        layoutManager2.setStackFromEnd(true);
                        rvAddresses.setLayoutManager(layoutManager2);
                        rvAddresses.setItemViewCacheSize(0);
                        rvAddresses.setHasFixedSize(true);
                        rvAddresses.setItemAnimator(new DefaultItemAnimator());
                        rvAddresses.setAdapter(address_adapter);
                        address_adapter.set(MyAccount_Activity2.this);

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
    void getDataProfileOnresume() {


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,
                ApiData.Profile,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                    if (response.getBoolean("status") == true) {
                        address_models = new ArrayList<Address_model>();
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
                            address_line= user.optString("address_line");
                            city = user.optString("city");
                            country = user.optString("country");
                            state = user.optString("state");
                            postal_code = user.optString("postal_code");
                            address_phone = user.optString("address_phone");
                            is_default = user.optInt("is_default");

                            address_models.add(new Address_model(email, first_name,last_name,created_at,updated_at,
                                    address_line,city,country,state,referral_code,customer_id,mobile,
                                    pincode,is_verified,address_id,postal_code,address_phone,is_default));

                        }

                        address_adapter = new Address_Adapter(getApplicationContext(), address_models);
                        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext(),
                                LinearLayoutManager.VERTICAL, true);
                        layoutManager2.setStackFromEnd(true);
                        rvAddresses.setLayoutManager(layoutManager2);
                        rvAddresses.setItemViewCacheSize(0);
                        rvAddresses.setHasFixedSize(true);
                        rvAddresses.setItemAnimator(new DefaultItemAnimator());
                        rvAddresses.setAdapter(address_adapter);
                        address_adapter.set(MyAccount_Activity2.this);

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

    @Override
    public void productClickAddress(int position, int customer_id, int address_id) {

        customer_id1=customer_id;
        address_id1=address_id;
        setAddressDefault(customer_id1,address_id1);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataProfileOnresume();
    }

}