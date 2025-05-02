package com.mynexmy.nex.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mynexmy.nex.Models.Orderdetail_Model;
import com.mynexmy.nex.R;

import java.util.List;

public class Orderdetail_Adapter extends RecyclerView.Adapter<Orderdetail_Adapter.MyViewHolder> {

    private Context context;
    private List<Orderdetail_Model> liveList;

    private Orderdetail_Adapter.ProductPageClick productPageClick;


    public interface  ProductPageClick{
        void productClick(int position, int order_id,int product_id
        );
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView p_id, p_name, saleprice, p_description,tvquan,tv_Desc;

        private ImageView p_image, rating;

        LinearLayout ll1;


        public MyViewHolder(View view) {
            super(view);
            p_name = view.findViewById(R.id.p_name);
            saleprice = view.findViewById(R.id.saleprice);
            p_description = view.findViewById(R.id.p_description);
            rating = view.findViewById(R.id.rating);
            tvquan = view.findViewById(R.id.tvquan);
            p_image = view.findViewById(R.id.p_image);
            tv_Desc = view.findViewById(R.id.tv_Desc);

            ll1 = view.findViewById(R.id.ll1);

        }
    }


    public Orderdetail_Adapter(Context context, List<Orderdetail_Model> liveList) {
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
    public Orderdetail_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_order_productlayout, parent, false);
        return new Orderdetail_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Orderdetail_Adapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Orderdetail_Model live = liveList.get(position);
        holder.saleprice.setText("\u20B9"+live.getProduct_sell_price());
        holder.tvquan.setText(""+live.getQty());

        holder.p_name.setText(live.getProduct_name());
        holder.tv_Desc.setText(live.getProduct_code());
        Glide.with(context).load(live.getProduct_image()) .placeholder(R.drawable.plash).dontAnimate()
                .into(holder.p_image);
//        Toast.makeText(context, ""+(live.getProduct_reg_price()), Toast.LENGTH_SHORT).show();




        holder.ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productPageClick.productClick(position,live.getOrder_id(),live.getProduct_id());

            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void set(Orderdetail_Adapter.ProductPageClick onClick)
    {
        this.productPageClick = onClick;
    }




}
