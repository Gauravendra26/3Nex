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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.anupkumarpanwar.scratchview.ScratchView;
import com.mynexmy.nex.Models.Scratchcard_Model;
import com.mynexmy.nex.R;

import java.util.List;

public class Scratchcard_Adapter  extends RecyclerView.Adapter<Scratchcard_Adapter.MyViewHolder>{


    private Context context;
    private List<Scratchcard_Model> liveList;

    ScratchView scratchView;
    private Scratchcard_Adapter.ProductPageClick productPageClick;

    //make interface like this
    public interface  ProductPageClick{

        void productClick1(int position,int scratch_card_payment_id, int scratch_card_id, int customer_id,
                           int amount,
                           int bucket_id, int level, int is_scratched, int bucket_product_id,
                           int bucket_product_status, int added_by, int ref_count_one, int ref_count_two,
                           int ref_count_three, int ref_count_four,String shipping_address,
                           String shipment_tracker, String created_at, String updated_at,
                           String bucket_product_title, String bucket_product_description,
                           String bucket_product_image);

    }
    //make interface like this



    public static class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView tv1, p_name, p_saleprice;

        private ImageView img1,img2;
CardView card_scratch;
        com.anupkumarpanwar.scratchview.ScratchView scratchview;
        RelativeLayout rlcard;


        public MyViewHolder(View view) {
            super(view);
            p_name = view.findViewById(R.id.p_name);
            tv1 = view.findViewById(R.id.tv1);
            p_saleprice = view.findViewById(R.id.p_saleprice);
            img1 = view.findViewById(R.id.img1);
            img2 = view.findViewById(R.id.img2);
            scratchview = view.findViewById(R.id.scratchview);

            rlcard = view.findViewById(R.id.rlcard);
            card_scratch = view.findViewById(R.id.card_scratch);

        }
    }

    public Scratchcard_Adapter(Context context, List<Scratchcard_Model> liveList) {
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
    public Scratchcard_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scratch_layout1, parent, false);
        return new Scratchcard_Adapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull Scratchcard_Adapter.MyViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        Scratchcard_Model live = liveList.get(position);





//        holder.tv1.setText(live.getBucket_product_title());



        // Method to handle the scratch action



//        holder.p_saleprice.setText("\u20B9" + live.getBucket_product_price());

        
//        Glide.with(context).load(live.getBucket_product_image()) .placeholder(R.drawable.plash).dontAnimate()
//                .into(holder.img1);

//if (live.getRef_count_one()==0 &&live.getRef_count_two()==0
//        &&live.getRef_count_three()==0 &&live.getRef_count_four()==0)
//{
//    holder.img1.setVisibility(View.GONE);
//    holder.img2.setVisibility(View.VISIBLE);
//}else {
//    holder.img1.setVisibility(View.VISIBLE);
//    holder.img2.setVisibility(View.GONE);
//}

        holder.rlcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productPageClick.productClick1(position, live.getScratch_card_payment_id(),live.getScratch_card_id(),
                        live.getCustomer_id(),live.getAmount(),live.getBucket_id(), live.getLevel(),
                        live.getIs_scratched(),live.getBucket_product_id(), live.getBucket_product_status(),
                        live.getAdded_by(),live.getRef_count_one(),live.getRef_count_two(),live.getRef_count_three(),
                        live.getRef_count_four(),live.getShipping_address(),live.getShipment_tracker(),live.getCreated_at(),
                        live.getUpdated_at(),live.getBucket_product_title(),live.getBucket_product_description(),
                        live.getBucket_product_image());
                final Animation myAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.bounce);
                holder.rlcard.startAnimation(myAnim);
            }
        });


    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void set(Scratchcard_Adapter.ProductPageClick onClick)
    {
        this.productPageClick = onClick;
    }


}
