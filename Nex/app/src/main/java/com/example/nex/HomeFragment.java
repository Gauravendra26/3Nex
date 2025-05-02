package com.example.nex;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    View view;
    ImageSlider imageSlider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();

        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQtiIhUMFEip0VsI8TddwAcFnu_iKuFvtL83NprGpQpKw&usqp=CAU&ec=48665698", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRqF2Wet1bb11FxO6bZ2HSz5KC1wYUM2X7xjgmorHWPmQ&usqp=CAU&ec=48665698", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.pngitem.com/pimgs/m/526-5268844_tv-fridge-washing-machine-hd-png-download.png", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://images.unsplash.com/photo-1542702942-4ec1d6a345bd?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8bmVja2JhbmQlMjBlYXJwaG9uZXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=400&q=60", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://cdn.firstcry.com/education/2022/11/06094158/Toy-Names-For-Kids.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.photo6, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);


        return view;
    }

    void init() {
        imageSlider = view.findViewById(R.id.imageslider);
    }
}