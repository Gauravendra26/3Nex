package com.mynexmy.nex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.mynexmy.nex.Activitys.Order_Summary_Activity;
//import com.mynexmy.nex.Adapters.SliderAdapterProduct;
import com.mynexmy.nex.Models.Slide_Model;
import com.mynexmy.nex.Models.product_images;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Room.Product;
import com.mynexmy.nex.Room.ProductDao;
import com.mynexmy.nex.Room.ProductDatabase;
//import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderProductDetailPage extends AppCompatActivity {


    List<com.mynexmy.nex.Models.product_images> product_images;
//    SliderView pSlider;
    ProgressDialog progressDialog;

    ArrayList<Slide_Model> slide_models = new ArrayList<>();
    int product_id, category_id, product_status, added_by, product_reg_price, product_sell_price, product_featured, category_parent, category_status, product_rating_total;
    double product_rating;
    String product_title, product_url, product_code, product_description, product_image,
            created_at, updated_at, category_title, category_slug, category_image, category_description;


    ImageView imageSlider, btn_back, imgrev, btn_like, imgrev1;

    TextView rating1, rating2, pname, saleprice, tvAdd, actprice, tvrev, tvdes, text_view, tvrating_total1, tvrating_total;
    LinearLayout lldes, llRev, llMore;
    RelativeLayout placeorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_product_detail_page);
        Utils.blackIconStatusBar(OrderProductDetailPage.this, R.color.white);
        init();


        Intent intent = getIntent();

        product_id = intent.getIntExtra("product_id", 0);


        getProductDetails(product_id);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtocart();
            }
        });
        tvrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lldes.setVisibility(View.GONE);
                llRev.setVisibility(View.VISIBLE);
                tvrev.setTextColor(getResources().getColor(R.color.main));
                tvdes.setTextColor(getResources().getColor(R.color.black));
            }

        });
        tvdes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lldes.setVisibility(View.VISIBLE);
                llRev.setVisibility(View.GONE);
                tvrev.setTextColor(getResources().getColor(R.color.black));
                tvdes.setTextColor(getResources().getColor(R.color.main));

            }

        });

        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addtocart1();


            }
        });

    }

    void init() {
        imageSlider = findViewById(R.id.imageSlider);
        pname = findViewById(R.id.pname);
        actprice = findViewById(R.id.actprice);
        saleprice = findViewById(R.id.saleprice);
        btn_back = findViewById(R.id.btn_back);
        rating1 = findViewById(R.id.rating1);
        tvAdd = findViewById(R.id.tvAdd);
        lldes = findViewById(R.id.lldes);
        llRev = findViewById(R.id.llRev);
        tvrev = findViewById(R.id.tvrev);
        tvdes = findViewById(R.id.tvdes);
        imgrev = findViewById(R.id.imgrev);


        imgrev1 = findViewById(R.id.imgrev1);
//        pSlider = findViewById(R.id.pSlider);
//        text_view = findViewById(R.id.text_view);
        tvrating_total = findViewById(R.id.tvrating_total);
        rating2 = findViewById(R.id.rating2);
        tvrating_total1 = findViewById(R.id.tvrating_total1);

        placeorder = findViewById(R.id.placeorder);

    }


    private void getProductDetails(int id) {
        //getting the progressbar
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.new_progresslogo);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ApiData.product_details + "?product_id=" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject data = jsonObject.getJSONObject("data");
                            product_id = data.getInt("product_id");
                            category_id = data.getInt("category_id");
                            product_status = data.getInt("product_status");
                            added_by = data.getInt("added_by");
                            product_reg_price = data.getInt("product_reg_price");
                            product_sell_price = data.getInt("product_sell_price");
                            product_rating = data.getDouble("product_rating");
                            product_rating_total = data.getInt("product_rating_total");
                            product_featured = data.getInt("product_featured");
                            category_parent = data.getInt("category_parent");
                            category_status = data.getInt("category_status");
                            product_title = data.getString("product_title");
                            product_url = data.getString("product_url");
                            product_code = data.getString("product_code");
                            product_description = data.getString("product_description");
                            product_image = data.getString("product_image");
                            created_at = data.getString("created_at");
                            updated_at = data.getString("updated_at");
                            category_title = data.getString("category_title");
                            category_slug = data.getString("category_slug");
                            category_image = data.getString("category_image");
                            category_description = data.getString("category_description");
                            JSONArray jsonArray = data.getJSONArray("product_images");

                            product_images = new ArrayList<product_images>();
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    slide_models.add(new Slide_Model(jsonObject1.optString("product_image_url")));
                                }
                            } else {
                                slide_models.add(new Slide_Model(product_image));

                            }
                            setSlider();

                            pname.setText(product_title);
                            text_view.setText(product_description);
                            rating1.setText("" + product_rating);
                            rating2.setText("" + product_rating);
                            tvrating_total.setText("" + product_rating_total);
                            tvrating_total1.setText("" + product_rating_total);
                            saleprice.setText("\u20B9" + product_sell_price);

                            String text = "<strike><font color=\'#757575\'>\u20B9" + product_reg_price + "</font></strike>";
                            Glide.with(getApplicationContext()).load(product_image).into(imgrev);
                            Glide.with(getApplicationContext()).load(product_image).into(imgrev1);

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

    public void setSlider() {
//        SliderAdapterProduct adapter = new SliderAdapterProduct(getApplicationContext(), slide_models);
//
//        pSlider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
//
//        pSlider.setSliderAdapter(adapter);
    }

    public void addtocart1() {

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
                    ApiData.IMAGE_BASE_URL + product_image));
            Intent i = new Intent(getApplicationContext(), Order_Summary_Activity.class);
            startActivity(i);
        } else {
            Intent i = new Intent(getApplicationContext(), Order_Summary_Activity.class);
            startActivity(i);
        }
    }

    public void addtocart()
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
                    ApiData.IMAGE_BASE_URL+product_image));
            Toast.makeText(getApplicationContext(), "Product Added to Cart Successfully", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), "Product Already in Cart", Toast.LENGTH_SHORT).show();
        }
    }
}