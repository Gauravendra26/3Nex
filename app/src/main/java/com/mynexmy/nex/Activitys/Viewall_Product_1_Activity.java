package com.mynexmy.nex.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mynexmy.nex.Adapters.Featured_Adapter;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.Models.Featured_Model;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Room.Product;
import com.mynexmy.nex.Room.ProductDao;
import com.mynexmy.nex.Room.ProductDatabase;
import com.mynexmy.nex.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Viewall_Product_1_Activity extends AppCompatActivity implements Featured_Adapter.ProductPageClick {
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    List<Featured_Model> featured_models;
    Featured_Adapter featured_adapter;
    RecyclerView rvView;
    ImageView btn_back1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewall_product1);
        Utils.blackIconStatusBar(Viewall_Product_1_Activity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        init();
        Intent intent = getIntent();
        int product_featured = intent.getIntExtra("product_featured", 1);
        fetchDataAndDisplayOnRecyclerView(product_featured);

        btn_back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void fetchDataAndDisplayOnRecyclerView(int id) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false); // Make the dialog non-cancelable
        progressDialog.setCanceledOnTouchOutside(false); // Make the dialog not disappear when touched outside
        progressDialog.show();
        progressDialog.setContentView(R.layout.new_progresslogo);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                ApiData.product_list+"?featured="+id, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("status")) {
                                JSONArray data = response.getJSONArray("data");
                                int totalDataCount = data.length();
                                featured_models = new ArrayList<>();

                                // Process the first 50 data sets
                                int firstBatchCount = Math.min(totalDataCount, 50);
                                for (int i = 0; i < firstBatchCount; i++) {
                                    JSONObject user = data.getJSONObject(i);
                                    // Parse data and add to featured_models
                                    featured_models.add(parseFeaturedModel(user));
                                }

                                // Display first 50 data sets
                                featured_adapter = new Featured_Adapter(getApplicationContext(), featured_models);
                                Collections.reverse(featured_models);
                                GridLayoutManager layoutManagerC = new GridLayoutManager(getApplicationContext(), 2);
                                rvView.setLayoutManager(layoutManagerC);
                                rvView.setItemAnimator(new DefaultItemAnimator());
                                rvView.setAdapter(featured_adapter);
                                featured_adapter.set(Viewall_Product_1_Activity.this);

                                // Dismiss ProgressDialog after displaying the first 50 data sets
                                progressDialog.dismiss();

                                // Process remaining data in batches of 50
                                int remainingDataCount = totalDataCount - firstBatchCount;
                                for (int i = 0; i < remainingDataCount; i++) {
                                    JSONObject user = data.getJSONObject(firstBatchCount + i);
                                    // Parse data and add to featured_models
                                    featured_models.add(parseFeaturedModel(user));
                                }
                                // Notify adapter of changes in the dataset
                                featured_adapter.notifyDataSetChanged();
                            } else {
                                // Handle case where status is false
                            }
                        } catch (JSONException e) {
                            // Handle JSON parsing error
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error response
            }
        });

        requestQueue.add(request);
    }

    // Method to parse JSON data into Featured_Model object
    private Featured_Model parseFeaturedModel(JSONObject user) throws JSONException {
        return new Featured_Model(
                user.optInt("product_id"),
                user.optDouble("product_rating"),
                user.optString("product_title"),
                user.optString("product_description"),
                user.optInt("product_rating_total"),
                ApiData.IMAGE_BASE_URL + user.optString("product_image"),
                user.optInt("product_sell_price"),
                user.optInt("product_reg_price"),
                user.optInt("added_by")
        );
    }
    void init() {
        rvView = findViewById(R.id.rvView);
        btn_back1 = findViewById(R.id.btn_back1);
    }




    @Override
    public void productClick1(int position, int product_id, int category_id, double product_rating,
                             String product_image, String product_title, int product_sell_price,
                             String product_description, int product_rating_total,
                             int product_reg_price) {
        Intent intent = new Intent(getApplicationContext(), ProductDetailPage_Featured_Activity.class);
        intent.putExtra("product_id", product_id);
        intent.putExtra("category_id", category_id);
        intent.putExtra("product_rating", product_rating);
        intent.putExtra("product_image", product_image);
        intent.putExtra("product_title", product_title);
        intent.putExtra("product_sell_price", product_sell_price);
        intent.putExtra("product_reg_price", product_reg_price);
        intent.putExtra("product_description", product_description);
        intent.putExtra("product_rating_total", product_rating_total);


        startActivity(intent);
    }

    @Override
    public void addToCart1(int position, int product_id, int category_id, double product_rating,
                           String product_image, String product_title, int product_sell_price,
                           String product_description, int product_rating_total, int product_reg_price)
    {

        ProductDatabase db = Room.databaseBuilder(getApplicationContext(), ProductDatabase.class, "cart_db")
                .allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        Boolean check = productDao.is_exist(Integer.parseInt("" + product_id));
        if (check == false) {
            int pid = product_id;
            String pname = product_title;

            int price1 = product_reg_price;
            int sale1 = product_sell_price;
            int qnt = Integer.parseInt("1");
            productDao.insertrecord(new Product(pid, pname, price1, qnt, sale1,
                    product_image));
            Toast.makeText(getApplicationContext(), "Product Added Successfully", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), "Product Already in Cart", Toast.LENGTH_SHORT).show();
        }
    }
}