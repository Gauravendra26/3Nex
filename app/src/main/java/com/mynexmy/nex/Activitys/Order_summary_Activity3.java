package com.mynexmy.nex.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class Order_summary_Activity3 extends AppCompatActivity {
    RelativeLayout changeaddress1, placeorder2;
    ImageView btn_back3;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    TextView tvPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary3);
        Utils.blackIconStatusBar(Order_summary_Activity3.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        init();

        changeaddress1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), AddNewAddress.class);
                startActivity(i);

            }
        });

        placeorder2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               PlaceOrder();
            }
        });
        btn_back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void PlaceOrder() {

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.new_progress);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

//        tvPrice.setText(sum);

        JSONObject jsonObject = new JSONObject();
        try {
            ProductDatabase db = Room.databaseBuilder(getApplicationContext(),
                    ProductDatabase.class, "cart_db").allowMainThreadQueries().build();
            ProductDao productDao = db.ProductDao();
            List<Product> products = productDao.getallproduct();

            JSONArray productArray = new JSONArray();
            int sum = 0;
            for (int i = 0; i < products.size(); i++) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("id", products.get(i).pid);
                jsonObject1.put("name", products.get(i).pname);
                jsonObject1.put("price", products.get(i).price);
                jsonObject1.put("quantity", products.get(i).qnt);
                jsonObject1.put("product_tax_percent", "5");

                productArray.put(i, jsonObject1);

                sum = sum + (products.get(i).getSale() * products.get(i).getQnt());

            }
            jsonObject.put("fname", "Dev");
            jsonObject.put("lname", "Kumar");
            jsonObject.put("email", "Gtest@gmail.com");
            jsonObject.put("phone", "123456");
            jsonObject.put("postal_code", "208025");
            jsonObject.put("city", "Agra");
            jsonObject.put("state", "UP");
            jsonObject.put("country", "Indian");
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
                        Toast.makeText(Order_summary_Activity3.this, "" + response.toString(),
                                Toast.LENGTH_LONG).show();
                        try {
                            if (response.getBoolean("status") == true) {
                                Intent i = new Intent(getApplicationContext(), Home.class);
                                startActivity(i);
                                finish();
                            } else {
                                Intent i = new Intent(getApplicationContext(), Home.class);
                                startActivity(i);
                                finish();                                }
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

    private void init() {

        changeaddress1 = findViewById(R.id.changeaddress1);
        placeorder2 = findViewById(R.id.placeorder2);
        btn_back3 = findViewById(R.id.btn_back3);
        tvPrice = findViewById(R.id.tvPrice);


    }
}