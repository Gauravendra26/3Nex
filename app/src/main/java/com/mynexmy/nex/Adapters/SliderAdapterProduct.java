package com.mynexmy.nex.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mynexmy.nex.Activitys.Movie_Activity;
import com.mynexmy.nex.Models.Slide_Model;
import com.mynexmy.nex.R;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapterProduct extends RecyclerView.Adapter<SliderAdapterProduct.ViewHolder> {

    private List<Slide_Model> slideModelProducts;
    private Context context;

    // Constructor to initialize the context and slide models
    public SliderAdapterProduct(Context context, ArrayList<Slide_Model> slideModelProducts) {
        this.context = context;
        this.slideModelProducts = slideModelProducts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for each image item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition();

        if (adapterPosition != RecyclerView.NO_POSITION) {
            String imageUrl = slideModelProducts.get(adapterPosition).getBanner_url();  // Get the image URL
            Log.d("SliderAdapterProduct", "Binding image at position " + adapterPosition + " with URL: " + imageUrl);

            // Glide image loading with error handling
            Glide.with(holder.imageView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.plash)  // Placeholder while loading
                    .error(R.drawable.plash)  // Error image if the URL is invalid
                    .transition(DrawableTransitionOptions.withCrossFade())  // Smooth transition
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Log.e("SliderAdapterProduct", "Failed to load image at position: " + adapterPosition, e);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            Log.d("SliderAdapterProduct", "Image successfully loaded at position: " + adapterPosition);
                            return false;
                        }
                    })
                    .into(holder.imageView);  // Set the image into the ImageView

            // Set click listener to navigate to another activity (e.g., Movie_Activity)
            holder.imageView.setOnClickListener(v -> {

            });
        }
    }

    @Override
    public int getItemCount() {
        return slideModelProducts.size();  // Return the size of the list
    }

    // ViewHolder class for RecyclerView item view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);  // Assuming the ImageView ID is imageView
        }
    }
}
