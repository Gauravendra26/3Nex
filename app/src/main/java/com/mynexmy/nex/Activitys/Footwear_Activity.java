package com.mynexmy.nex.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mynexmy.nex.Adapters.ImageSliderAdapterFootwear;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.Models.Slide_Model_Footwear;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Footwear_Activity extends AppCompatActivity {
    private ImageView imgbackFootwear;
    ArrayList<Slide_Model_Footwear> slide_model_footwears = new ArrayList<>();
    ImageView placeholderImageFootwear;
    private ViewPager2 viewPagerFootwear;
    private LinearLayout dotIndicatorFootwear;
    private Handler handlerFootwear;
    private Runnable runnableFootwear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_footwear);

        Utils.blackIconStatusBar(this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        init();
        fetchFootwearData();

        imgbackFootwear.setOnClickListener(v -> finish());

    }

    private void init() {
        imgbackFootwear = findViewById(R.id.imgbackFootwear);
        placeholderImageFootwear = findViewById(R.id.placeholderImageFootwear);
        viewPagerFootwear = findViewById(R.id.viewPagerFootwear);
        dotIndicatorFootwear = findViewById(R.id.dotIndicatorFootwear);

    }

    public void fetchFootwearData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ApiData.Banners,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("FootwearData", response);

                            JSONObject jsonObject = new JSONObject(response);

                            boolean status = jsonObject.getBoolean("status");
                            slide_model_footwears.clear();  // Clear any previous data

                            if (status) {
                                // Check if "data" object exists
                                if (jsonObject.has("data")) {
                                    // Get the "data" object
                                    JSONObject dataObject = jsonObject.getJSONObject("data");

                                    // Retrieve the footwear data
                                    JSONArray mobile_footwear = dataObject.optJSONArray("footwear");

                                    // Check if footwear data is available
                                    if (mobile_footwear != null && mobile_footwear.length() > 0) {
                                        // Process each footwear image URL
                                        for (int i = 0; i < mobile_footwear.length(); i++) {
                                            String imageUrl = mobile_footwear.getString(i);
                                            slide_model_footwears.add(new Slide_Model_Footwear(imageUrl,
                                                    String.valueOf(i), 2));
                                            Log.e("FootwearData", "" + imageUrl);
                                        }

                                        // Set up the adapter and ViewPager for footwear slider
                                        if (!slide_model_footwears.isEmpty()) {
                                            ImageSliderAdapterFootwear imageAdapter = new ImageSliderAdapterFootwear(
                                                    getApplicationContext(), slide_model_footwears
                                            );

                                            placeholderImageFootwear.setVisibility(View.GONE);
                                            viewPagerFootwear.setAdapter(imageAdapter);
                                            addDotsIndicatorFootwear();
                                            setupAutoScrollingFootwear();
                                            viewPagerFootwear.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                                @Override
                                                public void onPageSelected(int position) {
                                                    super.onPageSelected(position);
                                                    updateDotIndicatorFootwear(position);
                                                }
                                            });
                                        }

                                    } else {
                                        Log.e("FootwearData", "No footwear data found.");
                                    }
                                } else {
                                    Log.e("FootwearData", "No 'data' object found in JSON response");
                                }
                            } else {
                                Log.e("FootwearData", "Status is false in response");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("FootwearData", "Error parsing JSON: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log the Volley error object
                        Log.e("Volley Error", "Error: " + error.toString(), error);
                    }
                }
        );

        // Add the request to the request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void addDotsIndicatorFootwear() {
        // Ensure the fragment is attached and context is not null
        if (getApplicationContext() == null || dotIndicatorFootwear == null || slide_model_footwears.isEmpty())
            return;

        // Remove previous dots
        dotIndicatorFootwear.removeAllViews();

        // Add new dots based on the number of images
        for (int i = 0; i < slide_model_footwears.size(); i++) {
            ImageView dot = new ImageView(getApplicationContext());
            dot.setImageResource(R.drawable.dot_unselected);

            // Access resources safely
            int dotSize = getResources().getDimensionPixelSize(R.dimen.dot_size);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dotSize, dotSize);
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);
            dotIndicatorFootwear.addView(dot);
        }

        // Initially update the first dot
        updateDotIndicatorFootwear(0);
    }

    private void updateDotIndicatorFootwear(int position) {
        if (dotIndicatorFootwear == null) return;

        for (int i = 0; i < dotIndicatorFootwear.getChildCount(); i++) {
            ImageView dot = (ImageView) dotIndicatorFootwear.getChildAt(i);
            if (i == position) {
                dot.setImageResource(R.drawable.dot_selected);
            } else {
                dot.setImageResource(R.drawable.dot_unselected);
            }
        }
    }


    private void setupAutoScrollingFootwear() {
        handlerFootwear = new Handler(Looper.getMainLooper()); // Handler for Slide Models Product
        runnableFootwear = new Runnable() {
            @Override
            public void run() {
                if (slide_model_footwears.isEmpty()) return;

                int currentItem = viewPagerFootwear.getCurrentItem();
                int nextItem = (currentItem == slide_model_footwears.size() - 1) ? 0 : currentItem + 1;
                viewPagerFootwear.setCurrentItem(nextItem, true); // Scroll by one position
                updateDotIndicatorFootwear(nextItem); // Update the dot indicator
                handlerFootwear.postDelayed(this, 3000); // Change image every 3 seconds
            }
        };

        // Initialize auto-scrolling for Slide Models Product
        handlerFootwear.postDelayed(runnableFootwear, 3000);
    }


}