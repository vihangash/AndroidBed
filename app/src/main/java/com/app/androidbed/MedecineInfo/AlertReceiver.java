package com.app.androidbed.MedecineInfo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class AlertReceiver extends BroadcastReceiver {

    private int box1SwStatus, box2SwStatus, box3SwStatus, box4SwStatus, box5SwStatus, box6SwStatus, reqCode, repeatStatus;
    private String alarmTime;
    private FirebaseDatabase firebaseDatabase;
    private int delayTime = 60; //seconds

    @Override
    public void onReceive(Context context, Intent intent) {

        firebaseDatabase = FirebaseDatabase.getInstance();

        box1SwStatus = intent.getIntExtra("box1Sw",0);
        box2SwStatus = intent.getIntExtra("box2Sw",0);
        box3SwStatus = intent.getIntExtra("box3Sw",0);
        box4SwStatus = intent.getIntExtra("box4Sw",0);
        box5SwStatus = intent.getIntExtra("box5Sw",0);
        box6SwStatus = intent.getIntExtra("box6Sw",0);

        reqCode = intent.getIntExtra("requsetCode",0);
        repeatStatus = intent.getIntExtra("repeatSw",0);
        alarmTime = intent.getStringExtra("alarmTime");

        Toast.makeText(context, "Alarm Rinnging "+alarmTime, Toast.LENGTH_SHORT).show();

        if (repeatStatus == 0) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, reqCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
            firebaseDatabase.getReference().child("Reminders").child(String.valueOf(reqCode)).child("Alarm_time").setValue(alarmTime+" Ranged(Off)");
        }


        if (box1SwStatus == 1){
            firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton1").child("status").setValue(1);
            Log.d("smartBed","box1 on");
        }else {
            firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton1").child("status").setValue(0);
            Log.d("smartBed","box1 off");
        }
        if (box2SwStatus == 1){
            firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton2").child("status").setValue(1);
            Log.d("smartBed","box2 on");
        }else {
            firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton2").child("status").setValue(0);
            Log.d("smartBed","box2 off");
        }if (box3SwStatus == 1){
            firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton3").child("status").setValue(1);
            Log.d("smartBed","box3 on");
        }else {
            firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton3").child("status").setValue(0);
            Log.d("smartBed","box3 off");
        }if (box4SwStatus == 1){
            firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton4").child("status").setValue(1);
            Log.d("smartBed","box4 on");
        }else {
            firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton4").child("status").setValue(0);
            Log.d("smartBed","box4 off");
        }if (box5SwStatus == 1){
            firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton5").child("status").setValue(1);
            Log.d("smartBed","box5 on");
        }else {
            firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton5").child("status").setValue(0);
            Log.d("smartBed","box5 off");
        }if (box6SwStatus == 1){
            firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton6").child("status").setValue(1);
            Log.d("smartBed","box6 on");
        }else {
            firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton6").child("status").setValue(0);
            Log.d("smartBed","box6 off");
        }

        firebaseDatabase.getReference().child("Togale_Buttons").child("Buzzer").child("status").setValue(1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton1").child("status").setValue(0);
                firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton2").child("status").setValue(0);
                firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton3").child("status").setValue(0);
                firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton4").child("status").setValue(0);
                firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton5").child("status").setValue(0);
                firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton6").child("status").setValue(0);
                firebaseDatabase.getReference().child("Togale_Buttons").child("Buzzer").child("status").setValue(0);
            }
        },delayTime*1000);

    }


}
