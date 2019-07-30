package com.example.kh2191.testphonecall;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private boolean detectEnabled;
    private static Context mContext;
    private CountDownTimer counter;
    ProgressBar mProgressBar, mProgressBar1;
    private TextView textViewShowTime;
    int permsRequestCode = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar_timerview);
        String[] perms = {"android.permission.READ_PHONE_STATE","android.permission.PROCESS_OUTGOING_CALLS","android.permission.CALL_PHONE"};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(perms, permsRequestCode);
        }
        mContext = this.getApplicationContext();
        mProgressBar1 = (ProgressBar) findViewById(R.id.progressbar1_timerview);
        textViewShowTime = (TextView) findViewById(R.id.textView_timerview_time);
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){

        switch(permsRequestCode){
            case 200:
                boolean phonecall = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                boolean contact = grantResults[1]==PackageManager.PERMISSION_GRANTED;
                break;
        }

    }
    public void callNumber(View view) {
        setDetectEnabled(true);
    }
    public class MessageHandler extends Handler {

        @Override
        public void handleMessage(Message message) {
            System.out.println("In Message Handler");
            SetTimerAndStart(null);
        }
    }
    public  Handler messageHandler = new MessageHandler();

    private void setDetectEnabled(boolean enable) {
        detectEnabled = enable;
        Intent intent = new Intent(this, CallDetectService.class);
        intent.putExtra("MESSENGER", new Messenger(messageHandler));
        if (enable) {
            startService(intent);
        }
        else {
            stopService(intent);
        }
    }
    public void SetTimerAndStart(View v){
        mProgressBar.setVisibility(View.GONE);
        startTimer();
        mProgressBar1.setVisibility(View.VISIBLE);

    }
    public void startTimer(){
        counter = new CountDownTimer(5000, 1000){
            public void onTick(long millisUntilFinished){
                long seconds = millisUntilFinished/1000;
                mProgressBar1.setProgress((int)(seconds*4));
                textViewShowTime.setText(""+seconds);
                System.out.println(millisUntilFinished/1000 + "Seconds Remaining..");
            }
            public  void onFinish(){
                try {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar1.setVisibility(View.GONE);
                    testUtils.getNextFreeCustomer();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }.start();
    }

    public static Context getAppContext(){
        return mContext;
    }


    private void cancelTimer(){
        counter.cancel();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_search)
        {
            Intent intent = new Intent(MainActivity.this,loginActivity.class);
            startActivity(intent);
            /*Context context = MainActivity.this;
            String message = "Search is clicked";
            Toast.makeText(context,message,Toast.LENGTH_LONG).show();*/}
        return super.onOptionsItemSelected(item);
    }

}
