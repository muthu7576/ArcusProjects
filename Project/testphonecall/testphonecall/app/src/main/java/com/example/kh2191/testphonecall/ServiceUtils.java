package com.example.kh2191.testphonecall;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class ServiceUtils extends AsyncTask {

    public interface ServiceListner {
        void successCallback(String response);
    }

    public HashMap<String, Object> params;

    ServiceUtils(HashMap<String, Object> params) {
        this.params = params;
    }

    ServiceListner mServiceListener;

    public void setCallbacks(ServiceListner serviceListner) {
        mServiceListener = serviceListner;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Object doInBackground(Object[] objects) {
        String response = null;
        if (params.get("requestmethod").equals("GET")) {
            response = getrequestmethod();
        } else if (params.get("requestmethod").equals("POST")) {
            response = postrequestmethod();
        }
        return response;
    }

    @Override
    protected void onPostExecute(Object response) {
        try {
            if (mServiceListener != null) {
                mServiceListener.successCallback(response.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        } catch (Exception e) {
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
            System.out.println("response :" + response);
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
