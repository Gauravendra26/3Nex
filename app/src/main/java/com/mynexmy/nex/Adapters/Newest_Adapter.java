
package com.mynexmy.nex.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mynexmy.nex.Models.Newest_Model;
import com.mynexmy.nex.R;

import java.util.ArrayList;
import java.util.List;

public class Newest_Adapter extends RecyclerView.Adapter<Newest_Adapter.MyViewHolder>implements Filterable {


    private Context context;
    private List<Newest_Model> liveList;
    private List<Newest_Model> filteredItems;

    private Newest_Adapter.ProductPageClick productPageClick;

    //make interface like this
    public interface ProductPageClick {

        void dataUpdate(int position);

        void productClick1(int position, int product_id, int category_id, double product_rating,
                           String product_image, String product_title,
                           int product_sell_price, String product_description,
                           int product_rating_total, int product_reg_price, int product_tax_percent
        );

        void addToCart1(int position, int product_id, int category_id, double product_rating,
                        String product_image, String product_title, int product_sell_price,
                        String product_description, int product_rating_total, int product_reg_price,
                        int product_tax_percent);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView rating, proname, saleprize, actualprice,tvdiscount;

        private ImageView p_image, add;

        LinearLayout pd_1;

        public MyViewHolder(View view) {
            super(view);
            proname = view.findViewById(R.id.proname);
            rating = view.findViewById(R.id.rating);
            saleprize = view.findViewById(R.id.saleprize);
            actualprice = view.findViewById(R.id.actualprice);

            p_image = view.findViewById(R.id.p_image);
            add = view.findViewById(R.id.add);
            tvdiscount = view.findViewById(R.id.tvdiscount);

            pd_1 = view.findViewById(R.id.pd1);

        }
    }

    public Newest_Adapter(Context context, List<Newest_Model> liveList) {
        //List<SiderImageModel> slider_image_list
        this.context = context;
        this.liveList = liveList;

        this.filteredItems = new ArrayList<>(liveList);

        ////this.slider_image_list = slider_image_list;

    }


    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    @NonNull
    @Override
    public Newest_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_layout6_view1, parent, false);
        return new Newest_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Newest_Adapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Newest_Model live = liveList.get(position);
        holder.proname.setText(live.getProduct_title());

        holder.saleprize.setText("\u20B9" + live.getProduct_sell_price());
        holder.rating.setText("" + live.getProduct_rating());
        String text = "<strike><font color=\'#757575\'>\u20B9" + live.getProduct_reg_price() + "</font></strike>";
        holder.actualprice.setText(Html.fromHtml(text));
        Glide.with(context).load(live.getProduct_image()).placeholder(R.drawable.plash).dontAnimate()
                .into(holder.p_image);

        int Discount_Percentage1 = 0;
        double Discount1 = 0 ;
        Discount1 =  (live.getProduct_reg_price() - live.getProduct_sell_price() );
        Discount_Percentage1 = (int)((Discount1*100 )/live.getProduct_reg_price());
        holder.tvdiscount.setText("" + Discount_Percentage1+"% off");

        holder.pd_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productPageClick.productClick1(position, live.getProduct_id(), live.getCategory_id(),
                        live.getProduct_rating(), live.getProduct_image(), live.getProduct_title(),
                        live.getProduct_sell_price(), live.getProduct_description(),
                        live.getProduct_rating_total(), live.getProduct_reg_price(),
                        live.getProduct_tax_percent());
                final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
                holder.pd_1.startAnimation(myAnim);
            }

        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productPageClick.addToCart1(position, live.getProduct_id(), live.getCategory_id(),
                        live.getProduct_rating(), live.getProduct_image(), live.getProduct_title(),
                        live.getProduct_sell_price(), live.getProduct_description(),
                        live.getProduct_rating_total(), live.getProduct_reg_price(),
                        live.getProduct_tax_percent());
                productPageClick.dataUpdate(position);
                final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
                holder.add.startAnimation(myAnim);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void set(Newest_Adapter.ProductPageClick onClick) {
        this.productPageClick = onClick;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString().toLowerCase().trim();
                List<Newest_Model> filteredList = new ArrayList<>();

                if (query.isEmpty()) {
                    filteredList.addAll(liveList);
                } else {
                    for (Newest_Model repo : liveList) {
                        if (repo.getProduct_title().toLowerCase().contains(query)) {
                            filteredList.add(repo);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredItems.clear();
                filteredItems.addAll((List<Newest_Model>) results.values);
                notifyDataSetChanged();
            }
        };
    }

//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                String charcater = constraint.toString();
//                if (charcater.isEmpty()) {
//                    filteredItems = liveList;
//                } else {
//                    List<Newest_Model> filterList = new ArrayList<>();
//                    for (Newest_Model row : liveList) {
//                        if (row.getProduct_title().toLowerCase().contains(charcater.toLowerCase())) {
//                            filterList.add(row);
//                        }
//                    }
//
//                    filteredItems = liveList;
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = filteredItems;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                filteredItems = (ArrayList<Newest_Model>) results.values;
//                notifyDataSetChanged();
//            }
//        };
//    }
}


