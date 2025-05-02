
package com.mynexmy.nex.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.util.Log;
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
import com.mynexmy.nex.Models.Address_model;
import com.mynexmy.nex.Models.Featured_Model;
import com.mynexmy.nex.R;

import java.util.List;

public class Address_Adapter extends RecyclerView.Adapter<Address_Adapter.MyViewHolder> {


    private Context context;
    private List<Address_model> liveList;

    private Address_Adapter.ProductPageClick productPageClick;

    //make interface like this
    public interface  ProductPageClick{



        void productClickAddress(int position,int customer_id, int address_id
        );


    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private  TextView mobleno,tvhouseno,tvufirst,tvlastname,tvpincode,tvcity,tvstate,
                mobleno1,tvhouseno1,tvufirst1,tvlastname1,tvpincode1,tvcit1,tvstate1;



        LinearLayout llAddress,llAddress1;
        public MyViewHolder(View view) {
            super(view);
            mobleno = view.findViewById(R.id.mobleno);
            tvhouseno = view.findViewById(R.id.tvhouseno);
            tvufirst = view.findViewById(R.id.tvufirst);
            tvlastname = view.findViewById(R.id.tvlastname);
            tvpincode = view.findViewById(R.id.tvpincode);
            tvcity = view.findViewById(R.id.tvcity);
            tvstate = view.findViewById(R.id.tvstate);
            mobleno1 = view.findViewById(R.id.mobleno1);
            tvhouseno1 = view.findViewById(R.id.tvhouseno1);
            tvufirst1 = view.findViewById(R.id.tvufirst1);
            tvlastname1 = view.findViewById(R.id.tvlastname1);
            tvpincode1 = view.findViewById(R.id.tvpincode1);
            tvcit1= view.findViewById(R.id.tvcit1);
            tvstate1 = view.findViewById(R.id.tvstate1);
            llAddress1 = view.findViewById(R.id.llAddress1);
            llAddress = view.findViewById(R.id.llAddress);

        }
    }

    public Address_Adapter(Context context, List<Address_model> liveList) {
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
    public Address_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_layout, parent, false);
        return new Address_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Address_Adapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Address_model live = liveList.get(position);



        if (live.getIs_default()==0){
            holder.mobleno.setText(live.getAddress_phone());
            holder.tvhouseno.setText(live.getAddress_line());
            holder.tvufirst.setText(live.getFirst_name());
            holder.tvlastname.setText(live.getLast_name());
            holder.tvpincode.setText(""+live.getPostal_code());
            holder.tvcity.setText( live.getCity());
            holder.tvstate.setText( live.getState());
            holder.llAddress.setVisibility(View.VISIBLE);
            holder.llAddress1.setVisibility(View.GONE);
        }else if (live.getIs_default()==1){

            holder.mobleno1.setText(live.getAddress_phone());
            holder.tvhouseno1.setText(live.getAddress_line());
            holder.tvufirst1.setText(live.getFirst_name());
            holder.tvlastname1.setText(live.getLast_name());
            holder.tvpincode1.setText(""+live.getPostal_code());
            holder.tvcit1.setText( live.getCity());
            holder.tvstate1.setText( live.getState());
            holder.llAddress1.setVisibility(View.VISIBLE);
            holder.llAddress.setVisibility(View.GONE);
            Log.e("Addr",""+live.getCity());
        }




        holder.llAddress.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                productPageClick.productClickAddress(position, live.getCustomer_id(),
                        live.getAddress_id());

                final Animation myAnim = AnimationUtils.loadAnimation(context,R.anim.bounce);
                holder.llAddress.startAnimation(myAnim);
            }
        });
        holder.llAddress1.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                productPageClick.productClickAddress(position, live.getCustomer_id(),
                        live.getAddress_id());

                final Animation myAnim = AnimationUtils.loadAnimation(context,R.anim.bounce);
                holder.llAddress1.startAnimation(myAnim);
            }
        });


    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void set(Address_Adapter.ProductPageClick onClick)
    {
        this.productPageClick = onClick;
    }

}
