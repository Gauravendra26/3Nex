package com.mynexmy.nex.Adapters;

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
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Room.Product;
import com.mynexmy.nex.Room.ProductDao;
import com.mynexmy.nex.Room.ProductDatabase;

import java.util.List;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.MyViewHolder> {

    private Context context;
    private List<Product> liveList;
    TextView tvPrice,tvPriceItem1,tvPriceDiscount1,tvTotalAmount1,tvtvTotalSavings1,tvPriceQuantity;

    private OrderSummaryAdapter.whenClick onClick;

    //make interface like this
    public interface whenClick {
        //        void press(int position, String id, String image, String name, String sale ,String accu);
        void setNotify(int position);

        void setNotify1(int position);

        void press(int position, int pid, String pname, int price,
                   int qnt, int sale,
                   String image);
        void press1(int position, int pid, String pname, int price,
                   int qnt, int sale,
                   String image);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView p_name, p_description, p_saleprice, p_actualprice, p_quantity;

        private ImageView p_image, minus, add, cross;

        LinearLayout ll1;


        public MyViewHolder(View view) {
            super(view);
            p_name = view.findViewById(R.id.p_name);
            p_description = view.findViewById(R.id.p_description);
            p_saleprice = view.findViewById(R.id.p_saleprice);
            p_actualprice = view.findViewById(R.id.p_actualprice);
            p_quantity = view.findViewById(R.id.p_quantity);
            p_image = view.findViewById(R.id.p_image);
            minus = view.findViewById(R.id.minus);
            add = view.findViewById(R.id.add);
            cross = view.findViewById(R.id.cross);
            ll1 = view.findViewById(R.id.ll1);


        }
    }

    public OrderSummaryAdapter(Context context, List<Product> liveList, TextView tvPrice,TextView tvTotalAmount1,TextView tvPriceQuantity,
                               TextView tvtvTotalSavings1,TextView tvPriceDiscount1, TextView tvPriceItem1) {
        //List<SiderImageModel> slider_image_list
        this.context = context;
        this.liveList = liveList;
        this.tvPrice = tvPrice;
        this.tvTotalAmount1 = tvTotalAmount1;
        this.tvPriceQuantity = tvPriceQuantity;
        this.tvtvTotalSavings1 = tvtvTotalSavings1;
        this.tvPriceDiscount1 = tvPriceDiscount1;
        this.tvPriceItem1 = tvPriceItem1;
        ////this.slider_image_list = slider_image_list;

    }


    public void setOnClick(OrderSummaryAdapter.whenClick onClick) {
        this.onClick = onClick;
    }

    @Override
    public int getItemCount() {
        return liveList.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product live = liveList.get(position);
        holder.p_name.setText(live.getPname());
        holder.p_saleprice.setText("\u20B9" + live.getSale());
        String text = "<strike><font color=\'#757575\'>\u20B9" + live.getPrice() + "</font></strike>";
        holder.p_actualprice.setText(Html.fromHtml(text));
        Glide.with(context).load(live.getImage()) .placeholder(R.drawable.plash).dontAnimate()
                .into(holder.p_image);
        holder.p_quantity.setText("" + live.qnt);
        updateDiscount();
        updatePrice();
        updateQuantity();
        updatePriceforReg();
        holder.p_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.press1(position, live.getPid(), live.getPname(),
                        live.getPrice(), live.getQnt(), live.getSale(),
                        live.getImage()
                );
                final Animation myAnim = AnimationUtils.loadAnimation(context,R.anim.bounce);
                holder.ll1.startAnimation(myAnim);
            }
        });holder.p_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.press1(position, live.getPid(), live.getPname(),
                        live.getPrice(), live.getQnt(), live.getSale(),
                        live.getImage()
                );
                final Animation myAnim = AnimationUtils.loadAnimation(context,R.anim.bounce);
                holder.ll1.startAnimation(myAnim);
            }
        });holder.p_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.press1(position, live.getPid(), live.getPname(),
                        live.getPrice(), live.getQnt(), live.getSale(),
                        live.getImage()

                );
                final Animation myAnim = AnimationUtils.loadAnimation(context,R.anim.bounce);
                holder.ll1.startAnimation(myAnim);
            }
        });holder.p_saleprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.press1(position, live.getPid(), live.getPname(),
                        live.getPrice(), live.getQnt(), live.getSale(),
                        live.getImage()
                );
                final Animation myAnim = AnimationUtils.loadAnimation(context,R.anim.bounce);
                holder.ll1.startAnimation(myAnim);
            }
        });holder.p_actualprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.press1(position, live.getPid(), live.getPname(),
                        live.getPrice(), live.getQnt(), live.getSale(),
                        live.getImage()
                );
                final Animation myAnim = AnimationUtils.loadAnimation(context,R.anim.bounce);
                holder.ll1.startAnimation(myAnim);
            }
        });holder.p_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.press1(position, live.getPid(), live.getPname(),
                        live.getPrice(), live.getQnt(), live.getSale(),
                        live.getImage()
                );
                final Animation myAnim = AnimationUtils.loadAnimation(context,R.anim.bounce);
                holder.ll1.startAnimation(myAnim);
            }
        });


        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = (int) liveList.get(position).getQnt();
                qty++;
                liveList.get(position).setQnt(qty);
                holder.p_quantity.setText("" + qty);
//                onClick.setNotify(position);
                ProductDatabase db = Room.databaseBuilder(context,
                        ProductDatabase.class, "cart_db").allowMainThreadQueries().build();
                ProductDao productDao = db.ProductDao();
                productDao.updateRecord(live.getPid(), live.getPname(), live.getPrice(),
                        qty, live.getSale(), live.getImage());
                updatePriceforReg();
                updateDiscount();
                updatePrice();
                updateQuantity();
                onClick.setNotify(position);
                final Animation myAnim = AnimationUtils.loadAnimation(context,R.anim.bounce);
                holder.add.startAnimation(myAnim);
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View v) {
                int qty = (int) liveList.get(position).getQnt();
                if (qty > 0) {
                    qty = qty - 1;
                    ProductDatabase db = Room.databaseBuilder(context,
                            ProductDatabase.class, "cart_db").allowMainThreadQueries().build();
                    ProductDao productDao = db.ProductDao();
                    productDao.updateRecord(live.getPid(), live.getPname(), live.getPrice(),
                            qty, live.getSale(), live.getImage());
                    liveList.get(position).setQnt(qty);
                    holder.p_quantity.setText("" + qty);


                }
                if (qty < 1) {
                    ProductDatabase db = Room.databaseBuilder(context,
                            ProductDatabase.class, "cart_db").allowMainThreadQueries().build();
                    ProductDao productDao = db.ProductDao();
                    productDao.deleteById(liveList.get(position).getPid());
                    productDao.updateRecord(live.getPid(), live.getPname(), live.getPrice(),
                            qty, live.getSale(), live.getImage());
                    liveList.get(position).setQnt(qty);
                    liveList.remove(position);

                }
                final Animation myAnim = AnimationUtils.loadAnimation(context,R.anim.bounce);
                holder.minus.startAnimation(myAnim);
                updatePrice();
                updateDiscount();
                updatePriceforReg();
                updateQuantity();
                onClick.setNotify(position);

            }
        });
        holder.cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ProductDatabase db = Room.databaseBuilder(context,
                        ProductDatabase.class, "cart_db").allowMainThreadQueries().build();
                ProductDao productDao = db.ProductDao();
                productDao.deleteById(liveList.get(position).getPid());
                liveList.remove(position);
                notifyItemRemoved(position);
                onClick.setNotify(position);
                final Animation myAnim = AnimationUtils.loadAnimation(context,R.anim.bounce);
                holder.cross.startAnimation(myAnim);
            }
 });
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void updatePrice() {
        int sum = 0;
        for (int i = 0; i < liveList.size(); i++) {
            sum = sum + (liveList.get(i).getSale() * liveList.get(i).getQnt());
        }
        tvPrice.setText("\u20B9" + sum);
        tvTotalAmount1.setText("\u20B9" + sum);
    }
    public void updateDiscount() {
        int Discount = 0;

        double n1,n2;


        for (int i = 0; i < liveList.size(); i++) {
            n1= liveList.get(i).getPrice();
            n2=  liveList.get(i).getSale();

//            Discount_Percentage = (int) ((Discount /n1)*100);
            Discount = (int) ((n1 * liveList.get(i).getQnt())- (n2 * liveList.get(i).getQnt()));
         }
        tvPriceDiscount1.setText("- \u20B9" + Discount);
        tvtvTotalSavings1.setText("\u20B9" + Discount);
    }
    public void updateQuantity() {
        int Quantity = 0;
        for (int i = 0; i < liveList.size(); i++) {
            Quantity = Quantity + (liveList.get(i).getQnt());
        }
        tvPriceQuantity.setText("(" + Quantity+" items)");
    }

    public void updatePriceforReg() {
        int sumReg = 0;
        for (int i = 0; i < liveList.size(); i++) {
            sumReg = sumReg + (liveList.get(i).getPrice() * liveList.get(i).getQnt());
        }
        tvPriceItem1.setText("\u20B9" + sumReg);
    }
}
