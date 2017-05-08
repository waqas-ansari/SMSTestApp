package com.waqasansari.smstestapp.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.waqasansari.smstestapp.utils.DatabaseHandler;

public class SMSReceiver extends BroadcastReceiver {
    public SMSReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        // Retrieves a map of extended data from the intent.
        Bundle bundle;

        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdu_Objects = (Object[]) bundle.get("pdus");
                if (pdu_Objects != null) {

                    for (Object aObject : pdu_Objects) {

                        SmsMessage currentSMS = getIncomingMessage(aObject, bundle);

                        String senderNo = currentSMS.getDisplayOriginatingAddress();

                        String message = currentSMS.getDisplayMessageBody();
                        Toast.makeText(context, "senderNum: " + senderNo + " :\n message: " + message, Toast.LENGTH_LONG).show();

                        if(message.toLowerCase().equals("in") || message.toLowerCase().equals("out")) {
                            DatabaseHandler handler = new DatabaseHandler(context);
                            handler.checkInOrOut(senderNo);
                        }

                    }
                    this.abortBroadcast();
                    // End of loop
                }
            }
        } // bundle null
    }

//        try {
//
//            if (bundle != null) {
//
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//                    SmsMessage messages[] = Telephony.Sms.Intents.getMessagesFromIntent(intent);
//
//                    SmsMessage currentMessage = messages[0];
//
//                    String senderNum = currentMessage.getDisplayOriginatingAddress();
//                    String message = currentMessage.getDisplayMessageBody();
//
//                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
//
//
//                    // Show Alert
//                    int duration = Toast.LENGTH_LONG;
//                    Toast toast = Toast.makeText(context,
//                            "senderNum: " + senderNum + ", message: " + message, duration);
//                    toast.show();
//
//                    if(message.toLowerCase().equals("in") || message.toLowerCase().equals("out")) {
//                        DatabaseHandler handler = new DatabaseHandler(context);
//                        handler.checkInOrOut(senderNum);
//                    }
//
//                }
//
//
////                final Object[] pdusObj = (Object[]) bundle.get("pdus");
////
////                if (pdusObj != null) {
////                    for (Object aPdusObj : pdusObj) {
////
////                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
////
////                        String senderNum = currentMessage.getDisplayOriginatingAddress();
////                        String message = currentMessage.getDisplayMessageBody();
////
////                        Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
////
////
////                        // Show Alert
////                        int duration = Toast.LENGTH_LONG;
////                        Toast toast = Toast.makeText(context,
////                                "senderNum: " + senderNum + ", message: " + message, duration);
////                        toast.show();
////
////                        if(message.toLowerCase().equals("in") || message.toLowerCase().equals("out")) {
////                            DatabaseHandler handler = new DatabaseHandler(context);
////                            handler.checkInOrOut(senderNum);
////                        }
////
////                    } // end for loop
////                }
//            } // bundle is null
//
//        } catch (Exception e) {
//            Log.e("SmsReceiver", "Exception smsReceiver" +e);
//
//        }


    private SmsMessage getIncomingMessage(Object aObject, Bundle bundle) {
        SmsMessage currentSMS;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String format = bundle.getString("format");
            currentSMS = SmsMessage.createFromPdu((byte[]) aObject, format);
        } else {
            currentSMS = SmsMessage.createFromPdu((byte[]) aObject);
        }
        return currentSMS;
    }
}
