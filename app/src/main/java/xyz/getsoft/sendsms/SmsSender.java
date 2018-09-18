package xyz.getsoft.sendsms;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by sourav pk on 3/31/2018.
 */

public class SmsSender extends AppCompatActivity {

    private List mobileNumList;

    String phoneNo;
    String message;
    private Context context;

    SmsSender(Context context, List mobileNumList, String message){
        this.context = context;
        this.mobileNumList = mobileNumList;
        //message = "নিরামিষ মেস এ টাকা নেয়া শুরু হয়েছে ।";
        this.message = message;
    }

    //send to all numbers in a List
    void send(){
        long t1 = System.currentTimeMillis();
        for (Object num : mobileNumList) {
             send(num.toString());
            Log.d("phone num", num.toString());
        }
        long t2 = System.currentTimeMillis();
        Log.d("time taken", t2+" - "+t1+" = "+String.valueOf(t2-t1));
    }

    private int count = 0;
    //send to a single number
    void send(final String phoneNo){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo, null, message, null, null);
            }
        }).start();
        //Log.d("num",phoneNo);
        //textView.setText("Sent : "+ (++count)+"/"+ mobileNumList.size() );
    }
}

