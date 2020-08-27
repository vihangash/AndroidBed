package com.app.androidbed.MedecineInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

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

public class MedecineBoxSwitches extends AppCompatActivity {

    ToggleButton toggleButton1, toggleButton2, toggleButton3, toggleButton4, toggleButton5, toggleButton6;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_box_switches);

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        toggleButton1 = (ToggleButton)findViewById(R.id.toggleButton);
        toggleButton2 = (ToggleButton)findViewById(R.id.toggleButton1);
        toggleButton3 = (ToggleButton)findViewById(R.id.toggleButton5);
        toggleButton4 = (ToggleButton)findViewById(R.id.toggleButton2);
        toggleButton5 = (ToggleButton)findViewById(R.id.toggleButton4);
        toggleButton6 = (ToggleButton)findViewById(R.id.toggleButton3);

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Togale_Buttons");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (Integer.parseInt(dataSnapshot.child("TogaleButton1/status").getValue().toString())==1){
                    toggleButton1.setChecked(true);
                }else {
                    toggleButton1.setChecked(false);
                }
                if (Integer.parseInt(dataSnapshot.child("TogaleButton2/status").getValue().toString())==1){
                    toggleButton2.setChecked(true);
                }else {
                    toggleButton2.setChecked(false);
                }
                if (Integer.parseInt(dataSnapshot.child("TogaleButton3/status").getValue().toString())==1){
                    toggleButton3.setChecked(true);
                }else {
                    toggleButton3.setChecked(false);
                }
                if (Integer.parseInt(dataSnapshot.child("TogaleButton4/status").getValue().toString())==1){
                    toggleButton4.setChecked(true);
                }else {
                    toggleButton4.setChecked(false);
                }
                if (Integer.parseInt(dataSnapshot.child("TogaleButton5/status").getValue().toString())==1){
                    toggleButton5.setChecked(true);
                }else {
                    toggleButton5.setChecked(false);
                }
                if (Integer.parseInt(dataSnapshot.child("TogaleButton6/status").getValue().toString())==1){
                    toggleButton6.setChecked(true);
                }else {
                    toggleButton6.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        
        toggleButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton1").child("status").setValue(1);
                }else {
                    firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton1").child("status").setValue(0);
                }
            }
        });

        toggleButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton2").child("status").setValue(1);
                }else {
                    firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton2").child("status").setValue(0);
                }
            }
        });

        toggleButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton3").child("status").setValue(1);
                }else {
                    firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton3").child("status").setValue(0);
                }
            }
        });

        toggleButton4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton4").child("status").setValue(1);
                }else {
                    firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton4").child("status").setValue(0);
                }
            }
        });

        toggleButton5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton5").child("status").setValue(1);
                }else {
                    firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton5").child("status").setValue(0);
                }
            }
        });

        toggleButton6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton6").child("status").setValue(1);
                }else {
                    firebaseDatabase.getReference().child("Togale_Buttons").child("TogaleButton6").child("status").setValue(0);
                }
            }
        });
        
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
                startActivity(new Intent(MedecineBoxSwitches.this, Login.class));
                finish();
                break;

            case R.id.item2id:
                Intent intent = new Intent(MedecineBoxSwitches.this, HomeActivity.class);
                startActivity(intent);
                break;


        }

        return super.onOptionsItemSelected(item);
    }
}
