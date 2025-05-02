package com.mynexmy.nex.Adapters;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import com.mynexmy.nex.Activitys.Movie_Activity;
import com.mynexmy.nex.Models.Slide_Model_Product;
import com.mynexmy.nex.R;

import java.util.ArrayList;
import java.util.List;

public class ImageSliderAdapterProducts extends RecyclerView.Adapter<ImageSliderAdapterProducts.ViewHolder> {

    private List<Slide_Model_Product> slideModelProducts;

    private FragmentActivity context;

    // Interface for handling click events
    public interface whenClick {
        void setChip();
    }

    // Constructor to initialize the image list and click listener
    public ImageSliderAdapterProducts(FragmentActivity context, ArrayList<Slide_Model_Product> slideModelProducts) {
        this.context = context;
        this.slideModelProducts = slideModelProducts;
        // Ensure onClick is not null
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Log the position of the image using getAdapterPosition()
        int adapterPosition = holder.getAdapterPosition();
        Log.d("ImageSliderAdapterProducts", "Binding image at adapter position: " + adapterPosition);

        // Check if position is valid before proceeding (it might be invalid if the item has been recycled)
        if (adapterPosition != RecyclerView.NO_POSITION) {
            String imageUrl = slideModelProducts.get(adapterPosition).getImage();
            Log.d("ImageSliderAdapterProducts", "Loading image at position " + adapterPosition + " with URL: " + imageUrl);

            // Glide image loading with better error handling and logging
            Glide.with(holder.imageView.getContext())
                    .load(imageUrl) // Assuming getImage() provides the image URL
                    .placeholder(R.drawable.plash) // Set your placeholder image here
                    .error(R.drawable.plash) // Set an error image in case loading fails
                    .override(800, 600) // Resize image to 800x600 (adjust this as per your needs)

                    .transition(DrawableTransitionOptions.withCrossFade()) // Add fade-in effect when image loads
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Log.e("ImageSliderAdapterProducts", "Failed to load image at position: " + adapterPosition, e);
                            return false; // Allow Glide's default error handling
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            Log.d("ImageSliderAdapterProducts", "Image successfully set at position: " + adapterPosition);
                            return false; // Allow Glide to proceed with the default behavior
                        }
                    })
                    .into(holder.imageView);

            // Set click listener on the imageView
            holder.imageView.setOnClickListener(v -> {
                // Navigate to the MovieActivity when the image is clicked
                Intent intent = new Intent(context, Movie_Activity.class);

                context.startActivity(intent);
                final Animation myAnim = AnimationUtils.loadAnimation(context,R.anim.bounce);
                holder.imageView.startAnimation(myAnim);
            });

        }
    }

    @Override
    public int getItemCount() {
        return slideModelProducts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView); // Assuming imageView is the ID of the ImageView
        }
    }


}
