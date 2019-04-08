package com.example.martin.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    TextView city;
    TextView fname;
    TextView lname;
    TextView adress;
    TextView phone;
    TextView email;
    TextView day;
    TextView month;
    TextView year;
    user us;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        city = findViewById(R.id.profile_city);
        fname= findViewById(R.id.profile_fname);
        lname= findViewById(R.id.profile_lname);
        adress= findViewById(R.id.profile_address);
        phone =findViewById(R.id.profile_phone);
        email =findViewById(R.id.profile_email);
        day   =findViewById(R.id.day);
        month =findViewById(R.id.month);
        year  =findViewById(R.id.year);

        final ProgressDialog mpro= new ProgressDialog(this);
        mpro.setMessage("Downloading......");
        mpro.show();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        email.setText(currentUser.getEmail());

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user").child(currentUser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                  us =dataSnapshot.getValue(user.class);
                 city.setText(us.city);
                 fname.setText(us.fname);
                 lname.setText(us.lname);
                 adress.setText(us.addres);
                 phone.setText(us.phone);
                 day.setText(us.day+" ");
                 month.setText(us.month+" ");
                 year.setText(us.year+" ");
                 mpro.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void edit(View view) {
        Intent inte = new Intent(profile.this,edit_profile.class);
        Bundle b=new Bundle();
        b.putString("city",us.city);
        b.putString("phone",us.phone);
        b.putString("email",currentUser.getEmail());
        b.putString("fname",us.fname);
        b.putString("lname",us.lname);
        b.putInt("day",us.day);
        b.putInt("month",us.month);
        b.putInt("year",us.year);
        b.putString("address",us.addres);
        b.putString("uid",currentUser.getUid());
        inte.putExtras(b);
        startActivity(inte);
    }
}
