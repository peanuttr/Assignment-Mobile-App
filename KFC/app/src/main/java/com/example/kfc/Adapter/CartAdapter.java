package com.example.kfc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kfc.R;
import com.example.kfc.model.Carts;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    List<Carts> ProductCartList;

    public CartAdapter(Context context, List<Carts> productCartList) {
        this.context = context;
        ProductCartList = productCartList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_items,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.Name.setText(ProductCartList.get(position).getProdName());
        holder.Price.setText(ProductCartList.get(position).getProdPrice().toString());
        holder.Qty.setText(ProductCartList.get(position).getProdQty().toString());
        holder.Total.setText(ProductCartList.get(position).getProdTotal().toString());
    }

    @Override
    public int getItemCount() {
        return ProductCartList.size();
    }

    public static  final  class CartViewHolder extends RecyclerView.ViewHolder{
        TextView Name,Price,Qty,Total;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.tv_name);
            Price = itemView.findViewById(R.id.tv_rate);
            Qty = itemView.findViewById(R.id.tv_qty);
            Total = itemView.findViewById(R.id.tv_total);
        }
    }
}
