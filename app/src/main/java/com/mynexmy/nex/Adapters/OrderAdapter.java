package com.mynexmy.nex.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mynexmy.nex.Models.Order_model;
import com.mynexmy.nex.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private Context context;
    private List<Order_model> liveList;
    private OrderAdapter.ProductPageClick productPageClick;

    public interface ProductPageClick {
        void press(int position,int  order_id,double total_amount ,int status,String order_number,String created_at);
        void Track(int position,int  order_id,double total_amount ,int status,String order_number,String created_at);

    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvorderNumber,tvorderNumberCancel,tvAmountCancel,tvmethodCancel,marquee_textCancel,
                tvcreateCancel,tvtrackCancel,tvViewDetailsCancel,tvOrderIdCancel,tvAmount, tvmethod,
                marquee_text,tvStatusCancel,tvTransactionId,tvTransactionCancel,
                tvPname,tvcreate,tvtrack,tvViewDetails,tvorderId,tvStatus;
        LinearLayout lls1,llCancel1;
        public MyViewHolder(View view) {
            super(view);
            tvorderNumber = view.findViewById(R.id.tvorderNumber);
            tvorderNumberCancel = view.findViewById(R.id.tvorderNumberCancel);
            tvAmount = view.findViewById(R.id.tvAmount);
            tvAmountCancel = view.findViewById(R.id.tvAmountCancel);
            tvmethod = view.findViewById(R.id.tvmethod);
            tvmethodCancel = view.findViewById(R.id.tvmethodCancel);
            marquee_text = view.findViewById(R.id.marquee_text);
            marquee_textCancel = view.findViewById(R.id.marquee_textCancel);

            tvcreate = view.findViewById(R.id.tvcreate);
            tvcreateCancel = view.findViewById(R.id.tvcreateCancel);
            tvtrack = view.findViewById(R.id.tvtrack);
            tvtrackCancel = view.findViewById(R.id.tvtrackCancel);
            tvStatus = view.findViewById(R.id.tvStatus);
            tvStatusCancel = view.findViewById(R.id.tvStatusCancel);
            tvViewDetails = view.findViewById(R.id.tvViewDetails);
            tvViewDetailsCancel = view.findViewById(R.id.tvViewDetailsCancel);
            tvorderId = view.findViewById(R.id.tvorderId);
             tvOrderIdCancel = view.findViewById(R.id.tvOrderIdCancel);
            tvTransactionId = view.findViewById(R.id.tvTransactionId);
            tvTransactionCancel = view.findViewById(R.id.tvTransactionCancel);
            lls1 = view.findViewById(R.id.lls1);
            llCancel1 = view.findViewById(R.id.llCancel1);
        }
    }

    public OrderAdapter(Context context, List<Order_model> liveList) {
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
                .inflate(R.layout.fetch_order_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Order_model live = liveList.get(position);

if (live.getStatus()==1){
    holder.marquee_text.setSelected(true);
    holder.tvorderNumber.setText(live.getOrder_number());
    holder.tvAmount.setText("\u20B9" + live.getTotal_amount());
    holder.tvmethod.setText(live.getPayment_method());
    holder.marquee_text.setText(live.getShipping_address());
    holder.tvcreate.setText(live.getCreated_at());
    holder.tvorderId.setText(live.getOrder_tracking_id());

        holder.tvTransactionId.setText(live.getTransaction_details());

    holder.tvStatus.setText("Ordered");
    holder.llCancel1.setVisibility(View.GONE);
    holder.lls1.setVisibility(View.VISIBLE);
} else if (live.getStatus()==2){
    holder.marquee_text.setSelected(true);
    holder.tvorderNumber.setText(live.getOrder_number());
    holder.tvAmount.setText("\u20B9" + live.getTotal_amount());
    holder.tvmethod.setText(live.getPayment_method());
    holder.marquee_text.setText(live.getShipping_address());
    holder.tvcreate.setText(live.getCreated_at());
    holder.tvorderId.setText(live.getOrder_tracking_id());
    holder.tvTransactionId.setText(live.getTransaction_details());
    holder.tvStatus.setText("Processing");
    holder.llCancel1.setVisibility(View.GONE);
    holder.lls1.setVisibility(View.VISIBLE);
}else if (live.getStatus()==3){
    holder.marquee_text.setSelected(true);
    holder.tvorderNumber.setText(live.getOrder_number());
    holder.tvAmount.setText("\u20B9" + live.getTotal_amount());
    holder.tvmethod.setText(live.getPayment_method());
    holder.marquee_text.setText(live.getShipping_address());
    holder.tvcreate.setText(live.getCreated_at());
    holder.tvorderId.setText(live.getOrder_tracking_id());
    holder.tvTransactionId.setText(live.getTransaction_details());
    holder.tvStatus.setText("Shipped");
    holder.llCancel1.setVisibility(View.GONE);
    holder.lls1.setVisibility(View.VISIBLE);
}else if (live.getStatus()==4){
    holder.marquee_text.setSelected(true);
    holder.tvorderNumber.setText(live.getOrder_number());
    holder.tvAmount.setText("\u20B9" + live.getTotal_amount());
    holder.tvmethod.setText(live.getPayment_method());
    holder.marquee_text.setText(live.getShipping_address());
    holder.tvcreate.setText(live.getCreated_at());
    holder.tvorderId.setText(live.getOrder_tracking_id());
    holder.tvTransactionId.setText(live.getTransaction_details());
    holder.tvStatus.setText("Out for Delivery");
    holder.llCancel1.setVisibility(View.GONE);
    holder.lls1.setVisibility(View.VISIBLE);
}else if (live.getStatus()==5){
    holder.marquee_text.setSelected(true);
    holder.tvorderNumber.setText(live.getOrder_number());
    holder.tvAmount.setText("\u20B9" + live.getTotal_amount());
    holder.tvmethod.setText(live.getPayment_method());
    holder.marquee_text.setText(live.getShipping_address());
    holder.tvcreate.setText(live.getCreated_at());
    holder.tvorderId.setText(live.getOrder_tracking_id());
    holder.tvTransactionId.setText(live.getTransaction_details());
    holder.tvStatus.setText("Delivered");
    holder.llCancel1.setVisibility(View.GONE);
    holder.lls1.setVisibility(View.VISIBLE);
}else if (live.getStatus()==6){
    holder.marquee_textCancel.setSelected(true);
    holder.tvorderNumberCancel.setText(live.getOrder_number());
    holder.tvAmountCancel.setText("\u20B9" + live.getTotal_amount());
    holder.tvmethodCancel.setText(live.getPayment_method());
    holder.marquee_textCancel.setText(live.getShipping_address());
    holder.tvcreateCancel.setText(live.getCreated_at());
    holder.tvOrderIdCancel.setText(live.getOrder_tracking_id());
    holder.tvTransactionCancel.setText(live.getTransaction_details());
    holder.tvStatusCancel.setText("Order Cancel");
    holder.lls1.setVisibility(View.GONE);
    holder.llCancel1.setVisibility(View.VISIBLE);
}else if (live.getStatus()==7){
    holder.marquee_textCancel.setSelected(true);
    holder.tvorderNumberCancel.setText(live.getOrder_number());
    holder.tvAmountCancel.setText("\u20B9" + live.getTotal_amount());
    holder.tvmethodCancel.setText(live.getPayment_method());
    holder.marquee_textCancel.setText(live.getShipping_address());
    holder.tvcreateCancel.setText(live.getCreated_at());
    holder.tvOrderIdCancel.setText(live.getOrder_tracking_id());
    holder.tvTransactionCancel.setText(live.getTransaction_details());
    holder.tvStatusCancel.setText("Returned");
    holder.lls1.setVisibility(View.GONE);
    holder.llCancel1.setVisibility(View.VISIBLE);
}else if (live.getStatus()==8){
    holder.marquee_textCancel.setSelected(true);
    holder.tvorderNumberCancel.setText(live.getOrder_number());
    holder.tvAmountCancel.setText("\u20B9" + live.getTotal_amount());
    holder.tvmethodCancel.setText(live.getPayment_method());
    holder.marquee_textCancel.setText(live.getShipping_address());
    holder.tvcreateCancel.setText(live.getCreated_at());
    holder.tvOrderIdCancel.setText(live.getOrder_tracking_id());
    holder.tvTransactionCancel.setText(live.getTransaction_details());
    holder.tvStatusCancel.setText("Refunded");
    holder.lls1.setVisibility(View.GONE);
    holder.llCancel1.setVisibility(View.VISIBLE);
}else if (live.getStatus()==9){
    holder.marquee_textCancel.setSelected(true);
    holder.tvorderNumberCancel.setText(live.getOrder_number());
    holder.tvAmountCancel.setText("\u20B9" + live.getTotal_amount());
    holder.tvmethodCancel.setText(live.getPayment_method());
    holder.marquee_textCancel.setText(live.getShipping_address());
    holder.tvcreateCancel.setText(live.getCreated_at());
    holder.tvOrderIdCancel.setText(live.getOrder_tracking_id());
    holder.tvTransactionCancel.setText(live.getTransaction_details());
    holder.tvStatusCancel.setText("Refunded");
    holder.lls1.setVisibility(View.GONE);
    holder.llCancel1.setVisibility(View.VISIBLE);
}




        holder.tvViewDetails.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                productPageClick.press(position, live.getOrder_id(),live.getTotal_amount(),live.getStatus(),live.getOrder_number(),
                        live.getCreated_at());
                final Animation myAnim = AnimationUtils.loadAnimation(context,R.anim.bounce);
                holder.tvViewDetails.startAnimation(myAnim);
            }
        });
holder.tvViewDetailsCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                productPageClick.press(position, live.getOrder_id(),live.getTotal_amount(),live.getStatus(),live.getOrder_number(),
                        live.getCreated_at());
                final Animation myAnim = AnimationUtils.loadAnimation(context,R.anim.bounce);
                holder.tvViewDetailsCancel.startAnimation(myAnim);
            }

        });

        holder.tvtrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String Url = "https://www.dtdc.in/tracking.asp";
//                Context context = v.getContext();
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Url));
//                context.startActivity(intent);
                productPageClick.Track(position, live.getOrder_id(),live.getTotal_amount(),live.getStatus(),live.getOrder_number(),
                        live.getCreated_at());

                final Animation myAnim = AnimationUtils.loadAnimation(context,R.anim.bounce);
                holder.tvtrack.startAnimation(myAnim);
            }
        });
 holder.tvtrackCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String Url = "https://www.dtdc.in/tracking.asp";
//                Context context = v.getContext();
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Url));
//                context.startActivity(intent);
                productPageClick.Track(position, live.getOrder_id(),live.getTotal_amount(),live.getStatus(),live.getOrder_number(),
                        live.getCreated_at());

                final Animation myAnim = AnimationUtils.loadAnimation(context,R.anim.bounce);
                holder.tvtrackCancel.startAnimation(myAnim);
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void set(OrderAdapter.ProductPageClick onClick)
    {
        this.productPageClick = onClick;
    }

}