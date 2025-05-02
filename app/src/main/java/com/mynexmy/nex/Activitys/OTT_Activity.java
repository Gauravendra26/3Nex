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
import com.mynexmy.nex.Adapters.DotsAdapterOTT;
import com.mynexmy.nex.Adapters.SliderAdapterOTT;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.Models.Slide_Model_OTT;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;
import com.mynexmy.nex.AutoScrollRunnable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OTT_Activity extends AppCompatActivity {

    private ImageView btn_back;
    private RecyclerView recyclerViewOTT, dotsRecyclerViewOTT;
    private ArrayList<Slide_Model_OTT> slide_model_ott = new ArrayList<>();
    private SliderAdapterOTT sliderAdapterOTT;
    private DotsAdapterOTT dotsAdapterOTT;
    private int currentImagePositionOTT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ott);
        Utils.blackIconStatusBar(OTT_Activity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        init();
        showPlaceholder();
        fetchBannerImages();

        btn_back.setOnClickListener(v -> finish());

        recyclerViewOTT.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int currentPosition = layoutManager.findFirstVisibleItemPosition();
                    if (currentPosition != currentImagePositionOTT) {
                        currentImagePositionOTT = currentPosition;
                        updateDotPositionOTT(currentImagePositionOTT);  // Update dots when manually scrolling
                    }
                }
            }
        });
    }

    private void init() {
        btn_back = findViewById(R.id.btn_back);
        recyclerViewOTT = findViewById(R.id.recyclerViewOTT);
        dotsRecyclerViewOTT = findViewById(R.id.dotsRecyclerViewOTT);
    }

    private void showPlaceholder() {
        ArrayList<Slide_Model_OTT> placeholderList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            placeholderList.add(new Slide_Model_OTT("placeholder", String.valueOf(i)));
        }

        sliderAdapterOTT = new SliderAdapterOTT(getApplicationContext(), placeholderList);
        recyclerViewOTT.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewOTT.setAdapter(sliderAdapterOTT);

        dotsAdapterOTT = new DotsAdapterOTT(getApplicationContext(), placeholderList);
        dotsRecyclerViewOTT.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        dotsRecyclerViewOTT.setAdapter(dotsAdapterOTT);
    }

    public void fetchBannerImages() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiData.Banners,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("ottData", response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean("status");

                            if (status) {
                                if (jsonObject.has("data")) {
                                    JSONObject dataObject = jsonObject.getJSONObject("data");
                                    JSONArray mobile_ottArray = dataObject.optJSONArray("mobile_ott");

                                    if (mobile_ottArray != null) {
                                        slide_model_ott.clear();
                                        for (int i = 0; i < mobile_ottArray.length(); i++) {
                                            String imageUrlofmobile_ott = mobile_ottArray.getString(i);
                                            slide_model_ott.add(new Slide_Model_OTT(imageUrlofmobile_ott, String.valueOf(i)));
                                        }

                                        sliderAdapterOTT = new SliderAdapterOTT(getApplicationContext(), slide_model_ott);
                                        recyclerViewOTT.setAdapter(sliderAdapterOTT);

                                        dotsAdapterOTT = new DotsAdapterOTT(getApplicationContext(), slide_model_ott);
                                        dotsRecyclerViewOTT.setAdapter(dotsAdapterOTT);

                                        // Start auto-scrolling images and updating dots
                                        recyclerViewOTT.postDelayed(new AutoScrollRunnableOTT(recyclerViewOTT), 2000);
                                    } else {
                                        Log.e("ottData", "'mobile_ott' array is null or doesn't exist");
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", "Error: " + error.toString());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    // Update the selected dot position when the image changes
    public void updateDotPositionOTT(int position) {
        if (dotsAdapterOTT != null) {
            dotsAdapterOTT.setSelectedPosition(position);
            currentImagePositionOTT = position;  // Update the current position
        }
    }

    // AutoScrollRunnable can be modified as follows:
    private class AutoScrollRunnableOTT implements Runnable {
        private RecyclerView recyclerView;

        public AutoScrollRunnableOTT(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Override
        public void run() {
            int totalItemCount = recyclerView.getAdapter().getItemCount();
            int nextPosition = (currentImagePositionOTT + 1) % totalItemCount;  // Loop around the list

            recyclerView.smoothScrollToPosition(nextPosition);  // Scroll to the next image
            updateDotPositionOTT(nextPosition);  // Update the dot position

            // Post the Runnable to continue the auto-scrolling
            recyclerView.postDelayed(this, 2000);
        }
    }
}
