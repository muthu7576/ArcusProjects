package com.example.praka.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Databasehelper mdatabasehelper;
EditText notiedit;
Button SveSqlbtn,tokenshow;
    private String TAG = "MainActvity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notiedit = (EditText)findViewById(R.id.edit_notify);
        SveSqlbtn = (Button)findViewById(R.id.savesqllite);
        mdatabasehelper = new Databasehelper(this);
        tokenshow = (Button)findViewById(R.id.notifymestkn);


        tokenshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "getInstanceId failed", task.getException());
                                    return;
                                }

                                // Get new Instance ID token
                                String token = task.getResult().getToken();

                                // Log and toast
                                String msg = getString(R.string.msg_token_fmt, token);
                                Log.d(TAG, msg);
                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                                System.out.println("The firebase token is: "+msg);
                            }
                        });
                /*String Gettoken = FirebaseInstanceId.getInstance().getToken();
                System.out.println("The firebase token is: "+Gettoken);

                toastmessage(Gettoken);*/
            }
        });
        /*alldts();*/
        /*Calendar calendar = Calendar.getInstance();
        Intent notifyintents = new Intent(this,MyReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,2,notifyintents,PendingIntent.FLAG_UPDATE_CURRENT );
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
        }*/

        SveSqlbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String neweditd = notiedit.getText().toString();
                if(neweditd.length() != 0){
                    addDatas(neweditd);
                    notiedit.setText("");
                    alldts();*/
                    try {
                        alarm("2019/08/01 11:09:00");
                        alarm("2019/08/01 11:11:00");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
               /* }
                else {
                    toastmessage("You must put something in textfield");
                }*/
            }
        });

    }

    public void alldts(){
        Cursor cursor = mdatabasehelper.alldata();
        if(cursor.getCount() == 0){
            toastmessage("NO DATA");
        }else {
            /*if (cursor.moveToFirst()) {
                do {

                    Toast.makeText(this,"VALUES TIME id is"+cursor.getString(0),Toast.LENGTH_SHORT).show();
                    Toast.makeText(this,"VALUES TIME  is"+cursor.getString(1),Toast.LENGTH_SHORT).show();
                    System.out.println("the VALUES TIME id is pressed"+cursor.getString(0));
                    System.out.println("the VALUES TIMEpressed"+cursor.getString(1));

                    // Passing values
                    String column1 = cursor.getString(0);
                    String column2 = cursor.getString(1);
                    String timeStamp = new SimpleDateFormat("HH:mm").format(new java.util.Date());
                    System.out.println("the time onclick pressed"+timeStamp);
                    if(column2.equalsIgnoreCase(timeStamp)){
                        System.out.println("the time get Alarmed"+timeStamp);
                        Calendar calendar = Calendar.getInstance();
                        Intent notifyintents = new Intent(this,MyReciever.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,2,notifyintents,PendingIntent.FLAG_UPDATE_CURRENT );
                        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
                        }
                    }
                    // Do something Here with values

                }*/
            /*while (cursor.moveToNext()){
                System.out.println("the VALUES TIME id is pressed"+cursor.getString(0));
                System.out.println("the VALUES TIMEpressed"+cursor.getString(1));
                String timeStamp = new SimpleDateFormat("HH:mm").format(new java.util.Date());
                System.out.println("the time onclick pressed"+timeStamp);
                if(cursor.getString(1).equalsIgnoreCase(timeStamp)){
                    System.out.println("the time get Alarmed"+timeStamp);
                    alarm();
                }*/
               /* else{
                    while (cursor.moveToNext()) {
                        System.out.println("the time get Alarmed second type" + timeStamp);
                        System.out.println("the VALUES TIME id is pressed" + cursor.getString(0));
                        System.out.println("the VALUES TIMEpressed" + cursor.getString(1));
                        *//*String timeStamps = new SimpleDateFormat("HH:mm").format(new java.util.Date());*//*
                        System.out.println("the time onclick pressed" + timeStamp);
                        if (cursor.getString(1).equalsIgnoreCase(timeStamp)) {
                            System.out.println("the time get Alarmed" + timeStamp);
                            alarm();
                            return;
                        }
                    }
                }
*/
                }
                cursor.close();
            }





        public void alarm(String dateIp) throws ParseException {
            Calendar calendar = Calendar.getInstance();
            String myDate = dateIp;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = sdf.parse(myDate);
            long millis = date.getTime();
            final int _id = (int) System.currentTimeMillis();
            Intent notifyintents = new Intent(this,MyReciever.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,_id,notifyintents,PendingIntent.FLAG_UPDATE_CURRENT );
            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,millis,pendingIntent);
            }
        }



    public void addDatas(String newEntries){
        boolean notiydatas = mdatabasehelper.addData(newEntries);
        if(notiydatas){
            Toast.makeText(this,"Data Successfully inserted",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"Something went Wrong",Toast.LENGTH_LONG).show();
        }
    }


    public void notifiesget(View view){
        System.out.println("the button onclick pressed");
            NotificationUtils.remindUserBecauseCharging(this);
    }

    public void toastmessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}
