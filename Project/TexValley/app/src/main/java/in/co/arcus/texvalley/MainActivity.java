package in.co.arcus.texvalley;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;



public class MainActivity extends AppCompatActivity {
    EditText mlogin_user, mlogin_pwd;
    public static boolean isKill = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mlogin_user = (EditText) findViewById(R.id.login_user);
        mlogin_pwd = (EditText) findViewById(R.id.login_pwd);


        isKill = false;



    }


    public void onLogin(View view) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", mlogin_user.getText().toString());
        jsonObject.put("password", mlogin_pwd.getText().toString());
        String url = "http://texvalley.arcus.co.in/texvalleyapp/Login.php";
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("url", url);
        params.put("requestmethod", "POST");
        params.put("inputparams", jsonObject);
        asynctask login = new asynctask(params);
        login.setListener(new asynctask.MyListener() {
            @Override
            public void onpreExecutemethod() {

            }

            @Override
            public void onPostExecutemetod(String result) throws JSONException {
                System.out.println("the status output is: " + result);
                JSONObject jsonObjectsoflogin = new JSONObject(result);
                checkinglogin(jsonObjectsoflogin);
            }
        });
        login.execute();


    }

    private void checkinglogin(JSONObject result) throws JSONException {
        if ((result.getBoolean("isValid")) == true) {
            Intent intent = new Intent(MainActivity.this, dashboard.class);
            intent.putExtra("role_id",result.getString("role_id"));
            intent.putExtra("user_id",result.getString("user_id"));
            startActivity(intent);
        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_LONG).show();
        }

    }


}

