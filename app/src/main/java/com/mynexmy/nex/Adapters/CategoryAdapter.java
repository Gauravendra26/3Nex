package com.mynexmy.nex.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.mynexmy.nex.Models.CategoryModel;
import com.mynexmy.nex.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private Context context;
    private List<CategoryModel> liveList;

    private ProductPageClick productPageClick;


    //make interface like this
    public interface  ProductPageClick{
        void productClick(int position, int category_id,String category_title
                           );
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPname;

        private ImageView imgP;

        RelativeLayout rl;


        public MyViewHolder(View view) {
            super(view);
            tvPname = view.findViewById(R.id.tvPname);
            imgP = view.findViewById(R.id.imgP);
            rl = view.findViewById(R.id.ll);


        }
    }

    public CategoryAdapter(Context context, List<CategoryModel> liveList) {
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CategoryModel live = liveList.get(position);
        holder.tvPname.setText(live.getCategory_title());

        Glide.with(context).load(live.getCategory_image())
                .placeholder(R.drawable.plash).dontAnimate().into(holder.imgP);




        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productPageClick.productClick(position,live.getCategory_id(), live.getCategory_title());
                final Animation myAnim = AnimationUtils.loadAnimation(context,R.anim.bounce);
                holder.rl.startAnimation(myAnim);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void set(CategoryAdapter.ProductPageClick onClick)
    {
        this.productPageClick = onClick;
    }
}