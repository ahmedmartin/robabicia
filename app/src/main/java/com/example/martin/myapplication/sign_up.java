package com.example.martin.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class sign_up extends AppCompatActivity {


    Spinner city;
    TextView pass;
    TextView address;
    TextView phone;
    TextView email;
    TextView fname;
    TextView lname;
    DatePicker dp;
    FirebaseAuth mauth;
    FirebaseDatabase mdata;
    DatabaseReference ref;
    Intent main;
    Intent log;
    ArrayAdapter cityadab;
   // Calendar cal;

   // DatePickerDialog.OnDateSetListener dp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email=(TextView)findViewById(R.id.email);
        pass= (TextView)findViewById(R.id.pass);
        address=(TextView)findViewById(R.id.address);
        phone=(TextView)findViewById(R.id.phone);
        fname=(TextView)findViewById(R.id.fname);
        lname=(TextView)findViewById(R.id.lname);
        city =findViewById(R.id.city);
        dp=(DatePicker)findViewById(R.id.date);
        mauth=FirebaseAuth.getInstance();
        main=new Intent(this,MainActivity.class);
        log= new Intent(this,log_in.class);
        mdata = FirebaseDatabase.getInstance();
        ref=mdata.getReference("user");
        List<String> l= Arrays.asList(getResources().getStringArray(R.array.government));
        cityadab=new ArrayAdapter(this,R.layout.spinner_item, l);
        cityadab.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(cityadab);
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                ci = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    String fn,ln,add,ci,em,pas,ph;
    public void sign(View view) {
         fn=fname.getText().toString();
         ln=lname.getText().toString();
         add=address.getText().toString();
        // ci=city.getText().toString();
         em=email.getText().toString();
         pas=pass.getText().toString();
         ph=phone.getText().toString();
        if(TextUtils.isEmpty(fn)||TextUtils.isEmpty(ln)||TextUtils.isEmpty(add)||
           ci.equals("City")||TextUtils.isEmpty(em)||TextUtils.isEmpty(pas)||TextUtils.isEmpty(ph)) {
            Toast.makeText(sign_up.this, "fildes should all fill", Toast.LENGTH_LONG).show();
        }else{
            mauth.createUserWithEmailAndPassword(em,pas) .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                       FirebaseAuth mAuth= FirebaseAuth.getInstance();
                        FirebaseUser currentUser = mauth.getCurrentUser();
                        String uid= currentUser.getUid().toString();
                        user us=new user(add,ci,dp.getDayOfMonth(),fn,ln,dp.getMonth()+1,ph,dp.getYear());
                        ref.child(uid).setValue(us);
                        startActivity(main);
                    } else {
                       Toast.makeText(sign_up.this, "error not connected", Toast.LENGTH_LONG).show();
                   }
                }
            });
        }
    }

    public void log(View view) {
        startActivity(log);
    }
}
