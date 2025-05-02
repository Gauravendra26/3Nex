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
import com.mynexmy.nex.Adapters.DotsAdapterNextube;
import com.mynexmy.nex.Adapters.SliderAdapterNextube;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.Models.Slide_Model_Nextube;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Nextube_Activity extends AppCompatActivity {

    private ImageView imgback;
    private RecyclerView recyclerViewNextube, dotsRecyclerViewNextube;
    private ArrayList<Slide_Model_Nextube> slide_model_nextube = new ArrayList<>();
    private SliderAdapterNextube sliderAdapterNextube;
    private DotsAdapterNextube dotsAdapterNextube;
    private int currentImagePositionNextube = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nextube);
        Utils.blackIconStatusBar(Nextube_Activity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        init();
        showPlaceholder();
        fetchBannerImages();

        imgback.setOnClickListener(v -> finish());

        recyclerViewNextube.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int currentPosition = layoutManager.findFirstVisibleItemPosition();
                    if (currentPosition != currentImagePositionNextube) {
                        currentImagePositionNextube = currentPosition;
                        updateDotPositionNextube(currentImagePositionNextube); // Update dots on scroll
                    }
                }
            }
        });
    }

    private void init() {
        imgback = findViewById(R.id.imgback);
        recyclerViewNextube = findViewById(R.id.recyclerViewNextube);
        dotsRecyclerViewNextube = findViewById(R.id.dotsRecyclerViewNextube);
    }

    private void showPlaceholder() {
        ArrayList<Slide_Model_Nextube> placeholderList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            placeholderList.add(new Slide_Model_Nextube("placeholder", String.valueOf(i)));
        }

        sliderAdapterNextube = new SliderAdapterNextube(getApplicationContext(), placeholderList);
        recyclerViewNextube.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewNextube.setAdapter(sliderAdapterNextube);

        dotsAdapterNextube = new DotsAdapterNextube(getApplicationContext(), placeholderList);
        dotsRecyclerViewNextube.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        dotsRecyclerViewNextube.setAdapter(dotsAdapterNextube);
    }

    public void fetchBannerImages() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiData.Banners,
                response -> {
                    try {
                        Log.e("nextubeData", response);
                        JSONObject jsonObject = new JSONObject(response);
                        boolean status = jsonObject.getBoolean("status");

                        if (status) {
                            if (jsonObject.has("data")) {
                                JSONObject dataObject = jsonObject.getJSONObject("data");
                                JSONArray mobile_nextubeArray = dataObject.optJSONArray("mobile_nextube");

                                if (mobile_nextubeArray != null) {
                                    slide_model_nextube.clear();
                                    for (int i = 0; i < mobile_nextubeArray.length(); i++) {
                                        String imageUrlofmobile_nextube = mobile_nextubeArray.getString(i);
                                        slide_model_nextube.add(new Slide_Model_Nextube(imageUrlofmobile_nextube, String.valueOf(i)));
                                    }

                                    sliderAdapterNextube = new SliderAdapterNextube(getApplicationContext(), slide_model_nextube);
                                    recyclerViewNextube.setAdapter(sliderAdapterNextube);

                                    dotsAdapterNextube = new DotsAdapterNextube(getApplicationContext(), slide_model_nextube);
                                    dotsRecyclerViewNextube.setAdapter(dotsAdapterNextube);

                                    recyclerViewNextube.postDelayed(new AutoScrollRunnableNextube(recyclerViewNextube), 2000);
                                } else {
                                    Log.e("nextubeData", "'mobile_nextube' array is null or doesn't exist");
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

    public void updateDotPositionNextube(int position) {
        if (dotsAdapterNextube != null) {
            dotsAdapterNextube.setSelectedPosition(position);
            currentImagePositionNextube = position;  // Update the current position
        }
    }

    private class AutoScrollRunnableNextube implements Runnable {
        private RecyclerView recyclerView;

        public AutoScrollRunnableNextube(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Override
        public void run() {
            int totalItemCount = recyclerView.getAdapter().getItemCount();
            int nextPosition = (currentImagePositionNextube + 1) % totalItemCount;  // Loop around the list

            recyclerView.smoothScrollToPosition(nextPosition);  // Scroll to the next image
            updateDotPositionNextube(nextPosition);  // Update the dot position

            // Post the Runnable to continue the auto-scrolling
            recyclerView.postDelayed(this, 2000);
        }
    }
}
