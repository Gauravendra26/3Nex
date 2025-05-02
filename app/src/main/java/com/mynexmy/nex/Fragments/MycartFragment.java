package com.mynexmy.nex.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.mynexmy.nex.Activitys.Order_Summary_Activity;
import com.mynexmy.nex.Adapters.ImageSliderAdapterProducts;
import com.mynexmy.nex.Adapters.PAdapter;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.Home;
import com.mynexmy.nex.Adapters.ImageAdapter;
import com.mynexmy.nex.Models.Slide_Model_Product;
import com.mynexmy.nex.Models.Slide_Model_Scratch;
import com.mynexmy.nex.Product_DetailPage_Activity4;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Room.Product;
import com.mynexmy.nex.Room.ProductDao;
import com.mynexmy.nex.Room.ProductDatabase;
import com.mynexmy.nex.Signup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MycartFragment extends Fragment implements PAdapter.whenClick
{

    View view;
    RecyclerView rv1;

     ImageSliderAdapterProducts imageSliderAdapterProducts;



    private ArrayList<Slide_Model_Scratch> slide_models_Scratch = new ArrayList<>();
    private ArrayList<Slide_Model_Product> slide_model_products = new ArrayList<>();


    RequestQueue requestQueue;
    RelativeLayout placeorder,rlslider1;
    ImageView placeholderImageScratch,placeholderImageProduct ;
    private ViewPager2 viewPagerScratch,viewPagerProduct ;
    private LinearLayout dotIndicatorScratch,dotIndicatorProduct ;
    String email, first_name, last_name, mobile;

    private Handler handlerScratch;
    private Runnable runnableScratch;

    private Handler handlerProduct;
    private Runnable runnableProduct;


    PAdapter adapter;
    TextView tvPrice, tvPriceItem1;
    TextView tv_messages;
    LinearLayout bottom;
    List<Product> products = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mycart, container, false);
        requestQueue = Volley.newRequestQueue(getActivity());
        init();

        rv1 = view.findViewById(R.id.rv1);
        tv_messages = view.findViewById(R.id.tv_messages);
        bottom = view.findViewById(R.id.bottom);
        tvPrice = view.findViewById(R.id.tvPrice);
getDataProfile();
        ProductDatabase db = Room.databaseBuilder(getActivity(),
                ProductDatabase.class, "cart_db").allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        rv1.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PAdapter(getActivity(), products, tvPrice);
        rv1.setAdapter(adapter);
        products.addAll(productDao.getallproduct());
        ((Home) getActivity()).cartNotify(products.size());

        adapter.notifyDataSetChanged();
        if (products.isEmpty()) {
            tv_messages.setVisibility(View.VISIBLE);
            bottom.setVisibility(View.GONE);
            rv1.setVisibility(View.GONE);
        } else {
            tv_messages.setVisibility(View.GONE);
            bottom.setVisibility(View.VISIBLE);
            rv1.setVisibility(View.VISIBLE);
        }

        rv1.setLayoutManager(new LinearLayoutManager(getContext()));
        rv1.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnClick(this);

        fetchSliderImages();

        placeorder.setOnClickListener(v -> {
            if (first_name == null || first_name.equals("null")) {
                Intent homeIntent = new Intent(getContext(), Signup.class);
homeIntent.putExtra("mycartIndicator",11);
                startActivity(homeIntent);

            } else {
                showOrderConfirmationDialog();
            }

            placeorder.startAnimation(clickAnimation());
        });


        return view;
    }

    private void init() {
        placeorder = view.findViewById(R.id.placeorder);
        placeholderImageScratch = view.findViewById(R.id.placeholderImageScratch);
        placeholderImageProduct = view.findViewById(R.id.placeholderImageProduct);

        viewPagerScratch = view.findViewById(R.id.viewPagerScratch);
        viewPagerProduct = view.findViewById(R.id.viewPagerProduct);

        dotIndicatorScratch = view.findViewById(R.id.dotIndicatorScratch);
        dotIndicatorProduct = view.findViewById(R.id.dotIndicatorProduct);


    }

    @Override
    public void setNotify(int position) {
        ProductDatabase db = Room.databaseBuilder(getActivity(),
                ProductDatabase.class, "cart_db").allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        rv1.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Product> products = productDao.getallproduct();
        if (products.isEmpty()) {
            tv_messages.setVisibility(View.VISIBLE);
            bottom.setVisibility(View.GONE);
            rv1.setVisibility(View.GONE);
        } else {
            tv_messages.setVisibility(View.GONE);
            bottom.setVisibility(View.VISIBLE);
            rv1.setVisibility(View.VISIBLE);
        }
        ((Home) getActivity()).cartNotify(products.size());
    }

    public void showOrderConfirmationDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setIcon(R.drawable.nex)
                .setTitle("Order Confirmation")
                .setMessage("Do you want to confirm Order?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    Intent intent = new Intent(getActivity(), Order_Summary_Activity.class);
                    startActivity(intent);
                })
                .setNegativeButton("No", (dialogInterface, i) -> {})
                .create();

        alertDialog.setOnShowListener(dialog -> {
            Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

            positiveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.main));
            negativeButton.setTextColor(ContextCompat.getColor(getContext(), R.color.main));
        });

        alertDialog.show();
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
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + sharedPreferences.getString("Login_Token", ""));
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(req);
    }

    @Override
    public void setNotify1(int position) {
        ProductDatabase db = Room.databaseBuilder(getActivity(),
                ProductDatabase.class, "cart_db").allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        rv1.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Product> products = productDao.getallproduct();
    }

    public void fetchSliderImages() {
        // Display placeholder data initially
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

                                        // Clear previous data before populating the lists
                                        slide_models_Scratch.clear();
                                        slide_model_products.clear();


                                        // Process images in the "home" array
                                        for (int i = 0; i < homeArray.length(); i++) {
                                            String imageUrl = homeArray.getString(i);
                                            Log.d("sliderData", "Adding image URL: " + imageUrl);

                                            // Add to the respective list based on index range
                                            if (i >= 0 && i <= 4) {
                                                slide_models_Scratch.add(new Slide_Model_Scratch(imageUrl, String.valueOf(i)));
                                            }
                                            if (i >= 5 && i <= 14) {
                                                slide_model_products.add(new Slide_Model_Product(imageUrl, String.valueOf(i)));
                                            }

                                        }
                                        // Set up RecyclerView and adapters for Scratch section
                                        if (!slide_models_Scratch.isEmpty()) {
                                            ImageAdapter imageAdapter1 = new ImageAdapter(
                                                    (FragmentActivity) getActivity(),
                                                    slide_models_Scratch,
                                                    new ImageAdapter.whenClick() {
                                                        @Override
                                                        public void setChip() {
                                                            ((Home) getActivity()).chipNotify();
                                                        }
                                                    }
                                            );

                                            placeholderImageScratch.setVisibility(View.GONE);
                                            viewPagerScratch.setAdapter(imageAdapter1);
                                            addDotsIndicator();
                                            setupAutoScrolling();
                                            viewPagerScratch.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                                @Override
                                                public void onPageSelected(int position) {
                                                    super.onPageSelected(position);
                                                    updateDotIndicator(position);
                                                }
                                            });
                                        } else {
                                            Log.e("sliderData", "No data found for slide_models_Scratch (slide_models_Scratch is empty)");
                                        }

//                                         Set up RecyclerView and adapters for Products section
                                        if (!slide_model_products.isEmpty()) {
                                            ImageSliderAdapterProducts imageAdapter1 = new ImageSliderAdapterProducts(
                                                    (FragmentActivity) getActivity(),    // Pass the context (ensure it's a FragmentActivity)
                                                    slide_model_products                  // Pass the slide_model_products list
                                            );
                                            placeholderImageProduct.setVisibility(View.GONE);
                                            // Set the adapter to the ViewPager
                                            viewPagerProduct.setAdapter(imageAdapter1);
                                            addDotsIndicatorProduct();
                                            setupAutoScrollingProduct();

                                            viewPagerProduct.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                                @Override
                                                public void onPageSelected(int position) {
                                                    super.onPageSelected(position);
                                                    updateDotIndicatorProduct(position);
                                                }
                                            });
                                        } else {
                                            Log.e("sliderData", "No data found for recyclerViewProduct (slide_model_products is empty)");
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
                        Log.e("sliderData", "Error fetching slider images: " + error.getMessage());
                    }
                });

        // Add the request to the request queue
        requestQueue.add(stringRequest);
    }


    @Override
    public void press(int position, int pid, String pname, int price, int qnt, int sale, String image) {}

    @Override
    public void press1(int position, int pid, String pname, int price, int qnt, int sale, String image) {
        Intent intent = new Intent(getActivity(), Product_DetailPage_Activity4.class);
        intent.putExtra("pid", pid);
        intent.putExtra("product_image", image);
        startActivity(intent);
    }

    public AlphaAnimation clickAnimation() {
        return new AlphaAnimation(1F, 0.1F); // Change "0.4F" as per your requirement.
    }


    public void setChip() {
        ((Home) getActivity()).chipNotify();
    }



    private void addDotsIndicator() {
        if (dotIndicatorScratch == null || slide_models_Scratch.isEmpty()) return;

        // Remove previous dots
        dotIndicatorScratch.removeAllViews();

        // Add new dots based on the number of images
        for (int i = 0; i < slide_models_Scratch.size(); i++) {
            ImageView dot = new ImageView(getContext());
            dot.setImageResource(R.drawable.dot_unselected);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    getResources().getDimensionPixelSize(R.dimen.dot_size),
                    getResources().getDimensionPixelSize(R.dimen.dot_size)
            );
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);
            dotIndicatorScratch.addView(dot);
        }

        // Initially update the first dot
        updateDotIndicator(0);
    }
    private void addDotsIndicatorProduct() {
        if (dotIndicatorProduct == null || slide_model_products.isEmpty()) return;

        // Remove previous dots
        dotIndicatorProduct.removeAllViews();

        // Add new dots based on the number of images
        for (int i = 0; i < slide_model_products.size(); i++) {
            ImageView dot = new ImageView(getContext());
            dot.setImageResource(R.drawable.dot_unselected);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    getResources().getDimensionPixelSize(R.dimen.dot_size),
                    getResources().getDimensionPixelSize(R.dimen.dot_size)
            );
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);
            dotIndicatorProduct.addView(dot);
        }

        // Initially update the first dot
        updateDotIndicator(0);
    }

    private void updateDotIndicator(int position) {
        if (dotIndicatorScratch == null) return;

        for (int i = 0; i < dotIndicatorScratch.getChildCount(); i++) {
            ImageView dot = (ImageView) dotIndicatorScratch.getChildAt(i);
            if (i == position) {
                dot.setImageResource(R.drawable.dot_selected);
            } else {
                dot.setImageResource(R.drawable.dot_unselected);
            }
        }
    }
    private void updateDotIndicatorProduct(int position) {
        if (dotIndicatorProduct == null) return;

        for (int i = 0; i < dotIndicatorProduct.getChildCount(); i++) {
            ImageView dot = (ImageView) dotIndicatorProduct.getChildAt(i);
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
                if (slide_models_Scratch.isEmpty()) return;

                int currentItem = viewPagerScratch.getCurrentItem();
                int nextItem = (currentItem == slide_models_Scratch.size() - 1) ? 0 : currentItem + 1;
                viewPagerScratch.setCurrentItem(nextItem, true); // Scroll by one position
                updateDotIndicator(nextItem); // Update the dot indicator
                handlerScratch.postDelayed(this, 3000); // Change image every 3 seconds
            }
        };

        // Initialize auto-scrolling for Slide Models Scratch
        handlerScratch.postDelayed(runnableScratch, 3000);
    }

    private void setupAutoScrollingProduct() {
        handlerProduct = new Handler(Looper.getMainLooper()); // Handler for Slide Models Product
        runnableProduct = new Runnable() {
            @Override
            public void run() {
                if (slide_model_products.isEmpty()) return;

                int currentItem = viewPagerProduct.getCurrentItem();
                int nextItem = (currentItem == slide_model_products.size() - 1) ? 0 : currentItem + 1;
                viewPagerProduct.setCurrentItem(nextItem, true); // Scroll by one position
                updateDotIndicatorProduct(nextItem); // Update the dot indicator
                handlerProduct.postDelayed(this, 3000); // Change image every 3 seconds
            }
        };

        // Initialize auto-scrolling for Slide Models Product
        handlerProduct.postDelayed(runnableProduct, 3000);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (handlerScratch != null && runnableScratch != null) {
            handlerScratch.removeCallbacks(runnableScratch); // Stop auto-scrolling for Slide Models Scratch
        }
        if (handlerProduct != null && runnableProduct != null) {
            handlerProduct.removeCallbacks(runnableProduct); // Stop auto-scrolling for Slide Models Product
        }
     }

    @Override
    public void onResume() {
        super.onResume();
        if (handlerScratch != null && runnableScratch != null) {
            handlerScratch.postDelayed(runnableScratch, 3000); // Resume auto-scrolling for Slide Models Scratch
        }
        if (handlerProduct != null && runnableProduct != null) {
            handlerProduct.postDelayed(runnableProduct, 3000); // Resume auto-scrolling for Slide Models Product
        }
        ProductDatabase db = Room.databaseBuilder(getActivity(),
                ProductDatabase.class, "cart_db").allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        rv1.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Product> products = productDao.getallproduct();
        if (products.isEmpty()) {
            tv_messages.setVisibility(View.VISIBLE);
            bottom.setVisibility(View.GONE);
            rv1.setVisibility(View.GONE);
        } else {
            tv_messages.setVisibility(View.GONE);
            bottom.setVisibility(View.VISIBLE);
            rv1.setVisibility(View.VISIBLE);
        }
        ((Home) getActivity()).cartNotify(products.size());
    }



}
