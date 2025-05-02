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
import com.mynexmy.nex.Models.Slide_Model_Movie;
import com.mynexmy.nex.Models.Slide_Model_MovieInner;
import com.mynexmy.nex.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapterMovieInner extends RecyclerView.Adapter<SliderAdapterMovieInner.ImageViewHolder> {
    private Context context;
    private List<Slide_Model_MovieInner> slide_model_movies;




    public SliderAdapterMovieInner(Context context, List<Slide_Model_MovieInner> slide_model_movies) {
        this.context = context;
        this.slide_model_movies = slide_model_movies;
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
        Slide_Model_MovieInner slideModel = slide_model_movies.get(position);

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
//                if (slideModel.getPosition().equals("15")||slideModel.getPosition().equals("16")) {
//                    Intent intent = new Intent(context, Clothing_Activity.class);
//                    context.startActivity(intent);
//                    final Animation myAnim = AnimationUtils.loadAnimation(context,R.anim.bounce);
//                    holder.imageView.startAnimation(myAnim);
//
//                }   else if (slideModel.getPosition().equals("17")||slideModel.getPosition().equals("18")) {
//                    Intent intent = new Intent(context, OTT_Activity.class);
//                    context.startActivity(intent);
//                    final Animation myAnim = AnimationUtils.loadAnimation(context,R.anim.bounce);
//                    holder.imageView.startAnimation(myAnim);
//                } else if (slideModel.getPosition().equals("19")||slideModel.getPosition().equals("20")) {
//                    Intent intent = new Intent(context, Movie_Activity.class);
//                    context.startActivity(intent);
//                    final Animation myAnim = AnimationUtils.loadAnimation(context,R.anim.bounce);
//                    holder.imageView.startAnimation(myAnim);
//                }   else if (slideModel.getPosition().equals("21")||slideModel.getPosition().equals("22")) {
//                    Intent intent = new Intent(context, Grocery_Activity.class);
//                    context.startActivity(intent);
//                    final Animation myAnim = AnimationUtils.loadAnimation(context,R.anim.bounce);
//                    holder.imageView.startAnimation(myAnim);
//                }

            }
        });
    }

    @Override
    public int getItemCount() {
        // Return the size of the list
        Log.d("ImageSliderAdapterScratch", "Item count: " + slide_model_movies.size());
        return slide_model_movies.size();
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
