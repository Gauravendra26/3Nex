package com.mynexmy.nex.Activitys;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.mynexmy.nex.Home;
import com.mynexmy.nex.Models.Order_model;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Orders_Activity extends AppCompatActivity implements OrderAdapter.ProductPageClick {

    ImageView p_image, btnback;
    TextView p_name, saleprice, p_description,tvMesOrder;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    RecyclerView rvo, rvCancel;
    OrderAdapter orderAdapter;
    SwipeRefreshLayout refreshLayout;
    List<Order_model> order_models;


int check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        Utils.blackIconStatusBar(Orders_Activity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        init();
        Intent intent=getIntent();
        check = intent.getIntExtra("check",check);
//        Toast.makeText(this, ""+check, Toast.LENGTH_SHORT).show();
        FetchOrder();

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (check==1){
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                    finish();
                } else {
                    finish();

                }
            }
        });

        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {


                        FetchOrder();


                        refreshLayout.setRefreshing(false);
                    }
                }
        );
    }

    private void FetchOrder() {

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.new_progresslogo);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                ApiData.Fetchorder, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressDialog.dismiss();
                            if (response.getBoolean("status") == true) {
                            order_models = new ArrayList<>();

                            JSONArray jsonArray = response.getJSONArray("message");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject user = jsonArray.getJSONObject(i);
                                int order_id = user.optInt("order_id");
                                int customer_id = user.optInt("customer_id");
                                double total_amount = user.getDouble("total_amount");
                                int status = user.optInt("status");
                                String order_number = user.optString("order_number");
                                String shipping_address = user.optString("shipping_address");
                                String payment_method = user.optString("payment_method");
                                String transaction_details = user.optString("transaction_details");
                                String order_tracking_id = user.optString("updated_at");
                                String created_at = user.optString("created_at");
                                String updated_at = user.optString("updated_at");

                                    order_models.add(new Order_model(order_id, customer_id, total_amount, status, order_number, shipping_address,
                                            payment_method,transaction_details, order_tracking_id, created_at, updated_at));

                            }
                            orderAdapter = new OrderAdapter(getApplicationContext(),
                                    order_models);
                            LinearLayoutManager layoutManager
                                    = new LinearLayoutManager(getApplicationContext(),
                                    LinearLayoutManager.VERTICAL, false);
                            layoutManager.setReverseLayout(true);
                            layoutManager.setStackFromEnd(true);
                            rvo.setLayoutManager(layoutManager);
                            rvo.setItemAnimator(new DefaultItemAnimator());
                            rvo.setAdapter(orderAdapter);
                            orderAdapter.set(Orders_Activity.this);
                            }else {
                                tvMesOrder.setVisibility(View.VISIBLE);
                                refreshLayout.setVisibility(View.GONE);
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
    private void FetchOrderRefresh() {



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                ApiData.Fetchorder, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            order_models = new ArrayList<>();

                            JSONArray jsonArray = response.getJSONArray("message");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject user = jsonArray.getJSONObject(i);
                                int order_id = user.optInt("order_id");
                                int customer_id = user.optInt("customer_id");
                                double total_amount = user.getDouble("total_amount");
                                int status = user.optInt("status");
                                String order_number = user.optString("order_number");
                                String shipping_address = user.optString("shipping_address");
                                String payment_method = user.optString("payment_method");
                                String transaction_details = user.optString("transaction_details");
                                String order_tracking_id = user.optString("updated_at");
                                String created_at = user.optString("created_at");
                                String updated_at = user.optString("updated_at");

                                    order_models.add(new Order_model(order_id, customer_id, total_amount, status, order_number, shipping_address,
                                            payment_method,transaction_details, order_tracking_id, created_at, updated_at));

                            }
                            orderAdapter = new OrderAdapter(getApplicationContext(),
                                    order_models);
                            LinearLayoutManager layoutManager
                                    = new LinearLayoutManager(getApplicationContext(),
                                    LinearLayoutManager.VERTICAL, false);
                            layoutManager.setReverseLayout(true);
                            layoutManager.setStackFromEnd(true);
                            rvo.setLayoutManager(layoutManager);
                            rvo.setItemAnimator(new DefaultItemAnimator());
                            rvo.setAdapter(orderAdapter);
                            orderAdapter.set(Orders_Activity.this);


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



    void init() {
        tvMesOrder = findViewById(R.id.tvMesOrder);
        p_description = findViewById(R.id.p_description);
        p_image = findViewById(R.id.p_image);
        p_name = findViewById(R.id.p_name);
        saleprice = findViewById(R.id.saleprice);
        rvo = findViewById(R.id.rvo);
        refreshLayout = findViewById(R.id.refreshLayout);

        btnback = findViewById(R.id.btnback);
    }

    @Override
    public void press(int position, int order_id, double total_amount,int status, String order_number, String created_at) {
        Intent intent = new Intent(getApplicationContext(), OrderDetails_Activity.class);

        intent.putExtra("order_id", order_id);
        intent.putExtra("total_amount", total_amount);
        intent.putExtra("status", status);
        intent.putExtra("order_number", order_number);
        intent.putExtra("created_at", created_at);
        startActivity(intent);

    }

    @Override
    public void Track(int position, int order_id, double total_amount, int status, String order_number, String created_at) {
        Intent intent = new Intent(getApplicationContext(), OrderDetails_Activity.class);

        intent.putExtra("order_id", order_id);
        intent.putExtra("total_amount", total_amount);
        intent.putExtra("status", status);
        intent.putExtra("order_number", order_number);
        intent.putExtra("created_at", created_at);
        startActivity(intent);

    }

    @Override
    public void onResume() {
        super.onResume();
        FetchOrderRefresh();
    }
}