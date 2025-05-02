package com.mynexmy.nex.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.mynexmy.nex.Activitys.Clothing_Activity;
import com.mynexmy.nex.Activitys.Grocery_Activity;
import com.mynexmy.nex.Activitys.Movie_Activity;
import com.mynexmy.nex.Activitys.OTT_Activity;
import com.mynexmy.nex.Fragments.Scratchcard_Fragment;
import com.mynexmy.nex.Models.Slide_Model_Clothing;
import com.mynexmy.nex.Models.Slide_Model_Movie;
import com.mynexmy.nex.Models.Slide_Model_OTT;
import com.mynexmy.nex.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapterOTT extends RecyclerView.Adapter<SliderAdapterOTT.ImageViewHolder> {
    private Context context;
    private List<Slide_Model_OTT> slide_model_otts;





    public SliderAdapterOTT(Context context, List<Slide_Model_OTT> slide_model_otts) {
        this.context = context;
        this.slide_model_otts = slide_model_otts;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the item layout for the RecyclerView
        View view = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(view); // Return ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Slide_Model_OTT slideModel = slide_model_otts.get(position);

        // Log the image URL for debugging
        Log.d("Slide_Model_Scratch", "Image URL: " + slideModel.getImage() +
                ", Position: " + slideModel.getPosition());

        // Use Picasso to load the image into the ImageView
        Picasso.get()
                .load(slideModel.getImage())  // The URL for the image
                .placeholder(R.drawable.plash)  // Placeholder image
                .into(holder.imageView);  // The target ImageView

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    @Override
    public int getItemCount() {
        // Return the size of the list
        Log.d("ImageSliderAdapterScratch", "Item count: " + slide_model_otts.size());
        return slide_model_otts.size();
    }

    // ViewHolder class to hold the ImageView for each item
    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView); // Make sure the ID matches the one in your layout
        }
    }



}
