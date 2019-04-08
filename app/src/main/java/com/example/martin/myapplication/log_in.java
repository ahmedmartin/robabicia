package com.example.martin.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class log_in extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView email;
    private TextView password;
    private Intent main;
    private Intent signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mAuth = FirebaseAuth.getInstance();
        email=(TextView)findViewById(R.id.email);
        password=(TextView)findViewById(R.id.pass);
        main=new Intent(this,MainActivity.class);
        signin=new Intent(this,sign_up.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
         if(currentUser!=null &&currentUser.isEmailVerified())
            startActivity(main);
    }



    public void login(View view) {
       final  ProgressDialog mpr =new ProgressDialog(this);
        mpr.setMessage("Wait.....");
        String em=email.getText().toString();
        String pas=password.getText().toString();
        if(TextUtils.isEmpty(em)||TextUtils.isEmpty(pas)){
            Toast.makeText(log_in.this, " yor Email & Password should fill",
                    Toast.LENGTH_SHORT).show();
        }else {
            mpr.show();
            mAuth.signInWithEmailAndPassword(em, pas)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mpr.dismiss();
                                startActivity(main);
                            } else {
                                mpr.dismiss();
                                Toast.makeText(log_in.this, "Signin failed check yor Email & Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void signin(View view) {
        startActivity(signin);

    }
}
