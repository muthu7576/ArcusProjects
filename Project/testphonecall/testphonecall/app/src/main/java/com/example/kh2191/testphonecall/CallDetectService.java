package com.example.kh2191.testphonecall;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class CallDetectService extends Service {
    private Messenger messageHandler;

    private CallListenerServiceHelper callHelper;

    public CallDetectService() {
    }

    public interface CallDetectServiceListener{
        void onEndCall();
        void onPause();
        void onResume();
    }

    private CallDetectServiceListener mCallDetectServiceListener;

    public void setCallDetectServiceListener(CallDetectServiceListener callDetectServiceListener )
    {
        mCallDetectServiceListener = callDetectServiceListener;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        messageHandler = (Messenger) extras.get("MESSENGER");
        int res = 0;
        try {
            callHelper = new CallListenerServiceHelper(this);
            System.out.println("Call Started");
            callHelper.setCallListnerServiceListener(new CallListenerServiceHelper.CallListenerServiceHelperListener() {
                @Override
                public void onEndCall() throws Exception{
                    Message msg = Message.obtain();
                    msg.obj = "EndCall";
                    System.out.println("In Call Detect Service End Callback");
                    messageHandler.send(msg);
                }

                @Override
                public void onPause() {
                    mCallDetectServiceListener.onPause();
                }

                @Override
                public void onResume() {
                    mCallDetectServiceListener.onResume();
                }
            });
            res = super.onStartCommand(intent, flags, startId);
            callHelper.start();
            testUtils.getNextFreeCustomer();

        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            callHelper.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // not supporting binding
        return null;
    }
}