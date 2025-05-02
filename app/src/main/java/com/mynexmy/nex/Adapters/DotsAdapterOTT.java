package com.mynexmy.nex.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.mynexmy.nex.Models.Slide_Model_Clothing;
import com.mynexmy.nex.Models.Slide_Model_OTT;
import com.mynexmy.nex.R;

import java.util.List;

public class DotsAdapterOTT extends RecyclerView.Adapter<DotsAdapterOTT.DotsViewHolder> {

    private final Context context;
    private final List<Slide_Model_OTT> itemList; // List to hold Slide_Model_Grocery objects
    private int selectedPosition = 0;

    public DotsAdapterOTT(Context context, List<Slide_Model_OTT> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public DotsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dot, parent, false);
        return new DotsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DotsViewHolder holder, int position) {
        if (position == selectedPosition) {
            holder.dot.setImageResource(R.drawable.selected_dot); // Highlight selected dot
        } else {
            holder.dot.setImageResource(R.drawable.unselected_dot); // Normal dot
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class DotsViewHolder extends RecyclerView.ViewHolder {
        ImageView dot;

        DotsViewHolder(View itemView) {
            super(itemView);
            dot = itemView.findViewById(R.id.dot);
        }
    }
}
