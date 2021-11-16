package com.example.kfc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.kfc.Adapter.ProductAdapter;
import com.example.kfc.model.Products;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView ProductItem;
    ProductAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.cart_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });
        DownloadTextTask task = new DownloadTextTask();
        task.execute("http://192.168.100.10/mobileapp/index.php");
    }
    private void setProductItem(List<Products> Products){
        ProductItem = findViewById(R.id.prod_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        ProductItem.setLayoutManager(layoutManager);
        productAdapter = new ProductAdapter(this,Products);
        ProductItem.setAdapter(productAdapter);
    }
    private String downloadText(String strUrl) {
        String strResult = "";
        try {
            URL url = new URL(strUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            strResult = readStream(con.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strResult;
    }
    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } } }
        return sb.toString();
    }
    private class DownloadTextTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return downloadText(urls[0]);
            }
            catch(Exception e) {
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            List<Products> ProductItem = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0 ; i < jsonArray.length(); i++)
                {
                    JSONObject contactObject = jsonArray.optJSONObject(i);
                    ProductItem.add(new Products(contactObject.optInt("id"),
                            contactObject.optString("name"),
                            contactObject.optString("price"),
                            contactObject.optString("description"),
                            contactObject.optString("img")));
                }
                setProductItem(ProductItem);
            } catch (Exception ex) {}
        }
    }
}