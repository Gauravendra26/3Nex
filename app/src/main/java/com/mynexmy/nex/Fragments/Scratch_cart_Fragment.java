package com.mynexmy.nex.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mynexmy.nex.Adapters.Scratch_Adapter;
import com.mynexmy.nex.Models.Scratchcard_items_Model;
import com.mynexmy.nex.Activitys.Order_Summary_Activity;
import com.mynexmy.nex.R;

import java.util.List;

public class Scratch_cart_Fragment extends Fragment{
    View view;
    ProgressDialog progressDialog;
    RelativeLayout placeorder1;
    RecyclerView rv4;
    Scratch_Adapter scratch_adapter;
    LinearLayout ll1;
    ImageView btn_back2;


    List<Scratchcard_items_Model> scratchcard_items_models;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_scratch_cart_, container, false);

        init();

        placeorder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Order_Summary_Activity.class);
                startActivity(i);
            }
        });

        btn_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().finish();
            }
        });


        return view;
    }




    private void init() {

        rv4 = view.findViewById(R.id.rv4);
        placeorder1 = view.findViewById(R.id.placeorder1);
        btn_back2 = view.findViewById(R.id.btn_back2);


    }

//    @Override
//    public void productClick1(int position, int bucket_id) {
//
//    }

//    @Override
//    public void press(int position, String id, String p_description, String p_image, String p_name, String p_saleprice) {
//        Intent intent = new Intent(getActivity(), Product_detail_page3_Activity.class);
//        intent.putExtra("pid", id);
//        intent.putExtra("pimage", p_image);
//        intent.putExtra("pname", p_name);
//        intent.putExtra("psale", p_saleprice);
//        intent.putExtra("pdesc", p_description);
//
//        startActivity(intent);
//
//    }


}





