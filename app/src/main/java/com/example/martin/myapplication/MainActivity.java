package com.example.martin.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    String category []={
            "Clothes","Accessories","Mobile & Accessories","Cars & Accessories","Baby paradies","Antiques",
            "Electronic","Book","Pets","Sports","Building","Music","tools","other"};

    Integer [] image={R.drawable.clothes,R.drawable.accessories,R.drawable.mobile,R.drawable.car,R.drawable.baby,R.drawable.antiques,
            R.drawable.electronic,R.drawable.books,R.drawable.animals,R.drawable.sports,R.drawable.buildspng,
            R.drawable.music,R.drawable.tools,R.drawable.other};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv= (ListView)findViewById(R.id.list);
        CustomListAdapter adapter=new CustomListAdapter(this,category , image);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (+position) {
                   case 0 : {
                     Intent   it = new Intent(MainActivity.this,clothes.class);
                           startActivity(it);
                           break;
                   }
                    case 1 :{
                        Intent   it = new Intent(MainActivity.this,mobile.class);
                        startActivity(it);
                        break;
                    }
                    case 2 :{
                        Intent   it = new Intent(MainActivity.this,car.class);
                        startActivity(it);
                        break;
                    }
                    case 3 :{
                        Intent   it = new Intent(MainActivity.this,baby.class);
                        startActivity(it);
                        break;
                    }
                    case 4: {
                        Intent   it = new Intent(MainActivity.this,antiques.class);
                        startActivity(it);
                        break;
                    }
                    case 5:{
                        Intent   it = new Intent(MainActivity.this,electronic.class);
                        startActivity(it);
                        break;
                    }
                    case 6 : {
                        Intent   it = new Intent(MainActivity.this,book.class);
                        startActivity(it);
                        break;
                    }
                    case 7: {
                        Intent   it = new Intent(MainActivity.this,pets.class);
                        startActivity(it);
                        break;
                    }
                    case 8 :{
                        Intent   it = new Intent(MainActivity.this,sports.class);
                        startActivity(it);
                        break;
                    }
                    case 9:{
                        Intent   it = new Intent(MainActivity.this,build.class);
                        startActivity(it);
                        break;
                    }
                    case 10 :{
                        Intent   it = new Intent(MainActivity.this,music.class);
                        startActivity(it);
                        break;
                    }
                    case 11:{
                        Intent   it = new Intent(MainActivity.this,tool.class);
                        startActivity(it);
                        break;
                    }
                    case 12:{
                        Intent   it = new Intent(MainActivity.this,other.class);
                        startActivity(it);
                        break;
                    }
                }

            }
        });
    }

    public void profile(View view) {
        Intent inte =new Intent(MainActivity.this,profile.class);
        startActivity(inte);
    }
}

 class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;

    public CustomListAdapter(Activity context, String[] itemname, Integer[] imgid) {
        super(context, R.layout.product, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
    }

    public View getView(int position,View view,ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.product, null,true);

        TextView cat = (TextView) rowView.findViewById(R.id.category);
        ImageView image = (ImageView) rowView.findViewById(R.id.image);

            image.setImageResource(imgid[position]);
            cat.setText(itemname[position]);
            return rowView;


    }
}