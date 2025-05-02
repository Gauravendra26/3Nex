package com.example.nex;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WishlistFragment extends Fragment implements WishlistAdapter.whenClick{

    View view;
    RecyclerView rv1;

    WishlistAdapter wishlistAdapter;


    String actualprice[] = {"500",
            "600",
            "1800",
            "1560",
            "999",
    };
    String id[] = {
            "1",
            "2",
            "45",
            "4",
            "5",
    };

    String proname[] = {"pTron Studio Pixel Over-Ear Wireless",
            "Echo Dot (3rd Gen) ",
            "Fire-Boltt Phoenix Smart Watch",
            "GLUN Bolt Electronic Portable Fishing Hook",
            "Seagull flight of fashion Single Layer",
    };
    String saleprize[] = {"351",
            "350",
            "890",
            "700",
            "800",
    };
    String p_image[] = {"https://m.media-amazon.com/images/I/51Y7SIK68KL._AC_UL320_.jpg",
            "https://m.media-amazon.com/images/I/61EXU8BuGZL._AC_UY218_.jpg",
            "https://m.media-amazon.com/images/I/61y2VVWcGBL._AC_UY218_.jpg",
            "https://m.media-amazon.com/images/I/61ofVvpW2ZL._SY445_.jpg",
            "https://m.media-amazon.com/images/I/71xJ9KGPxNL._AC_UY218_.jpg",
    };
    String rating[] = {"4.7",
            "4.5",
            "3.8",
            "4.8",
            "3.8",
    };

    List<ProductModelWishlist> productModelWishlists;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_wishlist, container, false);
        init();
        setRecyclerView();

        return view;
    }

    private void setRecyclerView() {

        productModelWishlists = new ArrayList<ProductModelWishlist>();

        for(int i = 0;i<p_image.length;i++) {
            productModelWishlists.add(new ProductModelWishlist(p_image[i], proname[i], saleprize[i],
                    actualprice[i], rating[i], id[i]));


        }
        wishlistAdapter = new WishlistAdapter(getActivity(), productModelWishlists);
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
//        LinearLayoutManager layoutManager
//                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        rv1.setLayoutManager(layoutManager);
//        rv1.setItemAnimator(new DefaultItemAnimator());
//        rv1.setAdapter(wishlistAdapter);
//        wishlistAdapter.setOnClick(this);
//



        GridLayoutManager layoutManagerC = new GridLayoutManager(getContext(), 2);

        rv1.setLayoutManager(layoutManagerC);
        rv1.setItemAnimator(new DefaultItemAnimator());
        rv1.setAdapter(wishlistAdapter);
        wishlistAdapter.setOnClick(this);

    }


    private void init() {

        rv1=view.findViewById(R.id.rv1);



    }

    @Override
    public void press(int position, String id, String image, String name,String saleprize, String accu) {
        Intent  intent=new Intent(getActivity(),ProductDetailPage.class);
        intent.putExtra("pid",id);
        intent.putExtra("pimage",image);
        intent.putExtra("pname",name);
        intent.putExtra("psale",saleprize);
        intent.putExtra("paccu",accu);
        startActivity(intent);

}
}