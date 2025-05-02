package com.example.nex;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class OrderSummaryFragment extends Fragment {

    View view;
    RelativeLayout changeaddress,placeorder1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_order_summary_, container, false);

        init();

        changeaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getActivity(), SelectAddressPage.class);
                startActivity(i);

            }
        });

        placeorder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getActivity(), Payment_Activity.class);
                startActivity(i);

            }
        });
        return view;
    }
    private void init() {

        changeaddress = view.findViewById(R.id.changeaddress);
        placeorder1 = view.findViewById(R.id.placeorder1);


    }

}