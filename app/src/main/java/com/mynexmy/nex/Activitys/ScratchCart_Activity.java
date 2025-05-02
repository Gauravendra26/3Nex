package com.mynexmy.nex.Activitys;

import static com.mynexmy.nex.ApiData.AESrequestsalt;
import static com.mynexmy.nex.ApiData.AEresponsesalt;
import static com.mynexmy.nex.ApiData.HashRequestKey;
import static com.mynexmy.nex.ApiData.HashResponseKey;
import static com.mynexmy.nex.ApiData.ProductID;
import static com.mynexmy.nex.ApiData.TransactionPassword;
import static com.mynexmy.nex.ApiData.User_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
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
import com.atom.atompaynetzsdk.PayActivity;
import com.bumptech.glide.Glide;
import com.mynexmy.nex.Adapters.Scratch_Adapter;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.Fragments.Scratchcard_Fragment;
import com.mynexmy.nex.Home;
import com.mynexmy.nex.MainActivity;
import com.mynexmy.nex.Models.Scratchcard_items_Model;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Room.Product;
import com.mynexmy.nex.Room.ProductDao;
import com.mynexmy.nex.Room.ProductDatabase;
import com.mynexmy.nex.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ScratchCart_Activity extends AppCompatActivity   {

    ProgressDialog progressDialog;
    RelativeLayout placeorder1, rlBuyScratch,rlLayout,rlPlatformLine;
    LinearLayout llPlatformLine;
    TextView  tvScratchcardAmount,tvScratchcardDeliveryAmount,tvScratchcardtotalAmount,tv3Num,tvSharingCount;
    RecyclerView rv4;
    ImageView btn_back2, imgGift;

    int bucket_id, scratch_card_id, scratch_card_price ,scratch_card_priceNew;
    List<Scratchcard_items_Model> scratchcard_items_models;
    String email, first_name, last_name, address_phone, name, is_available;
    int products = 1;
    long randomNo;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch_cart);
        Utils.blackIconStatusBar(ScratchCart_Activity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        init();
        requestQueue = Volley.newRequestQueue(getApplicationContext());


        Intent intent = getIntent();
        if (intent != null) {
            scratch_card_id = intent.getIntExtra("scratch_card_id", 0);
            scratch_card_price = scratch_card_priceNew = intent.getIntExtra("scratch_card_price", 0);
            email = intent.getStringExtra("email");
            address_phone = intent.getStringExtra("address_phone");
            name = intent.getStringExtra("name");

            getDetailsScratchProduct(scratch_card_id);
        }

        if (scratch_card_price==49){
            tvScratchcardAmount.setText("₹ 49 /- ");
            tvScratchcardDeliveryAmount.setText("₹ 29 /- ");
            tvScratchcardtotalAmount.setText("₹ 78 /- ");
            tv3Num.setText("So we are giving this ₹ "+scratch_card_price+" /- scratch card to our customers,Kindly share it to your friends,family and relatives.");
            scratch_card_price=scratch_card_price+29;
            tvSharingCount.setText("To get this offer help us by referring 6 people of your contact.");
        } else if (scratch_card_price==99) {
            rlPlatformLine.setVisibility(View.VISIBLE);
            llPlatformLine.setVisibility(View.VISIBLE);
            tvScratchcardAmount.setText("₹ 99 /- ");
            tvScratchcardDeliveryAmount.setText("Free ");
            tvScratchcardtotalAmount.setText("₹ 118 /- ");
            tv3Num.setText("So we are giving this ₹ "+scratch_card_price+" /- scratch card to our customers,Kindly share it to your friends,family and relatives.");
            scratch_card_price=scratch_card_price+19;
            Glide.with(getApplicationContext())
                    .load(R.drawable.gift99)
                    .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                    .into(imgGift);
            tvSharingCount.setText("To get this offer help us by referring 6 people of your contact.");
        }else if (scratch_card_price==149) {
            tvScratchcardAmount.setText("₹ 149 /- ");
            tvScratchcardDeliveryAmount.setText("₹ 49 /- ");
            tvScratchcardtotalAmount.setText("₹ 198 /- ");
            tv3Num.setText("So we are giving this ₹ "+scratch_card_price+" /- scratch card to our customers,Kindly share it to your friends,family and relatives.");
            tvSharingCount.setText("To get this offer help us by referring 6 people of your contact.");
            scratch_card_price=scratch_card_price+49;
        }else if (scratch_card_price==199) {
            tvScratchcardAmount.setText("₹ 199 /- ");
            tvScratchcardDeliveryAmount.setText("₹ 49 /- ");
            tvScratchcardtotalAmount.setText("₹ 248 /- ");
            tv3Num.setText("So we are giving this ₹ "+scratch_card_price+" /- scratch card to our customers,Kindly share it to your friends,family and relatives.");
            tvSharingCount.setText("To get this offer help us by referring 6 people of your contact.");
            scratch_card_price=scratch_card_price+49;
            Glide.with(getApplicationContext())
                    .load(R.drawable.gift199)
                    .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                    .into(imgGift);
        }else if (scratch_card_price==249) {
            tvScratchcardAmount.setText("₹ 249 /- ");
            tvScratchcardDeliveryAmount.setText("₹ 55 /- ");
            tvScratchcardtotalAmount.setText("₹ 304 /- ");
            tv3Num.setText("So we are giving this ₹ "+scratch_card_price+" /- scratch card to our customers,Kindly share it to your friends,family and relatives.");
            tvSharingCount.setText("To get this offer help us by referring 6 people of your contact.");
            scratch_card_price=scratch_card_price+55;
        } else if (scratch_card_price==299) {
            tvScratchcardAmount.setText("₹ 299 /- ");
            tvScratchcardDeliveryAmount.setText("₹ 59 /- ");
            tvScratchcardtotalAmount.setText("₹ 358 /- ");
            tv3Num.setText("So we are giving this ₹ "+scratch_card_price+" /- scratch card to our customers,Kindly share it to your friends,family and relatives.");
            tvSharingCount.setText("To get this offer help us by referring 2 people of your contact.");
            scratch_card_price=scratch_card_price+59;
            Glide.with(getApplicationContext())
                    .load(R.drawable.gift299)
                    .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                    .into(imgGift);
        } else if (scratch_card_price==349) {
            tvScratchcardAmount.setText("₹ 349 /- ");
            tvScratchcardDeliveryAmount.setText("₹ 69 /- ");
            tvScratchcardtotalAmount.setText("₹ 418 /- ");
            tv3Num.setText("So we are giving this ₹ "+scratch_card_price+" /- scratch card to our customers,Kindly share it to your friends,family and relatives.");
            tvSharingCount.setText("To get this offer help us by referring 2 people of your contact.");
            scratch_card_price=scratch_card_price+69;
        } else if (scratch_card_price==399) {
            tvScratchcardAmount.setText("₹ 399 /- ");
            tvScratchcardDeliveryAmount.setText("₹ 59 /- ");
            tvScratchcardtotalAmount.setText("₹ 458 /- ");
            tv3Num.setText("So we are giving this ₹ "+scratch_card_price+" /- scratch card to our customers,Kindly share it to your friends,family and relatives.");
            tvSharingCount.setText("To get this offer help us by referring 2 people of your contact.");
            scratch_card_price=scratch_card_price+59;
            Glide.with(getApplicationContext())
                    .load(R.drawable.gift399)
                    .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                    .into(imgGift);
        } else if (scratch_card_price==449) {
            tvScratchcardAmount.setText("₹ 449 /- ");
            tvScratchcardDeliveryAmount.setText("₹ 69 /- ");
            tvScratchcardtotalAmount.setText("₹ 518 /- ");
            tv3Num.setText("So we are giving this ₹ "+scratch_card_price+" /- scratch card to our customers,Kindly share it to your friends,family and relatives.");
            scratch_card_price=scratch_card_price+69;
            tvSharingCount.setText("To get this offer help us by referring 2 people of your contact.");
        } else if (scratch_card_price==499) {
            tvScratchcardAmount.setText("₹ 499 /- ");
            tvScratchcardDeliveryAmount.setText("₹ 69 /- ");
            tvScratchcardtotalAmount.setText("₹ 568 /- ");
            tv3Num.setText("So we are giving this ₹ "+scratch_card_price+" /- scratch card to our customers,Kindly share it to your friends,family and relatives.");
            tvSharingCount.setText("To get this offer help us by referring 2 people of your contact.");
            scratch_card_price=scratch_card_price+69;
            Glide.with(getApplicationContext())
                    .load(R.drawable.gift499)
                    .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                    .into(imgGift);
        }
        Log.e("checkerror",  "" + scratch_card_priceNew +"" + name + " " + email + " " + address_phone + " " + scratch_card_price);

        if (email != null && address_phone != null && name != null) {
            Log.e("checkerror", " "+email+" "+address_phone+" "+name);

        } else {
            Log.e("checkerror", "One or more values (email, address_phone, name) are null.");

        }

        rlBuyScratch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (is_available != null   && is_available.equals("true")) {
                    Random random = new Random();

                    randomNo = random.nextInt(999999999)  + 1; // Generates a random number between 1 and 9999999999 (inclusive)
                    Log.e("randomNumber", String.valueOf(randomNo));

                    payment(randomNo);
//                    verifyTransaction("597641401");
                } else {
                    Toast.makeText(ScratchCart_Activity.this, "Scratch Card Will Be Available After 12AM "+scratch_card_price, Toast.LENGTH_SHORT).show();
                }

                rlBuyScratch.startAnimation(clickAnimation());

            }
        });

        btn_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }
    public static String generateAlphanumericString() {
        // Creating a new Random object to add delightful randomness
        Random random = new Random();

        // Define the exciting range of the string length
        int minLength = 5;
        int maxLength = 10;

        // Randomly determine the exact length within our desired thrilling range
        int length = random.nextInt(maxLength - minLength + 1) + minLength;

        // Characters that can be included in the alphanumeric string
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // Building the string with tantalizing unpredictability
        StringBuilder result = new StringBuilder(length);

        // Generate each character one by one in a wonderfully randomized fashion
        for (int i = 0; i < length; i++) {
            // Select a random character from the character set
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            // Append this enchanting character to the result
            result.append(randomChar);
        }

        // Return the beautifully crafted alphanumeric string
        return result.toString();
    }

    private void init() {


        placeorder1 = findViewById(R.id.placeorder1);
        rlBuyScratch = findViewById(R.id.rlBuyScratch);
        rlLayout = findViewById(R.id.rlLayout);
        rlPlatformLine = findViewById(R.id.rlPlatformLine);
        llPlatformLine = findViewById(R.id.llPlatformLine);
        btn_back2 = findViewById(R.id.btn_back2);
        imgGift = findViewById(R.id.imgGift);
        tvScratchcardAmount = findViewById(R.id.tvScratchcardAmount);
        tvScratchcardDeliveryAmount = findViewById(R.id.tvScratchcardDeliveryAmount);
        tvScratchcardtotalAmount = findViewById(R.id.tvScratchcardtotalAmount);
        tv3Num = findViewById(R.id.tv3Num);
        tvSharingCount = findViewById(R.id.tvSharingCount);
    }



    private void payment(long randomNo) {

        Intent newPayIntent = new Intent(getApplicationContext(), PayActivity.class);
        newPayIntent.putExtra("merchantId", User_ID);
        newPayIntent.putExtra("password", TransactionPassword);
        newPayIntent.putExtra("prodid", ProductID);
        newPayIntent.putExtra("txncurr", "INR");
        newPayIntent.putExtra("amt", Double.toString(scratch_card_price));
        newPayIntent.putExtra("txnid", String.valueOf(randomNo));
        newPayIntent.putExtra("signature_request", HashRequestKey);
        newPayIntent.putExtra("signature_response", HashResponseKey);
        newPayIntent.putExtra("enc_request", AESrequestsalt);
        newPayIntent.putExtra("salt_request", AESrequestsalt);
        newPayIntent.putExtra("salt_response", AEresponsesalt);
        newPayIntent.putExtra("enc_response", AEresponsesalt);
        newPayIntent.putExtra("isLive", true);
        newPayIntent.putExtra("custFirstName", name);
        newPayIntent.putExtra("customerEmailID", email);
        newPayIntent.putExtra("customerMobileNo", address_phone);
        startActivityForResult(newPayIntent, 1);

        Log.e("PAymentData", String.valueOf(scratch_card_price) + " " + scratch_card_id + " " + name + " " + email + " " + address_phone + " " + randomNo);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("resultCode = "+resultCode);
        System.out.println("onActivityResult data = "+data);

        if(data != null && resultCode != 2){

            System.out.println("ArrayList data = "+data.getExtras().getString("response"));
            Log.e("gatewayDetails", "" + data.getExtras().getString("response"));

            if(resultCode == 1){

//                Toast.makeText(ScratchCart_Activity.this,"Transaction Successful!  ,ResultCode= "+resultCode , Toast.LENGTH_LONG).show();
                Log.e("gatewayDetails", "" + data.getExtras().getString("response"));

                try {
                    JSONObject jsonObject = new JSONObject(data.getExtras().getString("response"));
                    JSONObject jsonObject1 = jsonObject.getJSONObject("payInstrument");
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("payDetails");
                    String atomTxnId = jsonObject2.optString("atomTxnId");

                    JSONObject jsonObject3 = jsonObject1.getJSONObject("merchDetails");
                    String merchTxnId = jsonObject3.optString("merchTxnId");

                    Log.e("txn", "merchTxnId: " + merchTxnId+" atomTxnId: " + atomTxnId);
//                    Toast.makeText(ScratchCart_Activity.this, "SUCCESS", Toast.LENGTH_SHORT).show();

                    buyScratchcard(atomTxnId);
//                    verifyTransaction(merchTxnId );

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }else{

                try {
                    JSONObject jsonObjectFailed = new JSONObject(data.getExtras().getString("response"));
                    JSONObject jsonObject1 = jsonObjectFailed.getJSONObject("payInstrument");
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("payDetails");
                    String atomTxnId = jsonObject2.optString("atomTxnId");

                    JSONObject jsonObject3 = jsonObject1.getJSONObject("merchDetails");
                    String merchTxnId = jsonObject3.optString("merchTxnId");

                    Log.e("txnFailed", "merchTxnId: " + merchTxnId+" atomTxnId: " + atomTxnId);
//                    buyScratchcard(atomTxnId);
                    verifyTransaction(merchTxnId );
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


//                Toast.makeText(ScratchCart_Activity.this,"Transaction Failed! \n"  + data.getExtras().getString("response"), Toast.LENGTH_LONG).show();
            }
        } else{
            Toast.makeText(ScratchCart_Activity.this,"Transaction Cancelled!", Toast.LENGTH_LONG).show();
        }
    }//onActivityResult


    void buyScratchcard(String txnId) {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setCancelable(false); // Make the dialog non-cancelable
        progressDialog.setCanceledOnTouchOutside(false); // Make the dialog not disappear when touched outside
        progressDialog.setContentView(R.layout.new_paymentdialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("scratch_card_id", scratch_card_id);
            requestBody.put("transaction_id", txnId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
// Create a new request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                ApiData.Scratchcards_buy, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {

                            if (response.getBoolean("status") == true) {

                                int scratch_card_payment_id = response.getInt("scratch_card_payment_id");
                                Intent i = new Intent(getApplicationContext(), Otp_sharelink_Activity.class);
                                i.putExtra("scratch_card_payment_id", scratch_card_payment_id);
                                i.putExtra("scratch_card_price", scratch_card_priceNew);
                                startActivity(i);

                                Toast.makeText(ScratchCart_Activity.this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();
//                                Toast.makeText(ScratchCart_Activity.this, ""+response.getInt("scratch_card_payment_id"), Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(ScratchCart_Activity.this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();
//                                Toast.makeText(ScratchCart_Activity.this, ""+response.getInt("scratch_card_payment_id"), Toast.LENGTH_SHORT).show();

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
//        RequestQueue queue = Volley.newRequestQueue(this);
//        queue.add(request);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    private void verifyTransaction(String merchTxnId) {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setCancelable(false); // Make the dialog non-cancelable
        progressDialog.setCanceledOnTouchOutside(false); // Make the dialog not disappear when touched outside
        progressDialog.setContentView(R.layout.new_paymentdialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("merchTxnId", merchTxnId);
        } catch (JSONException e) {
            Log.e("JSONError", "Failed to create JSON object", e);
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                ApiData.verify_transaction, jsonObject,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d("responseData", response.toString());

                        try {
                            if (response.getBoolean("status")) {
                                JSONObject data = response.getJSONObject("data");
                                Log.d("verificationData", data.toString());
                                String verified = data.optString("VERIFIED");
                                String merchantID = data.optString("MerchantID");
                                String merchantTxnID = data.optString("MerchantTxnID");
                                String atomTxnId = data.optString("AtomTxnId");
                                Log.d("necessaryData", merchantTxnID+" "+atomTxnId);

                                if ("SUCCESS".equals(verified)) {
//                                    Toast.makeText(ScratchCart_Activity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                                    // buyScratchcard(atomTxnId);
                                } else if ("FAILED".equals(verified)) {
//                                    Toast.makeText(ScratchCart_Activity.this, "FAILED", Toast.LENGTH_SHORT).show();
                                    showCustomAlertDialog(merchantTxnID, atomTxnId);
                                }
                            } else {
                                Toast.makeText(ScratchCart_Activity.this, "Verification failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Log.e("JSONError", "Failed to parse response", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error
                        // Display an error message or retry request
                    }
                });

        // Add the request to the Volley request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    private void showCustomAlertDialog(String merchantTxnID, String atomTxnId) {
        if (!isFinishing()) {
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.paymentdialog_custom_layout_, null);

            RelativeLayout rlCancel = dialogView.findViewById(R.id.rlCancel);
            TextView tv_merchantTxnId = dialogView.findViewById(R.id.tv_merchantTxnId);
            TextView tv_atomTxnId = dialogView.findViewById(R.id.tv_atomTxnId);

            AlertDialog.Builder builder = new AlertDialog.Builder(ScratchCart_Activity.this);
            builder.setView(dialogView);
            builder.setCancelable(false);  // Prevents the dialog from being dismissed by clicking outside

            final AlertDialog alertDialog = builder.create();

            tv_merchantTxnId.setText("Merchant Transaction ID: " + merchantTxnID);
            tv_atomTxnId.setText("Atom Transaction ID: " + atomTxnId);
            rlCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });

            alertDialog.show();
        }
    }






    public void getDetailsScratchProduct(int scratch_card_id) {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.new_progresslogo);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("scratch_card_id", scratch_card_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

// Create a new request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ApiData.Scratchcards_details, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressDialog.dismiss();
                            rlLayout.setVisibility(View.VISIBLE);
                            is_available = String.valueOf(response.getBoolean("is_available"));
//                            Toast.makeText(ScratchCart_Activity.this, ""+is_available, Toast.LENGTH_SHORT).show();
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
                        getApplicationContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + sharedPreferences.getString("Login_Token", ""));
                return headers;
            }
        };

// Add the request to the Volley request queue
        // Check if getContext() or getActivity() is null before proceeding
        if (getApplicationContext() != null) {
            // Create the Volley request queue here
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(request);
        }


    }

    public void getDetailsScratchProductOnResume(int scratch_card_id) {

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("scratch_card_id", scratch_card_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

// Create a new request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ApiData.Scratchcards_details, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            is_available = String.valueOf(response.getBoolean("is_available"));
//                            Toast.makeText(ScratchCart_Activity.this, ""+is_available, Toast.LENGTH_SHORT).show();
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
                        getApplicationContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + sharedPreferences.getString("Login_Token", ""));
                return headers;
            }
        };

// Add the request to the Volley request queue
        // Check if getContext() or getActivity() is null before proceeding
        if (getApplicationContext() != null) {
            // Create the Volley request queue here
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(request);
        }


    }

    public AlphaAnimation clickAnimation() {
        return new AlphaAnimation(1F, 0.1F); // Change "0.4F" as per your recruitment.
    }
    @Override
    public void onResume() {
        super.onResume();
        getDetailsScratchProductOnResume(scratch_card_id);
    }
}