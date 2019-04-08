package com.example.martin.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class edit_profile extends AppCompatActivity {

    private TextView fname;
    private TextView lname;
    private TextView addres;
    private TextView phone ;
    private TextView email;
    private String city;
    private String adress;
    private String ffname;
    private String llname;
    private String uid;
    private int day;
    private int month;
    private int year;
    private String phonee;
    private String emaill;
    private DatePicker date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

     Bundle b=getIntent().getExtras();
     city=b.getString("city");
     adress=b.getString("address");
     ffname=b.getString("fname");
     llname=b.getString("lname");
     day=b.getInt("day");
     month=b.getInt("month");
     year=b.getInt("year");
     phonee=b.getString("phone");
     emaill=b.getString("email");
     uid=b.getString("uid");

     date=findViewById(R.id.edit_profile_date);
     fname=findViewById(R.id.edit_profile_fname);
     lname=findViewById(R.id.edit_profile_lname);
     addres=findViewById(R.id.edit_profile_address);
     phone=findViewById(R.id.edit_profile_phone);
     email=findViewById(R.id.edit_profile_email);

     fname.setText(ffname);
     lname.setText(llname);
     addres.setText(adress);
     phone.setText(phonee);
     email.setText(emaill);
     date.init(year,month-1,day,null);

    }

    public void profile_edit(View view) {
        if(!ffname.equals(fname.getText().toString())||!llname.equals(lname.getText().toString())||!adress.equals(addres.getText().toString())
                ||!phonee.equals(phone.getText().toString())){
            user uss = new user(addres.getText().toString(),city,date.getDayOfMonth(),fname.getText().toString(),lname.getText().toString()
                    ,date.getMonth()+1,phone.getText().toString(),date.getYear());
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user").child(uid);
            ref.setValue(uss).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(edit_profile.this,"profile saved correctely",Toast.LENGTH_SHORT).show();
                    Intent inte =new Intent(edit_profile.this,profile.class);
                    startActivity(inte);
                }
            });
        }else{
            Toast.makeText(edit_profile.this,"profile not change",Toast.LENGTH_SHORT).show();
            Intent inte =new Intent(edit_profile.this,profile.class);
            startActivity(inte);
        }
    }
}
