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
import com.mynexmy.nex.Adapters.DotsAdapterMovieInner;
import com.mynexmy.nex.Adapters.ImageSliderAdapterMovie;
import com.mynexmy.nex.Adapters.SliderAdapterMovieInner;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.Models.Slide_Model_MovieInner;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class Movie_Activity extends AppCompatActivity {

    private ImageView btn_back;
    private RecyclerView recyclerViewMovieInner, dotsRecyclerViewMovieInner;
    private ArrayList<Slide_Model_MovieInner> slide_model_movieInner = new ArrayList<>();
    private SliderAdapterMovieInner sliderAdapterMovieInner;
    private DotsAdapterMovieInner dotsAdapterMovieInner;
    private int currentImagePositionMovieInner = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Utils.blackIconStatusBar(Movie_Activity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        init();
        showPlaceholder();
        fetchBannerImages();
        btn_back.setOnClickListener(v -> finish());

        recyclerViewMovieInner.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int currentPosition = layoutManager.findFirstVisibleItemPosition();
                    if (currentPosition != currentImagePositionMovieInner) {
                        currentImagePositionMovieInner = currentPosition;
                        updateDotPositionMovieInner(currentImagePositionMovieInner);  // Update dots when manually scrolling
                    }
                }
            }
        });
    }

    private void init() {
        btn_back = findViewById(R.id.btn_back);
        recyclerViewMovieInner = findViewById(R.id.recyclerViewMovieInner);
        dotsRecyclerViewMovieInner = findViewById(R.id.dotsRecyclerViewMovieInner);
    }

    private void showPlaceholder() {
        ArrayList<Slide_Model_MovieInner> placeholderList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            placeholderList.add(new Slide_Model_MovieInner("placeholder", String.valueOf(i)));
        }

        sliderAdapterMovieInner = new SliderAdapterMovieInner(getApplicationContext(), placeholderList);
        recyclerViewMovieInner.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewMovieInner.setAdapter(sliderAdapterMovieInner);

        dotsAdapterMovieInner = new DotsAdapterMovieInner(getApplicationContext(), placeholderList);
        dotsRecyclerViewMovieInner.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        dotsRecyclerViewMovieInner.setAdapter(dotsAdapterMovieInner);
    }

    public void fetchBannerImages() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiData.Banners,
                response -> {
                    try {
                        Log.e("movieData", response);
                        JSONObject jsonObject = new JSONObject(response);
                        boolean status = jsonObject.getBoolean("status");

                        if (status) {
                            if (jsonObject.has("data")) {
                                JSONObject dataObject = jsonObject.getJSONObject("data");
                                JSONArray mobile_movieArray = dataObject.optJSONArray("mobile_movie");

                                if (mobile_movieArray != null) {
                                    slide_model_movieInner.clear();
                                    for (int i = 0; i < mobile_movieArray.length(); i++) {
                                        String imageUrlofmobile_movie = mobile_movieArray.getString(i);
                                        slide_model_movieInner.add(new Slide_Model_MovieInner(imageUrlofmobile_movie, String.valueOf(i)));
                                    }

                                    sliderAdapterMovieInner = new SliderAdapterMovieInner(
                                            getApplicationContext(), slide_model_movieInner);
                                    recyclerViewMovieInner.setAdapter(sliderAdapterMovieInner);

                                    dotsAdapterMovieInner = new DotsAdapterMovieInner(getApplicationContext(), slide_model_movieInner);
                                    dotsRecyclerViewMovieInner.setAdapter(dotsAdapterMovieInner);

                                    recyclerViewMovieInner.postDelayed(new AutoScrollRunnableMovieInner(recyclerViewMovieInner), 2000);
                                } else {
                                    Log.e("movieData", "'mobile_movie' array is null or doesn't exist");
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

    public void updateDotPositionMovieInner(int position) {
        if (dotsAdapterMovieInner != null) {
            dotsAdapterMovieInner.setSelectedPosition(position);
            currentImagePositionMovieInner = position;  // Update the current position
        }
    }

    private class AutoScrollRunnableMovieInner implements Runnable {
        private RecyclerView recyclerView;

        public AutoScrollRunnableMovieInner(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Override
        public void run() {
            int totalItemCount = recyclerView.getAdapter().getItemCount();
            int nextPosition = (currentImagePositionMovieInner + 1) % totalItemCount;  // Loop around the list

            recyclerView.smoothScrollToPosition(nextPosition);  // Scroll to the next image
            updateDotPositionMovieInner(nextPosition);  // Update the dot position

            // Post the Runnable to continue the auto-scrolling
            recyclerView.postDelayed(this, 2000);
        }
    }
}


