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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.atom.atompaynetzsdk.PayActivity;
import com.mynexmy.nex.Adapters.PAdapter;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.Home;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Room.Product;
import com.mynexmy.nex.Room.ProductDao;
import com.mynexmy.nex.Room.ProductDatabase;
import com.mynexmy.nex.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Payment_Activity extends AppCompatActivity implements PAdapter.whenClick {
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    RelativeLayout placeorderCashOnDelivery, placeorderOnlinePayment;
    RecyclerView rv1;
    int sum1 = 0;
    long onlinerandomNumber;
    ImageView btn_back;
    TextView tvPrice, tvPriceItem1, tvPaymentMessage;
    PAdapter adapter;
    RadioGroup radiogroup;
    JSONArray productArray;
    RadioButton radio1, radio2;
    String email, first_name, last_name, mobile, pincode, created_at, updated_at, referral_code,
            city, country, state, postal_code, address, address_phone, address_line;
    int customer_id, is_verified, txnid, address_id, is_default, Tax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Utils.blackIconStatusBar(Payment_Activity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        init();
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        placeorderCashOnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaceOrderforCashOnDelivery();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        placeorderOnlinePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();

                onlinerandomNumber = random.nextInt(999999999)  + 1; // Generates a random number between 1 and 9999999999 (inclusive)
                Log.e("onlinerandomNumber", String.valueOf(onlinerandomNumber));


                paymentMethod(onlinerandomNumber);
//                verifyTransaction("597641401");
            }
        });
        radioGroupCheck();
        database();
        getDataProfile();
        databasePrice();
    }

    private void init() {
        tvPrice = findViewById(R.id.tvPrice);
        tvPaymentMessage = findViewById(R.id.tvPaymentMessage);
        placeorderCashOnDelivery = findViewById(R.id.placeorderCashOnDelivery);
        placeorderOnlinePayment = findViewById(R.id.placeorderOnlinePayment);
        radiogroup = findViewById(R.id.radiogroup);
        radio2 = findViewById(R.id.radio2);
        radio1 = findViewById(R.id.radio1);
        rv1 = findViewById(R.id.rv1);
        btn_back = findViewById(R.id.btn_back);
    }

    private void radioGroupCheck() {

        radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeorderCashOnDelivery.setVisibility(View.VISIBLE);
                placeorderOnlinePayment.setVisibility(View.GONE);

            }
        });

        radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeorderOnlinePayment.setVisibility(View.VISIBLE);
                placeorderCashOnDelivery.setVisibility(View.GONE);

            }
        });

    }

    private void PlaceOrderforCashOnDelivery() {

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setCancelable(false); // Make the dialog non-cancelable
        progressDialog.setCanceledOnTouchOutside(false); // Make the dialog not disappear when touched outside
        progressDialog.setContentView(R.layout.new_progresslogo);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        JSONObject jsonObject = new JSONObject();
        try {
            ProductDatabase db = Room.databaseBuilder(getApplicationContext(),
                    ProductDatabase.class, "cart_db").allowMainThreadQueries().build();
            ProductDao productDao = db.ProductDao();
            List<Product> products = productDao.getallproduct();
            JSONArray productArray = new JSONArray();
            double sum = 0;
            for (int i = 0; i < products.size(); i++) {
                JSONObject jsonObject1 = new JSONObject();
                SharedPreferences sharedPreferences1 = getSharedPreferences("Tax", MODE_PRIVATE);
                Tax = sharedPreferences1.getInt("product_tax_percent", 0);

                jsonObject1.put("id", products.get(i).pid);
                jsonObject1.put("name", products.get(i).pname);
                jsonObject1.put("price", products.get(i).sale);
                jsonObject1.put("quantity", products.get(i).qnt);
                jsonObject1.put("product_tax_percent", Tax);


                productArray.put(i, jsonObject1);

                sum = sum + (products.get(i).getSale() * products.get(i).getQnt());

            }
            SharedPreferences sharedPreferences = getSharedPreferences("AddressDetails", MODE_PRIVATE);
            address_phone = sharedPreferences.getString("address_phone", "");
            first_name = sharedPreferences.getString("first_name", "");
            last_name = sharedPreferences.getString("last_name", "");
            city = sharedPreferences.getString("city", "");
            postal_code = sharedPreferences.getString("postal_code", "");
            address_line = sharedPreferences.getString("address_line", "");
            state = sharedPreferences.getString("state", "");


            jsonObject.put("fname", first_name);
            jsonObject.put("lname", last_name);
            jsonObject.put("email", email);
            jsonObject.put("phone", address_phone);
            jsonObject.put("postal_code", postal_code);
            jsonObject.put("address", address_line);
            jsonObject.put("city", city);
            jsonObject.put("state", state);
            jsonObject.put("country", "India");
            jsonObject.put("total_amt", sum);
            jsonObject.put("payment_method", "cod");
            jsonObject.put("product", productArray);


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                ApiData.Placeorder, jsonObject,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {


                            if (response.getBoolean("status") == true) {
                                ProductDatabase db = Room.databaseBuilder(getApplicationContext(),
                                        ProductDatabase.class, "cart_db").allowMainThreadQueries().build();
                                ProductDao productDao = db.ProductDao();
                                List<Product> products = productDao.getallproduct();
                                for (int i = 0; i < products.size(); i++) {
                                    productDao.deleteById(products.get(i).getPid());
                                }

                                SharedPreferences sharedPreferences = getSharedPreferences("AddressDetails",
                                        MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.clear();
                                myEdit.apply();


                                SharedPreferences sharedPreferences1 = getSharedPreferences("Tax",
                                        MODE_PRIVATE);
                                SharedPreferences.Editor myEdit1 = sharedPreferences1.edit();
                                myEdit1.clear();
                                myEdit1.apply();

                                Intent i = new Intent(getApplicationContext(), OrderPlacedMsg.class);
                                startActivity(i);
                                finish();


                            } else {
                                Intent i = new Intent(getApplicationContext(), Home.class);
                                startActivity(i);
                                finish();
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


    private void database() {
        ProductDatabase db = Room.databaseBuilder(getApplicationContext(),
                ProductDatabase.class, "cart_db").allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        rv1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        List<Product> products = productDao.getallproduct();
        PAdapter adapter = new
                PAdapter(getApplicationContext(), products, tvPrice);
//        for (int i = 0; i < products.size(); i++) {
//            sum1 = sum1 + (products.get(i).getSale() * products.get(i).getQnt());
//        }
//        if (sum1 > 1 && sum1 < 999)
//        {
//            // If sum1 is greater than 1 and less than 9999, hide radio1
//            radio1.setVisibility(View.GONE);
//            placeorder1.setVisibility(View.GONE);
//            placeorder2.setVisibility(View.VISIBLE);
//            tvPaymentMessage.setVisibility(View.VISIBLE);
//            radio2.setChecked(true);
//            tvPaymentMessage.setText("COD is only applicable for 1000 rupees above.");
//
//        } else if (sum1 >= 9999) {
//            // If sum1 is greater than or equal to 9999, hide radio1
//            radio1.setVisibility(View.GONE);
//            placeorder1.setVisibility(View.GONE);
//            placeorder2.setVisibility(View.VISIBLE);
//            tvPaymentMessage.setVisibility(View.VISIBLE);
//            radio2.setChecked(true);
//            tvPaymentMessage.setText("COD is only applicable for 9999 rupees below.");
//        }
//        Toast.makeText(this, ""+sum1, Toast.LENGTH_SHORT).show();
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv1.setLayoutManager(layoutManager);
        rv1.setItemAnimator(new DefaultItemAnimator());
        rv1.setAdapter(adapter);
        adapter.setOnClick(this);
    }

    private void databasePrice() {
        ProductDatabase db = Room.databaseBuilder(getApplicationContext(),
                ProductDatabase.class, "cart_db").allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        rv1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        List<Product> products = productDao.getallproduct();
        PAdapter adapter = new
                PAdapter(getApplicationContext(), products, tvPrice);
        for (int i = 0; i < products.size(); i++) {
            sum1 = sum1 + (products.get(i).getSale() * products.get(i).getQnt());
        }
        if (sum1 > 1 && sum1 < 999)
        {
            // If sum1 is greater than 1 and less than 9999, hide radio1
            radio1.setVisibility(View.GONE);
            placeorderCashOnDelivery.setVisibility(View.GONE);
            placeorderOnlinePayment.setVisibility(View.VISIBLE);
            tvPaymentMessage.setVisibility(View.VISIBLE);
            radio2.setChecked(true);
            tvPaymentMessage.setText("COD is only applicable for 1000 rupees above.");

        } else if (sum1 >= 9999) {
            // If sum1 is greater than or equal to 9999, hide radio1
            radio1.setVisibility(View.GONE);
            placeorderCashOnDelivery.setVisibility(View.GONE);
            placeorderOnlinePayment.setVisibility(View.VISIBLE);
            tvPaymentMessage.setVisibility(View.VISIBLE);
            radio2.setChecked(true);
            tvPaymentMessage.setText("COD is only applicable for 9999 rupees below.");
        }
//        Toast.makeText(this, ""+sum1, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setNotify(int position) {

    }

    @Override
    public void setNotify1(int position) {

    }

    @Override
    public void press(int position, int pid, String pname, int price, int qnt, int sale, String image) {

    }

    @Override
    public void press1(int position, int pid, String pname, int price, int qnt, int sale, String image) {

    }

    void paymentMethod(long onlinerandomNumber) {
        double sum = 0;
        Intent newPayIntent = new Intent(Payment_Activity.this, PayActivity.class);

        SharedPreferences sharedPreferences = getSharedPreferences("AddressDetails", MODE_PRIVATE);
        first_name = sharedPreferences.getString("first_name", "");
        last_name = sharedPreferences.getString("last_name", "");
        address_phone = sharedPreferences.getString("address_phone", "");
        String name = first_name + " " + last_name;

        ProductDatabase db = Room.databaseBuilder(getApplicationContext(),
                ProductDatabase.class, "cart_db").allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        List<Product> products = productDao.getallproduct();
        for (int i = 0; i < products.size(); i++) {
            sum = sum + (products.get(i).getSale() * products.get(i).getQnt());
        }
        if (products.size() == 1) {
            String pName = "";
            String qty = "";
            for (int i = 0; i < products.size(); i++) {
                pName = products.get(i).pname;
                qty = String.valueOf(products.get(i).qnt);
            }
            newPayIntent.putExtra("merchantId", User_ID);
            newPayIntent.putExtra("password", TransactionPassword);
            newPayIntent.putExtra("prodid", ProductID);
            newPayIntent.putExtra("txncurr", "INR");
//        newPayIntent.putExtra("custacc", "100000036600");
            newPayIntent.putExtra("amt", Double.toString(sum));
            newPayIntent.putExtra("x", String.valueOf(onlinerandomNumber));
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
            newPayIntent.putExtra("udf1", address_phone);
            newPayIntent.putExtra("udf2", address_phone);
            newPayIntent.putExtra("udf3", address_phone);
            newPayIntent.putExtra("udf4", address_phone);
            newPayIntent.putExtra("udf5", address_phone);
//            newPayIntent.putExtra("udf1", "Product Name - "+pName);
//            newPayIntent.putExtra("udf2", "Product Quantity - "+qty);


        } else {

            newPayIntent.putExtra("merchantId", User_ID);
            newPayIntent.putExtra("password", TransactionPassword);
            newPayIntent.putExtra("prodid", ProductID);
            newPayIntent.putExtra("txncurr", "INR");
//        newPayIntent.putExtra("custacc", "100000036600");
            newPayIntent.putExtra("amt", Double.toString(sum));
            newPayIntent.putExtra("x", String.valueOf(onlinerandomNumber));
            newPayIntent.putExtra("signature_request", HashRequestKey);
            newPayIntent.putExtra("signature_response", HashResponseKey);
            newPayIntent.putExtra("enc_request", AESrequestsalt);
            newPayIntent.putExtra("salt_request", AESrequestsalt);
            newPayIntent.putExtra("salt_response", AEresponsesalt);
            newPayIntent.putExtra("enc_response", AEresponsesalt);
            newPayIntent.putExtra("multi_products", createMultiProductData());  // comment this line if not required
            newPayIntent.putExtra("isLive", true);
            newPayIntent.putExtra("custFirstName", name);
            newPayIntent.putExtra("customerEmailID", email);
            newPayIntent.putExtra("customerMobileNo", address_phone);
        }
        startActivityForResult(newPayIntent, 1);

    }

    private void verifyTransaction(String merchTxnId) {
        {
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
                throw new RuntimeException(e);
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    ApiData.verify_transaction, jsonObject,
                    new com.android.volley.Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressDialog.dismiss();
                            Log.e("responseData",response.toString());

                            try {
                                if (response.getBoolean("status") == true) {
                                    JSONObject data = response.getJSONObject("data");
                                    Log.e("verificationData",data.toString());
                                    String verified = data.optString("VERIFIED");
                                    String merchantID = data.optString("MerchantID");
                                    String merchantTxnID = data.optString("MerchantTxnID");
                                    String atomTxnId = data.optString("AtomTxnId");


                                    if (verified.matches("SUCCESS")){
//                                        Toast.makeText(ScratchCart_Activity.this, "SUCCESS" +
//                                                "", Toast.LENGTH_SHORT).show();
//                                        PlaceOrderforSmartpay(atomTxnId);
                                    }else if (verified.matches("FAILED")){
//                                        Toast.makeText(ScratchCart_Activity.this, "FAILED" +
//                                                "", Toast.LENGTH_SHORT).show();
                                        showCustomAlertDialog(merchantTxnID,atomTxnId);
                                    }
                                } else {

                                }
                            } catch (JSONException e) {

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });

            requestQueue.add(jsonObjectRequest);

        }


    }
    private void showCustomAlertDialog(String merchantTxnID, String atomTxnId) {
        if (!isFinishing()) {
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.paymentdialog_custom_layout_, null);

            RelativeLayout rlCancel = dialogView.findViewById(R.id.rlCancel);
            TextView tv_merchantTxnId = dialogView.findViewById(R.id.tv_merchantTxnId);
            TextView tv_atomTxnId = dialogView.findViewById(R.id.tv_atomTxnId);

            AlertDialog.Builder builder = new AlertDialog.Builder(Payment_Activity.this);
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

    public String createMultiProductData() {
        ProductDatabase db = Room.databaseBuilder(getApplicationContext(),
                ProductDatabase.class, "cart_db").allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        List<Product> products = productDao.getallproduct();
        productArray = new JSONArray();
        double sum = 0;

        for (int i = 0; i < products.size(); i++) {
            JSONObject jsonObject1 = new JSONObject();
            try {
                jsonObject1.put("prodName", products.get(i).pname);
                jsonObject1.put("prodAmount", Double.toString(products.get(i).sale));
                productArray.put(i, jsonObject1);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("jsonArray from createMultiProductData = " + productArray.toString());
        return productArray.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("resultCode = " + resultCode);
        System.out.println("onActivityResult data = " + data);
//        Log.e("Paymentsamountproduct", "" + data.getExtras().getString("response"));

        if (data != null && resultCode != 2) {
            System.out.println("ArrayList data = " + data.getExtras().getString("response"));
//            Log.e("Paymentsamount", "" + data.getExtras().getString("response"));

            if (resultCode == 1) {
                Log.e("PaymentsDetails", "" + data.getExtras().getString("response"));

                try {

                    JSONObject jsonObject = new JSONObject(data.getExtras().getString("response"));
                    JSONObject jsonObject1 = jsonObject.getJSONObject("payInstrument");
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("payDetails");
                    String atomTxnId = jsonObject2.optString("atomTxnId");

                    JSONObject jsonObject3 = jsonObject1.getJSONObject("merchDetails");
                    String merchTxnId = jsonObject3.optString("merchTxnId");

                    Log.e("txn", "merchTxnId: " + merchTxnId+" atomTxnId: " + atomTxnId);
                     PlaceOrderforSmartpay(atomTxnId);
//                    verifyTransaction(merchTxnId );



//                    Toast.makeText(this, ""+txnid, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            } else {
                try {

                    JSONObject jsonObject = new JSONObject(data.getExtras().getString("response"));

                    JSONObject jsonObject1 = jsonObject.getJSONObject("payInstrument");
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("payDetails");
                    String atomTxnId = jsonObject2.optString("atomTxnId");

                    JSONObject jsonObject3 = jsonObject1.getJSONObject("merchDetails");
                    String merchTxnId = jsonObject3.optString("merchTxnId");

                    Log.e("txn", "merchTxnId: " + merchTxnId+" atomTxnId: " + atomTxnId);
//                    buyScratchcard(atomTxnId);
                    verifyTransaction(merchTxnId );

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


//                Log.e("PaymentsDetails", "" + data.getExtras().getString("response"));
//                Toast.makeText(this, "Transaction Failed!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Transaction Cancelled!", Toast.LENGTH_LONG).show();
        }
    }

    private void PlaceOrderforSmartpay(String txnid) {


        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setCancelable(false); // Make the dialog non-cancelable
        progressDialog.setCanceledOnTouchOutside(false); // Make the dialog not disappear when touched outside
        progressDialog.setContentView(R.layout.new_paymentdialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        JSONObject jsonObject = new JSONObject();
        try {
            ProductDatabase db = Room.databaseBuilder(getApplicationContext(),
                    ProductDatabase.class, "cart_db").allowMainThreadQueries().build();
            ProductDao productDao = db.ProductDao();
            List<Product> products = productDao.getallproduct();
            productArray = new JSONArray();
            double sum = 0;
            for (int i = 0; i < products.size(); i++) {
                SharedPreferences sharedPreferences1 = getSharedPreferences("Tax", MODE_PRIVATE);
                Tax = sharedPreferences1.getInt("product_tax_percent", 0);

                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("id", products.get(i).pid);
                jsonObject1.put("name", products.get(i).pname);
                jsonObject1.put("price", products.get(i).sale);
                jsonObject1.put("quantity", products.get(i).qnt);
                jsonObject1.put("product_tax_percent", Tax);

                productArray.put(i, jsonObject1);

                sum = sum + (products.get(i).getSale() * products.get(i).getQnt());

            }
            SharedPreferences sharedPreferences = getSharedPreferences("AddressDetails", MODE_PRIVATE);
            address_phone = sharedPreferences.getString("address_phone", "");
            first_name = sharedPreferences.getString("first_name", "");
            last_name = sharedPreferences.getString("last_name", "");
            city = sharedPreferences.getString("city", "");
            postal_code = sharedPreferences.getString("postal_code", "");
            address_line = sharedPreferences.getString("address_line", "");
            state = sharedPreferences.getString("state", "");

            jsonObject.put("fname", first_name);
            jsonObject.put("lname", last_name);
            jsonObject.put("email", email);
            jsonObject.put("phone", address_phone);
            jsonObject.put("postal_code", postal_code);
            jsonObject.put("address", address_line);
            jsonObject.put("city", city);
            jsonObject.put("state", state);
            jsonObject.put("country", "India");
            jsonObject.put("total_amt", sum);
            jsonObject.put("payment_method", "OnlinePayment");
            jsonObject.put("transaction_details", txnid);
            jsonObject.put("product", productArray);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                ApiData.Placeorder, jsonObject,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {


                            if (response.getBoolean("status") == true) {
                                String url = response.optString("url");
                                ProductDatabase db = Room.databaseBuilder(getApplicationContext(),
                                        ProductDatabase.class, "cart_db").allowMainThreadQueries().build();
                                ProductDao productDao = db.ProductDao();
                                List<Product> products = productDao.getallproduct();
                                for (int i = 0; i < products.size(); i++) {
                                    productDao.deleteById(products.get(i).getPid());
                                }
//                                Intent i = new Intent(getApplicationContext(), OrderPlacedMsg.class);
//                                startActivity(i);
//                                finish();

                                Intent i = new Intent(getApplicationContext(), OrderPlacedMsg.class);
                                startActivity(i);
                                finish();

                                SharedPreferences sharedPreferences = getSharedPreferences("AddressDetails",
                                        MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.clear();
                                myEdit.apply();


                                SharedPreferences sharedPreferences1 = getSharedPreferences("Tax",
                                        MODE_PRIVATE);
                                SharedPreferences.Editor myEdit1 = sharedPreferences1.edit();
                                myEdit1.clear();
                                myEdit1.apply();

                            } else {
                                Intent i = new Intent(getApplicationContext(), Home.class);
                                startActivity(i);
                                finish();
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

}