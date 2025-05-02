package com.mynexmy.nex.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
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
import com.mynexmy.nex.Models.HomeProductModel1;
import com.mynexmy.nex.Models.ModelHome;
import com.mynexmy.nex.R;

import java.util.List;

public class HomeProductAdapter1 extends RecyclerView.Adapter<HomeProductAdapter1.MyViewHolder> {


    private Context context;
    private List<ModelHome> liveList;

    private HomeProductAdapter1.ProductPageClick productPageClick;

    //make interface like this
    public interface  ProductPageClick{
        void productClick1(int position, int product_id, int category_id, double product_rating,
                          String product_image, String product_title,
                          int product_sell_price,  String product_description,
                          int product_rating_total,int product_reg_price
        );
        void addToCart1(int position, int product_id, int category_id, double product_rating,
                       String product_image, String product_title, int product_sell_price,
                       String product_description, int product_rating_total,int product_reg_price);
    }


     public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView rating, proname, saleprize, actualprice;

        private ImageView p_image,  add;

        LinearLayout ll1;

        public MyViewHolder(View view) {
            super(view);
            proname = view.findViewById(R.id.proname);
            rating = view.findViewById(R.id.rating);
            saleprize = view.findViewById(R.id.saleprize);
            actualprice = view.findViewById(R.id.actualprice);

            p_image = view.findViewById(R.id.p_image);
            add = view.findViewById(R.id.add);

            ll1 = view.findViewById(R.id.pd1);


        }
    }

    public HomeProductAdapter1(Context context, List<ModelHome> liveList) {
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
    public HomeProductAdapter1.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_layout3, parent, false);
        return new HomeProductAdapter1.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeProductAdapter1.MyViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {


        ModelHome live = liveList.get(position);
        holder.proname.setText(live.getProduct_title());
        holder.saleprize.setText("\u20B9" + live.getProduct_sell_price());
        holder.rating.setText(""+live.getProduct_rating());
        String text = "<strike><font color=\'#757575\'>\u20B9" +live.getProduct_reg_price() + "</font></strike>";
        holder.actualprice.setText(Html.fromHtml(text));
        Glide.with(context).load(live.getProduct_image()) .placeholder(R.drawable.plash).dontAnimate()
                .into(holder.p_image);


        holder.ll1.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                productPageClick.productClick1(position, live.getProduct_id(), live.getCategory_id(),
                        live.getProduct_rating(), live.getProduct_image(), live.getProduct_title(),
                        live.getProduct_sell_price(),live.getProduct_description(),
                        live.getProduct_rating_total(), live.getProduct_reg_price());
            }

        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productPageClick.addToCart1(position, live.getProduct_id(), live.getCategory_id(),
                        live.getProduct_rating(), live.getProduct_image(), live.getProduct_title(),
                        live.getProduct_sell_price(),live.getProduct_description(),
                        live.getProduct_rating_total(), live.getProduct_reg_price());
            }
        });
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    public void set(HomeProductAdapter1.ProductPageClick onClick)
    {
        this.productPageClick = onClick;
    }


}
