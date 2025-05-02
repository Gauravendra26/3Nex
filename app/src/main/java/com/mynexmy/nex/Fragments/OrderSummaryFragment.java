package com.mynexmy.nex.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mynexmy.nex.Home;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Activitys.SelectAddress_MyCartActivity;


public class OrderSummaryFragment extends Fragment {
    View view;
    RelativeLayout changeaddress, placeorder1;
    ImageView btn_back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_summary_, container, false);
        init();
        placeorder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Home.class);
                startActivity(i);
            }
        });

        changeaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SelectAddress_MyCartActivity.class);
                startActivity(i);
            }
        });

        return view;
    }

    private void init() {

        changeaddress = view.findViewById(R.id.changeaddress);
        placeorder1 = view.findViewById(R.id.placeorder1);
        btn_back = view.findViewById(R.id.btn_back);


    }

}