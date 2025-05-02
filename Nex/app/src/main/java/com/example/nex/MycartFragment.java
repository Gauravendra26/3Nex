package com.example.nex;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MycartFragment extends Fragment implements PAdapter.whenClick {

View view;
    RelativeLayout placeorder;
    RecyclerView rv1;
    PAdapter adapter;
    String image[] = {"https://m.media-amazon.com/images/I/51Y7SIK68KL._AC_UL320_.jpg",
            "https://m.media-amazon.com/images/I/61EXU8BuGZL._AC_UY218_.jpg",
            "https://m.media-amazon.com/images/I/61y2VVWcGBL._AC_UY218_.jpg",
            "https://m.media-amazon.com/images/I/61ofVvpW2ZL._SY445_.jpg",
            "https://m.media-amazon.com/images/I/71xJ9KGPxNL._AC_UY218_.jpg",
    };
    String name[] = {"pTron Studio Pixel Over-Ear Wireless",
            "Echo Dot (3rd Gen) ",
            "Fire-Boltt Phoenix Smart Watch",
            "GLUN Bolt Electronic Portable Fishing Hook",
            "Seagull flight of fashion Single Layer",
    };
    String sale[] = {"200",
            "400",
            "600",
            "700",
            "800",
    };

    String actualpric[] = {"400",
            "355",
            "800",
            "1200",
            "1500",
    };
    String id[] = {
            "1",
            "2",
            "45",
            "4",
            "5",
    };
    List<ProductModel> userList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_mycart, container, false);




        rv1=view.findViewById(R.id.rv1);

        userList = new ArrayList<ProductModel>();

        for(int i = 0;i<image.length;i++){
            userList.add(new ProductModel( image[i],  name[i],  "",  sale[i],
                    actualpric[i],"",  id[i]));
        }

        adapter = new PAdapter(getActivity(), userList);
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv1.setLayoutManager(layoutManager);
        rv1.setItemAnimator(new DefaultItemAnimator());
        rv1.setAdapter(adapter);
        adapter.setOnClick(this);
        init();

        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new OrderSummaryFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.frame_container, fragment).addToBackStack("name").commit();
            }
        });
        return view;
    }
    private void init() {

        placeorder = view.findViewById(R.id.placeorder);

    }

    @Override
    public void press(int position, String id, String image, String name, String accu) {

    }
}