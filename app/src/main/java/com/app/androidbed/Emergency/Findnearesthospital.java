package com.app.androidbed.Emergency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

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
import java.util.List;

public class Findnearesthospital extends AppCompatActivity implements Adapter_emergency.Onitemclicklistener {

    private FloatingActionButton fab;
    private ProgressBar progressBar;

    private RecyclerView recyclerView;
    private List<Model_Hospital_Details> mlist;
    private Adapter_emergency adapter;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findnearesthospital);

        fab=findViewById(R.id.fab);
        progressBar=findViewById(R.id.progressbarid);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Hospital Details");

        recyclerView=findViewById(R.id.recyclerviewid);
        recyclerView.setHasFixedSize(true);

        recyclerView=findViewById(R.id.recyclerviewid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mlist=new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mlist.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Model_Hospital_Details mub=dataSnapshot1.getValue(Model_Hospital_Details.class);
                    mlist.add(mub);
                }

                adapter=new Adapter_emergency(Findnearesthospital.this,mlist);
                recyclerView.setAdapter(adapter);

                adapter.setOnitemclicklistener(Findnearesthospital.this);
                progressBar.setVisibility(View.INVISIBLE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Findnearesthospital.this,Add_Hospital.class));
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
                startActivity(new Intent(Findnearesthospital.this, Login.class));
                finish();
                break;

            case R.id.item2id:
                Intent intent = new Intent(Findnearesthospital.this,HomeActivity.class);
                startActivity(intent);
                break;


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void call_hospital(int position) {
        Model_Hospital_Details m=mlist.get(position);

        String phonenumber=m.getHospital_number();
        dialContactPhone(phonenumber);

    }

    private void dialContactPhone(String phonenumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phonenumber, null)));
    }
}
