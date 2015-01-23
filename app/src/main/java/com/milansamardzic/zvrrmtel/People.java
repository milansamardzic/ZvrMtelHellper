package com.milansamardzic.zvrrmtel;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by ms on 12/23/14.
 */

public class People {

    String name;
    String phoneNumber;
    String phoneN;
    String favourite;
    String frekquently;
    String img;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFavourite() {
        return favourite;
    }

    public String getFrekquently() {
        return frekquently;
    }

    public String getImg() {
        return img;
    }
}
