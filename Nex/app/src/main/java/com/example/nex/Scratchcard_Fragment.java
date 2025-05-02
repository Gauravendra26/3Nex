package com.example.nex;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class Scratchcard_Fragment extends Fragment {

    View view;
    RelativeLayout rlBuy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_scratchcard, container, false);

        init();
        rlBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Scratch_Buy_Fragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.frame_container, fragment).addToBackStack("name").commit();
            }
        });





        return view;
    }

    private void init() {

        rlBuy = view.findViewById(R.id.rlBuy);

    }
}