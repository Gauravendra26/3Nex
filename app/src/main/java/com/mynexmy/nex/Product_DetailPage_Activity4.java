package com.mynexmy.nex;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
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

public class Product_DetailPage_Activity4 extends AppCompatActivity {
    RatingBar ratingbar;
    List<com.mynexmy.nex.Models.product_images> product_images;
//    SliderView pSlider;
    ProgressDialog progressDialog;

    ArrayList<Slide_Model> slide_models = new ArrayList<>();
    int product_id, category_id, product_status, added_by, product_reg_price,
            product_sell_price, product_featured, category_parent, category_status, product_rating_total;
    double product_rating;
    String product_title, product_url, product_code, product_description, product_image,
            created_at, updated_at, category_title, category_slug, category_image, category_description;
    ImageView placeholderImageSlider ;
    private ViewPager2 viewPagerSlider ;
    private Handler handlerScratch;
    private Runnable runnableScratch;
    private LinearLayout dotIndicatorSlider   ;
    private boolean isTextViewExpanded = false;

    ImageView imageSlider, btn_back, imgrev, btn_Share, imgrev1, add;

    TextView rating1, rating2,tvDiscount, pname, saleprice, tvPrice, actprice, tvrev, tvdes, text_view,
            tvrating_total1, tvrating_total ,tvdate1,tvdate, toggleButtonofMyCartProduct,textViewofMyCartProduct;
    LinearLayout lldes, llRev, llMore;
    RelativeLayout placeorder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_page4);
        Utils.blackIconStatusBar(Product_DetailPage_Activity4.this, R.color.white);
        init();

        Intent intent = getIntent();

        int pid = intent.getIntExtra("pid",0);
        product_image = intent.getStringExtra("product_image");

        product_images = new ArrayList<product_images>();

        slide_models.add(new Slide_Model(product_image));
        setSlider();
        getProductDetails(pid);
        setRandomDateToTextViews(tvdate1,tvdate);
        toggleButtonofMyCartProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTextViewExpanded) {
                    textViewofMyCartProduct.setMaxLines(2);
                    toggleButtonofMyCartProduct.setText("View More");
                    isTextViewExpanded = false;
                } else {
                    textViewofMyCartProduct.setMaxLines(Integer.MAX_VALUE);
                    toggleButtonofMyCartProduct.setText("View Less");
                    isTextViewExpanded = true;
                }
            }
        });



        btn_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                String text="Check out this Application : ";
                i.putExtra(Intent.EXTRA_TEXT,text+"https://play.google.com/store/apps/details?id=com.mynexmy.nex");
                startActivity(Intent.createChooser(i,"Share Via"));
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (first_name == null || first_name.equals("null")) {
//                    Intent homeIntent = new Intent(getApplicationContext(), Signup.class);
//                    homeIntent.putExtra("mycartIndicator", 11);
//                    startActivity(homeIntent);
//                    finish();
//                } else {
//                    Intent i = new Intent(getApplicationContext(), Order_Summary_Activity.class);
//                    i.putExtra("product_sell_price", product_sell_price);
//                    startActivity(i);
//                }

                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
                placeorder.startAnimation(myAnim);
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

    }

    void init() {
//        imageSlider = findViewById(R.id.imageSlider);
        pname = findViewById(R.id.pname);
        btn_Share = findViewById(R.id.btn_Share);
        actprice = findViewById(R.id.actprice);
        saleprice = findViewById(R.id.saleprice);
        btn_back = findViewById(R.id.btn_back);
//        rating1 = findViewById(R.id.rating1);
        tvPrice = findViewById(R.id.tvPrice);
        placeholderImageSlider = findViewById(R.id.placeholderImageSlider);
        viewPagerSlider = findViewById(R.id.viewPagerSlider);
        dotIndicatorSlider = findViewById(R.id.dotIndicatorSlider);

        lldes = findViewById(R.id.lldes);
        llRev = findViewById(R.id.llRev);
        tvrev = findViewById(R.id.tvrev);
        tvdes = findViewById(R.id.tvdes);
        imgrev = findViewById(R.id.imgrev);
        add = findViewById(R.id.add);
        ratingbar = findViewById(R.id.ratingbar);
        tvDiscount = findViewById(R.id.tvDiscount);

        imgrev1 = findViewById(R.id.imgrev1);
        toggleButtonofMyCartProduct = findViewById(R.id.toggleButtonofMyCartProduct);
        textViewofMyCartProduct = findViewById(R.id.textViewofMyCartProduct);
        tvrating_total = findViewById(R.id.tvrating_total);
        rating2 = findViewById(R.id.rating2);
        tvrating_total1 = findViewById(R.id.tvrating_total1);
        tvdate1 = findViewById(R.id.tvdate1);
        tvdate = findViewById(R.id.tvdate);

        placeorder = findViewById(R.id.placeorder);
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
                ApiData.product_details +"?product_id=" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                             JSONObject jsonObject = new JSONObject(response);
                            JSONObject data = jsonObject.getJSONObject("data");
                            product_title = data.optString("product_title");
                            product_description = data.optString("product_description");
                            product_image = data.optString("product_image");
                            product_reg_price = data.optInt("product_reg_price");
                            product_sell_price = data.optInt("product_sell_price");
                            product_rating = data.optDouble("product_rating");
                            product_rating_total = data.optInt("product_rating_total");
                            product_featured = data.optInt("product_featured");
                            category_parent = data.optInt("category_parent");
                            category_status = data.optInt("category_status");
                            int product_tax_percent  = data.optInt("product_tax_percent");

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
                            textViewofMyCartProduct.setText(product_description);
                            textViewofMyCartProduct.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);  // Align text to the left
                            textViewofMyCartProduct.setEllipsize(TextUtils.TruncateAt.END);  // Optionally truncate with ellipsis
                            textViewofMyCartProduct.setMaxLines(2);
//                            rating1.setText("" + product_rating);
                            rating2.setText("" + product_rating);
                            Random random = new Random();
                            // Generate a random number between 999 and 9999
                            int randomNumber = 999 + random.nextInt(9001); // This gives a number between 999 and 9999
                            // Set the generated random number on both TextViews
                            tvrating_total1.setText(String.valueOf(randomNumber));
                            tvrating_total.setText(String.valueOf(randomNumber));

                            saleprice.setText("\u20B9" + product_sell_price);
                            tvPrice.setText("\u20B9" + product_sell_price);
                            String text = "<strike><font color=\'#757575\'>\u20B9" + product_reg_price + "</font></strike>";
                            actprice.setText(Html.fromHtml(text));
                            Glide.with(getApplicationContext()).load(ApiData.IMAGE_BASE_URL+product_image).into(imgrev);
                            Glide.with(getApplicationContext()).load(ApiData.IMAGE_BASE_URL+product_image).into(imgrev1);

                            BigDecimal number = new BigDecimal("2.3423424666767E13");
                            float myFloat = number.floatValue();
                            ratingbar.setRating(myFloat);
                            float rating = Float.parseFloat(""+product_rating); // Set the desired rating value
                            ratingbar.setRating(rating);
                            int  Discount_Percentage;
                            double n1,n2,Discount;
                            n1= product_reg_price ;
                            n2=  product_sell_price;
                            Discount = n1-n2;
                            Discount_Percentage = (int) ((Discount /n1)*100);

                            tvDiscount.setText("" + Discount_Percentage+"% off");

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

}