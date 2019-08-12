package com.example.praka.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReciever extends BroadcastReceiver {

    public MyReciever(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("the button myreciever entered pressed");
        Intent intent1 = new Intent(context,MyNewIntentService.class);
        context.startService(intent1);
    }
}
