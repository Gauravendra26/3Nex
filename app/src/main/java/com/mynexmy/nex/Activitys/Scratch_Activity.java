package com.mynexmy.nex.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
import com.anupkumarpanwar.scratchview.ScratchView;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.mynexmy.nex.Adapters.Scratchcard_Adapter;
import com.mynexmy.nex.Adapters.Scratchcard_Adapter1;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.Home;
import com.mynexmy.nex.Models.Scratchcard_Model;
import com.mynexmy.nex.Models.Scratchcard_Model1;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scratch_Activity extends AppCompatActivity implements Scratchcard_Adapter.ProductPageClick,
        Scratchcard_Adapter1.ProductPageClick {
    TextView tvshow, tvhide, tvScratchText;
    CardView card_scratch;
    RecyclerView rvScratchCard, rvScratched;
    Dialog dialog;
    Scratchcard_Adapter scratchcard_adapter;
    Scratchcard_Adapter1 scratchcard_adapter1;
    List<Scratchcard_Model> scratchcard_models;
    List<Scratchcard_Model1> scratchcard_models1;
    ProgressDialog progressDialog;
    ScratchView scratchView;
    SwipeRefreshLayout refreshLayout;
    String bucket_product_title, bucket_product_image, awb, tracking_data;
    RelativeLayout rlLower, rlScratchText;
    int scratch_card_payment_id, is_scratched, is_scratched1;
    int check;
    ImageView btn_back;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch);
        Utils.blackIconStatusBar(Scratch_Activity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        init();
        Intent intent = getIntent();
        check = intent.getIntExtra("check", 0);
          myscratchcardsOnresume();

        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        myscratchcardsOnresume();
                        refreshLayout.setRefreshing(false);
                    }
                }
        );


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (check == 1) {
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                    finish();
                } else {
                    finish();

                }
                SharedPreferences sharedPreferencesnew = getSharedPreferences("scratchIndicatorCheck",
                        MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferencesnew.edit();
                myEdit.clear();
                myEdit.apply();
            }
        });
    }

    void init() {

        tvScratchText = findViewById(R.id.tvScratchText);
        tvshow = findViewById(R.id.tvshow);
        tvhide = findViewById(R.id.tvhide);
        card_scratch = findViewById(R.id.card_scratch);
        rlLower = findViewById(R.id.rlLower);
        rlScratchText = findViewById(R.id.rlScratchText);

        rvScratchCard = findViewById(R.id.rvScratchCard);
        rvScratched = findViewById(R.id.rvScratched);
        btn_back = findViewById(R.id.btn_back);
        refreshLayout = findViewById(R.id.refreshLayout);
    }

    @Override
    public void onBackPressed() {
        // If check equals 1, start the Home activity and finish the current one
        Intent intent = new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
        finish();
        SharedPreferences sharedPreferencesnew = getSharedPreferences("scratchIndicatorCheck",
                MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferencesnew.edit();
        myEdit.clear();
        myEdit.apply();
        // Call the super method to ensure default back press behavior is retained
        super.onBackPressed();
    }



    void myscratchcards() {
        Log.d("myscratchcards", "Request started");

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.new_progresslogo);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Create a new request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                ApiData.Scratchcards_myscratchcards, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("myscratchcards", "Response received: " + response.toString());

                            progressDialog.dismiss();

                            scratchcard_models = new ArrayList<Scratchcard_Model>();
                            scratchcard_models1 = new ArrayList<Scratchcard_Model1>();

                            JSONArray jsonArray = response.getJSONArray("data");
                            Log.d("myscratchcards", "Data array size: " + jsonArray.length());

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject user = jsonArray.getJSONObject(i);

                                scratch_card_payment_id = user.optInt("scratch_card_payment_id");
                                int scratch_card_id = user.optInt("scratch_card_id");
                                int customer_id = user.optInt("customer_id");
                                int amount = user.optInt("amount");
                                int bucket_id = user.optInt("bucket_id");
                                int level = user.optInt("level");
                                int bucket_product_id = user.optInt("bucket_product_id");
                                int bucket_product_status = user.optInt("bucket_product_status");
                                int added_by = user.optInt("added_by");
                                int ref_count_one = user.optInt("ref_count_one");
                                int ref_count_two = user.optInt("ref_count_two");
                                int ref_count_three = user.optInt("ref_count_three");
                                int ref_count_four = user.optInt("ref_count_four");
                                String transaction_id = user.optString("transaction_id");
                                String payment_mode = user.optString("payment_mode");
                                String shipping_address = user.optString("shipping_address");
                                String shipment_tracker = user.optString("shipment_tracker");
                                String created_at = user.optString("created_at");
                                String updated_at = user.optString("updated_at");
                                bucket_product_title = user.optString("bucket_product_title");
                                String bucket_product_description = user.optString("bucket_product_description");
                                bucket_product_image = user.optString("bucket_product_image");
                                awb = user.optString("awb");
                                tracking_data = user.optString("tracking_data");
                                int is_scratched = user.optInt("is_scratched");

                                Log.d("myscratchcards", "Processing scratch card: " + scratch_card_payment_id);

                                if (is_scratched == 0) {
                                    scratchcard_models.add(new Scratchcard_Model(scratch_card_payment_id,
                                            scratch_card_id, customer_id, amount, bucket_id, level,
                                            is_scratched, bucket_product_id, bucket_product_status, added_by,
                                            ref_count_one, ref_count_two, ref_count_three, ref_count_four,
                                            shipping_address, shipment_tracker, created_at, updated_at,
                                            bucket_product_title, bucket_product_description, bucket_product_image,
                                            awb, tracking_data));
                                } else {
                                    scratchcard_models1.add(new Scratchcard_Model1(scratch_card_payment_id,
                                            scratch_card_id, customer_id, amount, bucket_id, level,
                                            is_scratched, bucket_product_id, bucket_product_status, added_by,
                                            ref_count_one, ref_count_two, ref_count_three, ref_count_four,
                                            shipping_address, shipment_tracker, created_at, updated_at,
                                            bucket_product_title, bucket_product_description, bucket_product_image,
                                            awb, tracking_data));
                                }
                            }

                            Scratched();
                        } catch (JSONException e) {
                            Log.e("myscratchcards", "JSON parsing error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("myscratchcards", "Request error: " + error.getMessage());
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
                Log.d("myscratchcards", "Authorization Header: " + headers.get("Authorization"));
                return headers;
            }
        };

        // Add the request to the Volley request queue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    void myscratchcardsOnresume() {
        Log.d("myscratchcardsOnresume", "Request started");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                ApiData.Scratchcards_myscratchcards, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("myscratchcardsOnresume", "Response received: " + response.toString());

                            scratchcard_models = new ArrayList<Scratchcard_Model>();
                            scratchcard_models1 = new ArrayList<Scratchcard_Model1>();

                            JSONArray jsonArray = response.getJSONArray("data");
                            Log.d("myscratchcardsOnresume", "Data array size: " + jsonArray.length());

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject user = jsonArray.getJSONObject(i);

                                scratch_card_payment_id = user.optInt("scratch_card_payment_id");
                                int scratch_card_id = user.optInt("scratch_card_id");
                                int customer_id = user.optInt("customer_id");
                                int amount = user.optInt("amount");
                                int bucket_id = user.optInt("bucket_id");
                                int level = user.optInt("level");
                                int bucket_product_id = user.optInt("bucket_product_id");
                                int bucket_product_status = user.optInt("bucket_product_status");
                                int added_by = user.optInt("added_by");
                                int ref_count_one = user.optInt("ref_count_one");
                                int ref_count_two = user.optInt("ref_count_two");
                                int ref_count_three = user.optInt("ref_count_three");
                                int ref_count_four = user.optInt("ref_count_four");
                                String transaction_id = user.optString("transaction_id");
                                String payment_mode = user.optString("payment_mode");
                                String shipping_address = user.optString("shipping_address");
                                String shipment_tracker = user.optString("shipment_tracker");
                                String created_at = user.optString("created_at");
                                String updated_at = user.optString("updated_at");
                                bucket_product_title = user.optString("bucket_product_title");
                                String bucket_product_description = user.optString("bucket_product_description");
                                bucket_product_image = user.optString("bucket_product_image");
                                awb = user.optString("awb");
                                tracking_data = user.optString("tracking_data");
                                int is_scratched = user.optInt("is_scratched");

                                Log.d("myscratchcardsOnresume", "Processing scratch card: " + scratch_card_payment_id);

                                if (is_scratched == 0) {
                                    scratchcard_models.add(new Scratchcard_Model(scratch_card_payment_id,
                                            scratch_card_id, customer_id, amount, bucket_id, level,
                                            is_scratched, bucket_product_id, bucket_product_status, added_by,
                                            ref_count_one, ref_count_two, ref_count_three, ref_count_four,
                                            shipping_address, shipment_tracker, created_at, updated_at,
                                            bucket_product_title, bucket_product_description, bucket_product_image,
                                            awb, tracking_data));
                                } else {
                                    scratchcard_models1.add(new Scratchcard_Model1(scratch_card_payment_id,
                                            scratch_card_id, customer_id, amount, bucket_id, level,
                                            is_scratched, bucket_product_id, bucket_product_status, added_by,
                                            ref_count_one, ref_count_two, ref_count_three, ref_count_four,
                                            shipping_address, shipment_tracker, created_at, updated_at,
                                            bucket_product_title, bucket_product_description, bucket_product_image,
                                            awb, tracking_data));
                                }
                            }

                            Scratched();
                        } catch (JSONException e) {
                            Log.e("myscratchcardsOnresume", "JSON parsing error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("myscratchcardsOnresume", "Request error: " + error.getMessage());
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
                Log.d("myscratchcardsOnresume", "Authorization Header: " + headers.get("Authorization"));
                return headers;
            }
        };

        // Add the request to the Volley request queue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }


    public void Scratched() {
        if (scratchcard_models1.size() == 0 && scratchcard_models.size() == 0) {
            tvScratchText.setVisibility(View.VISIBLE);
        }
        if (scratchcard_models1.size() == 0) {
            rlScratchText.setVisibility(View.GONE);
        } else if (scratchcard_models1.size() != 0) {
            rlScratchText.setVisibility(View.VISIBLE);
        }

        scratchcard_adapter = new Scratchcard_Adapter(getApplicationContext(), scratchcard_models);
        GridLayoutManager layoutManagerc11 = new GridLayoutManager(getApplicationContext(),
                2);
        rvScratchCard.setLayoutManager(layoutManagerc11);
        rvScratchCard.setItemAnimator(new DefaultItemAnimator());
        rvScratchCard.setNestedScrollingEnabled(false);
        rvScratchCard.setAdapter(scratchcard_adapter);
        scratchcard_adapter.notifyDataSetChanged();
        scratchcard_adapter.set(Scratch_Activity.this);

        scratchcard_adapter1 = new Scratchcard_Adapter1(getApplicationContext(), scratchcard_models1);

        GridLayoutManager layoutManagerC = new GridLayoutManager(getApplicationContext(),
                2);
        rvScratched.setLayoutManager(layoutManagerC);
        rvScratched.setItemAnimator(new DefaultItemAnimator());
        rvScratched.setNestedScrollingEnabled(false);
        rvScratched.setAdapter(scratchcard_adapter1);
        scratchcard_adapter1.notifyDataSetChanged();
        scratchcard_adapter1.set(Scratch_Activity.this);


    }


    @Override
    public void productClick1(int position, int scratch_card_payment_id, int scratch_card_id, int customer_id,
                              int amount,
                              int bucket_id, int level, int is_scratched, int bucket_product_id,
                              int bucket_product_status, int added_by, int ref_count_one, int ref_count_two,
                              int ref_count_three, int ref_count_four, String shipping_address,
                              String shipment_tracker, String created_at, String updated_at,
                              String bucket_product_title, String bucket_product_description,
                              String bucket_product_image) {
        SharedPreferences sharedPreferences = getSharedPreferences("scratchIndicatorCheck", MODE_PRIVATE);
       int scratchIndicator = sharedPreferences.getInt("scratchIndicator", 0);
        Log.d("dataofscratchcheck", "" + scratchIndicator);

//        showPopup(bucket_product_title,bucket_product_image);
        if (ref_count_one == 1 && ref_count_two == 1 && ref_count_three == 1 && ref_count_four == 1) {
            // All conditions are met, go to the scratch page
            Intent scratchPageIntent = new Intent(this, Scratch1_Activity.class);
            scratchPageIntent.putExtra("scratch_card_payment_id", scratch_card_payment_id);
            scratchPageIntent.putExtra("bucket_product_title", bucket_product_title);
            scratchPageIntent.putExtra("bucket_product_image", bucket_product_image);
            scratchPageIntent.putExtra("scratchIndicator", scratchIndicator);
            scratchPageIntent.putExtra("amount", amount);
            startActivity(scratchPageIntent);

        } else {
            // At least one condition is not met, go to the referral page
            Toast.makeText(this, "Please share the links, and once that’s done, the scratch card will open",
                    Toast.LENGTH_LONG).show();
            Intent referralPageIntent = new Intent(this, Otp_sharelink_Activity.class);
            referralPageIntent.putExtra("scratch_card_payment_id", scratch_card_payment_id);
            referralPageIntent.putExtra("scratch_card_price", amount);
            startActivity(referralPageIntent);
        }

    }

    @Override
    public void productClick2(int position, int scratch_card_payment_id, int scratch_card_id, int customer_id,
                              int amount, int bucket_id, int level, int is_scratched, int bucket_product_id,
                              int bucket_product_status, int added_by, String shipping_address,
                              String shipment_tracker, String created_at, String updated_at,
                              String bucket_product_title, String bucket_product_description,
                              String bucket_product_image, String awb, String tracking_data) {

        // Check if AWB is null or empty, or if shipping_address is "STALL"
        if ( "STALL".equals(shipping_address) && ( awb.equals("null") )) {
            Toast.makeText(this, "ScratchCard Purchased from stall", Toast.LENGTH_SHORT).show();
        }else if (!shipping_address.equals("STALL") && (awb.equals("null") || awb.isEmpty())) {
            // Log the scratch_card_payment_id when AWB is null or empty or shipping_address is "STALL"
            Log.d("AWBCheck", "AWB is null or empty or shipping_address is STALL. Scratch Card Payment ID: " + scratch_card_payment_id);
            // Show the custom alert dialog with the payment ID
            if (amount == 99){
                showCustomAlertDialog(scratch_card_payment_id);
            } else {
                Intent i = new Intent(getApplicationContext(), SelectAddressPage.class);
                i.putExtra("scratch_card_payment_id", scratch_card_payment_id);
                i.putExtra("scratchIndicator", 2);
                startActivity(i);
            }


        } else {
            // If AWB is available and shipping_address is not "STALL", navigate to TrackOrder_Activity
            Intent scratchPageIntent = new Intent(this, TrackOrder_Activity.class);
            scratchPageIntent.putExtra("awb", awb);
            startActivity(scratchPageIntent);
        }
    }


    private void showCustomAlertDialog(int scratch_card_payment_id) {
        // Ensure the activity is not finishing
        if (isFinishing()) {
            return;
        }
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.scratchcard_indicator_custom_layout, null);

        MaterialCardView mcvOnlineScratch = dialogView.findViewById(R.id.mcvOnlineScratch);
        MaterialCardView mcvStallScratch = dialogView.findViewById(R.id.mcvStallScratch);

         AlertDialog.Builder builder = new AlertDialog.Builder(Scratch_Activity.this);
        builder.setView(dialogView);
        builder.setCancelable(false);

         final AlertDialog alertDialog = builder.create();

         mcvOnlineScratch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start SelectAddressPage activity
                Intent i = new Intent(getApplicationContext(), SelectAddressPage.class);
                i.putExtra("scratch_card_payment_id", scratch_card_payment_id);
                i.putExtra("scratchIndicator", 2);
                startActivity(i);

                // Use a handler to ensure finish() is called after the dialog is dismissed
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 200);

                alertDialog.dismiss();
            }
        });

         mcvStallScratch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start Scratch_Delivery_Activity
                Intent homeIntent = new Intent(getApplicationContext(), Scratch_Delivery_Activity.class);
                homeIntent.putExtra("scratchIndicator", 1);
                homeIntent.putExtra("scratch_card_payment_id", scratch_card_payment_id);
                startActivity(homeIntent);

                // Use a handler to ensure finish() is called after the dialog is dismissed
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 200);

                alertDialog.dismiss();
            }
        });

         if (!isFinishing()) {
            alertDialog.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        myscratchcards();
    }

}