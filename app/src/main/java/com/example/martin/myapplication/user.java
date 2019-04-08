package com.example.martin.myapplication;

/**
 * Created by Ahmed_Martin on 26-Mar-18.
 */

public class user {
    public user() {
    }

    String lname;
    String fname;
    String addres;
    String city;
    String phone;
    int day;
    int month;
    int year;

    public user(String addres, String city, int day, String fname,String lname, int month,String phone, int year) {
        this.lname = lname;
        this.fname = fname;
        this.addres = addres;
        this.city = city;
        this.phone = phone;
        this.day = day;
        this.month = month;
        this.year = year;
    }


}
