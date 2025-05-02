package com.mynexmy.nex.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.mynexmy.nex.Adapters.OrderSummaryAdapter;
import com.mynexmy.nex.Adapters.PAdapter;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.Home;
import com.mynexmy.nex.MyAccount_Activity2;
import com.mynexmy.nex.Product_DetailPage_Activity4;
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
import java.util.logging.Handler;

public class Order_Summary_Activity extends AppCompatActivity implements OrderSummaryAdapter.whenClick {
    RelativeLayout changeaddress, placeorder1, changeaddress1, rlorder1, placeorder2;
    ImageView btn_back, imghole, imghole1, imgSolid, imgSolid1;
    TextView tvPrice,tvPriceItem1,tvPriceQuantity,tvTotalAmount1,tvtvTotalSavings1,tvPriceDiscount1,tvOR,tvHeading;
    SwipeRefreshLayout refreshLayout;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    RelativeLayout  rlbot;
    RecyclerView rv1;
    PAdapter adapter;
    OrderSummaryAdapter orderSummaryAdapter;
    RadioGroup radiogroup;
    RadioButton radio1, radio2;
    TextView tv_messages, mobleno, tvhouseno, tvufirst, tvlastname, tvpincode, tvcity, tvstate, tv3, tv2;
    LinearLayout bottom, llAddress,llOrder;
    String email, first_name, last_name, mobile, pincode, created_at, updated_at, referral_code,
            city, country, state, postal_code, address_phone, address_line;

    int customer_id, is_verified, address_id, is_default;
    String houseno;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary2);
        Utils.blackIconStatusBar(Order_Summary_Activity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        init();
        database();

        houseno = tvhouseno.getText().toString().trim();
        getDataProfile();

        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        getDataProfileONresume();
                        refreshLayout.setRefreshing(false);
                    }
                }
        );

        changeaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MyAccount_Activity2.class);
                startActivity(i);

                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
                changeaddress.startAnimation(myAnim);
            }
        });

        changeaddress1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MyAccount_Activity2.class);
                startActivity(i);

                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
                changeaddress1.startAnimation(myAnim);
            }
        });
        placeorder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Payment_Activity.class);
                startActivity(i);

                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
                placeorder1.startAnimation(myAnim);
            }
        });

        placeorder2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent i = new Intent(getApplicationContext(), Payment_Activity.class);
                startActivity(i);

                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
                placeorder2.startAnimation(myAnim);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }


    private void database() {
        ProductDatabase db = Room.databaseBuilder(getApplicationContext(),
                ProductDatabase.class, "cart_db").allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        rv1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        List<Product> products = productDao.getallproduct();
        if (products.isEmpty()) {
            tv_messages.setVisibility(View.VISIBLE);
            rv1.setVisibility(View.GONE);
            llOrder.setVisibility(View.GONE);

        } else {
            tv_messages.setVisibility(View.GONE);
            rv1.setVisibility(View.VISIBLE);
            llOrder.setVisibility(View.VISIBLE);

        }
        OrderSummaryAdapter adapter = new
                OrderSummaryAdapter(getApplicationContext(), products, tvPrice,tvTotalAmount1,tvPriceQuantity,tvtvTotalSavings1,
                tvPriceDiscount1,tvPriceItem1);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv1.setLayoutManager(layoutManager);
        rv1.setItemAnimator(new DefaultItemAnimator());
        rv1.setAdapter(adapter);
        adapter.setOnClick(this);
    }

    private void init() {
        changeaddress = findViewById(R.id.changeaddress);
        changeaddress1 = findViewById(R.id.changeaddress1);
        placeorder1 = findViewById(R.id.placeorder1);
        btn_back = findViewById(R.id.btn_back);
        tvPrice = findViewById(R.id.tvPrice);
        tvPriceItem1 = findViewById(R.id.tvPriceItem1);
        tvPriceQuantity = findViewById(R.id.tvPriceQuantity);
        refreshLayout = findViewById(R.id.refreshLayout);
        tvPriceDiscount1 = findViewById(R.id.tvPriceDiscount1);
        tvtvTotalSavings1 = findViewById(R.id.tvtvTotalSavings1);
        tvTotalAmount1 = findViewById(R.id.tvTotalAmount1);
        tvHeading = findViewById(R.id.tvHeading);
        tvOR = findViewById(R.id.tvOR);
        rlbot = findViewById(R.id.rlbot);
        rv1 = findViewById(R.id.rv1);
        tv_messages = findViewById(R.id.tv_messages);
        bottom = findViewById(R.id.bottom);
        mobleno = findViewById(R.id.mobleno);
        tvhouseno = findViewById(R.id.tvhouseno);
        tvufirst = findViewById(R.id.tvufirst);
        tvlastname = findViewById(R.id.tvlastname);
        tvpincode = findViewById(R.id.tvpincode);
        tvcity = findViewById(R.id.tvcity);
        tvstate = findViewById(R.id.tvstate);
        llAddress = findViewById(R.id.llAddress);
        llOrder = findViewById(R.id.llOrder);
        rlorder1 = findViewById(R.id.rlorder1);
        radiogroup = findViewById(R.id.radiogroup);
        radio2 = findViewById(R.id.radio2);
        radio1 = findViewById(R.id.radio1);

        placeorder2 = findViewById(R.id.placeorder2);

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
                            address_line= user.optString("address_line");
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
                            address_line= user.optString("address_line");
                            city = user.optString("city");
                            country = user.optString("country");
                            state = user.optString("state");
                            postal_code = user.optString("postal_code");
                            address_phone = user.optString("address_phone");
                            is_default = user.optInt("is_default");
                            if (is_default==1){
                                Log.e("asd","address_id=> "+address_id+" is_default->"+String.valueOf(is_default));

                                tvufirst.setText(first_name);
                                tvlastname.setText(last_name);
                                tvcity.setText(city);
                                tvpincode.setText(postal_code);
                                mobleno.setText(address_phone);
                                tvstate.setText(state);
                                tvhouseno.setText(address_line);

                                SharedPreferences sharedPreferences = getSharedPreferences("AddressDetails", MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.putString("address_phone", address_phone);
                                myEdit.putString("first_name",first_name);
                                myEdit.putString("last_name",last_name);
                                myEdit.putString("email",email);
                                myEdit.putString("city",city);
                                myEdit.putString("postal_code",postal_code);
                                myEdit.putString("address_line",address_line);
                                myEdit.putString("state",state);
                                myEdit.apply();
                                myEdit.commit();

                                llAddress.setVisibility(View.VISIBLE);
                                tvHeading.setVisibility(View.VISIBLE);
                                tvOR.setVisibility(View.VISIBLE);
                                changeaddress1.setVisibility(View.VISIBLE);
                                changeaddress.setVisibility(View.GONE);
                                rlbot.setVisibility(View.VISIBLE);
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


    @Override
    public void setNotify(int position) {
        ProductDatabase db = Room.databaseBuilder(getApplicationContext(),
                ProductDatabase.class, "cart_db").allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        rv1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        List<Product> products = productDao.getallproduct();
        if (products.isEmpty()) {
            tv_messages.setVisibility(View.VISIBLE);
            rlbot.setVisibility(View.GONE);

            rv1.setVisibility(View.GONE);
        } else {
            tv_messages.setVisibility(View.GONE);
            rlbot.setVisibility(View.VISIBLE);

            rv1.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setNotify1(int position) {
        ProductDatabase db = Room.databaseBuilder(getApplicationContext(),
                ProductDatabase.class, "cart_db").allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        rv1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        List<Product> products = productDao.getallproduct();
    }

    @Override
    public void press(int position, int pid, String pname, int price, int qnt, int sale, String
            image) {
    }

    @Override
    public void press1(int position, int pid, String pname, int price, int qnt,
                       int sale, String image) {
        Intent intent = new Intent(getApplicationContext(), Product_DetailPage_Activity4.class);
        intent.putExtra("pid", pid);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataProfileONresume();
    }





}


