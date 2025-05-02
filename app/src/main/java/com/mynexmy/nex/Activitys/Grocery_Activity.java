package com.mynexmy.nex.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mynexmy.nex.Adapters.ImageSliderAdapter1;
import com.mynexmy.nex.Adapters.DotsAdapter;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.AutoScrollRunnable;
import com.mynexmy.nex.Models.Slide_Model_Footwear;
import com.mynexmy.nex.Models.Slide_Model_Grocery;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Grocery_Activity extends AppCompatActivity {

    private ImageView imgback;
    private RecyclerView recyclerView, dotsRecyclerView;
    private ArrayList<Slide_Model_Grocery> slide_model_grocery = new ArrayList<>();
    private ImageSliderAdapter1 imageSliderAdapter;
    private DotsAdapter dotsAdapter;
    private int currentImagePosition = 0;
    TextView tv_pageText;
    int pageCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
        Utils.blackIconStatusBar(Grocery_Activity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        init();
        Intent i = getIntent();
        pageCheck = i.getIntExtra("pageCheck", 0);


        showPlaceholder();
        fetchBannerImages();
        imgback.setOnClickListener(v -> finish());

        tv_pageText.setText("Grocery");


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int currentPosition = layoutManager.findFirstVisibleItemPosition();
                    if (currentPosition != currentImagePosition) {
                        currentImagePosition = currentPosition;
                        updateDotPosition(currentImagePosition);  // Update dots when manually scrolling
                    }
                }
            }
        });

    }

    private void init() {
        imgback = findViewById(R.id.imgback);
        recyclerView = findViewById(R.id.recyclerView);
        dotsRecyclerView = findViewById(R.id.dotsRecyclerView);
        tv_pageText = findViewById(R.id.tv_pageText);
    }

    private void showPlaceholder() {
        ArrayList<Slide_Model_Grocery> placeholderList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            placeholderList.add(new Slide_Model_Grocery("placeholder", String.valueOf(i)));
        }

        imageSliderAdapter = new ImageSliderAdapter1(getApplicationContext(), placeholderList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(imageSliderAdapter);

        dotsAdapter = new DotsAdapter(getApplicationContext(), placeholderList);
        dotsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        dotsRecyclerView.setAdapter(dotsAdapter);
    }

    public void fetchBannerImages() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiData.Banners,
                response -> {
                    try {
                        Log.e("groceryData", response);
                        JSONObject jsonObject = new JSONObject(response);
                        boolean status = jsonObject.getBoolean("status");

                        if (status) {
                            if (jsonObject.has("data")) {
                                JSONObject dataObject = jsonObject.getJSONObject("data");
                                JSONArray mobile_groceryArray = dataObject.optJSONArray("mobile_grecery");
                                JSONArray mobile_footwear = dataObject.optJSONArray("footwear");

                                if (mobile_groceryArray != null) {
                                    slide_model_grocery.clear();
                                    for (int i = 0; i < mobile_groceryArray.length(); i++) {
                                        String imageUrlofmobile_grocery = mobile_groceryArray.getString(i);
                                        slide_model_grocery.add(new Slide_Model_Grocery(imageUrlofmobile_grocery, String.valueOf(i)));
                                    }

                                    imageSliderAdapter = new ImageSliderAdapter1(getApplicationContext(), slide_model_grocery);
                                    recyclerView.setAdapter(imageSliderAdapter);

                                    dotsAdapter = new DotsAdapter(getApplicationContext(), slide_model_grocery);
                                    dotsRecyclerView.setAdapter(dotsAdapter);

                                    // Start auto-scrolling images and updating dots
                                    recyclerView.postDelayed(new AutoScrollRunnable(recyclerView), 2000);
                                } else {
                                    Log.e("groceryData", "'mobile_grecery' array is null or doesn't exist");
                                }


                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("Volley Error", "Error: " + error.toString())
        );

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    // Update the selected dot position when the image changes
    public void updateDotPosition(int position) {
        if (dotsAdapter != null) {
            dotsAdapter.setSelectedPosition(position);
            currentImagePosition = position;  // Update the current position
        }
    }

    // AutoScrollRunnable can be modified as follows:
    private class AutoScrollRunnable implements Runnable {
        private RecyclerView recyclerView;

        public AutoScrollRunnable(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Override
        public void run() {
            int totalItemCount = recyclerView.getAdapter().getItemCount();
            int nextPosition = (currentImagePosition + 1) % totalItemCount;  // Loop around the list

            recyclerView.smoothScrollToPosition(nextPosition);  // Scroll to the next image
            updateDotPosition(nextPosition);  // Update the dot position

            // Post the Runnable to continue the auto-scrolling
            recyclerView.postDelayed(this, 2000);
        }
    }
}
