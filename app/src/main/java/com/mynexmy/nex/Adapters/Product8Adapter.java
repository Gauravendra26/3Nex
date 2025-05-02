package com.mynexmy.nex.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.mynexmy.nex.Models.Slide_Model_8Product;
import com.mynexmy.nex.R;
import java.util.List;

public class Product8Adapter extends RecyclerView.Adapter<Product8Adapter.ImageViewHolder> {

    private List<Slide_Model_8Product> slideModelList;

    public Product8Adapter(List<Slide_Model_8Product> slideModelList) {
        this.slideModelList = slideModelList;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product8_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        // Load the image into the ImageView using Glide
        Slide_Model_8Product slideModel = slideModelList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(slideModel.getImage())
                .into(holder.img8product);
    }

    @Override
    public int getItemCount() {
        return slideModelList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView img8product;
        MaterialCardView mcv8product;

        public ImageViewHolder(View itemView) {
            super(itemView);
            img8product = itemView.findViewById(R.id.img8product);
            mcv8product = itemView.findViewById(R.id.mcv8product);
        }
    }
}
