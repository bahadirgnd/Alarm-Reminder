package com.bignerdranch.android.myproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by hakyu on 12/2/2017.
 */

public class SQLiteListAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> UserID;
    ArrayList<String> UserName;
    ArrayList<String> UserAlarmtype;
    ArrayList<String> UserNotification ;
    ArrayList<String> UserRingtone;
    ArrayList<String> UserAlarmtiming;
    ArrayList<String> UserAlarmDate;

    public SQLiteListAdapter(
            Context context2,
            ArrayList<String> id,
            ArrayList<String> name,
            ArrayList<String> alarmtype,
            ArrayList<String> notification,
            ArrayList<String> ringtone,
            ArrayList<String> alarmtiming,
            ArrayList<String> alarmdate
    )
    {

        this.context = context2;
        this.UserID = id;
        this.UserName = name;
        this.UserAlarmtype = alarmtype;
        this.UserNotification = notification ;
        this.UserRingtone = ringtone;
        this.UserAlarmtiming = alarmtiming;
        this.UserAlarmDate = alarmdate;
    }

    public int getCount() {
        return UserID.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View child, ViewGroup parent) {

        Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.listviewdatalayout, null);

            holder = new Holder();

            holder.textviewid = (TextView) child.findViewById(R.id.textViewID);
            holder.textviewname = (TextView) child.findViewById(R.id.textViewNAME);
            holder.textviewalarmtype = (TextView) child.findViewById(R.id.textViewalarmtype);
            holder.textviewnotification = (TextView) child.findViewById(R.id.textViewnotification);
            holder.textviewringtone = (TextView) child.findViewById(R.id.textViewringtone);
            holder.textviewalarmtiming = (TextView) child.findViewById(R.id.textViewalarmtiming);
            holder.textviewalarmdate = (TextView) child.findViewById(R.id.textViewalarmdate);

            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }
        holder.textviewid.setText(UserID.get(position));
        holder.textviewname.setText(UserName.get(position));
        holder.textviewalarmtype.setText(UserAlarmtype.get(position));
        holder.textviewnotification.setText(UserNotification.get(position));
        holder.textviewringtone.setText(UserRingtone.get(position));
        holder.textviewalarmtiming.setText(UserAlarmtiming.get(position));
        holder.textviewalarmdate.setText(UserAlarmDate.get(position));
        return child;
    }

    public class Holder {
        TextView textviewid;
        TextView textviewname;
        TextView textviewalarmtype;
        TextView textviewnotification;
        TextView textviewringtone;
        TextView textviewalarmtiming;
        TextView textviewalarmdate;
    }



}
