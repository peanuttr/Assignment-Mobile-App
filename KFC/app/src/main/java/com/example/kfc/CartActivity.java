package com.example.kfc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.kfc.Adapter.CartAdapter;
import com.example.kfc.model.Carts;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView CartItems;
    CartAdapter cartAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PostMethod().execute();
                Toast.makeText(getApplicationContext(),"Clear Cart Successfull",Toast.LENGTH_LONG).show();
            }
        });

        DownloadTextTask task = new DownloadTextTask();
        task.execute("http://192.168.100.10/mobileapp/cart.php");
    }
    private void setCartItem(List<Carts> carts){
        CartItems = findViewById(R.id.cart_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        CartItems.setLayoutManager(layoutManager);
        cartAdapter = new CartAdapter(this,carts);
        CartItems.setAdapter(cartAdapter);
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
            List<Carts> carts = new ArrayList<>();

            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0 ; i < jsonArray.length(); i++)
                {
                    JSONObject contactObject = jsonArray.optJSONObject(i);
                    carts.add(new Carts(contactObject.optInt("id"),contactObject.optString("name"),contactObject.optInt("price"),contactObject.optInt("qty"),contactObject.optInt("price")*contactObject.optInt("qty")));

                }
                setCartItem(carts);
            } catch (Exception ex) {}
        }
    }
    public class PostMethod extends AsyncTask<String, Void, String> {
        String server_response;

        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL("http://192.168.100.10/mobileapp/delete.php");
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