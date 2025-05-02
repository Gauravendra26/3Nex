package com.mynexmy.nex.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mynexmy.nex.Activitys.ProductDetailPage_Featured_Activity;
import com.mynexmy.nex.Adapters.Newest_Adapter;
import com.mynexmy.nex.Adapters.Search_Adapter;
//import com.mynexmy.nex.Adapters.SliderAdapter1;
//import com.mynexmy.nex.Adapters.SliderAdapter2;
//import com.mynexmy.nex.Adapters.SliderAdapterMovie;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.Home;
import com.mynexmy.nex.Adapters.ImageAdapter;
import com.mynexmy.nex.Models.Newest_Model;
import com.mynexmy.nex.Models.Search_Model;
import com.mynexmy.nex.Models.Slide_Model_Scratch;
import com.mynexmy.nex.ProductDetailPage_Newest2_Activity;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Room.Product;
import com.mynexmy.nex.Room.ProductDao;
import com.mynexmy.nex.Room.ProductDatabase;
//import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchFragment extends Fragment implements Search_Adapter.ProductPageClick,
        Newest_Adapter.ProductPageClick
{
    View view;

    LottieAnimationView ani1;
    private ArrayList<Slide_Model_Scratch> slide_models_Scratch = new ArrayList<>();

    EditText etSearch;

    List<Newest_Model> newest_models;
    Newest_Adapter newest_adapter;
    RequestQueue requestQueue;

    RecyclerView rvSearch,rvSearch1;
    Search_Adapter search_adapter;
    ImageView placeholderImageScratch;
    private ViewPager2 viewPagerScratch;
    private LinearLayout dotIndicatorScratch;
    private Handler handlerScratch;
    private Runnable runnableScratch;

    List<Search_Model> search_models;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        requestQueue = Volley.newRequestQueue(getActivity());
        init();
        getdetails();

        fetchSliderImages();



        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getProductDetails(etSearch.getText().toString().trim());

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

       return view;
    }

    void init() {
         etSearch = view.findViewById(R.id.etSearch);
        rvSearch = view.findViewById(R.id.rvSearch);
        rvSearch1 = view.findViewById(R.id.rvSearch1);
        placeholderImageScratch = view.findViewById(R.id.placeholderImageScratch);
        viewPagerScratch = view.findViewById(R.id.viewPagerScratch);
        dotIndicatorScratch = view.findViewById(R.id.dotIndicatorScratch);
         ani1 = view.findViewById(R.id.ani1);
     }
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
    }
    private void getProductDetails(String id) {
         StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ApiData.Search + "?query=" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ani1.setVisibility(View.GONE);
                        search_models = new ArrayList<>();
                        search_models.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("products");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject user = jsonArray.getJSONObject(i);

                                int product_id = user.optInt("product_id");
                                int category_id = user.optInt("category_id");
                                int product_status = user.optInt("product_status");
                                int added_by = user.optInt("added_by");
                                int product_reg_price = user.optInt("product_reg_price");
                                int product_sell_price = user.optInt("product_sell_price");
                                double product_rating = user.optDouble("product_rating");
                                int product_rating_total = user.optInt("product_rating_total");
                                int product_featured = user.optInt("product_featured");
                                int product_tax_percent = user.optInt("product_tax_percent");
                                String product_title = user.optString("product_title");
                                String product_url= user.optString("product_url");
                                String product_code = user.optString("product_code");
                                String product_description = user.optString("product_description");
                                String product_image = ApiData.IMAGE_BASE_URL + user.optString("product_image");
                                String created_at = user.optString("created_at");
                                String updated_at = user.optString("updated_at");

                                search_models.add(new Search_Model(product_id, product_rating, product_image,
                                        product_title, product_sell_price,product_reg_price,product_tax_percent));

                            }

                            search_adapter = new Search_Adapter(getActivity(), search_models);
                            Collections.reverse(search_models);
                            GridLayoutManager layoutManagerC = new GridLayoutManager(getActivity(),
                                    2);
                            rvSearch.setLayoutManager(layoutManagerC);
                            rvSearch.setItemAnimator(new DefaultItemAnimator());
                            rvSearch.setAdapter(search_adapter);
                            search_adapter.set(SearchFragment.this);

                            rvSearch.setVisibility(View.VISIBLE);
                            rvSearch1.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                     }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }

    private void getdetails() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                ApiData.product_list, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if (response.getBoolean("status") == true) {
                                newest_models = new ArrayList<Newest_Model>();
                                JSONArray data = response.getJSONArray("data");

                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject user = data.getJSONObject(i);
                                    int product_id = user.optInt("product_id");
                                    String product_title = user.optString("product_title");
                                    String product_url = user.optString("product_url");
                                    String product_code = user.optString("product_code");
                                    String product_description = user.optString("product_description");
                                    int category_id = user.optInt("category_id");
                                    String product_image = ApiData.IMAGE_BASE_URL + user.optString("product_image");
                                    int product_status = user.optInt("product_status");
                                    int added_by = user.optInt("added_by");
                                    int product_reg_price = user.optInt("product_reg_price");
                                    int product_sell_price = user.optInt("product_sell_price");
                                    double product_rating = user.optDouble("product_rating");
                                    int product_rating_total = user.optInt("product_rating_total");
                                    int product_featured = user.optInt("product_featured");
                                    int product_tax_percent = user.optInt("product_tax_percent");
                                    String created_at = user.optString("created_at");
                                    String updated_at = user.optString("updated_at");
                                    int category_parent = user.optInt("category_parent");
                                    String category_title = user.optString("category_title");
                                    String category_slug = user.optString("category_slug");
                                    int category_status = user.optInt("category_status");
                                    String category_image = user.optString("category_image");
                                    String category_description = user.optString("category_description");
                                    JSONArray productsImages = user.getJSONArray("product_images");


                                    newest_models.add(new Newest_Model(product_id, product_rating,
                                            product_title, product_description, product_rating_total,
                                            product_image, product_sell_price, product_reg_price, product_tax_percent,added_by));

                                }
                                ani1.setVisibility(View.GONE);
                                 newest_adapter = new Newest_Adapter(getContext(),
                                        newest_models);
                                Collections.reverse(newest_models);
                                GridLayoutManager layoutManagerC = new GridLayoutManager(getContext(),
                                        2);


                                rvSearch1.setLayoutManager(layoutManagerC);
                                rvSearch1.setItemAnimator(new DefaultItemAnimator());
                                rvSearch1.setAdapter(newest_adapter);
                                newest_adapter.set(SearchFragment.this);

                            } else {

                            }  //ye wale
                        } catch (JSONException e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);

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

                                        // Process images in the "home" array
                                        for (int i = 0; i < homeArray.length(); i++) {
                                            String imageUrl = homeArray.getString(i);
                                            Log.d("sliderData", "Adding image URL: " + imageUrl);

                                            // Add to the respective list based on index range
                                            if (i >= 0 && i <= 4) {
                                                slide_models_Scratch.add(new Slide_Model_Scratch(imageUrl, String.valueOf(i)));
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
    public void productClick(int position, int product_id, double product_rating, String product_image,
                             String product_title, int product_sell_price, int product_reg_price,int product_tax_percent) {
        Intent intent = new Intent(getActivity(), ProductDetailPage_Newest2_Activity.class);

        intent.putExtra("product_id", product_id);
        intent.putExtra("product_rating", product_rating);
        intent.putExtra("product_image", product_image);
        intent.putExtra("product_title", product_title);
        intent.putExtra("product_sell_price", product_sell_price);
        intent.putExtra("product_reg_price", product_reg_price);

        startActivity(intent);
    }

    @Override
    public void addtocart(int position, int product_id, double product_rating, String product_image,
                          String product_title, int product_sell_price, int product_reg_price,int product_tax_percent) {

         SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Tax", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt("product_tax_percent", product_tax_percent);
        myEdit.apply();
        myEdit.commit();

        ProductDatabase db = Room.databaseBuilder(getActivity(), ProductDatabase.class, "cart_db")
                .allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        Boolean check = productDao.is_exist(Integer.parseInt("" + product_id));
        if (check == false) {
            int pid = product_id;
            String pname = product_title;

            int price1 = product_reg_price;
            int sale1 = product_sell_price;
            int qnt = Integer.parseInt("1");
            productDao.insertrecord(new Product(pid, pname, price1, qnt, sale1, product_image));
            Toast.makeText(getActivity(), "" + pname + " Added to Cart Successfully", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Product Already in Cart", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public void dataUpdate(int position) {
        ProductDatabase db = Room.databaseBuilder(getContext(), ProductDatabase.class, "cart_db")
                .allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        List<Product> products = productDao.getallproduct();
        ((Home) getActivity()).cartNotify(products.size());
    }

    @Override
    public void productClick1(int position, int product_id, int category_id, double product_rating,
                              String product_image, String product_title, int product_sell_price,
                              String product_description, int product_rating_total,
                              int product_reg_price,int product_tax_percent) {

        Intent intent = new Intent(getActivity(), ProductDetailPage_Featured_Activity.class);
        intent.putExtra("product_id", product_id);
        intent.putExtra("category_id", category_id);
        intent.putExtra("product_rating", product_rating);
        intent.putExtra("product_image", product_image);
        intent.putExtra("product_title", product_title);
        intent.putExtra("product_sell_price", product_sell_price);
        intent.putExtra("product_reg_price", product_reg_price);
        intent.putExtra("product_description", product_description);
        intent.putExtra("product_rating_total", product_rating_total);

        startActivity(intent);
    }

    @Override
    public void addToCart1(int position, int product_id, int category_id, double product_rating,
                           String product_image, String product_title, int product_sell_price,
                           String product_description, int product_rating_total, int product_reg_price,
                           int product_tax_percent) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Tax", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt("product_tax_percent", product_tax_percent);
        myEdit.apply();
        myEdit.commit();

        ProductDatabase db = Room.databaseBuilder(getActivity(), ProductDatabase.class, "cart_db")
                .allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        Boolean check = productDao.is_exist(Integer.parseInt("" + product_id));
        if (check == false) {
            int pid = product_id;
            String pname = product_title;

            int price1 = product_reg_price;
            int sale1 = product_sell_price;
            int qnt = Integer.parseInt("1");
            productDao.insertrecord(new Product(pid, pname, price1, qnt, sale1,
                    product_image));
            Toast.makeText(getActivity(), "" + product_title + " Added Successfully", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Product Already in Cart", Toast.LENGTH_SHORT).show();
        }
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
            handlerScratch.postDelayed(runnableScratch, 3000); // Resume auto-scrolling for Slide Models Scratch
        }

    }


}
