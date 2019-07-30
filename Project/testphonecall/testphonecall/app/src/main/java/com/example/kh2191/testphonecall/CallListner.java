package com.example.kh2191.testphonecall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class CallListner extends BroadcastReceiver {
    TelephonyManager telManager;
    Context ctx, ctcCH;
    public CallListner() {
    }
    public interface callListnerCallBackListner {
        void onEndCall() throws Exception;
        void onPause();
        void onResume();
    }

    private callListnerCallBackListner mListener;

    public void setOnEndCallListener(callListnerCallBackListner listener){
        mListener = listener;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        this.ctx=context;
        boolean startedCall = false; // New added boolean
        telManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        telManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private final PhoneStateListener phoneListener = new PhoneStateListener() {
        private boolean startedCall;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            try {
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING: {
                        if(incomingNumber!=null) {
                            Toast.makeText(ctx,"Ringing",Toast.LENGTH_LONG).show();
                        }
                        break;
                    }
                    case TelephonyManager.CALL_STATE_OFFHOOK: {
                        startedCall  = true;
                        if(incomingNumber!=null) {
                            //Toast.makeText(ctx,"Outgoing",Toast.LENGTH_LONG).show();
                        }
                        break;
                    }
                    case TelephonyManager.CALL_STATE_IDLE: {
                        if(startedCall) {
                            startedCall = false;
                            mListener.onEndCall();
                        }
                        break;
                    }
                    default: { }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };
}
