package com.mynexmy.nex.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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
import com.mynexmy.nex.Adapters.Orderdetail_Adapter;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.Fragments.ProfileFragment;
import com.mynexmy.nex.Models.Orderdetail_Model;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetails_Activity extends AppCompatActivity implements Orderdetail_Adapter.ProductPageClick {
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    Orderdetail_Adapter orderdetail_adapter;
    List<Orderdetail_Model> orderdetail_models;
    RelativeLayout rlCancelOrder, rlCancelOrder1, rlInvoice;
    ImageView btnback;
    TextView tv_myc1, tv_amount1, tv_amount, tv_Discount1, tv_Price1, tv_created1,tv_Status1,tv_Location1,tv_Message1;
    RecyclerView rvOrder;
    int order_id, status;
    double total_amount;
    String order_number, created_at;
    String order_idTrack, order_numberTrack, pickup_date, statusTrack, status_code, location, event_time,
            message, country, state, postal_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Utils.blackIconStatusBar(OrderDetails_Activity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        init();
        Intent intent = getIntent();
        order_id = intent.getIntExtra("order_id", 0);
        status = intent.getIntExtra("status", 0);
        total_amount = intent.getDoubleExtra("total_amount", 0);
        order_number = intent.getStringExtra("order_number");
        created_at = intent.getStringExtra("created_at");

        getDetails();
        getDataTrackOrder();

        tv_myc1.setText(order_number);
        tv_created1.setText(created_at);
        tv_amount1.setText("\u20B9" + total_amount);

        if (status == 1) {
            rlCancelOrder.setVisibility(View.VISIBLE);
            rlCancelOrder1.setVisibility(View.GONE);

        }
        if (status == 2) {
            rlCancelOrder.setVisibility(View.VISIBLE);
            rlCancelOrder1.setVisibility(View.GONE);

        }
        if (status == 3) {

            rlCancelOrder.setVisibility(View.GONE);
            rlCancelOrder1.setVisibility(View.VISIBLE);

        }
        if (status == 4) {
            rlCancelOrder.setVisibility(View.GONE);
            rlCancelOrder1.setVisibility(View.VISIBLE);


        }
        if (status == 5) {
            rlCancelOrder.setVisibility(View.GONE);
            rlCancelOrder1.setVisibility(View.VISIBLE);

        }
        if (status == 6) {
            rlCancelOrder.setVisibility(View.GONE);
            rlCancelOrder1.setVisibility(View.VISIBLE);

        }
        if (status == 7) {
            rlCancelOrder.setVisibility(View.GONE);
            rlCancelOrder1.setVisibility(View.VISIBLE);

        }
        if (status == 8) {
            rlCancelOrder.setVisibility(View.GONE);
            rlCancelOrder1.setVisibility(View.VISIBLE);

        }
        if (status == 9) {
            rlCancelOrder.setVisibility(View.GONE);
            rlCancelOrder1.setVisibility(View.VISIBLE);

        }

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rlCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogOrderCancel();
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                rlCancelOrder.startAnimation(myAnim);
            }
        });
        rlInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Url = "https://3nex.co.in/pdf/" + order_id;
                Context context = v.getContext();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Url));
                context.startActivity(intent);

                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                rlInvoice.startAnimation(myAnim);
            }
        });
    }

    public void AlertDialogOrderCancel() {


        AlertDialog alertDialog = new AlertDialog.Builder(this)
//set icon
                .setIcon(R.drawable.logo_new)
//set title
                .setTitle("Order Cancellation")
//set message
                .setMessage("Do you want to Cancel Order?")

//set positive button
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        CancelOrder();
                        finish();

                    }
                })

//set negative button
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                    }
                })
                .show();
    }

    void init() {
        rvOrder = findViewById(R.id.rvOrder);
        tv_myc1 = findViewById(R.id.tv_myc1);
        btnback = findViewById(R.id.btnback);
        tv_amount1 = findViewById(R.id.tv_amount1);
        tv_amount = findViewById(R.id.tv_amount);
        tv_Price1 = findViewById(R.id.tv_Price1);
        tv_Discount1 = findViewById(R.id.tv_Discount1);
        tv_created1 = findViewById(R.id.tv_created1);
        tv_Status1 = findViewById(R.id.tv_Status1);
        tv_Location1 = findViewById(R.id.tv_Location1);
        tv_Message1 = findViewById(R.id.tv_Message1);
        rlCancelOrder = findViewById(R.id.rlCancelOrder);
        rlCancelOrder1 = findViewById(R.id.rlCancelOrder1);
        rlInvoice = findViewById(R.id.rlInvoice);

    }

    void getDetails() {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.new_progresslogo);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("order_id", order_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

// Create a new request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ApiData.Orderdetail, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressDialog.dismiss();
                            orderdetail_models = new ArrayList<Orderdetail_Model>();

                            JSONArray jsonArray = response.getJSONArray("message");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject user = jsonArray.getJSONObject(i);
                                int order_details_id = user.optInt("order_details_id");
                                int order_id = user.optInt("order_id");
                                int product_id = user.optInt("product_id");
                                int price = user.optInt("price");
                                int qty = user.optInt("qty");
                                int tax = user.optInt("tax");
                                int product_status = user.optInt("product_status");
                                int added_by = user.optInt("added_by");
                                int product_reg_price = user.optInt("product_reg_price");
                                int product_sell_price = user.optInt("product_sell_price");
                                int product_rating = user.optInt("product_rating");
                                int product_rating_total = user.optInt("product_rating_total");
                                int product_featured = user.optInt("product_featured");
                                int product_tax_percent = user.optInt("product_tax_percent");
                                String product_name = user.optString("product_name");
                                String product_title = user.optString("product_title");
                                String product_url = user.optString("product_url");
                                String product_code = user.optString("product_code");
                                String product_description = user.optString("product_description");
                                String category_id = user.optString("category_id");
                                String product_image = ApiData.IMAGE_BASE_URL + user.optString("product_image");
                                String created_at = user.optString("created_at");
                                String updated_at = user.optString("updated_at");

                                orderdetail_models.add(new Orderdetail_Model(order_details_id, order_id, product_id, price, qty, tax, product_status, added_by,
                                        product_reg_price, product_sell_price, product_rating, product_rating_total, product_featured, product_tax_percent,
                                        product_name, product_title, product_url, product_code, product_description, category_id, product_image, created_at, updated_at));

                                double sum = 0, Discount;
                                for (int k = 0; k < orderdetail_models.size(); k++) {
                                    sum = sum + (orderdetail_models.get(k).getProduct_reg_price());

                                }
                                tv_Price1.setText("\u20B9" + sum);

                                Discount = sum - total_amount;
                                tv_Discount1.setText("-\u20B9" + Discount);

                            }
                            orderdetail_adapter = new Orderdetail_Adapter(getApplicationContext(),
                                    orderdetail_models);
                            LinearLayoutManager layoutManager
                                    = new LinearLayoutManager(getApplicationContext(),
                                    LinearLayoutManager.VERTICAL, false);
                            rvOrder.setLayoutManager(layoutManager);
                            rvOrder.setItemAnimator(new DefaultItemAnimator());
                            rvOrder.setAdapter(orderdetail_adapter);
                            orderdetail_adapter.set(OrderDetails_Activity.this);


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
                headers.put("Authorization", "Bearer " + sharedPreferences.getString("Login_Token", ""));
                return headers;
            }
        };

// Add the request to the Volley request queue
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    void getDataTrackOrder() {

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("order_id", order_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, ApiData.Trackorder,
                requestBody, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    progressDialog.dismiss();
                    if (response.getBoolean("status") == true) {
                        JSONObject jsonObject1 = response.getJSONObject("data");
                        JSONArray addre = jsonObject1.getJSONArray("history");
                        for (int i = 0; i < addre.length(); i++) {
                            JSONObject user = addre.getJSONObject(i);
                            order_idTrack = jsonObject1.optString("order_id");
                            order_numberTrack = jsonObject1.optString("order_number");
                            pickup_date = jsonObject1.optString("pickup_date");
                            statusTrack = jsonObject1.optString("status");

                            status_code = user.optString("status_code");
                            location = user.optString("location");
                            event_time = user.optString("event_time");
                            message = user.optString("message");

                            tv_Status1.setText(statusTrack);
                            tv_Location1.setText(location);
                            tv_Message1.setText(message);

                        }
                    } else {

//                        Toast.makeText(OrderDetails_Activity.this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();
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

    private void CancelOrder() {

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.new_progresslogo);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("order_id", order_id);
        } catch (JSONException e) {

        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                ApiData.Cancelorder, jsonObject,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            Toast.makeText(OrderDetails_Activity.this, "" + response.getString("message"),
                                    Toast.LENGTH_SHORT).show();
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

    @Override
    public void productClick(int position, int order_id, int product_id) {
//        Intent intent = new Intent(getApplicationContext(), OrderProductDetailPage.class);
//
//        intent.putExtra("product_id", product_id);
//
//        startActivity(intent);
    }


}