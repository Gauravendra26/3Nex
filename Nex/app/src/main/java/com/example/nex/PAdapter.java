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

public class PAdapter extends RecyclerView.Adapter<PAdapter.MyViewHolder> {

    private Context context;
    private List<ProductModel> liveList;


    private PAdapter.whenClick onClick;

    //make interface like this
    public interface whenClick {
        void press(int position,String id,String image,String name,String accu);

    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView p_name,p_description,p_saleprice,p_actualprice,p_quantity;

        private ImageView p_image,minus,add,cross;

        LinearLayout ll1;


        public MyViewHolder(View view) {
            super(view);
            p_name = view.findViewById(R.id.p_name);
            p_description = view.findViewById(R.id.p_description);
            p_saleprice = view.findViewById(R.id.p_saleprice);
            p_actualprice = view.findViewById(R.id.p_actualprice);
            p_quantity = view.findViewById(R.id.p_quantity);
            p_image = view.findViewById(R.id.p_image);
            minus = view.findViewById(R.id.minus);
            add = view.findViewById(R.id.add);
            cross = view.findViewById(R.id.cross);
            ll1 = view.findViewById(R.id.ll1);



        }
    }

    public PAdapter(Context context, List<ProductModel> liveList) {
        //List<SiderImageModel> slider_image_list
        this.context = context;
        this.liveList = liveList;
        ////this.slider_image_list = slider_image_list;

    }




    public void setOnClick(PAdapter.whenClick onClick) {
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
                .inflate(R.layout.product_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ProductModel live = liveList.get(position);
        holder.p_name.setText(live.getP_name());
        holder.p_saleprice.setText("\u20B9"+live.getP_saleprice());
        String text = "<strike><font color=\'#757575\'>\u20B9"+live.getP_actualprice()+"</font></strike>";
        holder.p_actualprice.setText(Html.fromHtml(text));
        Glide.with(context).load(live.getP_image()).into(holder.p_image);
        holder.ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // onClick.press(position,live.getId(),live.getImageUrl(),live.getName(),live.getAccupation());
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


}
