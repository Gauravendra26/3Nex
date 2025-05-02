package com.mynexmy.nex.Activitys;

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
import com.mynexmy.nex.Adapters.ImageSliderAdapterMovie;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.Models.Slide_Model_Movie;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PickNPay_Activity extends AppCompatActivity {
    ArrayList<Slide_Model_Movie> slide_model_movie = new ArrayList<>();
    ImageView placeholderImageMovie, imgbackPicknPay;
    private ViewPager2 viewPagerMovie;
    private LinearLayout dotIndicatorMovie;
    private Handler handlerMovie;
    private Runnable runnableMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_npay);
        Utils.blackIconStatusBar(this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        init();
        fetchMovieSliderImages();


        imgbackPicknPay.setOnClickListener(v -> finish());

    }

    private void init() {
        imgbackPicknPay = findViewById(R.id.imgbackPicknPay);
        placeholderImageMovie = findViewById(R.id.placeholderImageMovie);
        viewPagerMovie = findViewById(R.id.viewPagerMovie);
        dotIndicatorMovie = findViewById(R.id.dotIndicatorMovie);

    }


    public void fetchMovieSliderImages() {
        // Initialize the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Make the network request to fetch slider data
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ApiData.Sliders,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("sliderData", response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean("status");

                            // If the status is true, process the slider data
                            if (status) {
                                if (jsonObject.has("data")) {
                                    JSONObject dataObject = jsonObject.getJSONObject("data");

                                    // Extract the "home" array
                                    if (dataObject.has("home")) {
                                        JSONArray homeArray = dataObject.getJSONArray("home");

                                        // Clear previous movie data before populating the list
                                        slide_model_movie.clear();

                                        // Process images in the "home" array for movie section (i >= 15 && i <= 18)
                                        for (int i = 15; i <= 18; i++) {
                                            if (i < homeArray.length()) {
                                                String imageUrl = homeArray.getString(i);
                                                Log.d("sliderData", "Adding movie image URL: " + imageUrl);
                                                slide_model_movie.add(new Slide_Model_Movie(imageUrl,
                                                        String.valueOf(i), 2));
                                            }
                                        }

                                        // Set up RecyclerView and adapter for Movie section
                                        if (!slide_model_movie.isEmpty()) {
                                            ImageSliderAdapterMovie imageAdapter1 = new ImageSliderAdapterMovie(
                                                    getApplicationContext(),  // Pass the context (ensure it's a FragmentActivity)
                                                    slide_model_movie                  // Pass the slide_model_movie list
                                            );
                                            placeholderImageMovie.setVisibility(View.GONE);
                                            // Set the adapter to the ViewPager
                                            viewPagerMovie.setAdapter(imageAdapter1);
                                            addDotsIndicatorMovie();
                                            setupAutoScrollingMovie();

                                            viewPagerMovie.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                                @Override
                                                public void onPageSelected(int position) {
                                                    super.onPageSelected(position);
                                                    updateDotIndicatorMovie(position);
                                                }
                                            });

                                        } else {
                                            Log.e("sliderData", "No data found for movie section (slide_model_movie is empty)");
                                        }
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            Log.e("sliderData", "Error parsing response: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("sliderData", "Error fetching movie slider images: " + error.getMessage());
                    }
                });

        // Add the request to the request queue
        requestQueue.add(stringRequest);
    }

    private void addDotsIndicatorMovie() {
        // Ensure the fragment is attached and context is not null
        if (getApplicationContext() == null || dotIndicatorMovie == null || slide_model_movie.isEmpty())
            return;

        // Remove previous dots
        dotIndicatorMovie.removeAllViews();

        // Add new dots based on the number of images
        for (int i = 0; i < slide_model_movie.size(); i++) {
            ImageView dot = new ImageView(getApplicationContext());
            dot.setImageResource(R.drawable.dot_unselected);

            // Access resources safely
            int dotSize = getResources().getDimensionPixelSize(R.dimen.dot_size);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dotSize, dotSize);
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);
            dotIndicatorMovie.addView(dot);
        }

        // Initially update the first dot
        updateDotIndicatorMovie(0);
    }

    private void updateDotIndicatorMovie(int position) {
        if (dotIndicatorMovie == null) return;

        for (int i = 0; i < dotIndicatorMovie.getChildCount(); i++) {
            ImageView dot = (ImageView) dotIndicatorMovie.getChildAt(i);
            if (i == position) {
                dot.setImageResource(R.drawable.dot_selected);
            } else {
                dot.setImageResource(R.drawable.dot_unselected);
            }
        }
    }

    private void setupAutoScrollingMovie() {
        handlerMovie = new Handler(Looper.getMainLooper()); // Handler for Slide Models Product
        runnableMovie = new Runnable() {
            @Override
            public void run() {
                if (slide_model_movie.isEmpty()) return;

                int currentItem = viewPagerMovie.getCurrentItem();
                int nextItem = (currentItem == slide_model_movie.size() - 1) ? 0 : currentItem + 1;
                viewPagerMovie.setCurrentItem(nextItem, true); // Scroll by one position
                updateDotIndicatorMovie(nextItem); // Update the dot indicator
                handlerMovie.postDelayed(this, 3000); // Change image every 3 seconds
            }
        };

        // Initialize auto-scrolling for Slide Models Product
        handlerMovie.postDelayed(runnableMovie, 3000);
    }

}