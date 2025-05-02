package com.mynexmy.nex.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.mynexmy.nex.Models.Search_Model;
import com.mynexmy.nex.R;

import java.util.ArrayList;
import java.util.List;

public class Search_Adapter extends RecyclerView.Adapter<Search_Adapter.MyViewHolder> {


    private Context context;
    private List<Search_Model> liveList;
    private List<Search_Model> exampleListFull;
    private Search_Adapter.ProductPageClick productPageClick;


    public interface  ProductPageClick{
        void dataUpdate(int position);

        void productClick(int position,int product_id, double product_rating, String product_image, String product_title,
                          int product_sell_price,int product_reg_price,int product_tax_percent

        );
        void addtocart(int position,int product_id, double product_rating, String product_image, String product_title,
                          int product_sell_price,int product_reg_price,int product_tax_percent
        );

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView rating, proname, saleprize, actualprice,tvdiscount;

        private ImageView p_image,  add;

        LinearLayout pd_1;
        public MyViewHolder(View view) {
            super(view);
            proname = view.findViewById(R.id.proname);
            rating = view.findViewById(R.id.rating);
            saleprize = view.findViewById(R.id.saleprize);
            actualprice = view.findViewById(R.id.actualprice);
            tvdiscount = view.findViewById(R.id.tvdiscount);

            p_image = view.findViewById(R.id.p_image);
            add = view.findViewById(R.id.add);
            pd_1 = view.findViewById(R.id.pd1);

        }
    }
    public Search_Adapter(Context context, List<Search_Model> liveList) {
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
    public Search_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_layout6_view1, parent, false);
        return new Search_Adapter.MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull Search_Adapter.MyViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {

        Search_Model live = liveList.get(position);


        holder.proname.setText(live.getProduct_title());
        holder.saleprize.setText("\u20B9" + live.getProduct_sell_price());
        holder.rating.setText(""+live.getProduct_rating());
        String text = "<strike><font color=\'#757575\'>\u20B9" +live.getProduct_reg_price() + "</font></strike>";
        holder.actualprice.setText(Html.fromHtml(text));
        Glide.with(context).load(live.getProduct_image()) .placeholder(R.drawable.plash).dontAnimate()
                .into(holder.p_image);

        int Discount_Percentage1 = 0;
        double Discount1 = 0 ;

        Discount1 =  (live.getProduct_reg_price() - live.getProduct_sell_price() );
        Discount_Percentage1 = (int)((Discount1*100 )/live.getProduct_reg_price());
        holder.tvdiscount.setText("" + Discount_Percentage1+"% off");


        holder.pd_1.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                productPageClick.productClick(position, live.getProduct_id(),
                        live.getProduct_rating(), live.getProduct_image(), live.getProduct_title(),
                        live.getProduct_sell_price(),live.getProduct_reg_price(),live.getProduct_tax_percent());
            }

        });


        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productPageClick.addtocart(position, live.getProduct_id(),
                        live.getProduct_rating(), live.getProduct_image(), live.getProduct_title(),
                        live.getProduct_sell_price(),live.getProduct_reg_price(),live.getProduct_tax_percent());
                productPageClick.dataUpdate(position);

            }
        });

    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    public void set(Search_Adapter.ProductPageClick onClick)
    {
        this.productPageClick = onClick;
    }


}

