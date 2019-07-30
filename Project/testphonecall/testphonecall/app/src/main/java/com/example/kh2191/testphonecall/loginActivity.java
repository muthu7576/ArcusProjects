package com.example.kh2191.testphonecall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class loginActivity extends AppCompatActivity {
    EditText mlogin_user, mlogin_pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mlogin_user = (EditText) findViewById(R.id.login_users);
        mlogin_pwd = (EditText) findViewById(R.id.login_pwds);


    }

    public void onLoginscre(View view) throws JSONException {
        JSONObject jsonObjects = new JSONObject();
        jsonObjects.put("username", mlogin_user.getText().toString());
        jsonObjects.put("password", mlogin_pwd.getText().toString());
        String url = "http://arcus.co.in/tvshd/tvsapp/Login.php";
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("url", url);
        params.put("requestmethod", "POST");
        params.put("inputparams", jsonObjects);
        asynctask logins = new asynctask(params);
        logins.setListener(new asynctask.MyListener() {
            @Override
            public void onpreExecutemethod() {

            }

            @Override
            public void onPostExecutemetod(String result) throws JSONException {
                System.out.println("the status output is: " + result);
                JSONObject jsonObjectsoflogins = new JSONObject(result);
                checkinglogins(jsonObjectsoflogins);
            }
        });
        logins.execute();


    }

    private void checkinglogins(JSONObject result) throws JSONException {
        if ((result.getBoolean("isValid")) == true) {
            Intent intent = new Intent(loginActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_LONG).show();
        }

    }


}

