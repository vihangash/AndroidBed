package com.app.androidbed.Emergency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.androidbed.Authentication.Login;
import com.app.androidbed.HomeActivity;
import com.app.androidbed.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.app.androidbed.Emergency.NotificationFSR.CHANNEL_1_ID;

public class emergencyhome extends AppCompatActivity {

    private Button stopbtn, nearesthospitalbtn, btncall;
    private DatabaseReference databaseReference;
    private TextView textView;
    private ImageView imageView;
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencyhome);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.sample);
        notificationManager = NotificationManagerCompat.from(this);

        stopbtn = findViewById(R.id.btn1);
        nearesthospitalbtn = findViewById(R.id.button);
        btncall = findViewById(R.id.button2);

        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Message");

        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("signal").setValue("off");
                mp.pause();
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String data = dataSnapshot1.getValue(String.class);

                    if (data.equals("on")) {
                        imageView.setImageResource(R.drawable.redsignal);
                        textView.setText(data);
                        mp.start();
                        send();
                    } else {
                        imageView.setImageResource(R.drawable.greensignal);
                        textView.setText(data);
                        mp.pause();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        nearesthospitalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(emergencyhome.this, Findnearesthospital.class));
            }
        });

        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialContactPhone();
            }
        });


    }

    private void dialContactPhone() {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "100", null)));
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
                startActivity(new Intent(emergencyhome.this, Login.class));
                finish();
                break;

            case R.id.item2id:
                Intent intent = new Intent(emergencyhome.this,HomeActivity.class);
                startActivity(intent);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void send() {

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.abc)
                .setContentTitle("Warning!!!")
                .setContentText(" Patient in the Emerency condition ")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);

    }


}
