package com.app.androidbed.MedecineInfo;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.androidbed.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ReminderAdapter extends RecyclerView.Adapter <ReminderAdapter.RemindViewHolder> {

    private ArrayList<Single_Remider_Item> mRemindList;
    private Context mContext;
    private FirebaseDatabase firebaseDatabase;
    private int box1Val, box2Val, box3Val, box4Val, box5Val, box6Val, repeatStatus, hour, min;


    public  static class RemindViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mremoveAlarm;
        public Switch alarmSwitch;

        public RemindViewHolder(View itemView){
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            mremoveAlarm = itemView.findViewById(R.id.deviceRemove);
            alarmSwitch = itemView.findViewById(R.id.alarm_switch);
        }
    }

    public ReminderAdapter(ArrayList<Single_Remider_Item> exampleList, Context context){
        mRemindList = exampleList;
        mContext = context;
    }

    @NonNull
    @Override
    public RemindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        RemindViewHolder evh = new RemindViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final RemindViewHolder holder, int position) {

        final Single_Remider_Item currentItem = mRemindList.get(position);
        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mTextView1.setText(currentItem.getmText1());
        holder.mTextView2.setText(currentItem.getMtext2());

        firebaseDatabase = FirebaseDatabase.getInstance();

        //to itemclick event
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, AddEditReminder.class).putExtra("re_id",currentItem.getReminderId()));
            }
        });

        //to remove button click event
        holder.mremoveAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Delete ReminderView");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int reqCode = Integer.parseInt(currentItem.getReminderId().trim());
                        removeReminder(reqCode,"ReminderView delete..");
                        firebaseDatabase.getReference().child("Reminders").child(currentItem.getReminderId()).removeValue();
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        //alarm switch event
        holder.alarmSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = firebaseDatabase.getReference().child("Reminders").child(currentItem.getReminderId());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String time = dataSnapshot.child("Hour_Min").getValue().toString();
                        repeatStatus = Integer.parseInt(dataSnapshot.child("Repeat").getValue().toString());
                        String[] timeParts = time.split("/");
                        hour = Integer.parseInt(timeParts[0]);
                        min = Integer.parseInt(timeParts[1]);
                        box1Val = Integer.parseInt(dataSnapshot.child("Box_1").getValue().toString());
                        box2Val = Integer.parseInt(dataSnapshot.child("Box_2").getValue().toString());
                        box3Val = Integer.parseInt(dataSnapshot.child("Box_3").getValue().toString());
                        box4Val = Integer.parseInt(dataSnapshot.child("Box_4").getValue().toString());
                        box5Val = Integer.parseInt(dataSnapshot.child("Box_5").getValue().toString());
                        box6Val = Integer.parseInt(dataSnapshot.child("Box_6").getValue().toString());

                        //Log.d("smartBed", timeParts[0]+" "+timeParts[1]+" "+repeatStatus);

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, hour);
                        calendar.set(Calendar.MINUTE, min);
                        calendar.set(Calendar.SECOND,0);

                        if (holder.alarmSwitch.isChecked()){
                            AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
                            Intent intent = new Intent(mContext, AlertReceiver.class);
                            intent.putExtra("box1Sw",box1Val);
                            intent.putExtra("box2Sw",box2Val);
                            intent.putExtra("box3Sw",box3Val);
                            intent.putExtra("box4Sw",box4Val);
                            intent.putExtra("box5Sw",box5Val);
                            intent.putExtra("box6Sw",box6Val);
                            intent.putExtra("repeatSw",repeatStatus);
                            intent.putExtra("requsetCode",Integer.parseInt(currentItem.getReminderId()));
                            intent.putExtra("repeatSw",repeatStatus);
                            intent.putExtra("alarmTime",DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime()));

                            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, Integer.parseInt(currentItem.getReminderId()), intent, PendingIntent.FLAG_UPDATE_CURRENT);

                            if (calendar.before(Calendar.getInstance())){
                                calendar.add(Calendar.DATE,1);
                            }

                            if (repeatStatus == 1){
                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
                            }else {
                                alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                            }
                            firebaseDatabase.getReference().child("Reminders").child(currentItem.getReminderId()).child("Alarm_time").setValue(DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime()));
                            Toast.makeText(mContext, "Alarm set for "+DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime()), Toast.LENGTH_SHORT).show();
                            Log.d("smartBed", "Alarm set by switch > "+"Hour : "+hour+" Minute : "+min);
                        }else {
                            int reqCode = Integer.parseInt(currentItem.getReminderId().trim());
                            firebaseDatabase.getReference().child("Reminders").child(currentItem.getReminderId()).child("Alarm_time").setValue(DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime())+" (Off)");
                            removeReminder(reqCode, "Alarm Off");
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }

                });
            }
        });

        Intent intent1 = new Intent(mContext, AlertReceiver.class);//the same as up
        boolean isWorking = (PendingIntent.getBroadcast(mContext, Integer.parseInt(currentItem.getReminderId()), intent1, PendingIntent.FLAG_NO_CREATE) != null);
                if (isWorking){
                    holder.alarmSwitch.setChecked(true);
                }else {
                    holder.alarmSwitch.setChecked(false);
                }

    }

    @Override
    public int getItemCount() {
        return mRemindList.size();
    }

    private void removeReminder(int reqCode, String text){
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext,AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, reqCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

}
