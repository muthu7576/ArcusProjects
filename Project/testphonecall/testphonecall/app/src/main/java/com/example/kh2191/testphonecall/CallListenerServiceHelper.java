package com.example.kh2191.testphonecall;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.RemoteException;
import android.telephony.TelephonyManager;

public class CallListenerServiceHelper{
    private Context ctx;
    private TelephonyManager tm;
    private CallListner callListener;

    public interface CallListenerServiceHelperListener{
        void onEndCall() throws RemoteException, Exception;
        void onPause();
        void onResume();
    }

    private CallListenerServiceHelperListener mCallListenerServiceHelperListener;

    public void setCallListnerServiceListener(CallListenerServiceHelperListener callListenerServiceHelperListener ){
        mCallListenerServiceHelperListener = callListenerServiceHelperListener;
    }

    public CallListenerServiceHelper(Context ctx) throws Exception {
        this.ctx = ctx;
        callListener = new CallListner();
        callListener.setOnEndCallListener(new CallListner.callListnerCallBackListner() {
            @Override
            public void onEndCall() throws Exception {
                mCallListenerServiceHelperListener.onEndCall();
            }

            @Override
            public void onPause() {
                pause();
            }

            @Override
            public void onResume() {
                resume();
            }
        });
    }

    private void pause() {
        mCallListenerServiceHelperListener.onPause();
    }

    private void resume() {
        mCallListenerServiceHelperListener.onResume();
    }

    /**
   * Start calls detection.
     */
    public void start() {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        ctx.registerReceiver(callListener, intentFilter);
    }

    /**
     * Stop calls detection.
     */
    public void stop() throws Exception {
        System.out.println("In Call ListenerService Helper Stop Service");
            System.out.println("In Call ListenerService unregistering");
            ctx.unregisterReceiver(callListener);
    }

}
