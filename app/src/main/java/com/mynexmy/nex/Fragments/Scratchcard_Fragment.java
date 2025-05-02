package com.mynexmy.nex.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.mynexmy.nex.Activitys.ScratchCart_Activity;
import com.mynexmy.nex.Adapters.Scratch_Adapter;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.Models.Scratchcard_items_Model;
import com.mynexmy.nex.Models.scratch1_model;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Signup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Scratchcard_Fragment extends Fragment implements
        Scratch_Adapter.ProductPageClick {
    ProgressDialog progressDialog;
    View view;
    private RelativeLayout[] layouts;
    LinearLayout llproduct, llDetails;
    RelativeLayout rlBuyScratch, rlcard1, rlScr49, rlScr99, rlScr149, rlScr199, rlScr249, rlScr299, rlScr349,
            rlScr399, rlScr449, rlScr499, rlScrI49, rlScrI99, rlScrI149, rlScrI199, rlScrI249, rlScrI299, rlScrI349,
            rlScrI399, rlScrI449, rlScrI499;
    ImageView imgScr49, imgScr99, imgScr149, imgScr199, imgScr249, imgScr299, imgScr349, imgScr399, imgScr449,
            imgScr499, imgScr49real, imgScr99real, imgScr149real, imgScr199real, imgScr249real, imgScr299real,
            imgScr349real, imgScr399real, imgScr449real, imgScr499real,
            imgLock1, imgLock2, imgLock3, imgLock4, imgLock5, imgLock6, imgLock7, imgLock8, imgLock9, imgLock10;

    List<scratch1_model> scratch1_models;
    RecyclerView rvScratch_card, rvScratch;
    int scid0, scid1, scid2, scid3, scid4, scid5, scid6, scid7, scid8, scid9, st0, st1, st2, st3, st4, st5, st6, st7, st8, st9,
            scpri0, scpri1, scpri2, scpri3, scpri4, scpri5, scpri6, scpri7, scpri8, scpri9, lcount0, lcount1, lcount2, lcount3,
            lcount4, lcount5, lcount6, lcount7, lcount8, lcount9, sccount0, sccount1, sccount2, sccount3, sccount4,
            sccount5, sccount6, sccount7, sccount8, sccount9, scratch_card_id, scratch_card_price;
    String sctitle0, sctitle1, sctitle2, sctitle3, sctitle4, sctitle5, sctitle6, sctitle7, sctitle8, sctitle9;
    Scratch_Adapter scratch_adapter;

    String email, first_name, last_name, address_phone, name, is_available, imageUrlofmobile_scratch_card;
    List<Scratchcard_items_Model> scratchcard_items_models;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_scratchcard, container, false);

        init();
        fetchBannerImages();
        getDetailsofScratch();
        getDataProfile();

        button();
        imgScr99real.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sccount0 >= lcount0) {
                } else {
                    scratch_card_id = scid1;
                    scratch_card_price = scpri1;
                    getDetailsScratchProduct(scratch_card_id);
                }
            }
        });
        imgScr149real.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.shape_scborder);
                rlScr149.setBackgroundColor(Color.WHITE);
                rlScr99.setBackground(drawable);
                rlScr199.setBackground(drawable);
                rlScr249.setBackground(drawable);
                rlScr299.setBackground(drawable);
                rlScr349.setBackground(drawable);
                rlScr399.setBackground(drawable);
                rlScr449.setBackground(drawable);
                rlScr499.setBackground(drawable);
                scratch_card_id = scid2;
                scratch_card_price = scpri2;
                getDetailsScratchProduct(scratch_card_id);
            }
        });
        imgScr199real.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.shape_scborder);
                rlScr199.setBackgroundColor(Color.WHITE);
                rlScr99.setBackground(drawable);
                rlScr149.setBackground(drawable);
                rlScr249.setBackground(drawable);
                rlScr299.setBackground(drawable);
                rlScr349.setBackground(drawable);
                rlScr399.setBackground(drawable);
                rlScr449.setBackground(drawable);
                rlScr499.setBackground(drawable);
                scratch_card_id = scid3;
                scratch_card_price = scpri3;
                getDetailsScratchProduct(scratch_card_id);
            }
        });
        imgScr249real.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.shape_scborder);
                rlScr249.setBackgroundColor(Color.WHITE);
                rlScr99.setBackground(drawable);
                rlScr199.setBackground(drawable);
                rlScr149.setBackground(drawable);
                rlScr299.setBackground(drawable);
                rlScr349.setBackground(drawable);
                rlScr399.setBackground(drawable);
                rlScr449.setBackground(drawable);
                rlScr499.setBackground(drawable);
                scratch_card_id = scid4;
                scratch_card_price = scpri4;
                getDetailsScratchProduct(scratch_card_id);

            }
        });
        imgScr299real.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.shape_scborder);
                rlScr299.setBackgroundColor(Color.WHITE);
                rlScr99.setBackground(drawable);
                rlScr199.setBackground(drawable);
                rlScr249.setBackground(drawable);
                rlScr149.setBackground(drawable);
                rlScr349.setBackground(drawable);
                rlScr399.setBackground(drawable);
                rlScr449.setBackground(drawable);
                rlScr499.setBackground(drawable);
                scratch_card_id = scid5;
                scratch_card_price = scpri5;
                getDetailsScratchProduct(scratch_card_id);

            }
        });
        imgScr349real.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.shape_scborder);
                rlScr349.setBackgroundColor(Color.WHITE);
                rlScr99.setBackground(drawable);
                rlScr199.setBackground(drawable);
                rlScr249.setBackground(drawable);
                rlScr299.setBackground(drawable);
                rlScr149.setBackground(drawable);
                rlScr399.setBackground(drawable);
                rlScr449.setBackground(drawable);
                rlScr499.setBackground(drawable);
                scratch_card_id = scid6;
                scratch_card_price = scpri6;
                getDetailsScratchProduct(scratch_card_id);

            }
        });
        imgScr399real.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.shape_scborder);
                rlScr399.setBackgroundColor(Color.WHITE);
                rlScr99.setBackground(drawable);
                rlScr199.setBackground(drawable);
                rlScr249.setBackground(drawable);
                rlScr299.setBackground(drawable);
                rlScr349.setBackground(drawable);
                rlScr149.setBackground(drawable);
                rlScr449.setBackground(drawable);
                rlScr499.setBackground(drawable);
                scratch_card_id = scid7;
                scratch_card_price = scpri7;
                getDetailsScratchProduct(scratch_card_id);
            }
        });
        imgScr449real.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.shape_scborder);
                rlScr449.setBackgroundColor(Color.WHITE);
                rlScr99.setBackground(drawable);
                rlScr199.setBackground(drawable);
                rlScr249.setBackground(drawable);
                rlScr299.setBackground(drawable);
                rlScr349.setBackground(drawable);
                rlScr399.setBackground(drawable);
                rlScr149.setBackground(drawable);
                rlScr499.setBackground(drawable);
                scratch_card_id = scid8;
                scratch_card_price = scpri8;
                getDetailsScratchProduct(scratch_card_id);
            }
        });
        imgScr499real.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.shape_scborder);
                rlScr499.setBackgroundColor(Color.WHITE);
                rlScr99.setBackground(drawable);
                rlScr199.setBackground(drawable);
                rlScr249.setBackground(drawable);
                rlScr299.setBackground(drawable);
                rlScr349.setBackground(drawable);
                rlScr399.setBackground(drawable);
                rlScr449.setBackground(drawable);
                rlScr149.setBackground(drawable);
                scratch_card_id = scid9;
                scratch_card_price = scpri9;
                getDetailsScratchProduct(scratch_card_id);
            }
        });
        return view;
    }

    private void init() {
        layouts = new RelativeLayout[10];
        rlBuyScratch = view.findViewById(R.id.rlBuyScratch);
        layouts[0] = view.findViewById(R.id.rlScrI49);
        layouts[1] = view.findViewById(R.id.rlScrI99);
        layouts[2] = view.findViewById(R.id.rlScrI149);
        layouts[3] = view.findViewById(R.id.rlScrI199);
        layouts[4] = view.findViewById(R.id.rlScrI249);
        layouts[5] = view.findViewById(R.id.rlScrI299);
        layouts[6] = view.findViewById(R.id.rlScrI349);
        layouts[7] = view.findViewById(R.id.rlScrI399);
        layouts[8] = view.findViewById(R.id.rlScrI449);
        layouts[9] = view.findViewById(R.id.rlScrI499);

        rlScr49 = view.findViewById(R.id.rlScr49);
        rlScr99 = view.findViewById(R.id.rlScr99);
        rlScr149 = view.findViewById(R.id.rlScr149);
        rlScr199 = view.findViewById(R.id.rlScr199);
        rlScr249 = view.findViewById(R.id.rlScr249);
        rlScr299 = view.findViewById(R.id.rlScr299);
        rlScr349 = view.findViewById(R.id.rlScr349);
        rlScr399 = view.findViewById(R.id.rlScr399);
        rlScr449 = view.findViewById(R.id.rlScr449);
        rlScr499 = view.findViewById(R.id.rlScr499);

        rvScratch_card = view.findViewById(R.id.rvScratch_card);
        rvScratch = view.findViewById(R.id.rvScratch);
        imgScr49 = view.findViewById(R.id.imgScr49);
        imgScr99 = view.findViewById(R.id.imgScr99);
        imgScr149 = view.findViewById(R.id.imgScr149);
        imgScr199 = view.findViewById(R.id.imgScr199);
        imgScr249 = view.findViewById(R.id.imgScr249);
        imgScr299 = view.findViewById(R.id.imgScr299);
        imgScr349 = view.findViewById(R.id.imgScr349);
        imgScr399 = view.findViewById(R.id.imgScr399);
        imgScr449 = view.findViewById(R.id.imgScr449);
        imgScr499 = view.findViewById(R.id.imgScr499);
        imgScr49real = view.findViewById(R.id.imgScr49real);
        imgScr99real = view.findViewById(R.id.imgScr99real);
        imgScr149real = view.findViewById(R.id.imgScr149real);
        imgScr199real = view.findViewById(R.id.imgScr199real);
        imgScr249real = view.findViewById(R.id.imgScr249real);
        imgScr299real = view.findViewById(R.id.imgScr299real);
        imgScr349real = view.findViewById(R.id.imgScr349real);
        imgScr399real = view.findViewById(R.id.imgScr399real);
        imgScr449real = view.findViewById(R.id.imgScr449real);
        imgScr499real = view.findViewById(R.id.imgScr499real);

        imgLock1 = view.findViewById(R.id.imgLock1);
        imgLock2 = view.findViewById(R.id.imgLock2);
        imgLock3 = view.findViewById(R.id.imgLock3);
        imgLock4 = view.findViewById(R.id.imgLock4);
        imgLock5 = view.findViewById(R.id.imgLock5);
        imgLock6 = view.findViewById(R.id.imgLock6);
        imgLock7 = view.findViewById(R.id.imgLock7);
        imgLock8 = view.findViewById(R.id.imgLock8);
        imgLock9 = view.findViewById(R.id.imgLock9);
        imgLock10 = view.findViewById(R.id.imgLock10);

        llproduct = view.findViewById(R.id.llproduct);
        llDetails = view.findViewById(R.id.llDetails);

    }


    int firstScratchCardId = -1, firstScratchCardPrice = -1;

    void getDetailsofScratch() {
        Log.d("ScratchCardDetails", "Starting getDetailsofScratch()");

        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        progressDialog.setCancelable(false); // Make the dialog non-cancelable
        progressDialog.setCanceledOnTouchOutside(false); // Make the dialog not disappear when touched outside
        progressDialog.setContentView(R.layout.new_progresslogo);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Log.d("ScratchCardDetails", "ProgressDialog shown");

        // Create a new request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, ApiData.Scratchcards,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("ScratchCardDetails", "Response received");
                            progressDialog.dismiss();
                            Log.d("ScratchCardDetails", "ProgressDialog dismissed");

                            scratch1_models = new ArrayList<scratch1_model>();
                            JSONArray jsonArray = response.getJSONArray("data");
                            Log.d("ScratchCardDetails", "JSON Array size: " + jsonArray.length());

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject user = jsonArray.getJSONObject(i);

                                Log.d("ScratchCardDetails", "Processing scratch card at index " + i);
                                Log.d("ScratchCardDetails", "Scratch Card ID: " + user.optInt("scratch_card_id"));
                                if (i == 0) {
                                    scid1 = user.optInt("scratch_card_id");
                                    sctitle1 = user.optString("scratch_card_title");
                                    st1 = user.optInt("status");
                                    scpri1 = user.optInt("scratch_card_price");
                                    lcount1 = user.optInt("level_count");
                                    sccount1 = user.optInt("scratch_count");
                                    firstScratchCardId = scid0;
                                    firstScratchCardPrice = scpri0;
                                }
                                if (i == 1) {
                                    scid3 = user.optInt("scratch_card_id");
                                    sctitle3 = user.optString("scratch_card_title");
                                    st3 = user.optInt("status");
                                    scpri3 = user.optInt("scratch_card_price");
                                    lcount3 = user.optInt("level_count");
                                    sccount3 = user.optInt("scratch_count");

                                }
                                if (i == 2) {
                                    scid5 = user.optInt("scratch_card_id");
                                    sctitle5 = user.optString("scratch_card_title");
                                    st5 = user.optInt("status");
                                    scpri5 = user.optInt("scratch_card_price");
                                    lcount5 = user.optInt("level_count");
                                    sccount5 = user.optInt("scratch_count");

                                }
                                if (i == 3) {
                                    scid7 = user.optInt("scratch_card_id");
                                    sctitle7 = user.optString("scratch_card_title");
                                    st7 = user.optInt("status");
                                    scpri7 = user.optInt("scratch_card_price");
                                    lcount7 = user.optInt("level_count");
                                    sccount7 = user.optInt("scratch_count");

                                }
                                if (i == 4) {
                                    scid9 = user.optInt("scratch_card_id");
                                    sctitle9 = user.optString("scratch_card_title");
                                    st9 = user.optInt("status");
                                    scpri9 = user.optInt("scratch_card_price");
                                    lcount9 = user.optInt("level_count");
                                    sccount9 = user.optInt("scratch_count");

                                }

                            }
                            Log.e("modelClassData",
                                    "scid0: " + scid0 + " " +
                                            "scid1: " + scid1 + " " +
                                            "scid2: " + scid2 + " " +
                                            "scid3: " + scid3 + " " +
                                            "scid4: " + scid4 + " " +
                                            "scid5: " + scid5 + " " +
                                            "scid6: " + scid6 + " " +
                                            "scid7: " + scid7 + " " +
                                            "scid8: " + scid8 + " " +
                                            "scid9: " + scid9 + " " +
                                            "st0: " + st0 + " " +
                                            "st1: " + st1 + " " +
                                            "st2: " + st2 + " " +
                                            "st3: " + st3 + " " +
                                            "st4: " + st4 + " " +
                                            "st5: " + st5 + " " +
                                            "st6: " + st6 + " " +
                                            "st7: " + st7 + " " +
                                            "st8: " + st8 + " " +
                                            "st9: " + st9 + " " +
                                            "scpri0: " + scpri0 + " " +
                                            "scpri1: " + scpri1 + " " +
                                            "scpri2: " + scpri2 + " " +
                                            "scpri3: " + scpri3 + " " +
                                            "scpri4: " + scpri4 + " " +
                                            "scpri5: " + scpri5 + " " +
                                            "scpri6: " + scpri6 + " " +
                                            "scpri7: " + scpri7 + " " +
                                            "scpri8: " + scpri8 + " " +
                                            "scpri9: " + scpri9 + " " +
                                            "lcount0: " + lcount0 + " " +
                                            "lcount1: " + lcount1 + " " +
                                            "lcount2: " + lcount2 + " " +
                                            "lcount3: " + lcount3 + " " +
                                            "lcount4: " + lcount4 + " " +
                                            "lcount5: " + lcount5 + " " +
                                            "lcount6: " + lcount6 + " " +
                                            "lcount7: " + lcount7 + " " +
                                            "lcount8: " + lcount8 + " " +
                                            "lcount9: " + lcount9 + " " +
                                            "sccount0: " + sccount0 + " " +
                                            "sccount1: " + sccount1 + " " +
                                            "sccount2: " + sccount2 + " " +
                                            "sccount3: " + sccount3 + " " +
                                            "sccount4: " + sccount4 + " " +
                                            "sccount5: " + sccount5 + " " +
                                            "sccount6: " + sccount6 + " " +
                                            "sccount7: " + sccount7 + " " +
                                            "sccount8: " + sccount8 + " " +
                                            "sccount9: " + sccount9 + " " +
                                            "sctitle0: " + sctitle0 + " " +
                                            "sctitle1: " + sctitle1 + " " +
                                            "sctitle2: " + sctitle2 + " " +
                                            "sctitle3: " + sctitle3 + " " +
                                            "sctitle4: " + sctitle4 + " " +
                                            "sctitle5: " + sctitle5 + " " +
                                            "sctitle6: " + sctitle6 + " " +
                                            "sctitle7: " + sctitle7 + " " +
                                            "sctitle8: " + sctitle8 + " " +
                                            "sctitle9: " + sctitle9);

                            if (scpri1 == 99) {
                                if (sccount1 >= lcount1) {
                                    imgLock2.setVisibility(View.VISIBLE);

                                    if (scpri3 == 199) {
                                        if (sccount3 >= lcount3) {
                                            imgLock4.setVisibility(View.GONE);
                                            getDetailsScratchProduct(scid3);
                                            scratch_card_id = scid3;
                                            scratch_card_price = scpri3;
                                            imgScr199real.setVisibility(View.VISIBLE);
                                            if (scpri5 == 299) {
                                                if (sccount5 >= lcount5) {
                                                    imgLock6.setVisibility(View.GONE);
                                                    getDetailsScratchProduct(scid5);
                                                    scratch_card_id = scid5;
                                                    scratch_card_price = scpri5;
                                                    imgScr299real.setVisibility(View.VISIBLE);
                                                    if (scpri7 == 399) {
                                                        if (sccount7 >= lcount7) {
                                                            imgLock8.setVisibility(View.GONE);
                                                            getDetailsScratchProduct(scid7);
                                                            scratch_card_id = scid7;
                                                            scratch_card_price = scpri7;
                                                            imgScr399real.setVisibility(View.VISIBLE);
                                                            if (scpri9 == 499) {
                                                                if (sccount9 >= lcount9) {
                                                                    imgLock10.setVisibility(View.GONE);
                                                                    getDetailsScratchProduct(scid9);
                                                                    scratch_card_id = scid9;
                                                                    scratch_card_price = scpri9;
                                                                    imgScr499real.setVisibility(View.VISIBLE);

//
                                                                } else {
                                                                    imgLock10.setVisibility(View.GONE);
                                                                    rlScr499.setBackgroundColor(Color.WHITE);
                                                                    getDetailsScratchProduct(scid9);
                                                                    scratch_card_id = scid9;
                                                                    scratch_card_price = scpri9;
                                                                    imgScr499real.setVisibility(View.VISIBLE);

                                                                }
                                                            }
                                                        } else {
                                                            imgLock8.setVisibility(View.GONE);
                                                            rlScr399.setBackgroundColor(Color.WHITE);
                                                            getDetailsScratchProduct(scid7);
                                                            scratch_card_id = scid7;
                                                            scratch_card_price = scpri7;
                                                            imgScr399real.setVisibility(View.VISIBLE);

                                                        }
                                                    }
                                                } else {
                                                    imgLock6.setVisibility(View.GONE);
                                                    rlScr299.setBackgroundColor(Color.WHITE);
                                                    getDetailsScratchProduct(scid5);
                                                    scratch_card_id = scid5;
                                                    scratch_card_price = scpri5;
                                                    imgScr299real.setVisibility(View.VISIBLE);

                                                }
                                            }

                                        } else {
                                            imgLock4.setVisibility(View.GONE);
                                            rlScr199.setBackgroundColor(Color.WHITE);
                                            getDetailsScratchProduct(scid3);
                                            scratch_card_id = scid3;
                                            scratch_card_price = scpri3;
                                            imgScr199real.setVisibility(View.VISIBLE);

                                        }
                                    }


                                } else {
                                    imgLock2.setVisibility(View.GONE);
                                    rlScr99.setBackgroundColor(Color.WHITE);
                                    getDetailsScratchProduct(scid1);
                                    scratch_card_id = scid1;
                                    scratch_card_price = scpri1;
                                    imgScr99real.setVisibility(View.VISIBLE);
                                }
                            }
                        } catch (JSONException e) {

                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e("ScratchCardDetails", "Error occurred: " + error.getMessage());
                        // Display an error message or retry request
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Set the token in the headers
                SharedPreferences sharedPreferences =
                        getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + sharedPreferences.getString("Login_Token", ""));
                Log.d("ScratchCardDetails", "Authorization header set");
                return headers;
            }
        };

        // Add the request to the Volley request queue
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
        Log.d("ScratchCardDetails", "Request added to queue");
    }

    void getDataProfile() {


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, ApiData.Profile,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {


                    if (response.getBoolean("status") == true) {
                        JSONObject jsonObject1 = response.getJSONObject("data");
                        JSONObject jsonObject = jsonObject1.getJSONObject("details");

                        email = jsonObject.optString("email");
                        first_name = jsonObject.optString("first_name");
                        last_name = jsonObject.optString("last_name");
                        address_phone = jsonObject.optString("mobile");
                        name = first_name + " " + last_name;
//                            Toast.makeText(getContext(), ""+name+" "+email+" "+address_phone, Toast.LENGTH_SHORT).show();

                        llproduct.setVisibility(View.VISIBLE);
                        rlBuyScratch.setVisibility(View.VISIBLE);

                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", "Error: " + error.getMessage());

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                SharedPreferences sharedPreferences =
                        getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + sharedPreferences.getString("Login_Token", ""));


                return headers;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(req);
    }

    public void fetchBannerImages() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ApiData.Banners,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("BannerData", response);

                            JSONObject jsonObject = new JSONObject(response);

                            boolean status = jsonObject.getBoolean("status");

                            if (status) {
                                // Check if "data" object exists
                                if (jsonObject.has("data")) {
                                    // Get the "data" object
                                    JSONObject dataObject = jsonObject.getJSONObject("data");
                                    JSONArray mobile_scratch_cardArray = dataObject.optJSONArray("mobile_scratch_card");

                                    for (int i = 0; i < mobile_scratch_cardArray.length(); i++) {
                                        imageUrlofmobile_scratch_card = mobile_scratch_cardArray.getString(i);
                                        if (isAdded()) {
//                                            if (i == 0) {
//                                                Glide.with(requireContext())
//                                                        .load(imageUrlofmobile_scratch_card)
//                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
//                                                        .into(imgScr49);
//                                            Glide.with(requireContext())
//                                                        .load(imageUrlofmobile_scratch_card)
//                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
//                                                        .into(imgScr49real);
//                                            }
                                            if (i == 8) {
                                                Glide.with(requireContext())
                                                        .load(imageUrlofmobile_scratch_card)
                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                                                        .into(imgScr99);
                                                Glide.with(requireContext())
                                                        .load(imageUrlofmobile_scratch_card)
                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                                                        .into(imgScr99real);
                                            }
//                                            if (i == 2) {
//                                                Glide.with(requireContext())
//                                                        .load(imageUrlofmobile_scratch_card)
//                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
//                                                        .into(imgScr149);
//                                                Glide.with(requireContext())
//                                                        .load(imageUrlofmobile_scratch_card)
//                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
//                                                        .into(imgScr149real);
//                                            }
                                            if (i == 6) {
                                                Glide.with(requireContext())
                                                        .load(imageUrlofmobile_scratch_card)
                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                                                        .into(imgScr199);
                                                Glide.with(requireContext())
                                                        .load(imageUrlofmobile_scratch_card)
                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                                                        .into(imgScr199real);
                                            }
//                                            if (i == 4) {
//                                                Glide.with(requireContext())
//                                                        .load(imageUrlofmobile_scratch_card)
//                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
//                                                        .into(imgScr249);
//                                                Glide.with(requireContext())
//                                                        .load(imageUrlofmobile_scratch_card)
//                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
//                                                        .into(imgScr249real);
//                                            }
                                            if (i == 4) {
                                                Glide.with(requireContext())
                                                        .load(imageUrlofmobile_scratch_card)
                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                                                        .into(imgScr299);
                                                Glide.with(requireContext())
                                                        .load(imageUrlofmobile_scratch_card)
                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                                                        .into(imgScr299real);
                                            }
//                                            if (i == 6) {
//                                                Glide.with(requireContext())
//                                                        .load(imageUrlofmobile_scratch_card)
//                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
//                                                        .into(imgScr349);
//                                                Glide.with(requireContext())
//                                                        .load(imageUrlofmobile_scratch_card)
//                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
//                                                        .into(imgScr349real);
//                                            }

                                            if (i == 2) {
                                                Glide.with(requireContext())
                                                        .load(imageUrlofmobile_scratch_card)
                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                                                        .into(imgScr399);
                                                Glide.with(requireContext())
                                                        .load(imageUrlofmobile_scratch_card)
                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                                                        .into(imgScr399real);
                                            }
//                                            if (i == 8) {
//                                                Glide.with(requireContext())
//                                                        .load(imageUrlofmobile_scratch_card)
//                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
//                                                        .into(imgScr449);
//                                                Glide.with(requireContext())
//                                                        .load(imageUrlofmobile_scratch_card)
//                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
//                                                        .into(imgScr449real);
//                                            }
                                            if (i == 0) {
                                                Glide.with(requireContext())
                                                        .load(imageUrlofmobile_scratch_card)
                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                                                        .into(imgScr499);
                                                Glide.with(requireContext())
                                                        .load(imageUrlofmobile_scratch_card)
                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                                                        .into(imgScr499real);
                                            }
                                        }
                                    }

                                    Log.e("BannerData", " mobile_scratch_cardArray " + mobile_scratch_cardArray);

                                } else {
                                    // Handle case when "data" object is missing
                                    Log.e("BannerData", "No 'data' object found in JSON response");
                                }
                            } else {
                                // Handle case when status is false
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Handle JSON exception
                            Log.e("BannerData", "Error parsing JSON: " + e.getMessage());
//                            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log the Volley error object
                        Log.e("Volley Error", "Error: " + error.toString(), error);
                        // Handle the error as needed
                    }
                }
        );

// Add the request to the request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }


    public void getDetailsScratchProduct(int scratch_card_id) {

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("scratch_card_id", scratch_card_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

// Create a new request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ApiData.Scratchcards_details, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            scratchcard_items_models = new ArrayList<Scratchcard_items_Model>();
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject user = jsonArray.getJSONObject(i);
                                int bucket_product_map_id = user.optInt("bucket_product_map_id");
                                int bucket_id = user.optInt("bucket_id");
                                int bucket_product_id = user.optInt("bucket_product_id");
                                int ordering = user.optInt("ordering");
                                int bucket_product_status = user.optInt("bucket_product_status");
                                int added_by = user.optInt("added_by");
                                String bucket_product_image = user.optString("bucket_product_image");
                                String bucket_product_title = user.optString("bucket_product_title");
                                String created_at = user.optString("created_at");
                                String updated_at = user.optString("updated_at");

                                scratchcard_items_models.add(new Scratchcard_items_Model(bucket_product_map_id,
                                        bucket_id, bucket_product_id, ordering, bucket_product_status,
                                        added_by, bucket_product_title,
                                        bucket_product_image, created_at, updated_at));

                            }
                            is_available = String.valueOf(response.getBoolean("is_available"));
                            scratch_adapter = new Scratch_Adapter(getContext(), scratchcard_items_models);

                            GridLayoutManager layoutManagerC = new GridLayoutManager(getContext(),
                                    3);
                            rvScratch_card.setLayoutManager(layoutManagerC);
                            rvScratch_card.setItemAnimator(new DefaultItemAnimator());
                            rvScratch_card.setAdapter(scratch_adapter);
                            scratch_adapter.set(Scratchcard_Fragment.this);

                        } catch (JSONException e) {

                        }
//
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error
                        // Display an error message or retry request
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Set the token in the headers
                SharedPreferences sharedPreferences =
                        getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + sharedPreferences.getString("Login_Token", ""));
                return headers;
            }
        };

// Add the request to the Volley request queue
        // Check if getContext() or getActivity() is null before proceeding
        if (getContext() != null) {
            // Create the Volley request queue here
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            queue.add(request);
        }
        if (isAdded()) {
            // Fragment is attached to the activity, safe to proceed with the request
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            queue.add(request);
        }

    }


    void button() {
        rlBuyScratch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.equals("null") || address_phone.equals("null") || name.equals("null")) {

                    Log.e("scratchcardfragment", "One or more values (email, address_phone, name) are null. email: " + email + ", address_phone: " + address_phone + ", name: " + name);

                    Intent homeIntent = new Intent(getContext(), Signup.class);
                    homeIntent.putExtra("scratchcartIndicator", 21);
                    homeIntent.putExtra("scratch_card_id", scratch_card_id);
                    homeIntent.putExtra("scratch_card_price", scratch_card_price);
                    homeIntent.putExtra("address_phone", address_phone);
                    startActivity(homeIntent);

                } else {
                    if (scratch_card_price == 0) {
                        Toast.makeText(getContext(), "Scratch Card Not Activated For You", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent i = new Intent(getActivity(), ScratchCart_Activity.class);
                        i.putExtra("scratch_card_id", scratch_card_id);
                        i.putExtra("scratch_card_price", scratch_card_price);
                        i.putExtra("email", email);
                        i.putExtra("address_phone", address_phone);
                        i.putExtra("name", name);
                        startActivity(i);
                    }

                }


                rlBuyScratch.startAnimation(clickAnimation());


            }
        });
        llproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.equals("null") || address_phone.equals("null") || name.equals("null")) {
                    Log.e("scratchcardfragment", "One or more values (email, address_phone, name) are null. email: " + email + ", address_phone: " + address_phone + ", name: " + name);

                    Intent homeIntent = new Intent(getContext(), Signup.class);
                    homeIntent.putExtra("scratchcartIndicator", 21);
                    homeIntent.putExtra("scratch_card_id", scratch_card_id);
                    homeIntent.putExtra("scratch_card_price", scratch_card_price);
                    homeIntent.putExtra("address_phone", address_phone);
                    startActivity(homeIntent);

                } else {
                    if (scratch_card_price == 0) {
                        Toast.makeText(getContext(), "Scratch Card Not Activated For You", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent i = new Intent(getActivity(), ScratchCart_Activity.class);
                        i.putExtra("scratch_card_id", scratch_card_id);
                        i.putExtra("scratch_card_price", scratch_card_price);
                        i.putExtra("email", email);
                        i.putExtra("address_phone", address_phone);
                        i.putExtra("name", name);
                        startActivity(i);
                    }

                }

            }
        });
    }

    @Override
    public void productClickCard(int position) {
        if (email.equals("null") || address_phone.equals("null") || name.equals("null")) {
            Log.e("scratchcardfragment", "One or more values (email, address_phone, name) are null. email: " + email + ", address_phone: " + address_phone + ", name: " + name);
            Intent homeIntent = new Intent(getContext(), Signup.class);
            homeIntent.putExtra("scratchcartIndicator", 21);
            homeIntent.putExtra("scratch_card_id", scratch_card_id);
            homeIntent.putExtra("scratch_card_price", scratch_card_price);
            homeIntent.putExtra("address_phone", address_phone);
            startActivity(homeIntent);
        } else {
            if (scratch_card_price == 0) {
                Toast.makeText(getContext(), "Scratch Card Not Activated For You", Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent(getActivity(), ScratchCart_Activity.class);
                i.putExtra("scratch_card_id", scratch_card_id);
                i.putExtra("scratch_card_price", scratch_card_price);
                i.putExtra("email", email);
                i.putExtra("address_phone", address_phone);
                i.putExtra("name", name);
                startActivity(i);
            }
        }
    }
    public AlphaAnimation clickAnimation() {
        return new AlphaAnimation(1F, 0.1F); // Change "0.4F" as per your recruitment.
    }
}