package com.mynexmy.nex.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mynexmy.nex.Models.CacheManager;
import com.mynexmy.nex.Models.HomeCategory_Model;
import com.mynexmy.nex.Models.RecyclerViewCacheManager;
import com.mynexmy.nex.R;

import java.util.List;

public class HomeCategory_Adapter    extends RecyclerView.Adapter<HomeCategory_Adapter   .MyViewHolder> {

    private Context context;
    private List<HomeCategory_Model> liveList;

    private HomeCategory_Adapter.ProductPageClick productPageClick;

    //make interface like this
    public interface  ProductPageClick{
        void categoryproductClick(int position, int category_id,String category_title
        );
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPname;

        private ImageView imgP;

        LinearLayout ll;


        public MyViewHolder(View view) {
            super(view);
            tvPname = view.findViewById(R.id.tvPname);
            imgP = view.findViewById(R.id.imgP);
            ll = view.findViewById(R.id.ll);


        }
    }

    public HomeCategory_Adapter (Context context, List<HomeCategory_Model> liveList) {
        //List<SiderImageModel> slider_image_list
        this.context = context;
        this.liveList = liveList;
        ////this.slider_image_list = slider_image_list;

    }
    public void setData(List<HomeCategory_Model> newData) {
        this.liveList = newData;
        RecyclerViewCacheManager.saveRecyclerViewData(context, newData); // Save data to cache
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return liveList.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_layout7_home_category, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        HomeCategory_Model live = liveList.get(position);
        holder.tvPname.setText(live.getCategory_title());

        Glide.with(context).load(live.getCategory_image())
                .placeholder(R.drawable.plash).dontAnimate().into(holder.imgP);


        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productPageClick.categoryproductClick(position,live.getCategory_id(), live.getCategory_title());
                final Animation myAnim = AnimationUtils.loadAnimation(context,R.anim.bounce);
                holder.ll.startAnimation(myAnim);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void set(HomeCategory_Adapter.ProductPageClick onClick)
    {
        this.productPageClick = onClick;
    }

}