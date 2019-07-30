package com.example.kh2191.testphonecall;

import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class testUtils {
    public static void getNextFreeCustomer() throws JSONException {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("url" ,"http://arcus.co.in/tvshd/data/mt/get_mno.php");
        JSONObject inputParams = new JSONObject();
        inputParams.put("exec_id", "1");
        params.put("requestmethod","POST");
        params.put("inputparams",inputParams);
        ServiceUtils getCustomerService  = new ServiceUtils(params);
        getCustomerService.setCallbacks(new ServiceUtils.ServiceListner() {
            @Override
            public void successCallback(String response) {
                try {
                    JSONObject jsResponse = new JSONObject(response);
                    processResponseandProceed(jsResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        getCustomerService.execute();
    }

    public static void processResponseandProceed(JSONObject response) {
        try {
            JSONArray customerList = response.getJSONArray("response");
            if(customerList.length()>0){
                String customerPhone = customerList.getJSONObject(0).get("phone").toString();
                if(!customerPhone.equals("null")){
                    Intent i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:" + customerPhone));
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MainActivity.getAppContext().startActivity(i);
                }else{
                    Toast.makeText(MainActivity.getAppContext(),"No Customers!!!",Toast.LENGTH_LONG).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
