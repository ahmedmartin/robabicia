package com.example.martin.myapplication;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.Instrumentation;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//import droidninja.filepicker.FilePickerBuilder;
//import droidninja.filepicker.FilePickerConst;

//import droidninja.filepicker.FilePickerBuilder;
//import droidninja.filepicker.FilePickerConst;

public class new_post extends AppCompatActivity {

    private EditText title;
    private Spinner type;
    private Spinner category;
    private Spinner gender;
    private EditText description;
    private EditText price;
   // private ListView lv;
    
    private EditText guarant;
    private String show_type;
    private String show_gender;
    private String show_category;
    //private ArrayList<String> filepath =new ArrayList<>();
    private FirebaseStorage fstor;
    private StorageReference mstor;
    private Uri ur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        fstor  =FirebaseStorage.getInstance();
        mstor = fstor.getReference();
        title = findViewById(R.id.post_title);
        type = findViewById(R.id.rent_sele);
        category = findViewById(R.id.category);
        gender = findViewById(R.id.gender);
        description = findViewById(R.id.descrip);
        price = findViewById(R.id.price);
        guarant=findViewById(R.id.guarante);
        //lv = findViewById(R.id.product_image_list);


        ArrayAdapter<String> typ = new ArrayAdapter<String>(this, R.layout.spinner_item, Arrays.asList(getResources().getStringArray(R.array.rent_buy)));
        typ.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(typ);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                show_type = parent.getItemAtPosition(position).toString();
                if(show_type.equals("SALE")){
                    guarant.setEnabled(false);
                    guarant.setVisibility(TextView.INVISIBLE);
                }else{
                    guarant.setEnabled(true);
                    guarant.setVisibility(TextView.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> cate = new ArrayAdapter<String>(this, R.layout.spinner_item, Arrays.asList(getResources().getStringArray(R.array.category)));
        cate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(cate);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                show_category = parent.getItemAtPosition(position).toString();
                if (show_category.equals("Clothes") || show_category.equals("Baby paradies")||show_category.equals("Accessories")) {
                    gender.setEnabled(true);
                    gender.setVisibility(Spinner.VISIBLE);
                }else {
                    gender.setEnabled(false);
                    gender.setVisibility(Spinner.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<String> bo_gi = new ArrayAdapter<String>(this, R.layout.spinner_item, Arrays.asList(getResources().getStringArray(R.array.boy_girl)));
        bo_gi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(bo_gi);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                show_gender = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

     Date();
    }


    String city;
     String tit;
     String des;
     String pri;
     String phone;
     String gua;
    public void post(View view) {
         tit = title.getText().toString();
         des = description.getText().toString();
         pri = price.getText().toString();

        if(guarant.isEnabled()) {
           gua  = guarant.getText().toString();
        }else{
            gua=null;
        }
        if (TextUtils.isEmpty(tit) || TextUtils.isEmpty(des) || TextUtils.isEmpty(pri) || (gender.isEnabled() && show_gender.equals("Gender"))
                ||( guarant.isEnabled()&& TextUtils.isEmpty(gua) )|| show_category.equals("Category") || show_type.equals("Type"))
            Toast.makeText(this, "should fill all fields", Toast.LENGTH_LONG).show();
        else {
            FirebaseAuth mauth = FirebaseAuth.getInstance();
            FirebaseUser carr = mauth.getCurrentUser();
            final String uid = carr.getUid().toString();

              DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user").child(uid);
               ref.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                     user us=  dataSnapshot.getValue(user.class);
                        city = us.city;
                        phone= us.phone;
                       pro = new products(Double.parseDouble(pri), tit, gua, des, uid,fdate,ldate,chek_photo);

                     DatabaseReference  mref=FirebaseDatabase.getInstance().getReference().child(show_category).child(city);
                       DatabaseReference re ;
                       if (gender.isEnabled()) {
                           re=   mref.child(show_gender).child(show_type).child(uid);
                       }else {
                           re = mref.child(show_type).child(uid);
                       }
                          check_post(re,tit,des,pro);

                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });


        }
    }

    products pro;
   boolean chek_photo = false;
    public void photo(View view) {
     //Intent inte=new Intent(Intent.ACTION_GET_CONTENT);
     Intent inte=new Intent(Intent.ACTION_PICK);
     inte.setType("image/*");
     startActivityForResult(inte,gla);
    }
    int gla=2;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==gla&&resultCode==RESULT_OK) {
            ur = data.getData();
          ImageView image = findViewById(R.id.post_image);
          Picasso.with(new_post.this).load(ur).into(image);
            chek_photo=true;
           /* StorageReference pRef = mstor.child("images").child(ur.getLastPathSegment());
            pRef.putFile(ur).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(new_post.this, " photo uploaded ", Toast.LENGTH_SHORT).show();
                }
            }); */
        }
    }

    void send_photo (String path){
       final Intent show_post=new Intent(this,post.class);
       final Bundle b =new Bundle();
        b.putString("city",city);
        b.putString("title",tit);
        b.putDouble("price",Double.parseDouble(pri));
        b.putString("description",des);
        b.putString("phone",phone);
        b.putString("guarante",gua);
        b.putBoolean("chek_photo",chek_photo);
        b.putString("category",show_category);
        b.putString("key",path);

       final ProgressDialog mpr=new ProgressDialog(this);
        mpr.setMessage("Uploaded.....");
        mpr.show();
        if (chek_photo&&ur!=null) {
            StorageReference pRef = mstor.child(path);
            UploadTask up = pRef.putFile(ur);
            up.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(new_post.this, " photo uploaded ", Toast.LENGTH_SHORT).show();

                    // show_post.putExtras(b);
                    // startActivity(show_post);
                    mpr.dismiss();
                    show_post.putExtras(b);
                    startActivity(show_post);
                }
            });

        }else{
            mpr.dismiss();
            show_post.putExtras(b);
            startActivity(show_post);
        }



    }

    String fdate;
    String ldate;
    void Date (){
        Date fdt= new Date();
        SimpleDateFormat f= new SimpleDateFormat("dd-MM-yy");
         fdate =f.format(fdt).toString();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fdt);
        cal.add(Calendar.MONTH, 1);
        Date ldt= cal.getTime();
         ldate =f.format(ldt).toString();
    }

    String key;


    void check_post(final DatabaseReference mref, String title, final String description,final products pro){
       key= mref.push().getKey();
        send_photo(key);
        mref.child(key).setValue(pro);
        //photo_key(mref);
        Toast.makeText(new_post.this, "post uploaded ", Toast.LENGTH_LONG).show();
    }


}
