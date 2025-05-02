package com.mynexmy.nex.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mynexmy.nex.Models.Slide_Model_Grocery;
import com.mynexmy.nex.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageSliderAdapter1 extends RecyclerView.Adapter<ImageSliderAdapter1.ImageViewHolder> {
    private Context context;
    private List<Slide_Model_Grocery> slideModelGroceryList;

    // Constructor to initialize context and list
    public ImageSliderAdapter1(Context context, List<Slide_Model_Grocery> slideModelGroceryList) {
        this.context = context;
        this.slideModelGroceryList = slideModelGroceryList;
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
        Slide_Model_Grocery slideModel = slideModelGroceryList.get(position);

        // Use Picasso to load the image into the ImageView
        Picasso.get()
                .load(slideModel.getImage()) // The URL for the image
                .placeholder(R.drawable.plash) // Set a placeholder image
                .into(holder.imageView); // The target ImageView

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

    }

    @Override
    public int getItemCount() {
        return slideModelGroceryList.size(); // Return the size of the list
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
