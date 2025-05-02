package com.example.nex;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class SearchFragment extends Fragment {

    View view;
    ImageView img1, img2, img3, img4,img5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        init();
        Glide.with(getActivity()).load("https://w0.peakpx.com/wallpaper/799/824/HD-wallpaper-nice-looking-girl-model-with-blur-background-girl.jpg")
                .placeholder(R.drawable.baseline_person_24)
                .into(img1);
        Glide.with(getActivity()).load("https://img.freepik.com/free-photo/smiley-man-with-brown-hat-shopping-bags_23-2148316518.jpg?size=626&ext=jpg")
                .placeholder(R.drawable.baseline_person_24)
                .into(img2);
        Glide.with(getActivity()).load("https://img.freepik.com/free-photo/young-woman-with-shopping-bags-city_1303-16815.jpg?size=626&ext=jpg&ga=GA1.2.1103702345.1681905134&semt=ais")
                .placeholder(R.drawable.baseline_person_24)
                .into(img3);
        Glide.with(getActivity()).load("https://media.istockphoto.com/id/1288151008/photo/boy-at-blue-background-stock-photo.jpg?s=612x612&w=0&k=20&c=3GpKxajzMWmNPNE6FYZSZyhQJadZQTg2eIZ9OG7rge8=")
                .placeholder(R.drawable.baseline_person_24)
                .into(img4);
        Glide.with(getActivity()).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS8CkiNY02MSUV9l3LgwTQZACDrXsmV-SJF7QOuCUP3eQ&usqp=CAU&ec=48665698")
                .placeholder(R.drawable.baseline_person_24)
                .into(img5);

        return view;
    }

    private void init() {
        img1 = view.findViewById(R.id.img1);
        img2 = view.findViewById(R.id.img2);
        img3 = view.findViewById(R.id.img3);
        img4 = view.findViewById(R.id.img4);
        img5 = view.findViewById(R.id.img5);
    }
}