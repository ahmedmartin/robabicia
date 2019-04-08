package com.example.martin.myapplication;

import android.net.Uri;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by Ahmed_Martin on 30-Mar-18.
 */

public class products {


    public products() {

    }
    double price;
    String title;
    String guarante;
    String description;
    String fdate;
    String ldate;
    boolean check_photo;
    String uid;   //user id  b7tago 3l arag3 el data bta3t el user mn el Firedatabase

    public products(double price, String title,  String guarante, String description,String uid,String fdate,String ldate,boolean check_photo) {
        this.price = price;
        this.title = title;
        this.guarante = guarante;
        this.description = description;
        this.fdate=fdate;
        this.ldate=ldate;
        this.uid=uid;
        this.check_photo =check_photo;
    }
}
