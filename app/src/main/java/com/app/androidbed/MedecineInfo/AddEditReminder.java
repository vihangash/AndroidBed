package com.app.androidbed.MedecineInfo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.androidbed.AndroidFSR.PositionActivity;
import com.app.androidbed.Authentication.Login;
import com.app.androidbed.HomeActivity;
import com.app.androidbed.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

public class AddEditReminder extends AppCompatActivity {

    private int reminderId = 0;
    int status = 0;
    TextView alarmtxt;
    EditText descriptionText;
    Button buttonOk;
    Switch box1Switch, box2Switch, box3Switch, box4Switch, box5Switch, box6Switch, repeatSwitch;
    TimePicker timePicker;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_add_edit_reminder);

        firebaseDatabase = FirebaseDatabase.getInstance();

        alarmtxt = findViewById(R.id.textViewalarm);
        descriptionText = findViewById(R.id.description_editText);
        buttonOk = findViewById(R.id.okbtn);
        timePicker = findViewById(R.id.timePicker);

        box1Switch = findViewById(R.id.switch_box1);
        box2Switch = findViewById(R.id.switch_box2);
        box3Switch = findViewById(R.id.switch_box3);
        box4Switch = findViewById(R.id.switch_box4);
        box5Switch = findViewById(R.id.switch_box5);
        box6Switch = findViewById(R.id.switch_box6);

        repeatSwitch = findViewById(R.id.switch_repeat);

        Intent intent = getIntent();
        if (intent.getStringExtra("re_id")==null) {
            DatabaseReference reference = firebaseDatabase.getReference().child("Reminders");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int j;
                    for (j = 1; dataSnapshot.hasChild("" + j); j++) { }
                    reminderId = j;
                    Log.d("smartBed1", "reminderId : " + reminderId);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });
        }else {
            reminderId = Integer.parseInt(intent.getStringExtra("re_id"));
            Log.d("smartBed1", "reminderId : " + reminderId);

            DatabaseReference databaseReference1 = firebaseDatabase.getReference().child("Reminders").child(""+reminderId);
            databaseReference1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String time = dataSnapshot.child("Hour_Min").getValue().toString();
                    String[] timeParts = time.split("/");
                    timePicker.setCurrentHour(Integer.parseInt(timeParts[0]));
                    timePicker.setCurrentMinute(Integer.parseInt(timeParts[1]));
                    alarmtxt.setText("Alarm set : "+dataSnapshot.child("Alarm_time").getValue().toString());
                    descriptionText.setText(dataSnapshot.child("Description").getValue().toString());

                    setStatus(box1Switch,dataSnapshot.child("Box_1").getValue().toString());
                    setStatus(box2Switch,dataSnapshot.child("Box_2").getValue().toString());
                    setStatus(box3Switch,dataSnapshot.child("Box_3").getValue().toString());
                    setStatus(box4Switch,dataSnapshot.child("Box_4").getValue().toString());
                    setStatus(box5Switch,dataSnapshot.child("Box_5").getValue().toString());
                    setStatus(box6Switch,dataSnapshot.child("Box_6").getValue().toString());
                    setStatus(repeatSwitch,dataSnapshot.child("Repeat").getValue().toString());
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Medecine_Information");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                box1Switch.setText(dataSnapshot.child("Box1").getValue().toString());
                box2Switch.setText(dataSnapshot.child("Box2").getValue().toString());
                box3Switch.setText(dataSnapshot.child("Box3").getValue().toString());
                box4Switch.setText(dataSnapshot.child("Box4").getValue().toString());
                box5Switch.setText(dataSnapshot.child("Box5").getValue().toString());
                box6Switch.setText(dataSnapshot.child("Box6").getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });


        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE,timePicker.getCurrentMinute());
                calendar.set(Calendar.SECOND,0);

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(AddEditReminder.this, AlertReceiver.class);
                intent.putExtra("box1Sw",getStatus(box1Switch));
                intent.putExtra("box2Sw",getStatus(box2Switch));
                intent.putExtra("box3Sw",getStatus(box3Switch));
                intent.putExtra("box4Sw",getStatus(box4Switch));
                intent.putExtra("box5Sw",getStatus(box5Switch));
                intent.putExtra("box6Sw",getStatus(box6Switch));
                intent.putExtra("repeatSw",getStatus(repeatSwitch));
                intent.putExtra("requsetCode",reminderId);
                intent.putExtra("alarmTime",DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime()));
                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddEditReminder.this,reminderId,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                if (calendar.before(Calendar.getInstance())){
                    calendar.add(Calendar.DATE,1);
                }
                if (getStatus(repeatSwitch) == 1){
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
                }else {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                }

                firebaseDatabase.getReference().child("Reminders").child(""+reminderId).child("Alarm_time").setValue(DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime()));
                firebaseDatabase.getReference().child("Reminders").child(""+reminderId).child("Remind_id").setValue(""+reminderId);
                firebaseDatabase.getReference().child("Reminders").child(""+reminderId).child("Description").setValue(descriptionText.getText().toString());
                firebaseDatabase.getReference().child("Reminders").child(""+reminderId).child("Hour_Min").setValue(timePicker.getCurrentHour()+"/"+timePicker.getCurrentMinute());
                firebaseDatabase.getReference().child("Reminders").child(""+reminderId).child("Repeat").setValue(getStatus(repeatSwitch));
                firebaseDatabase.getReference().child("Reminders").child(""+reminderId).child("Box_1").setValue(getStatus(box1Switch));
                firebaseDatabase.getReference().child("Reminders").child(""+reminderId).child("Box_2").setValue(getStatus(box2Switch));
                firebaseDatabase.getReference().child("Reminders").child(""+reminderId).child("Box_3").setValue(getStatus(box3Switch));
                firebaseDatabase.getReference().child("Reminders").child(""+reminderId).child("Box_4").setValue(getStatus(box4Switch));
                firebaseDatabase.getReference().child("Reminders").child(""+reminderId).child("Box_5").setValue(getStatus(box5Switch));
                firebaseDatabase.getReference().child("Reminders").child(""+reminderId).child("Box_6").setValue(getStatus(box6Switch));


                startActivity(new Intent(AddEditReminder.this, ReminderView.class));
                finish();
            }
        });
    }

    private int getStatus(Switch s){
        if (s.isChecked()){
            status = 1;
        }else {
            status = 0;
        }
        return status;
    }

    private void setStatus(Switch s, String val){
        if (Integer.parseInt(val) == 1){
            s.setChecked(true);
        }else {
            s.setChecked(false);
        }
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
                startActivity(new Intent(AddEditReminder.this, Login.class));
                finish();
                break;

            case R.id.item2id:
                Intent intent = new Intent(AddEditReminder.this, HomeActivity.class);
                startActivity(intent);
                break;


        }

        return super.onOptionsItemSelected(item);
    }

}
