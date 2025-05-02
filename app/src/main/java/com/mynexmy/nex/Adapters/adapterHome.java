package com.mynexmy.nex.Adapters;

import static com.google.android.material.transition.MaterialSharedAxis.X;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mynexmy.nex.Models.CacheManager;
import com.mynexmy.nex.Models.HomeCategory_Model;
import com.mynexmy.nex.Models.ModelHome;
import com.mynexmy.nex.Models.RecyclerViewCacheManager;
import com.mynexmy.nex.R;

import java.util.List;

public class adapterHome  extends RecyclerView.Adapter<adapterHome.MyViewHolder> {


    private Context context;
    private List<ModelHome> liveList;

    private adapterHome.ProductClick productClick;

    //make interface like this
    public interface  ProductClick{
        void dataUpdate(int position);


        void productClickHomeNew(int position, int product_id, int category_id, double product_rating,
                              String product_image, String product_title, int product_sell_price,
                              String product_description, int product_rating_total,int product_reg_price,
                                 int product_tax_percent);



        void addToCartHome(int position, int product_id, int category_id, double product_rating,
                           String product_image, String product_title, int product_sell_price,
                           String product_description, int product_rating_total, int product_reg_price, int product_tax_percent);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView rating, proname, saleprize, actualprice,tvdiscount;

        private ImageView p_image, add;

        LinearLayout ll1;

        public MyViewHolder(View view) {
            super(view);
            proname = view.findViewById(R.id.proname);
            rating = view.findViewById(R.id.rating);
            saleprize = view.findViewById(R.id.saleprize);
            actualprice = view.findViewById(R.id.actualprice);

            p_image = view.findViewById(R.id.p_image);
            add = view.findViewById(R.id.add);
            tvdiscount = view.findViewById(R.id.tvdiscount);

            ll1 = view.findViewById(R.id.pd1);


        }
    }

    public adapterHome(Context context, List<ModelHome> liveList) {
        //List<SiderImageModel> slider_image_list
        this.context = context;
        this.liveList = liveList;
        ////this.slider_image_list = slider_image_list;

    }
    public void setData(List<ModelHome> newData) {
        this.liveList = newData;
       // Save data to cache
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
                .inflate(R.layout.product_layout6_view1, parent, false);
        return new MyViewHolder(itemView);


    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {

        ModelHome live = liveList.get(position);

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




        holder.ll1.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                productClick.productClickHomeNew(position, live.getProduct_id(), live.getCategory_id(),
                        live.getProduct_rating(), live.getProduct_image(), live.getProduct_title(),
                        live.getProduct_sell_price(),live.getProduct_description(),
                        live.getProduct_rating_total(), live.getProduct_reg_price(), live.getProduct_tax_percent());

                final Animation myAnim = AnimationUtils.loadAnimation(context,R.anim.bounce);
                holder.ll1.startAnimation(myAnim);
             }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productClick.addToCartHome(position, live.getProduct_id(), live.getCategory_id(),
                        live.getProduct_rating(), live.getProduct_image(), live.getProduct_title(),
                        live.getProduct_sell_price(),live.getProduct_description(),
                        live.getProduct_rating_total(), live.getProduct_reg_price(), live.getProduct_tax_percent());
                productClick.dataUpdate(position);
                final Animation myAnim = AnimationUtils.loadAnimation(context,R.anim.bounce);
                holder.add.startAnimation(myAnim);

            }
        });
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    public void set(ProductClick onClick)
    {
        this.productClick = onClick;
    }

}
