package com.mynexmy.nex.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

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
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.anupkumarpanwar.scratchview.ScratchView;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.Models.Scratchcard_Model;
import com.mynexmy.nex.Models.Scratchcard_Model1;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Signup;
import com.mynexmy.nex.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Scratch1_Activity extends AppCompatActivity {
    CardView card_scratch;
    ImageView imgScratch, imgCongrats;
    LottieAnimationView aniCongrats;
    Dialog dialog;
    TextView tvshow, tvhide;
    ProgressDialog progressDialog;
    RelativeLayout rlsave1, rlsave, rlcard;
    TextView tvT, tvScratch;
    ScratchView scratchview;
    int scratch_card_payment_id, scratchIndicator,amount;

    Animation topanimantion, centeroutanimantion;
    String bucket_product_title, bucket_product_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch1);
        Utils.blackIconStatusBar(Scratch1_Activity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        init();

        Intent i = getIntent();
        scratch_card_payment_id = i.getIntExtra("scratch_card_payment_id", 0);
        scratchIndicator = i.getIntExtra("scratchIndicator", 0);
        amount = i.getIntExtra("amount", 0);
        bucket_product_title = i.getStringExtra("bucket_product_title");
        bucket_product_image = i.getStringExtra("bucket_product_image");

        Log.d("ScratchCardData", "scratch_card_payment_id: " + scratch_card_payment_id);
        Log.d("ScratchCardData", "scratchIndicator: " + scratchIndicator);
        Log.d("ScratchCardData", "bucket_product_title: " + bucket_product_title);
        Log.d("ScratchCardData", "bucket_product_image: " + bucket_product_image);

        tvScratch.setText(bucket_product_title);
        Glide.with(getApplicationContext()).load(bucket_product_image).into(imgScratch);

        topanimantion = AnimationUtils.loadAnimation(this, R.anim.topanimantion);
        centeroutanimantion = AnimationUtils.loadAnimation(this, R.anim.center_out);

        card_scratch.setAnimation(centeroutanimantion);
        tvshow.setAnimation(centeroutanimantion);

        scratchview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialog = new Dialog(this);

        scratchview.setRevealListener(new ScratchView.IRevealListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onRevealed(ScratchView scratchView) {
                // Handle the completion of scratching here

                rlsave.setBackgroundColor(getResources().getColor(R.color.buy_now));
                tvT.setTextColor(getResources().getColor(R.color.white));
                aniCongrats.setVisibility(View.VISIBLE);
                tvhide.setVisibility(View.VISIBLE);
                tvshow.setVisibility(View.INVISIBLE);
                rlsave1.setVisibility(View.VISIBLE);
                rlsave.setVisibility(View.INVISIBLE);

                markscratched(scratch_card_payment_id);
            }

            @Override
            public void onRevealPercentChangedListener(ScratchView scratchView, float percent) {
                Log.d("Revealed", String.valueOf(percent));

                // Check if the percentage reached 30% and automatically reveal
                if (percent >= 50) {
                    scratchView.reveal();
                }
            }
        });


        rlsave1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (scratchIndicator == 0) {
                    if (amount == 99){
                        showCustomAlertDialog(scratch_card_payment_id);
                    } else {
                        Intent i = new Intent(getApplicationContext(), SelectAddressPage.class);
                        i.putExtra("scratch_card_payment_id", scratch_card_payment_id);
                        i.putExtra("scratchIndicator", 2);
                        startActivity(i);
                    }


                } else if (scratchIndicator == 1) {
                    // If scratchIndicator is 1, go to Scratch_Delivery_Activity
                    Intent homeIntent = new Intent(getApplicationContext(), Scratch_Delivery_Activity.class);
                    homeIntent.putExtra("scratchIndicator", scratchIndicator);
                    homeIntent.putExtra("scratch_card_payment_id", scratch_card_payment_id);
                    startActivity(homeIntent);
                    finish();
                } else if (scratchIndicator == 2) {
                    // If scratchIndicator is 2, go to SelectAddressPage
                    Intent i = new Intent(getApplicationContext(), SelectAddressPage.class);
                    i.putExtra("scratch_card_payment_id", scratch_card_payment_id);
                    i.putExtra("scratchIndicator", scratchIndicator);
                    startActivity(i);
                    finish();
                }

                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                rlsave1.startAnimation(myAnim);
            }
        });


    }


    void init() {

        tvshow = findViewById(R.id.tvshow);
        scratchview = findViewById(R.id.scratchview);
        tvhide = findViewById(R.id.tvhide);
        rlsave = findViewById(R.id.rlsave);
        scratchview = findViewById(R.id.scratchview);
        rlsave1 = findViewById(R.id.rlsave1);
        tvT = findViewById(R.id.tvT);
        imgScratch = findViewById(R.id.imgScratch);
        tvScratch = findViewById(R.id.tvScratch);
        card_scratch = findViewById(R.id.card_scratch);
        rlcard = findViewById(R.id.rlcard);
        aniCongrats = findViewById(R.id.aniCongrats);

    }


    void markscratched(int scratch_card_payment_id) {

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("scratch_card_payment_id", scratch_card_payment_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
// Create a new request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                ApiData.Scratchcards_markscratched, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.getBoolean("status") == true) {

                                Toast.makeText(Scratch1_Activity.this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(Scratch1_Activity.this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();

                            }


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
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
    private void showCustomAlertDialog(int scratch_card_payment_id) {
        // Ensure the activity is not finishing
        if (isFinishing()) {
            return;
        }

        // Inflate the custom layout for the dialog
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.scratchcard_indicator_custom_layout, null);

        // Find the MaterialCardView elements from the inflated layout
        MaterialCardView mcvOnlineScratch = dialogView.findViewById(R.id.mcvOnlineScratch);
        MaterialCardView mcvStallScratch = dialogView.findViewById(R.id.mcvStallScratch);

        // Use the activity context instead of application context
        AlertDialog.Builder builder = new AlertDialog.Builder(Scratch1_Activity.this);
        builder.setView(dialogView);
        builder.setCancelable(false);

        // Create the alert dialog
        final AlertDialog alertDialog = builder.create();

        // Set up the click listener for mcvOnlineScratch
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

        // Set up the click listener for mcvStallScratch
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

        // Show the dialog only if the activity is not finishing or destroyed
        if (!isFinishing()) {
            alertDialog.show();
        }
    }

}