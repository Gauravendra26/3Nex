package com.example.nex;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;


public class ProfileFragment extends Fragment {


     View view;
     ImageView imgP;
     RelativeLayout rlscratch,rlwish,rlmycart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_profile, container, false);


        init();
        Glide.with(getActivity()).load("https://randomuser.me/api/portraits/women/58.jpg")
                .placeholder(R.drawable.baseline_person_24)
                .into(imgP);

        rlscratch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Scratchcard_Fragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.frame_container, fragment).addToBackStack("name").commit();
            }

        });
        rlwish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new WishlistFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.frame_container, fragment).addToBackStack("name").commit();
            }

        });

        rlmycart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MycartFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

              transaction.replace(R.id.frame_container, fragment).addToBackStack("name").commit();

            }

        });

       


        return view;
    }

    private void init() {
        imgP = view.findViewById(R.id.imgP);
        rlscratch = view.findViewById(R.id.rlscratch);
        rlwish = view.findViewById(R.id.rlwish);
        rlmycart = view.findViewById(R.id.rlmycart);
    }
}