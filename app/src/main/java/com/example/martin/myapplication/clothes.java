package com.example.martin.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class clothes extends AppCompatActivity  {


    ListView list;
    Spinner city_cloth;
    Spinner b_g_clothe;
    Spinner re_bu_cloth;
    DatabaseReference db_r;
    ArrayAdapter <String> cityadab;
    ArrayAdapter <String> bo_gi_adab;
    ArrayAdapter<String> re_bu_adab;
    String city_show,gender_show,tybe_show;
    ArrayList <Uri>  image = new ArrayList<>();
    ArrayList <products> product =new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> key =new ArrayList<>();
    ArrayList<String> phone = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);

        b_g_clothe=findViewById(R.id.b_g_clothe);
        city_cloth=findViewById(R.id.city_cloth);
        re_bu_cloth=findViewById(R.id.re_ba_clothe);





          // gender spinner
        bo_gi_adab=new ArrayAdapter(this,android.R.layout.simple_spinner_item, Arrays.asList(getResources().getStringArray(R.array.boy_girl)));
        bo_gi_adab.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        b_g_clothe.setAdapter(bo_gi_adab);
        b_g_clothe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                gender_show= parent.getItemAtPosition(position).toString();
                    show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
         //rent _ buy spinner
        re_bu_adab=new ArrayAdapter(this,android.R.layout.simple_spinner_item, Arrays.asList(getResources().getStringArray(R.array.rent_buy)));
        re_bu_adab.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        re_bu_cloth.setAdapter(re_bu_adab);
        re_bu_cloth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                tybe_show = parent.getItemAtPosition(position).toString();
                show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // city spinner
        cityadab=new ArrayAdapter(this,android.R.layout.simple_spinner_item, Arrays.asList(getResources().getStringArray(R.array.government)));
        cityadab.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city_cloth.setAdapter(cityadab);
        city_cloth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                city_show = parent.getItemAtPosition(position).toString();
                show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        list=findViewById(R.id.clothe_list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
               final Intent show_post=new Intent(clothes.this,post.class);
               final Bundle b =new Bundle();
                b.putString("city",city_show);
                b.putString("title",product.get(position).title);
                b.putDouble("price",product.get(position).price);
                b.putString("description",product.get(position).description);

                b.putString("guarante",product.get(position).guarante);
                b.putBoolean("chek_photo",product.get(position).check_photo);
                b.putString("category","Clothes");
                b.putString("key",key.get(position));
                DatabaseReference mreff=FirebaseDatabase.getInstance().getReference().child("user").child(product.get(position).uid);
                mreff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        user s= dataSnapshot.getValue(user.class);
                        b.putString("phone",s.phone);
                        show_post.putExtras(b);
                        startActivity(show_post);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });



    }

    void get_photo(String key){

        StorageReference mstor = FirebaseStorage.getInstance().getReference();
        mstor.child(key).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                 sum2++;
                 image.set(image_downloaded.get(sum2-1),uri);
                if(sum1==sum2){
                    mpr.dismiss();
                    ListAdapter adap = new ListAdapter(clothes.this, title, image);
                    list.setAdapter(adap);
                }
            }
        });
    }
      int sum1 =0;
      int sum2 =0;
      long ds_chiled=0;
      long dscount=0;
      long datacount=0;
      long data_child=0;
      long disimage=0;
      long last_iteration=0;
      ArrayList<Integer> image_downloaded =new ArrayList<>();
    ProgressDialog mpr;

    void check_disphoto(){
        if((ds_chiled==dscount)&&(data_child==0)&&(datacount==disimage)&&(sum1==0)){
            mpr.dismiss();
            ListAdapter adap = new ListAdapter(clothes.this, title, image);
            list.setAdapter(adap);
        }
    }

    void show () {
        boolean b1 = !(city_show == null || city_show.equals("City"));
        boolean b2 = !(gender_show == null || gender_show.equals("Gender"));
        boolean b3 = !(tybe_show == null || tybe_show.equals("Type"));
        if (b1 && b2 && b3) {
            mpr = new ProgressDialog(this);
            mpr.setMessage("Downloading........");
            mpr.show();
            db_r = FirebaseDatabase.getInstance().getReference().child("Clothes").child(city_show).child(gender_show).child(tybe_show);
            db_r.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                            ds_chiled=dataSnapshot.getChildrenCount();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            data_child= ds.getChildrenCount();
                                   dscount++;
                            for (DataSnapshot data : ds.getChildren()) {
                                data_child--;
                                datacount++;
                                products pro = data.getValue(products.class);
                                product.add(pro);
                                title.add(pro.title);
                                if (pro.check_photo) {
                                    String k = data.getKey().toString();
                                    Uri img= null;
                                    image.add(img);
                                    image_downloaded.add(image.size()-1);
                                    get_photo(k);
                                    sum1++;
                                } else {
                                    disimage++;
                                    check_disphoto();
                                    image.add(Uri.parse("android.resource://com.example.martin.myapplication/drawable/cloth"));
                                }
                                key.add(data.getKey());
                            }
                        }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }
    // new post putton ...
    public void new_post(View view) {
        Intent inte = new Intent(this,new_post.class);
            startActivity(inte);
    }
}


class ListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> itemname;
    private final ArrayList<Uri> imgid;

    public ListAdapter(Activity context, ArrayList<String> itemname, ArrayList<Uri> imgid) {
        super(context, R.layout.list_show, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_show, null,true);

        TextView title = (TextView) rowView.findViewById(R.id.pro_title);
        ImageView image = (ImageView) rowView.findViewById(R.id.pro_image);

        title.setText(itemname.get(position));
        Picasso.with(context).load(imgid.get(position)).into(image);
        return rowView;

    }
}
