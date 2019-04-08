package com.example.martin.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class post extends AppCompatActivity {


    private ImageView image;
    private TextView  title;
    private TextView  price;
    private TextView  description;
    private TextView  city;
    private TextView  phone;
    private TextView  guarante;
    private TextView  categor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        image =findViewById(R.id.show_image);
        title =findViewById(R.id.title);
        price =findViewById(R.id.post_price);
        description=findViewById(R.id.post_description);
        city = findViewById(R.id.post_city);
        phone =findViewById(R.id.post_phone);
        guarante =findViewById(R.id.post_guarant);
        categor=findViewById(R.id.post_category);


        Bundle b=getIntent().getExtras();
        String cit =b.getString("city");
        String tit =b.getString("title");
        String des =b.getString("description");
        String pho =b.getString("phone");
        String gua =b.getString("guarante");
        double pri =b.getDouble("price");
        String category =b.getString("category");
        String photo_path= b.getString("key");


        city.setText(cit);
        title.setText(tit);
        description.setText(des);
        phone.setText(pho);
        price.setText(pri+" ");
        categor.setText(category);
        if(gua!=null){
            guarante.setText(gua);
        }else{
            guarante.setActivated(false);
            guarante.setVisibility(TextView.INVISIBLE);
        }

        boolean check_photo =b.getBoolean("chek_photo");
        if(check_photo){
            StorageReference mstor = FirebaseStorage.getInstance().getReference();
            mstor.child(photo_path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(post.this).load(uri).into(image);
                }
            });
        }else
            show_default_image(category );


    }

    private void show_default_image(String category) {
        switch (category){
            case "Clothes" : {
                image.setImageResource(R.drawable.cloth);
                break;
            }case "Accessories" :{
                image.setImageResource(R.drawable.shoping);
                break;
            }case "Mobile and Accessories" :{
                image.setImageResource(R.drawable.mobile1);
                break;
            }case "Cars and Accessories" :{
                image.setImageResource(R.drawable.car1);
                break;
            }case "Baby paradies" :{
                image.setImageResource(R.drawable.baby1);
                break;
            }case "Antiques" :{
                image.setImageResource(R.drawable.antiq);
                break;
            }case "Electronic" :{
                image.setImageResource(R.drawable.electro);
                break;
            }case "Book" :{
                image.setImageResource(R.drawable.book);
                break;
            }case "Pets" :{
                image.setImageResource(R.drawable.pet);
                break;
            }case "Sports" :{
                image.setImageResource(R.drawable.sport);
                break;
            }case "Building" :{
                image.setImageResource(R.drawable.build);
                break;
            }case "Music" :{
                image.setImageResource(R.drawable.music1);
                break;
            }case "tools" :{
                image.setImageResource(R.drawable.tool);
                break;
            }case "other" :{
                image.setImageResource(R.drawable.questionpng);
                break;
            }
        }
    }

    public void menu(View view) {
        Intent inte = new Intent(post.this,MainActivity.class);
        startActivity(inte);
    }
}
