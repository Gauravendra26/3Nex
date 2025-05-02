package com.mynexmy.nex.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mynexmy.nex.Adapters.CategoryAdapter;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.Activitys.Category_Details_Activity;
import com.mynexmy.nex.Models.CategoryModel;
import com.mynexmy.nex.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment implements CategoryAdapter.ProductPageClick{

    View view;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    CategoryAdapter categoryAdapter;
    RecyclerView rv1;
    List<CategoryModel> categoryModels;
    LottieAnimationView ani1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_category, container, false);
        requestQueue = Volley.newRequestQueue(getActivity());
        init();

        getCategory();
        return view;
    }

    void init() {
        rv1 = view.findViewById(R.id.rv1);
        ani1 = view.findViewById(R.id.ani1);
    }

    private void getCategory() {
        {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    ApiData.Category, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {

                                if (response.getBoolean("status") == true) {
                                    categoryModels = new ArrayList<CategoryModel>();
                                    JSONArray data = response.getJSONArray("data");
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject user = data.getJSONObject(i);
                                        int category_id = user.getInt("category_id");
                                        String category_parent = user.getString("category_parent");
                                        String category_title = user.getString("category_title");
                                        String category_slug = user.getString("category_slug");
                                        String category_status = user.getString("category_status");
                                        String category_image = user.getString("category_image");
                                        String category_description = user.getString("category_description");
                                        String added_by = user.getString("added_by");
                                        String created_at = user.getString("created_at");
                                        String updated_at = user.getString("updated_at");

                                         categoryModels.add(new CategoryModel(category_id, category_title,
                                                category_image));

                                    }
                                    rv1.setVisibility(View.VISIBLE);
                                    ani1.setVisibility(View.GONE);
                                    categoryAdapter = new CategoryAdapter(getActivity(), categoryModels);
                                    GridLayoutManager layoutManagerC = new GridLayoutManager(getContext(),
                                            3);

                                    rv1.setLayoutManager(layoutManagerC);
                                    rv1.setItemViewCacheSize(0);

                                    rv1.setItemAnimator(new DefaultItemAnimator());
                                    rv1.setAdapter(categoryAdapter);
                                     categoryAdapter.set(CategoryFragment.this);

                                } else {

                                }
                            } catch (JSONException e) {

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });

            requestQueue.add(jsonObjectRequest);

        }

    }

    @Override
    public void productClick(int position, int category_id, String category_title) {
        Intent intent = new Intent(getActivity(), Category_Details_Activity.class);
        intent.putExtra("category_id", category_id);
        intent.putExtra("category_title", category_title);
        startActivity(intent);
    }
}