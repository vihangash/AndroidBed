package com.app.androidbed.AndroidFSR;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.app.androidbed.Authentication.Login;
import com.app.androidbed.HomeActivity;
import com.app.androidbed.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.app.androidbed.AndroidFSR.App.CHANNEL_2_ID;
import static com.app.androidbed.Emergency.NotificationFSR.CHANNEL_1_ID;


public class ViewPositionActivity extends AppCompatActivity {


    private NotificationManagerCompat notificationManager;
    Button button;
    Button viewbtn ;
    ImageButton alarmstop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_position);

        viewbtn =(Button)findViewById(R.id.viewBTN);
        notificationManager = NotificationManagerCompat.from(this);
        alarmstop = (ImageButton)findViewById(R.id.alarmstop);
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.sample);
        button =(Button)findViewById(R.id.pressureBTN);

        notificationManager = NotificationManagerCompat.from(this);




        viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.macrovideo.v380pro");
                startActivity(intent);
            }
        });




        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("FSR/fsr");



        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {

            @SuppressLint("ResourceAsColor")
            @Override


            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                float value = dataSnapshot.getValue(float.class);

                alarmstop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mp.pause();
                    }
                });

                if(value == 0 || value == 1) {
                    button.getBackground().setColorFilter(button.getContext().getResources().getColor(R.color.blue), PorterDuff.Mode.MULTIPLY);
                    alarmstop.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mp.pause();
                        }
                    });

                }
                else if( 1 < value && value <=200){
                    button.getBackground().setColorFilter(button.getContext().getResources().getColor(R.color.green), PorterDuff.Mode.MULTIPLY);

                }
                else if(200 < value && value <= 500){

                    button.getBackground().setColorFilter(button.getContext().getResources().getColor(R.color.yellow), PorterDuff.Mode.MULTIPLY);
                    mp.start();
                    sendOnChannel1();

                }
                else if(500 < value && value <= 700){
                    sendOnChannel1();
                    button.getBackground().setColorFilter(button.getContext().getResources().getColor(R.color.orange), PorterDuff.Mode.MULTIPLY);
                    mp.start();

                }
                else if(700 < value && value <=1000){

                    button.getBackground().setColorFilter(button.getContext().getResources().getColor(R.color.red), PorterDuff.Mode.MULTIPLY);
                    mp.start();

                                 }



            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });





    }
    public void sendOnChannel1(){

        String title = "Warning";
        String message = "Patient might get out of bed";

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.abc)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notification.contentIntent=  PendingIntent.getActivity(this, 0,
                new Intent(this, ViewPositionActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        notificationManager.notify(1,notification);
    }

    public void sendOnChannel2(){

        String title = "Warning";
        String message = "Check Patient";

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_2_ID)
                .setSmallIcon(R.drawable.abc)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManager.notify(2,notification);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item1id:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ViewPositionActivity.this, Login.class));
                finish();
                break;

            case R.id.item2id:
                Intent intent = new Intent(ViewPositionActivity.this, HomeActivity.class);
                startActivity(intent);
                break;


        }

        return super.onOptionsItemSelected(item);
    }

}
