package com.example.kfc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kfc.Adapter.ProductAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class ProductDetailActivity extends AppCompatActivity {
    Integer qty = 0;
    ImageView Img;
    TextView Name;
    TextView Price;
    TextView Qty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Integer pId = getIntent().getExtras().getInt("prodId");
        String pName = getIntent().getExtras().getString("prodName");
        String pDetail = getIntent().getExtras().getString("prodDetail");
        String pPrice = getIntent().getExtras().getString("prodPrice");
        String pImg = getIntent().getExtras().getString("prodImg");

        Name = (TextView) findViewById(R.id.prodName);
        Name.setText(pName);
        TextView Detail = (TextView) findViewById(R.id.prodDetail);
        Detail.setText(pDetail);
        Price = (TextView) findViewById(R.id.prodPrice);
        Price.setText(pPrice);
        Img = (ImageView) findViewById(R.id.prodImg);
        Qty = (TextView) findViewById(R.id.quantity);
        Qty.setText(Integer.toString(qty));

        DownloadImageTask task = new DownloadImageTask();
        task.execute("http://192.168.100.10/mobileapp/img/"+pImg);

        findViewById(R.id.addquantity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty++;
                Qty.setText(Integer.toString(qty));
            }
        });
        findViewById(R.id.subquantity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qty<=0){
                    return;
                }
                else {
                    qty--;
                    Qty.setText(Integer.toString(qty));
                }

            }
        });
        findViewById(R.id.addcartbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PostMethod().execute();
                Toast.makeText(getApplicationContext(),"Add to Cart Successfull",Toast.LENGTH_LONG).show();
            }
        });
        findViewById(R.id.backbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

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
            Img.setImageBitmap(result);
        }
    }
    public class PostMethod extends AsyncTask<String, Void, String> {
        String server_response;

        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL("http://192.168.100.10/mobileapp/insert.php");
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setReadTimeout(15000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                try {
                    JSONObject obj = new JSONObject();
                    String pName = Name.getText().toString();
                    String pPrice = Price.getText().toString();
                    String pQty = Qty.getText().toString();

                    obj.put("name", pName);
                    obj.put("price", pPrice);
                    obj.put("qty", pQty);

                    writer.write(getPostDataString(obj));

                    Log.e("JSON Input", obj.toString());

                    writer.flush();
                    writer.close();
                    os.close();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                urlConnection.connect();
                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    server_response = readStream(urlConnection.getInputStream());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Response", " " + server_response);
        }

        public String getPostDataString(JSONObject params) throws Exception {
            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {
                String key = itr.next();
                Object value = params.get(key);

                if (first) first = false;
                else result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));
            }

            return result.toString();
        }

        public String readStream(InputStream in) {
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
                    }
                }
            }
            return sb.toString();
        }
    }
}