package com.mynexmy.nex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.mynexmy.nex.Activitys.Order_Summary_Activity;
//import com.mynexmy.nex.Adapters.SliderAdapterProduct;
import com.mynexmy.nex.Adapters.SliderAdapterProduct;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class Product_CategoryDeatailPAge_Activity extends AppCompatActivity {
    List<com.mynexmy.nex.Models.product_images> product_images;
    //    SliderView pSlider;
    RatingBar ratingbar;
    ProgressDialog progressDialog;
    ArrayList<Slide_Model> slide_models = new ArrayList<>();
    int product_id, category_id, product_status, added_by, product_reg_price, product_sell_price, product_featured, product_tax_percent, category_parent, category_status, product_rating_total;
    double product_rating;
    String product_title, product_url, product_code, product_description, product_image,
            created_at, updated_at, category_title, category_slug, category_image, category_description;
    ImageView placeholderImageSliderforCategoryDeatailPAge;
    private ViewPager2 viewPagerSliderforCategoryDeatailPAge;
    private Handler handlerScratch;
    private Runnable runnableScratch;
    private LinearLayout dotIndicatorSliderforCategoryDeatailPAge;

    ImageView imageSlider, btn_back, imgrev, btn_Share, imgrev1;

    TextView rating1, rating2, tvDiscount, pname, saleprice, tvAdd,tvBuyNow, actprice, tvrev, tvdes, toggleButtonforCategoryDeatailPAge,
            textViewforCategoryDeatailPAge, tvdate1, tvdate, tvrating_total1, tvrating_total;
    LinearLayout lldes, llRev, llMore;
    RelativeLayout placeorder,placeorderCategoryDetail;
    private boolean isTextViewExpanded = false;
    String email, first_name, last_name, mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category_deatail_page);
        Utils.blackIconStatusBar(Product_CategoryDeatailPAge_Activity.this, R.color.white);
        init();
        getDataProfile();

        Intent intent = getIntent();

        product_id = intent.getIntExtra("product_id", 0);
        category_id = intent.getIntExtra("category_id", 0);
        product_rating = intent.getDoubleExtra("product_rating", 0);
        product_image = intent.getStringExtra("product_image");
        product_title = intent.getStringExtra("product_title");
        product_description = intent.getStringExtra("product_description");
        product_sell_price = intent.getIntExtra("product_sell_price", 0);
        product_rating_total = intent.getIntExtra("product_rating_total", 0);
        product_reg_price = intent.getIntExtra("product_reg_price", 0);
        product_images = new ArrayList<product_images>();
        slide_models.add(new Slide_Model(product_image));
        setSlider();
        getProductDetails(product_id);

        pname.setText(product_title);
        textViewforCategoryDeatailPAge.setText(product_description);
        textViewforCategoryDeatailPAge.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);  // Align text to the left
        textViewforCategoryDeatailPAge.setEllipsize(TextUtils.TruncateAt.END);  // Optionally truncate with ellipsis
        textViewforCategoryDeatailPAge.setMaxLines(2);

        toggleButtonforCategoryDeatailPAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTextViewExpanded) {
                    textViewforCategoryDeatailPAge.setMaxLines(2);
                    toggleButtonforCategoryDeatailPAge.setText("View More");
                    isTextViewExpanded = false;
                } else {
                    textViewforCategoryDeatailPAge.setMaxLines(Integer.MAX_VALUE);
                    toggleButtonforCategoryDeatailPAge.setText("View Less");
                    isTextViewExpanded = true;
                }
            }
        });


        Random random = new Random();
        // Generate a random number between 999 and 9999
        int randomNumber = 999 + random.nextInt(9001); // This gives a number between 999 and 9999
        // Set the generated random number on both TextViews
        tvrating_total1.setText(String.valueOf(randomNumber));
        tvrating_total.setText(String.valueOf(randomNumber));
        setRandomDateToTextViews(tvdate1, tvdate);

        rating2.setText("" + product_rating);

        saleprice.setText("\u20B9" + product_sell_price);

        String text = "<strike><font color=\'#757575\'>\u20B9" + product_reg_price + "</font></strike>";
        actprice.setText(Html.fromHtml(text));
        Glide.with(getApplicationContext()).load(product_image).into(imgrev);
        Glide.with(getApplicationContext()).load(product_image).into(imgrev1);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        BigDecimal number = new BigDecimal("2.3423424666767E13");
        float myFloat = number.floatValue();
        ratingbar.setRating(myFloat);
        float rating = Float.parseFloat("" + product_rating); // Set the desired rating value
        ratingbar.setRating(rating);

        btn_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                String text = "Check out this Application : ";
                i.putExtra(Intent.EXTRA_TEXT, text + "https://play.google.com/store/apps/details?id=com.mynexmy.nex");
                startActivity(Intent.createChooser(i, "Share Via"));
            }
        });
        placeorderCategoryDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtocart();
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                tvAdd.startAnimation(myAnim);
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
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                tvBuyNow.startAnimation(myAnim);
            }
        });

    }

    void init() {
        imageSlider = findViewById(R.id.imageSlider);
        pname = findViewById(R.id.pname);
        btn_Share = findViewById(R.id.btn_Share);
        actprice = findViewById(R.id.actprice);
        saleprice = findViewById(R.id.saleprice);
        btn_back = findViewById(R.id.btn_back);
        rating1 = findViewById(R.id.rating1);
        tvAdd = findViewById(R.id.tvAdd);
        tvBuyNow = findViewById(R.id.tvBuyNow);
        lldes = findViewById(R.id.lldes);
        llRev = findViewById(R.id.llRev);
        tvrev = findViewById(R.id.tvrev);
        tvdes = findViewById(R.id.tvdes);
        imgrev = findViewById(R.id.imgrev);
        tvDiscount = findViewById(R.id.tvDiscount);

        ratingbar = findViewById(R.id.ratingbar);

        imgrev1 = findViewById(R.id.imgrev1);
        placeholderImageSliderforCategoryDeatailPAge = findViewById(R.id.placeholderImageSliderforCategoryDeatailPAge);
        viewPagerSliderforCategoryDeatailPAge = findViewById(R.id.viewPagerSliderforCategoryDeatailPAge);
        dotIndicatorSliderforCategoryDeatailPAge = findViewById(R.id.dotIndicatorSliderforCategoryDeatailPAge);
        toggleButtonforCategoryDeatailPAge = findViewById(R.id.toggleButtonforCategoryDeatailPAge);
        textViewforCategoryDeatailPAge = findViewById(R.id.textViewforCategoryDeatailPAge);
        tvrating_total = findViewById(R.id.tvrating_total);
        rating2 = findViewById(R.id.rating2);
        tvrating_total1 = findViewById(R.id.tvrating_total1);
        tvdate1 = findViewById(R.id.tvdate1);
        tvdate = findViewById(R.id.tvdate);
        placeorder = findViewById(R.id.placeorder);
        placeorderCategoryDetail = findViewById(R.id.placeorderCategoryDetail);
    }

    void getDataProfile() {

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, ApiData.Profile,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("status")) {
                        JSONObject jsonObject1 = response.getJSONObject("data");
                        JSONObject jsonObject = jsonObject1.getJSONObject("details");

                        email = jsonObject.optString("email");
                        first_name = jsonObject.optString("first_name");
                        last_name = jsonObject.optString("last_name");


                    }
                } catch (JSONException e) {
                    Log.e("dataofLogin", "Error parsing response", e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "Error: " + error.getMessage());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + sharedPreferences.getString("Login_Token", ""));
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(req);
    }


    public void setRandomDateToTextViews(TextView tvdate1, TextView tvdate) {
        // Create an instance of Random to generate random dates and months
        Random random = new Random();

        // Generate a random day (between 1 and 28 to avoid month-length issues)
        int randomDay1 = random.nextInt(28) + 1; // Random day for tvdate1
        int randomDay2 = random.nextInt(28) + 1; // Random day for tvdate2, different from tvdate1
        while (randomDay2 == randomDay1) {
            randomDay2 = random.nextInt(28) + 1; // Ensure the days are different
        }

        // Generate a random month (1-12)
        int randomMonth1 = random.nextInt(12);
        int randomMonth2 = random.nextInt(12);
        while (randomMonth2 == randomMonth1) {
            randomMonth2 = random.nextInt(12); // Ensure the months are different
        }

        // Set the year to the current year
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // Get the first 4 letters of the random month (e.g., "Janu" for January)
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};
        String month1 = months[randomMonth1];
        String month2 = months[randomMonth2];

        // Create the formatted date strings
        String formattedDate1 = String.format(Locale.getDefault(), "%02d %s %d", randomDay1, month1, currentYear);
        String formattedDate2 = String.format(Locale.getDefault(), "%02d %s %d", randomDay2, month2, currentYear);

        // Set the formatted dates on the TextViews
        tvdate1.setText(formattedDate1);
        tvdate.setText(formattedDate2);
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
                            product_tax_percent = data.getInt("product_tax_percent");
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
                                    slide_models.add(new Slide_Model(jsonObject1.getString("product_image_url")));
                                }
                            } else {
                                slide_models.add(new Slide_Model(product_image));

                            }
                            setSlider();

                            int Discount_Percentage;
                            double n1, n2, Discount;
                            n1 = product_reg_price;
                            n2 = product_sell_price;
                            Discount = n1 - n2;
                            Discount_Percentage = (int) ((Discount / n1) * 100);

                            tvDiscount.setText("" + Discount_Percentage + "% off");


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
        if (!slide_models.isEmpty()) {
            // Create the image adapter with the context and list of slide models
            SliderAdapterProduct imageAdapter1 = new SliderAdapterProduct(this, slide_models);

            // Assuming placeholderImageSlider and viewPagerSlider are initialized
            placeholderImageSliderforCategoryDeatailPAge.setVisibility(View.GONE);

            // Set the adapter to the ViewPager
            viewPagerSliderforCategoryDeatailPAge.setAdapter(imageAdapter1);
            addDotsIndicator();
            setupAutoScrolling();
            // Register page change callback for slider
            viewPagerSliderforCategoryDeatailPAge.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    // Optional: update any indicators or UI on page selection
                    updateDotIndicator(position);
                }
            });
        } else {
            Log.e("sliderData", "No data found for recyclerViewProduct (slide_model_products is empty)");
        }
    }

    private void addDotsIndicator() {
        // Check if dotIndicatorSlider is null or has no child views
        if (dotIndicatorSliderforCategoryDeatailPAge == null || slide_models.isEmpty()) return;

        // Remove previous dots
        dotIndicatorSliderforCategoryDeatailPAge.removeAllViews();

        // Add new dots based on the number of images
        for (int i = 0; i < slide_models.size(); i++) {
            ImageView dot = new ImageView(getApplicationContext());
            dot.setImageResource(R.drawable.dot_unselected);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    getResources().getDimensionPixelSize(R.dimen.dot_sizeForDetailpage),
                    getResources().getDimensionPixelSize(R.dimen.dot_sizeForDetailpage)
            );
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);
            dotIndicatorSliderforCategoryDeatailPAge.addView(dot);
        }

        // Initially update the first dot
        updateDotIndicator(0);
    }

    private void updateDotIndicator(int position) {
        if (dotIndicatorSliderforCategoryDeatailPAge == null) return;

        for (int i = 0; i < dotIndicatorSliderforCategoryDeatailPAge.getChildCount(); i++) {
            ImageView dot = (ImageView) dotIndicatorSliderforCategoryDeatailPAge.getChildAt(i);
            if (i == position) {
                dot.setImageResource(R.drawable.dot_selected);
            } else {
                dot.setImageResource(R.drawable.dot_unselected);
            }
        }
    }

    private void setupAutoScrolling() {
        handlerScratch = new Handler(Looper.getMainLooper()); // Handler for Slide Models Scratch
        runnableScratch = new Runnable() {
            @Override
            public void run() {
                if (slide_models.isEmpty()) return;

                int currentItem = viewPagerSliderforCategoryDeatailPAge.getCurrentItem();
                int nextItem = (currentItem == slide_models.size() - 1) ? 0 : currentItem + 1;
                viewPagerSliderforCategoryDeatailPAge.setCurrentItem(nextItem, true); // Scroll by one position
                updateDotIndicator(nextItem); // Update the dot indicator
                handlerScratch.postDelayed(this, 5000); // Change image every 3 seconds
            }
        };

        // Initialize auto-scrolling for Slide Models Scratch
        handlerScratch.postDelayed(runnableScratch, 5000);
    }


    public void addtocart() {
        SharedPreferences sharedPreferences = getSharedPreferences("Tax", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt("product_tax_percent", product_tax_percent);
        myEdit.apply();
        myEdit.commit();
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
            Toast.makeText(getApplicationContext(), "Product Added to Cart Successfully", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), "Product Already in Cart", Toast.LENGTH_SHORT).show();
        }
    }

    public void addtocart1() {
        SharedPreferences sharedPreferences = getSharedPreferences("Tax", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt("product_tax_percent", product_tax_percent);
        myEdit.apply();
        myEdit.commit();

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
            if (first_name == null || first_name.equals("null")) {
                Intent homeIntent = new Intent(getApplicationContext(), Signup.class);
                homeIntent.putExtra("mycartIndicator", 11);
                startActivity(homeIntent);

            } else {
                Intent i = new Intent(getApplicationContext(), Order_Summary_Activity.class);
                startActivity(i);

            }

        } else {
            if (first_name == null || first_name.equals("null")) {
                Intent homeIntent = new Intent(getApplicationContext(), Signup.class);
                homeIntent.putExtra("mycartIndicator", 11);
                startActivity(homeIntent);

            } else {
                Intent i = new Intent(getApplicationContext(), Order_Summary_Activity.class);
                startActivity(i);

            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (handlerScratch != null && runnableScratch != null) {
            handlerScratch.removeCallbacks(runnableScratch); // Stop auto-scrolling for Slide Models Scratch
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (handlerScratch != null && runnableScratch != null) {
            handlerScratch.postDelayed(runnableScratch, 5000); // Resume auto-scrolling for Slide Models Scratch
        }

    }

}
