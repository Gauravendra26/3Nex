package com.mynexmy.nex.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.card.MaterialCardView;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.Models.Scratchcard_Model;
import com.mynexmy.nex.Models.Scratchcard_Model1;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Otp_sharelink_Activity extends AppCompatActivity {
    RelativeLayout rlshare, rlshare1, rlshare2, rlshare3, rlshare4, rlshare5,rlBottomfor99,rlBottomOthers;
    MaterialCardView mcvOnline,   mcvOnlineDone;
    MaterialCardView mcvOnline99, mcvStall99, mcvOnlineDone99, mcvStallDone99;
    LinearLayout llLinkshareNotDone, llLinkshareDone;
    LinearLayout llLinkshareNotDone99, llLinkshareDone99;
    TextView tvshare,tvReferNumber, tvT, tvrlshare, tvrlshare1, tvrlshare2, tvrlshare3,tvSharingLinkCount;
    ImageView btn_back;
    ProgressDialog progressDialog;
    private int sharedLayoutCount = 0;
    private int currentLayoutIndex = 0, scratchIndicator; // Start with rlshare3
    private RelativeLayout[] layouts;
    private RelativeLayout[] layoutsforHigherPrice;
    private static final int REQUEST_SHARE = 123;
    int scratch_card_payment_id , scratch_card_price;
    private static final int TOTAL_SHARES_NEEDED = 1;
    private HashMap<Integer, Integer> shareCounts = new HashMap<>();
    int check = 1, checkToastcount = 1;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_sharelink);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Utils.blackIconStatusBar(Otp_sharelink_Activity.this, R.color.white);

        init();

        Intent intent = getIntent();
        scratch_card_payment_id = intent.getIntExtra("scratch_card_payment_id", 0);
        scratch_card_price = intent.getIntExtra("scratch_card_price", 0);

        layouts = new RelativeLayout[]{rlshare, rlshare1, rlshare2, rlshare3, rlshare4, rlshare5};
        layoutsforHigherPrice = new RelativeLayout[]{rlshare, rlshare1};

        // Set up click listeners for each RelativeLayout
        if (scratch_card_price == 99){
            rlBottomfor99 .setVisibility(View.VISIBLE);
            rlBottomOthers .setVisibility(View.GONE);
        } else {
            rlBottomfor99 .setVisibility(View.GONE);
            rlBottomOthers .setVisibility(View.VISIBLE);
        }

        if (scratch_card_price == 99 || scratch_card_price == 199) {
            for (int i = 0; i < layouts.length; i++) {
                setupShareButton(layouts[i], i);
            }
            tvReferNumber.setText("By referring 6 different contacts you will get scratch card.");
            tvSharingLinkCount.setText("Once you complete 6 links.");
//            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
        } else if (scratch_card_price == 299 || scratch_card_price == 399 || scratch_card_price == 499) {
            for (int i = 0; i < layoutsforHigherPrice.length; i++) {
                setupShareButton(layoutsforHigherPrice[i], i);
            }
            tvReferNumber.setText("By referring 2 different contacts you will get scratch card.");
            tvSharingLinkCount.setText("Once you complete 2 links.");
            rlshare2.setVisibility(View.GONE);
            rlshare3.setVisibility(View.GONE);
            rlshare4.setVisibility(View.GONE);
            rlshare5.setVisibility(View.GONE);
//            Toast.makeText(this, "Fine", Toast.LENGTH_SHORT).show();
        }

//        rlClaim1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                updatemycard1();
//
//                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
//                rlClaim1.startAnimation(myAnim);
//
//            }
//        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mcvOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Otp_sharelink_Activity.this,
                        "First Share the Link, Then you will go to the next page",
                        Toast.LENGTH_LONG).show();
            }
        });
        mcvOnline99.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Otp_sharelink_Activity.this,
                        "First Share the Link, Then you will go to the next page",
                        Toast.LENGTH_LONG).show();
            }
        });

        mcvStall99.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Otp_sharelink_Activity.this,
                        "First Share the Link, Then you will go to the next page",
                        Toast.LENGTH_LONG).show();
            }
        });
        mcvOnlineDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatemycard1();
                Intent intent = new Intent(getApplicationContext(), Scratch_Activity.class);
                SharedPreferences sharedPreferences = getSharedPreferences("scratchIndicatorCheck", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putInt("scratchIndicator", 2);  // Save the value
                myEdit.apply(); // Save changes to shared preferences

                startActivity(intent);
                finish();
            }
        });
        mcvOnlineDone99.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatemycard1();
                Intent intent = new Intent(getApplicationContext(), Scratch_Activity.class);
                SharedPreferences sharedPreferences = getSharedPreferences("scratchIndicatorCheck", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putInt("scratchIndicator", 2);  // Save the value
                myEdit.apply(); // Save changes to shared preferences

                startActivity(intent);
                finish();
            }
        });

        mcvStallDone99.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatemycard1();

                // Intent initialization
                Intent intent = new Intent(getApplicationContext(), Scratch_Activity.class);

                // Save the value into SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("scratchIndicatorCheck", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putInt("scratchIndicator", 1);  // Save the value
                myEdit.apply(); // Save changes to shared preferences

                // Start activity
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            // Exit the activity or go to the previous screen
            super.onBackPressed();
            return;
        }

        mBackPressed = System.currentTimeMillis();
    }


    private void init() {


        llLinkshareNotDone = findViewById(R.id.llLinkshareNotDone);
        llLinkshareDone = findViewById(R.id.llLinkshareDone);

        mcvOnline = findViewById(R.id.mcvOnline);

        mcvOnlineDone = findViewById(R.id.mcvOnlineDone);
        llLinkshareNotDone99 = findViewById(R.id.llLinkshareNotDone99);
        llLinkshareDone99 = findViewById(R.id.llLinkshareDone99);
        mcvStall99 = findViewById(R.id.mcvStall99);
        mcvOnline99 = findViewById(R.id.mcvOnline99);
        mcvStallDone99 = findViewById(R.id.mcvStallDone99);
        mcvOnlineDone99 = findViewById(R.id.mcvOnlineDone99);
//        rlClaim1 = findViewById(R.id.rlClaim1);
        rlshare = findViewById(R.id.rlshare);
        rlshare1 = findViewById(R.id.rlshare1);
        rlshare2 = findViewById(R.id.rlshare2);
        rlshare3 = findViewById(R.id.rlshare3);
        rlshare4 = findViewById(R.id.rlshare4);
        rlshare5 = findViewById(R.id.rlshare5);
        rlBottomfor99 = findViewById(R.id.rlBottomfor99);
        rlBottomOthers = findViewById(R.id.rlBottomOthers);
        tvshare = findViewById(R.id.tvshare);
        tvReferNumber = findViewById(R.id.tvReferNumber);
        tvT = findViewById(R.id.tvT);
        tvrlshare = findViewById(R.id.tvrlshare);

        tvrlshare1 = findViewById(R.id.tvrlshare1);
        tvrlshare2 = findViewById(R.id.tvrlshare2);
        tvrlshare3 = findViewById(R.id.tvrlshare3);
        tvSharingLinkCount = findViewById(R.id.tvSharingLinkCount);
        btn_back = findViewById(R.id.btn_back);

    }


    private void setupShareButton(final RelativeLayout layout, final int index) {
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the clicked layout is the current one in the sequence
                if (index == currentLayoutIndex) {
                    // Create an Intent to share the link
                    shareLink(index);
                } else {
                    // Notify the user that they need to complete the previous step first
                    Toast.makeText(Otp_sharelink_Activity.this, "Please Share Step by Step", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void shareLink(int index) {
        Intent i = new Intent(Intent.ACTION_SEND);

        i.setType("text/plain");
        String text = "Check out this Application: ";
        i.putExtra(Intent.EXTRA_TEXT, text + "https://play.google.com/store/apps/details?id=com.mynexmy.nex");
        startActivityForResult(Intent.createChooser(i, "Share Via"), REQUEST_SHARE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SHARE) {
            if (resultCode == RESULT_OK) {

                if (shareCounts.containsKey(currentLayoutIndex)) {
                    int currentShareCount = shareCounts.get(currentLayoutIndex);
                    shareCounts.put(currentLayoutIndex, currentShareCount + 1);
                } else {
                    shareCounts.put(currentLayoutIndex, 1);
                }

                int currentShareCount = shareCounts.get(currentLayoutIndex);
                String toastMessage = "Layout " + (currentLayoutIndex + 1) + " shared " + currentShareCount + " times";
//                Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
                if (currentLayoutIndex == 0) {
                    tvrlshare3.setText(String.valueOf(currentShareCount));
                } else if (currentLayoutIndex == 1) {
                    tvrlshare2.setText(String.valueOf(currentShareCount));
                } else if (currentLayoutIndex == 2) {
                    tvrlshare1.setText(String.valueOf(currentShareCount));
                } else if (currentLayoutIndex == 3) {
//                    tvrlshare.setText(String.valueOf(currentShareCount));
                }
                // Check if the current layout has been shared 5 times
                if (shareCounts.get(currentLayoutIndex) >= TOTAL_SHARES_NEEDED) {
                    // Change the background color of the clicked layout to light green

                    if (scratch_card_price == 99 || scratch_card_price == 199) {
                        if (currentLayoutIndex >= 0 && currentLayoutIndex < layouts.length) {
                            RelativeLayout clickedLayout = layouts[currentLayoutIndex];
                            clickedLayout.setBackgroundColor(Color.parseColor("#ff99cc00"));
                            if (checkToastcount == 1) {
                                Toast.makeText(this, "Share link to different number", Toast.LENGTH_SHORT).show();

                                checkToastcount = 2;
                            } else if (checkToastcount == 2) {
                                Toast.makeText(this, "Don’t share the link to same number", Toast.LENGTH_SHORT).show();
                                checkToastcount = 3;
                            } else {
                                Toast.makeText(this, "Don’t share the link to same number", Toast.LENGTH_SHORT).show();
                            }
                        }

                        sharedLayoutCount++;
                        // Check if all layouts have successfully shared
                        if (sharedLayoutCount == layouts.length) {

                            if (scratch_card_price == 99){
                                showCustomAlertDialog();
                                llLinkshareNotDone99.setVisibility(View.GONE);
                                llLinkshareDone99.setVisibility(View.VISIBLE);

                                tvrlshare.setVisibility(View.VISIBLE);
                                tvrlshare.setText("Please Click Below For Type of Delivery");
                            } else {
                                llLinkshareNotDone.setVisibility(View.GONE);
                                llLinkshareDone.setVisibility(View.VISIBLE);
                                tvrlshare.setVisibility(View.VISIBLE);
                                tvrlshare.setText("Please Click Below For Delivery");
                            }


                         }

                        // Proceed to the next step if not all layouts have been processed
                        if (currentLayoutIndex < layouts.length - 1) {
                            currentLayoutIndex++;
                        }


                    } else if (scratch_card_price == 299 || scratch_card_price == 399 || scratch_card_price == 499) {

                        if (currentLayoutIndex >= 0 && currentLayoutIndex < layoutsforHigherPrice.length) {
                            RelativeLayout clickedLayout = layoutsforHigherPrice[currentLayoutIndex];
                            clickedLayout.setBackgroundColor(Color.parseColor("#ff99cc00"));
                            if (checkToastcount == 1) {
                                Toast.makeText(this, "Share link to different number", Toast.LENGTH_SHORT).show();

                                checkToastcount = 2;
                            } else if (checkToastcount == 2) {
                                Toast.makeText(this, "Don’t share the link to same number", Toast.LENGTH_SHORT).show();
                                checkToastcount = 3;
                            } else {
                                Toast.makeText(this, "Don’t share the link to same number", Toast.LENGTH_SHORT).show();
                            }
                        }

                        sharedLayoutCount++;
                        // Check if all layouts have successfully shared
                        if (sharedLayoutCount == layoutsforHigherPrice.length) {
                            if (scratch_card_price == 99){
                                showCustomAlertDialog();
                                llLinkshareNotDone99.setVisibility(View.GONE);
                                llLinkshareDone99.setVisibility(View.VISIBLE);

                                tvrlshare.setVisibility(View.VISIBLE);
                                tvrlshare.setText("Please Click Below For Type of Delivery");
                            } else {
                                llLinkshareNotDone.setVisibility(View.GONE);
                                llLinkshareDone.setVisibility(View.VISIBLE);
                                tvrlshare.setVisibility(View.VISIBLE);
                                tvrlshare.setText("Please Click Below For Delivery");
                            }
                        }

                        // Proceed to the next step if not all layouts have been processed
                        if (currentLayoutIndex < layoutsforHigherPrice.length - 1) {
                            currentLayoutIndex++;
                        }

                    }

                }
            } else {
                // The sharing action was canceled or failed
                Log.d("Share Link", "Link sharing canceled or failed.");
            }
        }
    }

    void updatemycard1() {

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.new_progresslogo);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("scratch_card_payment_id", scratch_card_payment_id);
            requestBody.put("ref_count_one", true);
            requestBody.put("ref_count_two", true);
            requestBody.put("ref_count_three", true);
            requestBody.put("ref_count_four", true);

        } catch (JSONException e) {
            e.printStackTrace();
        }
// Create a new request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                ApiData.Scratchcards_updatemycard, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            if (response.getBoolean("status") == true) {

                                Toast.makeText(Otp_sharelink_Activity.this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), Scratch_Activity.class);
                                intent.putExtra("check", check);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(Otp_sharelink_Activity.this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();

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


    private void showCustomAlertDialog() {
        // Ensure activity is still in a valid state
        if (isFinishing() || isDestroyed()) {
            return;
        }

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.otpsharelink_custom_layout, null);

        RelativeLayout rlCancel = dialogView.findViewById(R.id.rlCancel);

        // Use the current activity context instead of application context
        AlertDialog.Builder builder = new AlertDialog.Builder(Otp_sharelink_Activity.this);
        builder.setView(dialogView);

        // Make the dialog non-cancelable
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);  // Prevent the dialog from being dismissed when clicking outside

        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss(); // Dismiss the dialog on rlCancel click
            }
        });

        alertDialog.show();
    }


}