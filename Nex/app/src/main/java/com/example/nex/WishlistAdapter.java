package com.example.nex;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.MyViewHolder> {

    private Context context;
    private List<ProductModelWishlist> liveList;


    private WishlistAdapter.whenClick onClick;

    //make interface like this
    public interface whenClick {
        void press(int position, String id, String image, String name, String saleprice, String accu);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView rating, proname, saleprize, actualprice;

        private ImageView p_image, img_cross;

        LinearLayout ll1;


        public MyViewHolder(View view) {
            super(view);
            proname = view.findViewById(R.id.proname);
            rating = view.findViewById(R.id.rating);
            saleprize = view.findViewById(R.id.saleprize);
            actualprice = view.findViewById(R.id.actualprice);
            img_cross = view.findViewById(R.id.img_cross);
            p_image = view.findViewById(R.id.p_image);

            ll1 = view.findViewById(R.id.ll1);

        }
    }

    public WishlistAdapter(Context context, List<ProductModelWishlist> liveList) {
        //List<SiderImageModel> slider_image_list
        this.context = context;
        this.liveList = liveList;
        ////this.slider_image_list = slider_image_list;

    }

    public void setOnClick(WishlistAdapter.whenClick onClick) {
        this.onClick = onClick;
    }

    @Override
    public int getItemCount() {
        return liveList.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_layout2, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ProductModelWishlist live = liveList.get(position);
        holder.proname.setText(live.getProname());
        holder.saleprize.setText("\u20B9" + live.getSaleprize());
        holder.rating.setText(live.getRating());
        String text = "<strike><font color=\'#757575\'>\u20B9" + live.getActualprice() + "</font></strike>";
        holder.actualprice.setText(Html.fromHtml(text));
        Glide.with(context).load(live.getP_image()).into(holder.p_image);
        holder.ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.press(position, live.getId(), live.getP_image(), live.getProname(), live.saleprize, live.getActualprice());
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


}