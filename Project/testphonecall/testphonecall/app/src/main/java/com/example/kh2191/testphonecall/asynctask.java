package com.example.kh2191.testphonecall;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class asynctask extends AsyncTask<Void, Void, String> {
    HashMap<String, Object> params;

    public interface MyListener {
        void onpreExecutemethod();

        void onPostExecutemetod(String result) throws JSONException;
    }

    private MyListener mlistener;

    public void setListener(MyListener listener) {
        mlistener = listener;
    }

    asynctask(HashMap<String, Object> params) {
        this.params = params;
    }

    @Override
    protected void onPreExecute() {

        if (mlistener != null) {
            mlistener.onpreExecutemethod();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(Void... someString) {

        String response = null;
        String requestmethod = params.get("requestmethod").toString();
        if (requestmethod.equals("GET")) {
            response = getrequestmethod();
        } else if (requestmethod.equals("POST")) {
            response = postrequestmethod();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result) {

        if (mlistener != null) {
            try {
                mlistener.onPostExecutemetod(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private String getrequestmethod() {
        String url = params.get("url").toString();

        try {
            URL login_url = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) login_url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
   /* String in;
    String result= "";*/
            int responseCode = urlConnection.getResponseCode();
            System.out.println("Response Code : " + responseCode);
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println("isValid" + response);
                return response.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String postrequestmethod() {

        String url = params.get("url").toString();
        StringBuffer response = new StringBuffer();
        try {
            URL login_url = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) login_url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setUseCaches(false);
            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.writeBytes(params.get("inputparams").toString());
                wr.flush();
                wr.close();
            }
            if (conn.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            }
            conn.disconnect();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("response :" + response);

        return null;
    }
}
