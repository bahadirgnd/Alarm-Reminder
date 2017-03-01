package com.bignerdranch.android.myproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by User on 21/02/2017.
 */

public class ViewListContents extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);


    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ViewListContents.this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
