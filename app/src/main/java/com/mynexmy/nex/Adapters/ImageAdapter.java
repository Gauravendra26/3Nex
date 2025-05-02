package com.mynexmy.nex.Adapters;


import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mynexmy.nex.Fragments.Scratchcard_Fragment;
import com.mynexmy.nex.Models.Slide_Model_Scratch;
import com.mynexmy.nex.R;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<Slide_Model_Scratch> images;
    private whenClick onClick;
    private FragmentActivity context;

    // Interface for handling click events
    public interface whenClick {
        void setChip();
    }

    // Constructor to initialize the image list and click listener
    public ImageAdapter(FragmentActivity context, ArrayList<Slide_Model_Scratch> images,whenClick onClick) {
        this.context = context;
        this.images = images;
        this.onClick = onClick;
        // Ensure onClick is not null
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Log the position of the image using getAdapterPosition()
        int adapterPosition = holder.getAdapterPosition();
        Log.d("ImageAdapter", "Binding image at adapter position: " + adapterPosition);

        // Check if position is valid before proceeding (it might be invalid if the item has been recycled)
        if (adapterPosition != RecyclerView.NO_POSITION) {
            // Set a placeholder image while the image is loading
            Glide.with(holder.imageView.getContext())
                    .load(images.get(adapterPosition).getImage()) // Assuming getImage() provides the image URL
                    .placeholder(R.drawable.plash) // Set your placeholder image here
                    .error(R.drawable.plash) // Set an error image in case loading fails
                    .override(800, 600) // Resize image to 800x600 (adjust this as per your needs)
                      // Crop image to fit the view without distortion
                    .transition(DrawableTransitionOptions.withCrossFade()) // Add fade-in effect when image loads
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                            // Image has been successfully loaded
                            holder.imageView.setImageDrawable(resource);
                            Log.d("ImageAdapter", "Image successfully set at position: " + adapterPosition);
                        }

                        @Override
                        public void onLoadFailed(Drawable errorDrawable) {
                            // Handle the failure case
                            Log.e("ImageAdapter", "Failed to load image at position: " + adapterPosition);
                            super.onLoadFailed(errorDrawable);
                        }
                    });

            // Set the click listener for the ImageView
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Trigger the setChip() method in the interface
                    onClick.setChip();

                    // Check the position of the image and perform the necessary action
                    String position = images.get(adapterPosition).getPosition();
                    if ("0".equals(position) || "1".equals(position) || "2".equals(position) ||
                            "3".equals(position) || "4".equals(position)) {
                        // Create and display the fragment
                        Scratchcard_Fragment fragment2 = new Scratchcard_Fragment();
                        FragmentManager fragmentManager = context.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                        fragmentTransaction.replace(R.id.frame_container, fragment2).commit();

                        // Apply the bounce animation on the image view
//                        Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
//                        holder.imageView.startAnimation(myAnim);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.sliderImage); // Assuming sliderImage is the ID of the ImageView
        }
    }
}
