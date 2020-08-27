package com.app.androidbed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.app.androidbed.AndroidFSR.PositionActivity;
import com.app.androidbed.Authentication.Login;
import com.app.androidbed.Emergency.emergencyhome;
import com.app.androidbed.MedecineInfo.MainView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    ImageButton positionbtn,medicinebtn,emergenctbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        positionbtn = (ImageButton) findViewById(R.id.positionBTN);
        medicinebtn=findViewById(R.id.medicineBTN);
        emergenctbtn=findViewById(R.id.emergencyBTN);

        positionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PositionActivity.class);
                startActivity(intent);
            }
        });

        medicinebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, MainView.class));
            }
        });

        emergenctbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, emergencyhome.class));
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
                startActivity(new Intent(HomeActivity.this, Login.class));
                finish();
                break;

            case R.id.item2id:
                Intent intent = new Intent(HomeActivity.this,HomeActivity.class);
                startActivity(intent);
                break;
                

        }

        return super.onOptionsItemSelected(item);
    }


}
