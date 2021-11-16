package com.example.kfc.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.telecom.Call;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kfc.ProductDetailActivity;
import com.example.kfc.R;
import com.example.kfc.model.Products;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    Context context;
    List<Products> ProductList;

    public ProductAdapter(Context context, List<Products> productList) {
        this.context = context;
        ProductList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_items ,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        DownloadImageTask task = new DownloadImageTask();
        task.execute("http://192.168.100.10/mobileapp/img/"+ProductList.get(position).getImgUrl());
        holder.productName.setText(ProductList.get(position).getProdName());
        holder.productPrice.setText(ProductList.get(position).getProdPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("prodId",ProductList.get(position).getProdId());
                intent.putExtra("prodName",ProductList.get(position).getProdName());
                intent.putExtra("prodDetail",ProductList.get(position).getProdDetail());
                intent.putExtra("prodPrice",ProductList.get(position).getProdPrice());
                intent.putExtra("prodImg",ProductList.get(position).getImgUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ProductList.size();
    }
    ImageView productImage;
    public final class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName,productPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.prod_name);
            productPrice = itemView.findViewById(R.id.prod_price);
            productImage = itemView.findViewById(R.id.prod_img);
        }
    }
    private Bitmap downloadImage(String strUrl) {
        Bitmap bmpResult = null;
        try {
            URL url = new URL(strUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            bmpResult = BitmapFactory.decodeStream(con.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmpResult;
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            return downloadImage(urls[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            productImage.setImageBitmap(result);
        }
    }

}
