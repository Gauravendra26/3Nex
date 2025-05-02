package com.mynexmy.nex.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mynexmy.nex.Adapters.CategoryDetail_Adapter;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.Models.Category_Details_Model;
import com.mynexmy.nex.Product_CategoryDeatailPAge_Activity;
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

public class Category_Details_Activity extends AppCompatActivity implements
        CategoryDetail_Adapter.ProductPageClick {
    RequestQueue requestQueue;

    CategoryDetail_Adapter categoryDetails_Adapter;

    private ProductDatabase productDatabase;
    private ProductDao productDao;
    ProgressDialog progressDialog;
    int category_id,product_tax_percent, category_parent, category_status, added_by;
    String category_title, hadder, category_description, category_slug,
            created_at, updated_at;
    TextView tv_myc;
    ImageView btn_back1;

    RecyclerView rvcategoryDetails;
    List<Category_Details_Model> category_details_models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);
        Utils.blackIconStatusBar(Category_Details_Activity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        init();
        Intent intent = getIntent();
        category_id = intent.getIntExtra("category_id", 0);
        hadder = intent.getStringExtra("category_title");

        getProductDetails(category_id);

         tv_myc.setText(hadder);

        btn_back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    void init() {

        rvcategoryDetails = findViewById(R.id.rvcategoryDetails);
        btn_back1 = findViewById(R.id.btn_back1);
        tv_myc = findViewById(R.id.tv_myc);
    }

    private void getProductDetails(int id) {
        //getting the progressbar
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.new_progresslogo);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ApiData.product_list + "?category_id=" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            category_details_models = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject user = jsonArray.getJSONObject(i);
                                int product_id = user.getInt("product_id");
                                String product_title = user.getString("product_title");
                                String product_url = user.getString("product_url");
                                String product_code = user.getString("product_code");
                                String product_description = user.getString("product_description");
                                int category_id = user.getInt("category_id");
                                String product_image = ApiData.IMAGE_BASE_URL + user.getString("product_image");
                                int product_status = user.getInt("product_status");
                                int added_by = user.getInt("added_by");
                                int product_reg_price = user.getInt("product_reg_price");
                                int product_sell_price = user.getInt("product_sell_price");
                                double product_rating = user.getDouble("product_rating");
                                int product_rating_total = user.getInt("product_rating_total");
                                int product_featured = user.getInt("product_featured");
                                int product_tax_percent = user.getInt("product_tax_percent");
                                String created_at = user.getString("created_at");
                                String updated_at = user.getString("updated_at");
                                int category_parent = user.getInt("category_parent");
                                String category_title = user.getString("category_title");
                                String category_slug = user.getString("category_slug");
                                int category_status = user.getInt("category_status");
                                String category_image = user.getString("category_image");
                                String category_description = user.getString("category_description");
                                JSONArray productsImages = user.getJSONArray("product_images");

                                category_details_models.add(new Category_Details_Model(product_id, product_rating,
                                        product_title, product_description, product_rating_total,
                                        product_image, product_sell_price, product_reg_price,product_tax_percent, added_by));
                            }
                            categoryDetails_Adapter = new CategoryDetail_Adapter(getApplicationContext(),
                                    category_details_models);
                            Collections.reverse(category_details_models);
                            GridLayoutManager layoutManagerC = new GridLayoutManager(getApplicationContext(),
                                    2);

                            rvcategoryDetails.setLayoutManager(layoutManagerC);
                            rvcategoryDetails.setItemViewCacheSize(0);

                            rvcategoryDetails.setItemAnimator(new DefaultItemAnimator());
                            rvcategoryDetails.setAdapter(categoryDetails_Adapter);
                            categoryDetails_Adapter.set(Category_Details_Activity.this);


                        } catch (JSONException e) {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        //displaying the error in toast if occur
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }

    @Override
    public void productClick(int position, int product_id, int category_id, double product_rating,
                             String product_image, String product_title, int product_sell_price, String product_description,
                             int product_rating_total, int product_reg_price, int product_tax_percent) {
        Intent intent = new Intent(getApplicationContext(), Product_CategoryDeatailPAge_Activity.class);

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
    public void addToCart(int position, int product_id, int category_id, double product_rating,
                          String product_image, String product_title, int product_sell_price,
                          String product_description, int product_rating_total, int product_reg_price, int product_tax_percent)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("Tax", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt("product_tax_percent", product_tax_percent);
        myEdit.apply();
        myEdit.commit();


        ProductDatabase db = Room.databaseBuilder(getApplicationContext(), ProductDatabase.class, "cart_db")
                .allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        Boolean check = productDao.is_exist(Integer.parseInt(""+product_id));
        if (check == false) {

            int pid = product_id;
            String pname = product_title;
            int price1 = product_reg_price;
            int sale1 = product_sell_price;
            int qnt= Integer.parseInt("1");
            productDao.insertrecord(new Product(pid, pname, price1, qnt, sale1, product_image));
            Toast.makeText(getApplicationContext(), ""+pname+" Added to Cart Successfully", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(getApplicationContext(), "Product Already in Cart", Toast.LENGTH_SHORT).show();

        }
    }
}

