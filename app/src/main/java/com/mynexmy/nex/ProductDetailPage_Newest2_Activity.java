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
import com.codebyashish.autoimageslider.Enums.ImageActionTypes;
import com.codebyashish.autoimageslider.Enums.ImageScaleType;
import com.codebyashish.autoimageslider.Interfaces.ItemsListener;
import com.codebyashish.autoimageslider.Models.ImageSlidesModel;
import com.mynexmy.nex.Activitys.Order_Summary_Activity;
import com.mynexmy.nex.Adapters.SliderAdapterProduct;
import com.mynexmy.nex.Models.Slide_Model;
import com.mynexmy.nex.Models.product_images;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Room.Product;
import com.mynexmy.nex.Room.ProductDao;
import com.mynexmy.nex.Room.ProductDatabase;

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

public class ProductDetailPage_Newest2_Activity extends AppCompatActivity implements ItemsListener {

    RatingBar ratingbar;
    List<com.mynexmy.nex.Models.product_images> product_images;
    com.codebyashish.autoimageslider.AutoImageSlider pSlider;
    ProgressDialog progressDialog;
    ArrayList<Slide_Model> slide_models = new ArrayList<>();
    int product_id, category_id, product_status, added_by, product_reg_price, product_sell_price
            , product_featured, product_tax_percent, category_parent, category_status, product_rating_total;
    double product_rating;
    String product_title, product_url, product_code, product_description, product_image,
            created_at, updated_at, category_title, category_slug, category_image, category_description;
    ImageView placeholderImageSlider ;
    private ViewPager2 viewPagerSlider ;
    private Handler handlerScratch;
    private Runnable runnableScratch;
    private LinearLayout dotIndicatorSlider   ;
    ImageView imageSlider, btn_back, imgrev, btn_Share, imgrev1;
    TextView toggleButtonforNewest, rating2, tvDiscount, pname, saleprice, tvAdd,tvBuyNow, actprice, tvrev, tvdes,
            textViewforNewest, tvrating_total1,tvdate1,tvdate, tvrating_total;
    private boolean isTextViewExpanded = false;
    String email, first_name, last_name, mobile;
    LinearLayout lldes, llRev, llMore;
    RelativeLayout placeorder,placeorderAddNewest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_page_newest);
        Utils.blackIconStatusBar(ProductDetailPage_Newest2_Activity.this, R.color.white);
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

         String logMessage = "Product ID: " + product_id +
                ", Category ID: " + category_id +
                ", Product Rating: " + product_rating +
                ", Product Image URL: " + product_image +
                ", Product Title: " + product_title +
                ", Product Description: " + product_description +
                ", Product Sell Price: " + product_sell_price +
                ", Product Rating Total: " + product_rating_total +
                ", Product Regular Price: " + product_reg_price;

// Log the combined message
        Log.d("ProductDetailPageLogdata", logMessage);

        product_images = new ArrayList<product_images>();

        slide_models.add(new Slide_Model(product_image));
        setSlider();
        getProductDetails(product_id);

        pname.setText(product_title);
        textViewforNewest.setText(product_description);
        textViewforNewest.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);  // Align text to the left
        textViewforNewest.setEllipsize(TextUtils.TruncateAt.END);  // Optionally truncate with ellipsis
        textViewforNewest.setMaxLines(2);

        toggleButtonforNewest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTextViewExpanded) {
                    textViewforNewest.setMaxLines(2);
                    toggleButtonforNewest.setText("View More");
                    isTextViewExpanded = false;
                } else {
                    textViewforNewest.setMaxLines(Integer.MAX_VALUE);
                    toggleButtonforNewest.setText("View Less");
                    isTextViewExpanded = true;
                }
            }
        });




        rating2.setText("" + product_rating);
        Random random = new Random();
        // Generate a random number between 999 and 9999
        int randomNumber = 999 + random.nextInt(9001); // This gives a number between 999 and 9999
        // Set the generated random number on both TextViews
        tvrating_total1.setText(String.valueOf(randomNumber));
        tvrating_total.setText(String.valueOf(randomNumber));



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
setRandomDateToTextViews(tvdate1,tvdate);
        BigDecimal number = new BigDecimal("2.3423424666767E13");
        float myFloat = number.floatValue();
        ratingbar.setRating(myFloat);
        float rating = Float.parseFloat("" + product_rating);
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
        placeorderAddNewest.setOnClickListener(new View.OnClickListener() {
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
        tvBuyNow = findViewById(R.id.tvBuyNow);
        tvAdd = findViewById(R.id.tvAdd);
        lldes = findViewById(R.id.lldes);
        llRev = findViewById(R.id.llRev);
        tvrev = findViewById(R.id.tvrev);
        tvdes = findViewById(R.id.tvdes);
        imgrev = findViewById(R.id.imgrev);
        ratingbar = findViewById(R.id.ratingbar);
        tvDiscount = findViewById(R.id.tvDiscount);
        imgrev1 = findViewById(R.id.imgrev1);
        placeholderImageSlider = findViewById(R.id.placeholderImageSlider);
        viewPagerSlider = findViewById(R.id.viewPagerSlider);
        dotIndicatorSlider = findViewById(R.id.dotIndicatorSlider);
        tvrating_total = findViewById(R.id.tvrating_total);
        tvdate1 = findViewById(R.id.tvdate1);
        tvdate = findViewById(R.id.tvdate);
        rating2 = findViewById(R.id.rating2);
        toggleButtonforNewest = findViewById(R.id.toggleButtonforNewest);
        textViewforNewest = findViewById(R.id.textViewforNewest);
        tvrating_total1 = findViewById(R.id.tvrating_total1);
        placeorder = findViewById(R.id.placeorder);
        placeorderAddNewest = findViewById(R.id.placeorderAddNewest);
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
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.new_progresslogo);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ApiData.product_details + "?product_id=" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            // Log the full response from the API
                            Log.d("API Response", "Response: " + response);

                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject data = jsonObject.getJSONObject("data");

                            // Log the data object to inspect its content
                            Log.d("API Response", "Data Object: " + data.toString());

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

                            // Log the extracted fields to verify their values
                            Log.d("Product Details", "Product ID: " + product_id);
                            Log.d("Product Details", "Product Title: " + product_title);
                            Log.d("Product Details", "Product Image: " + product_image);
                            Log.d("Product Details", "Product Price: " + product_reg_price);
                            Log.d("Product Details", "Category Title: " + category_title);

                            JSONArray jsonArray = data.getJSONArray("product_images");

                            // Log the length of the product images array
                            Log.d("Product Images", "Number of images: " + jsonArray.length());

                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String imageUrl = jsonObject1.getString("product_image_url");
                                    slide_models.add(new Slide_Model(imageUrl));

                                    // Log each product image URL
                                    Log.d("Product Image URL", "Image " + i + ": " + imageUrl);
                                }
                            } else {
                                slide_models.add(new Slide_Model(product_image));
                                Log.d("Product Image URL", "No additional images, using default image.");
                            }

                            setSlider();

                            // Calculate and log the discount percentage
                            int Discount_Percentage;
                            double n1, n2, Discount;
                            n1 = product_reg_price;
                            n2 = product_sell_price;
                            Discount = n1 - n2;
                            Discount_Percentage = (int) ((Discount / n1) * 100);
                            Log.d("Discount", "Discount Percentage: " + Discount_Percentage + "% off");

                            tvDiscount.setText("" + Discount_Percentage + "% off");

                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Log the error if the JSON parsing fails
                            Log.e("API Error", "JSON Parsing error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log the error response
                        Log.e("API Error", "Error in API request: " + error.toString());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void setSlider() {
        if (!slide_models.isEmpty()) {
            // Create the image adapter with the context and list of slide models
            SliderAdapterProduct imageAdapter1 = new SliderAdapterProduct(this, slide_models);

            // Assuming placeholderImageSlider and viewPagerSlider are initialized
            placeholderImageSlider.setVisibility(View.GONE);

            // Set the adapter to the ViewPager
            viewPagerSlider.setAdapter(imageAdapter1);
            addDotsIndicator();
            setupAutoScrolling();
            // Register page change callback for slider
            viewPagerSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
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
        if (dotIndicatorSlider == null || slide_models.isEmpty() ) return;

        // Remove previous dots
        dotIndicatorSlider.removeAllViews();

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
            dotIndicatorSlider.addView(dot);
        }

        // Initially update the first dot
        updateDotIndicator(0);
    }
    private void updateDotIndicator(int position) {
        if (dotIndicatorSlider == null) return;

        for (int i = 0; i < dotIndicatorSlider.getChildCount(); i++) {
            ImageView dot = (ImageView) dotIndicatorSlider.getChildAt(i);
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

                int currentItem = viewPagerSlider.getCurrentItem();
                int nextItem = (currentItem == slide_models.size() - 1) ? 0 : currentItem + 1;
                viewPagerSlider.setCurrentItem(nextItem, true); // Scroll by one position
                updateDotIndicator(nextItem); // Update the dot indicator
                handlerScratch.postDelayed(this, 5000); // Change image every 3 seconds
            }
        };

        // Initialize auto-scrolling for Slide Models Scratch
        handlerScratch.postDelayed(runnableScratch, 5000);
    }


    @Override
    public void onItemChanged(int position) {
        // Handle item change event
    }

    @Override
    public void onTouched(ImageActionTypes actionTypes, int position) {
        // Handle item touch event
    }

    @Override
    public void onItemClicked(int position) {
        // Handle item click event
    }

    public void addtocart() {
        SharedPreferences sharedPreferences = getSharedPreferences("Tax", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt("product_tax_percent", product_tax_percent);
        myEdit.apply();

        ProductDatabase db = Room.databaseBuilder(getApplicationContext(), ProductDatabase.class, "cart_db")
                .allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        Boolean check = productDao.is_exist(Integer.parseInt("" + product_id));
        if (!check) {
            productDao.insertrecord(new Product(product_id, product_title, product_reg_price, 1, product_sell_price, product_image));
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

        ProductDatabase db = Room.databaseBuilder(getApplicationContext(), ProductDatabase.class, "cart_db")
                .allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        Boolean check = productDao.is_exist(Integer.parseInt("" + product_id));
        if (!check) {
            productDao.insertrecord(new Product(product_id, product_title, product_reg_price, 1, product_sell_price, product_image));

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
