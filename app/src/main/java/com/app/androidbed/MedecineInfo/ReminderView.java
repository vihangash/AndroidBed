package com.app.androidbed.MedecineInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.androidbed.AndroidFSR.PositionActivity;
import com.app.androidbed.Authentication.Login;
import com.app.androidbed.HomeActivity;
import com.app.androidbed.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReminderView extends AppCompatActivity {

    private ArrayList<Single_Remider_Item> mReminderList;
    private RecyclerView mRecyclerView;
    private ReminderAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private FloatingActionButton newRemindbtn;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_reminder);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mReminderList = new ArrayList<>();

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Reminders");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mReminderList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    try {
                        String rid = snapshot.child("Remind_id").getValue().toString();
                        String time = snapshot.child("Alarm_time").getValue().toString();
                        String description = snapshot.child("Description").getValue().toString();
                        createReminderList(rid, time, description);
                    }catch (Exception e){

                    }
                }
                buildRecycleView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        newRemindbtn = findViewById(R.id.floatingActionButton_alarm);
        newRemindbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReminderView.this, AddEditReminder.class));
            }
        });
    }

    public void createReminderList(String rid, String time, String des){
        mReminderList.add(new Single_Remider_Item(rid, R.drawable.ic_alarm, time,des));
    }

    public void buildRecycleView(){
        mRecyclerView = findViewById(R.id.recyclerview_reminder);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ReminderAdapter(mReminderList,this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }


    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(this,MainView.class));
        finish();
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
                startActivity(new Intent(ReminderView.this, Login.class));
                finish();
                break;

            case R.id.item2id:
                Intent intent = new Intent(ReminderView.this, HomeActivity.class);
                startActivity(intent);
                break;


        }

        return super.onOptionsItemSelected(item);
    }
}
