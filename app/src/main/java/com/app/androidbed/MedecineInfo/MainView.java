package com.app.androidbed.MedecineInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.app.androidbed.AndroidFSR.PositionActivity;
import com.app.androidbed.Authentication.Login;
import com.app.androidbed.HomeActivity;
import com.app.androidbed.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainView extends AppCompatActivity {

    Button reminderButton, medicineButton, medicineInfoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_home);

        reminderButton = (Button)findViewById(R.id.Btn1);
        medicineButton = (Button)findViewById(R.id.Btn2);
        medicineInfoBtn = (Button)findViewById(R.id.btnMedinfo);

        medicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainView.this, MedecineBoxSwitches.class);
                startActivity(intent);
            }
        });

        reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainView.this, ReminderView.class));
            }
        });

        medicineInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainView.this,MedecineInfo.class));
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
                startActivity(new Intent(MainView.this, Login.class));
                finish();
                break;

            case R.id.item2id:
                Intent intent = new Intent(MainView.this, HomeActivity.class);
                startActivity(intent);
                break;


        }

        return super.onOptionsItemSelected(item);
    }
}






