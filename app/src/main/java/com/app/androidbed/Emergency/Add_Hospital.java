package com.app.androidbed.Emergency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.androidbed.AndroidFSR.PositionActivity;
import com.app.androidbed.Authentication.Login;
import com.app.androidbed.HomeActivity;
import com.app.androidbed.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add_Hospital extends AppCompatActivity {

    private EditText edname,ednumber,edaddress;
    private Button btnupload;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__hospital);

        edname=findViewById(R.id.nameid);
        ednumber=findViewById(R.id.numverid);
        edaddress=findViewById(R.id.address);

        btnupload=findViewById(R.id.btnupload);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Hospital Details");

        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edname.getText().toString().trim().equals(""))
                {
                    Toast.makeText(Add_Hospital.this, "Enter Hospital Name", Toast.LENGTH_SHORT).show();
                }
                else if(ednumber.getText().toString().trim().equals(""))
                    {
                        Toast.makeText(Add_Hospital.this, "Enter Hospital Number", Toast.LENGTH_SHORT).show();
                    }
                else if(edaddress.getText().toString().trim().equals(""))
                    {
                        Toast.makeText(Add_Hospital.this, "Enter ", Toast.LENGTH_SHORT).show();
                    }
                else
                {
                    String key=databaseReference.push().getKey();

                    Model_Hospital_Details mhd=new Model_Hospital_Details();
                    mhd.setKey(key);
                    mhd.setHospital_name(edname.getText().toString());
                    mhd.setHospital_number(ednumber.getText().toString());
                    mhd.setAddress(edaddress.getText().toString());


                    databaseReference.child(key).setValue(mhd).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(Add_Hospital.this, "Hospital Data Added", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Add_Hospital.this,Findnearesthospital.class));
                            }
                            else
                                {
                                Toast.makeText(Add_Hospital.this, "Try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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
                startActivity(new Intent(Add_Hospital.this, Login.class));
                finish();
                break;

            case R.id.item2id:
                Intent intent = new Intent(Add_Hospital.this, HomeActivity.class);
                startActivity(intent);
                break;


        }

        return super.onOptionsItemSelected(item);
    }
}
