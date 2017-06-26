package com.example.javaniceyou.a1506anew;

import android.os.AsyncTask;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by javaniceyou on 2017/6/23.
 */
public class CustomAsyncTask extends AsyncTask<String,Integer,String>{
    private ListView listView;

    public CustomAsyncTask(ListView listView) {
        this.listView = listView;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            String result  = outputStream.toString();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Gson gson = new Gson();
        JavaBean javaBean = gson.fromJson(s, JavaBean.class);

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}