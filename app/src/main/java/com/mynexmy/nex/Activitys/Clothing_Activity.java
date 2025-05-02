package com.mynexmy.nex.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mynexmy.nex.Adapters.DotsAdapterClothing;
import com.mynexmy.nex.Adapters.SliderAdapterClothing;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.Models.Slide_Model_Clothing;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Clothing_Activity extends AppCompatActivity {

    private ImageView btn_back;
    private RecyclerView recyclerViewClothing, dotsRecyclerViewClothing;
    private ArrayList<Slide_Model_Clothing> slide_model_clothing = new ArrayList<>();
    private SliderAdapterClothing sliderAdapterClothing;
    private DotsAdapterClothing dotsAdapterClothing;
    private int currentImagePositionClothing = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing);
        Utils.blackIconStatusBar(Clothing_Activity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        init();
        showPlaceholder();
        fetchBannerImages();

        btn_back.setOnClickListener(v -> finish());

        recyclerViewClothing.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int currentPosition = layoutManager.findFirstVisibleItemPosition();
                    if (currentPosition != currentImagePositionClothing) {
                        currentImagePositionClothing = currentPosition;
                        updateDotPositionClothing(currentImagePositionClothing); // Update dots on scroll
                    }
                }
            }
        });
    }

    private void init() {
        btn_back = findViewById(R.id.btn_back);
        recyclerViewClothing = findViewById(R.id.recyclerViewClothing);
        dotsRecyclerViewClothing = findViewById(R.id.dotsRecyclerViewClothing);
    }

    private void showPlaceholder() {
        ArrayList<Slide_Model_Clothing> placeholderList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            placeholderList.add(new Slide_Model_Clothing("placeholder", String.valueOf(i)));
        }

        sliderAdapterClothing = new SliderAdapterClothing(getApplicationContext(), placeholderList);
        recyclerViewClothing.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewClothing.setAdapter(sliderAdapterClothing);

        dotsAdapterClothing = new DotsAdapterClothing(getApplicationContext(), placeholderList);
        dotsRecyclerViewClothing.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        dotsRecyclerViewClothing.setAdapter(dotsAdapterClothing);
    }

    public void fetchBannerImages() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiData.Banners,
                response -> {
                    try {
                        Log.e("clothingData", response);
                        JSONObject jsonObject = new JSONObject(response);
                        boolean status = jsonObject.getBoolean("status");

                        if (status) {
                            if (jsonObject.has("data")) {
                                JSONObject dataObject = jsonObject.getJSONObject("data");
                                JSONArray mobile_clothingArray = dataObject.optJSONArray("mobile_clothing");

                                if (mobile_clothingArray != null) {
                                    slide_model_clothing.clear();
                                    for (int i = 0; i < mobile_clothingArray.length(); i++) {
                                        String imageUrlofmobile_clothing = mobile_clothingArray.getString(i);
                                        slide_model_clothing.add(new Slide_Model_Clothing(imageUrlofmobile_clothing, String.valueOf(i)));
                                    }

                                    sliderAdapterClothing = new SliderAdapterClothing(getApplicationContext(), slide_model_clothing);
                                    recyclerViewClothing.setAdapter(sliderAdapterClothing);

                                    dotsAdapterClothing = new DotsAdapterClothing(getApplicationContext(), slide_model_clothing);
                                    dotsRecyclerViewClothing.setAdapter(dotsAdapterClothing);

                                    recyclerViewClothing.postDelayed(new AutoScrollRunnableClothing(recyclerViewClothing), 2000);
                                } else {
                                    Log.e("clothingData", "'mobile_clothing' array is null or doesn't exist");
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

    public void updateDotPositionClothing(int position) {
        if (dotsAdapterClothing != null) {
            dotsAdapterClothing.setSelectedPosition(position);
            currentImagePositionClothing = position;  // Update the current position
        }
    }

    private class AutoScrollRunnableClothing implements Runnable {
        private RecyclerView recyclerView;

        public AutoScrollRunnableClothing(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Override
        public void run() {
            int totalItemCount = recyclerView.getAdapter().getItemCount();
            int nextPosition = (currentImagePositionClothing + 1) % totalItemCount;  // Loop around the list

            recyclerView.smoothScrollToPosition(nextPosition);  // Scroll to the next image
            updateDotPositionClothing(nextPosition);  // Update the dot position

            // Post the Runnable to continue the auto-scrolling
            recyclerView.postDelayed(this, 2000);
        }
    }
}
