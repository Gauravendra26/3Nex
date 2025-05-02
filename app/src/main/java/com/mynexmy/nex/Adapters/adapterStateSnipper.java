package com.mynexmy.nex.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mynexmy.nex.R;

import java.util.List;

public class adapterStateSnipper extends RecyclerView.Adapter<adapterStateSnipper.MyViewHolder> {

    private Context context;
    private List<String> statesList;

    private ProductClick productClick;

    // Interface for handling clicks
    public interface ProductClick {
        void productClickHomeNew(int position, String stateName);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView stateName;
        private LinearLayout llState;
        private View viewLine;

        public MyViewHolder(View view) {
            super(view);
            stateName = view.findViewById(R.id.stateName); // Assuming a TextView with id stateName
            llState = view.findViewById(R.id.llState); // Assuming a LinearLayout for click area
            viewLine = view.findViewById(R.id.viewLine); // Assuming a LinearLayout for click area
        }
    }

    public adapterStateSnipper(Context context, List<String> statesList) {
        this.context = context;
        this.statesList = statesList;
    }

    public void setData(List<String> newData) {
        this.statesList = newData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return statesList.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.spinner_item, parent, false); // Use your layout here
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String state = statesList.get(position);
        holder.stateName.setText(state);
        if (statesList.get(position).equals(0)) {
            holder.viewLine.setVisibility(View.GONE);
        }

        holder.llState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition(); // Get the current position
                if (adapterPosition != RecyclerView.NO_POSITION) {  // Ensure position is valid
                    String stateName = statesList.get(adapterPosition); // Get the state name from the list
                    if (productClick != null) {
                        productClick.productClickHomeNew(adapterPosition, stateName);
                    }
                }
            }
        });
    }

    public void set(ProductClick onClick) {
        this.productClick = onClick;
    }
}
