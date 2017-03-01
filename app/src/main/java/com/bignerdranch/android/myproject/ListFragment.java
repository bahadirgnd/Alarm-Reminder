package com.bignerdranch.android.myproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class ListFragment extends Fragment {
    SQLiteHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    SQLiteListAdapter ListAdapter1;

    ArrayList<String> ID_ArrayList = new ArrayList<String>();
    ArrayList<String> NAME_ArrayList = new ArrayList<String>();
    ArrayList<String> ALARMTYPE_ArrayList = new ArrayList<String>();
    ArrayList<String> NOTIFICATION_ArrayList = new ArrayList<String>();
    ArrayList<String> RINGTONE_ArrayList = new ArrayList<String>();
    ArrayList<String> ALARMTIMING_ArrayList = new ArrayList<String>();
    ArrayList<String> ALARMDATE_ArrayList = new ArrayList<String>();
    ListView LISTVIEW;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.fragment_list, container, false);
        LISTVIEW = (ListView) mview.findViewById(R.id.listView1);
        SQLITEHELPER = new SQLiteHelper(getContext());
        SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();

        cursor = SQLITEDATABASE.rawQuery("SELECT * FROM demo", null);

        ID_ArrayList.clear();
        NAME_ArrayList.clear();
        ALARMTYPE_ArrayList.clear();
        NOTIFICATION_ArrayList.clear();
        RINGTONE_ArrayList.clear();
        ALARMTIMING_ArrayList.clear();
        ALARMDATE_ArrayList.clear();

        if (cursor.moveToFirst()) {
            do {
                ID_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COL1)));

                NAME_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COL2)));

                ALARMTYPE_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COL3)));

                NOTIFICATION_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COL4)));

                RINGTONE_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COL5)));

                ALARMTIMING_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COL6)));

                ALARMDATE_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COL7)));
            } while (cursor.moveToNext());
        }


        ListAdapter1 = new SQLiteListAdapter(getContext(),

                ID_ArrayList,
                NAME_ArrayList,
                ALARMTYPE_ArrayList,
                NOTIFICATION_ArrayList,
                RINGTONE_ArrayList,
                ALARMTIMING_ArrayList,
                ALARMDATE_ArrayList

        );

        LISTVIEW.setAdapter(ListAdapter1);

        cursor.close();
        // Inflate the layout for this fragment
        return mview;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);


    }

}
