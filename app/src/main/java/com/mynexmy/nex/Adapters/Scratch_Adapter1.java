package com.mynexmy.nex.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mynexmy.nex.Models.Scratchcard_items_Model;
import com.mynexmy.nex.Models.Scratchcard_items_Model1;
import com.mynexmy.nex.R;

import java.util.List;

public class Scratch_Adapter1 extends RecyclerView.Adapter<Scratch_Adapter1.MyViewHolder> {

    private Context context;
    private List<Scratchcard_items_Model1> liveList;


    private Scratch_Adapter1.ProductPageClick productPageClick;

    //make interface like this
    public interface  ProductPageClick{
        void productClickCard(int position
        );

    }
    //make interface like this


    public static class MyViewHolder extends RecyclerView.ViewHolder {



        private ImageView imgProduct;




        public MyViewHolder(View view) {
            super(view);

            imgProduct = view.findViewById(R.id.imgProduct);



        }
    }

    public Scratch_Adapter1(Context context, List<Scratchcard_items_Model1> liveList) {
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
    public Scratch_Adapter1.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_layout_order, parent, false);
        return new Scratch_Adapter1.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull Scratch_Adapter1.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Scratchcard_items_Model1 live = liveList.get(position);

        Glide.with(context).load(live.getBucket_product_image()) .placeholder(R.drawable.plash).dontAnimate()
                .into(holder.imgProduct);
         holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productPageClick.productClickCard(position);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void set(Scratch_Adapter1.ProductPageClick onClick)
    {
        this.productPageClick = onClick;
    }

}