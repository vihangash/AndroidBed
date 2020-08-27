package com.app.androidbed.MedecineInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class MedecineInfo extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;

    EditText box1,box2,box3,box4,box5,box6;
    Button updatebutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medecine_info);

        box1 = (EditText) findViewById(R.id.editTextBox1);
        box2 = (EditText) findViewById(R.id.editTextBox2);
        box3 = (EditText) findViewById(R.id.editTextBox3);
        box4 = (EditText) findViewById(R.id.editTextBox4);
        box5 = (EditText) findViewById(R.id.editTextBox5);
        box6 = (EditText) findViewById(R.id.editTextBox6);

        updatebutton = (Button) findViewById(R.id.button_update);

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Medecine_Information");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                box1.setText(dataSnapshot.child("Box1").getValue().toString());
                box2.setText(dataSnapshot.child("Box2").getValue().toString());
                box3.setText(dataSnapshot.child("Box3").getValue().toString());
                box4.setText(dataSnapshot.child("Box4").getValue().toString());
                box5.setText(dataSnapshot.child("Box5").getValue().toString());
                box6.setText(dataSnapshot.child("Box6").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMedecineInfo(box1.getText().toString(),box2.getText().toString(),box3.getText().toString(),box4.getText().toString(),
                        box5.getText().toString(),box6.getText().toString());
                startActivity(new Intent(MedecineInfo.this, MainView.class));
                finish();
            }
        });


    }

    private void addMedecineInfo(String b1,String b2,String b3,String b4,String b5,String b6){
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("Medecine_Information").child("Box1").setValue(b1);
        firebaseDatabase.getReference().child("Medecine_Information").child("Box2").setValue(b2);
        firebaseDatabase.getReference().child("Medecine_Information").child("Box3").setValue(b3);
        firebaseDatabase.getReference().child("Medecine_Information").child("Box4").setValue(b4);
        firebaseDatabase.getReference().child("Medecine_Information").child("Box5").setValue(b5);
        firebaseDatabase.getReference().child("Medecine_Information").child("Box6").setValue(b6);
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
                startActivity(new Intent(MedecineInfo.this, Login.class));
                finish();
                break;

            case R.id.item2id:
                Intent intent = new Intent(MedecineInfo.this, HomeActivity.class);
                startActivity(intent);
                break;


        }

        return super.onOptionsItemSelected(item);
    }
}

