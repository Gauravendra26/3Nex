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
import com.mynexmy.nex.Adapters.Newest_Adapter;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.Models.Newest_Model;
import com.mynexmy.nex.ProductDetailPage_Newest2_Activity;
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

public class Viewall_Product_2_Activity extends AppCompatActivity implements Newest_Adapter.ProductPageClick{
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    List<Newest_Model> newest_models;
    Newest_Adapter newest_adapter;
    RecyclerView rvView;
    ImageView btn_back1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewall_product2);
        Utils.blackIconStatusBar(Viewall_Product_2_Activity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        init();

        getView();

        btn_back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void getView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setCancelable(false); // Make the dialog non-cancelable
        progressDialog.setCanceledOnTouchOutside(false); // Make the dialog not disappear when touched outside

        progressDialog.setContentView(R.layout.new_progresslogo);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                ApiData.product_list, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressDialog.dismiss();

                            if (response.getBoolean("status") == true) {
                                newest_models = new ArrayList<Newest_Model>();
                                JSONArray data = response.getJSONArray("data");

                                for (int i = data.length() - 1; i >= 0; i--){
                                    JSONObject user = data.getJSONObject(i);
                                    int product_id = user.optInt("product_id");
                                    String product_title = user.optString("product_title");
                                    String product_url = user.optString("product_url");
                                    String product_code = user.optString("product_code");
                                    String product_description = user.optString("product_description");
                                    int category_id = user.optInt("category_id");
                                    String product_image = ApiData.IMAGE_BASE_URL + user.optString("product_image");
                                    int product_status = user.optInt("product_status");
                                    int added_by = user.optInt("added_by");
                                    int product_reg_price = user.optInt("product_reg_price");
                                    int product_sell_price = user.optInt("product_sell_price");
                                    double product_rating = user.optDouble("product_rating");
                                    int product_rating_total = user.optInt("product_rating_total");
                                    int product_featured = user.optInt("product_featured");
                                    int product_tax_percent = user.optInt("product_tax_percent");
                                    String created_at = user.optString("created_at");
                                    String updated_at = user.optString("updated_at");
                                    int category_parent = user.optInt("category_parent");
                                    String category_title = user.optString("category_title");
                                    String category_slug = user.optString("category_slug");
                                    int category_status = user.optInt("category_status");
                                    String category_image = user.optString("category_image");
                                    String category_description = user.optString("category_description");
                                    JSONArray productsImages = user.getJSONArray("product_images");


                                    newest_models.add(new Newest_Model(product_id, product_rating,
                                            product_title, product_description, product_rating_total,
                                            product_image, product_sell_price, product_reg_price,product_tax_percent,
                                            added_by));

                                }

                                newest_adapter = new Newest_Adapter(getApplicationContext(),
                                        newest_models);
//                                Collections.reverse(newest_models);
                                GridLayoutManager layoutManagerC = new GridLayoutManager(getApplicationContext(),
                                        2);


                                rvView.setLayoutManager(layoutManagerC);
                                rvView.setItemAnimator(new DefaultItemAnimator());
                                rvView.setAdapter(newest_adapter);
                                newest_adapter.set(Viewall_Product_2_Activity.this);

                            } else {

                            }  //ye wale
                        } catch (JSONException e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);

    }


    void init() {
        rvView = findViewById(R.id.rvView);
        btn_back1 = findViewById(R.id.btn_back1);
    }

    @Override
    public void dataUpdate(int position) {

    }

    @Override
    public void productClick1(int position, int product_id, int category_id, double product_rating,
                             String product_image, String product_title, int product_sell_price,
                             String product_description, int product_rating_total,
                             int product_reg_price,int product_tax_percent) {
        Intent intent = new Intent(getApplicationContext(), ProductDetailPage_Newest2_Activity.class);

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
    public void addToCart1(int position, int product_id, int category_id,
                          double product_rating, String product_image,
                          String product_title, int product_sell_price,
                          String product_description, int product_rating_total
            , int product_reg_price, int product_tax_percent)
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
            Toast.makeText(getApplicationContext(), "Product Added to Cart Successfully", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), "Product Already in Cart", Toast.LENGTH_SHORT).show();
        }
    }
}