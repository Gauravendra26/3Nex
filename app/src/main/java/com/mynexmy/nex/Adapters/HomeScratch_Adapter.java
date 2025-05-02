
package com.mynexmy.nex.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mynexmy.nex.Fragments.Scratchcard_Fragment;
import com.mynexmy.nex.Models.Newest_Model;
import com.mynexmy.nex.Models.Slide_Model_HomeScratch;
import com.mynexmy.nex.R;

import java.util.ArrayList;
import java.util.List;

public class HomeScratch_Adapter extends RecyclerView.Adapter<HomeScratch_Adapter.MyViewHolder>{


    private Context context;
    private List<Slide_Model_HomeScratch> liveList;


    private HomeScratch_Adapter.ProductPageClick productPageClick;
    private final int SCROLL_DELAY = 1000;

    //make interface like this
    public interface ProductPageClick {

        void setChip();

//        void productClick1(int position);
//
//        void addToCart1(int position, int product_id, int category_id, double product_rating,
//                        String product_image, String product_title, int product_sell_price,
//                        String product_description, int product_rating_total, int product_reg_price,
//                        int product_tax_percent);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {


        private ImageView imgScratchhome;

        CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            imgScratchhome = view.findViewById(R.id.imgScratchhome);
            cardView = view.findViewById(R.id.cardView);


        }
    }

    public HomeScratch_Adapter(Context context, List<Slide_Model_HomeScratch> liveList) {
        //List<SiderImageModel> slider_image_list
        this.context = context;
        this.liveList = liveList;



        ////this.slider_image_list = slider_image_list;

    }


    @Override
    public int getItemCount() {
        return liveList.size();
    }

    @NonNull
    @Override
    public HomeScratch_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scratch_home, parent, false);
        return new HomeScratch_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeScratch_Adapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Slide_Model_HomeScratch live = liveList.get(position);




        Glide.with(context)
                .load(live.getImage())
                .centerCrop() // Use centerCrop for full image without scaling and maintaining aspect ratio
                .placeholder(R.drawable.plash)
                .dontAnimate()
                .into(holder.imgScratchhome);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Scratchcard_Fragment fragment2 = new Scratchcard_Fragment();
                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                fragmentTransaction.replace(R.id.frame_container, fragment2).commit();
                productPageClick.setChip();
                final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
                holder.cardView.startAnimation(myAnim);
            }

        });

//        holder.add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                productPageClick.addToCart1(position, live.getProduct_id(), live.getCategory_id(),
////                        live.getProduct_rating(), live.getProduct_image(), live.getProduct_title(),
////                        live.getProduct_sell_price(), live.getProduct_description(),
////                        live.getProduct_rating_total(), live.getProduct_reg_price(),
////                        live.getProduct_tax_percent());
//
//                final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
//                holder.add.startAnimation(myAnim);
//            }
//        });
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void set(HomeScratch_Adapter.ProductPageClick onClick) {
        this.productPageClick = onClick;
    }



}


