package com.mynexmy.nex.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mynexmy.nex.Models.Selectaddress_Model;
import com.mynexmy.nex.R;

import java.util.List;

public class Selectaddress_Adapter extends RecyclerView.Adapter<Selectaddress_Adapter.MyViewHolder> {

    private Context context;
    private List<Selectaddress_Model> liveList;

    private ProductPageClick productPageClick;



    public interface ProductPageClick{
        void productClick(int position,int cid,int c_mobile, int c_pin, String c_name, String c_email,
                              String c_address, String c_locality, String c_city, String c_state

        );
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView anyhouseno, tvPin, tvMobile,tvremove,tvedit;

       private RelativeLayout rlSelectAdd;
       private RadioGroup rdGroup;

        LinearLayout pd_1;
        public MyViewHolder(View view) {
            super(view);

            anyhouseno = view.findViewById(R.id.anyhouseno);
            tvPin = view.findViewById(R.id.tvPin);
            tvMobile = view.findViewById(R.id.tvMobile);
            tvremove = view.findViewById(R.id.tvremove);
            rdGroup = view.findViewById(R.id.rdGroup);
            tvedit = view.findViewById(R.id.tvedit);
            rlSelectAdd = view.findViewById(R.id.rlSelectAdd);

        }
    }

    public Selectaddress_Adapter(Context context, List<Selectaddress_Model> liveList) {
        //List<SiderImageModel> slider_image_list
        this.context = context;
        this.liveList = liveList;
        ////this.slider_image_list = slider_image_list;

    }
    public int getItemCount() {
        return liveList.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.selectaddresslayout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        Selectaddress_Model live = liveList.get(position);

        holder.anyhouseno.setText(""+live.getC_address()+" "+live.getC_locality()+" "+live.getC_city()
                +" "+live.getC_state());
        holder.tvPin.setText(""+ live.getC_pin());
        holder.tvMobile.setText(""+live.getC_mobile());

//        Toast.makeText(context, ""+live.c_mobile, Toast.LENGTH_SHORT).show();

        holder.rlSelectAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                productPageClick.productClick(position,live.getCid(), live.getC_mobile(), live.getC_pin(),
//                        live.getC_name(), live.getC_email(), live.getC_address(), live.getC_locality(),
//                        live.getC_city(), live.getC_state() );
            }

        });


    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }




}
