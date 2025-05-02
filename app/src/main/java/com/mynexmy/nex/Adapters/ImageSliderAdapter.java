package com.mynexmy.nex.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Models.product_images;

import java.util.List;

public class ImageSliderAdapter extends PagerAdapter {

    private Context context;
    private List<product_images> images;

    public ImageSliderAdapter(Context context, List<product_images> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_images, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
         product_images images1= images.get(position);
        Glide.with(context).load(images1.getProduct_image_url()).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
