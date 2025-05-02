package com.example.nex;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Scratch_Buy_Fragment extends Fragment {

    View view;
    RelativeLayout rlbuy;
    ImageView imghide1,imgshow1;
    TextView tv1,tv2,tv3,tv4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_scratch__buy_, container, false);

        init();

        rlbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getActivity(), Congratulations_scratch_Activity2.class);
                startActivity(i);

            }
        });
        return view;
//        tv2.setTransformationMethod(new PasswordTransformationMethod());
//        imgshow1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                tv2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                tv2.setSelection(tv2.length());
//                imgshow1.setVisibility(View.INVISIBLE);
//                imghide1.setVisibility(View.VISIBLE);
//
//
//            }
//        });
//        imghide1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv2.setSelection(tv2.length());
//                imghide1.setVisibility(View.INVISIBLE);
//                imgshow1.setVisibility(View.VISIBLE);
//                tv2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//
//
//            }
//        });

    }
    private void init() {

        rlbuy = view.findViewById(R.id.rlbuy);
        imghide1=view.findViewById(R.id.imghide1);
        imgshow1=view.findViewById(R.id.imgshow1);
        tv1=view.findViewById(R.id.tv1);
        tv2=view.findViewById(R.id.tv2);
        tv3=view.findViewById(R.id.tv3);

    }


}

