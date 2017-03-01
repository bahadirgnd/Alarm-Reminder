package com.bignerdranch.android.myproject;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;


import static com.bignerdranch.android.myproject.R.id.textView1;

public class MainActivity extends AppCompatActivity {
    private Button FirstButton;
    private Button SecondButton;
    private Button ThirdButton;
    private Button FourthButton;

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;

    Context context = this;

    CheckBox checkBox1;
    Button buttonSetAlarm;
    Button buttonstartDateDialog;
    Button buttonstartSetDialog;
    Button buttonCancelAlarm;
    TextView textAlarmPrompt;
    TextView textAlarmDate;
    TimePickerDialog timePickerDialog;
    Ringtone ringTone;
    AlarmManager alarmManager;
    SQLiteDatabase SQLITEDATABASE;
    String SQLiteQuery;

    final static int RQS_1=0;
    private PendingIntent pending_intent;
    final int RQS_RINGTONEPICKER = 1;
    String ring_chosen = "energy"; // default ringtone chosen
    int checkboxnotif = 0; // default check box notification not checked
    int alarmtype = 1; // default single alarm type
    boolean insertData; // check whether true or false the data insertion
    int mYear, mMonth, mDay;
    boolean checkFill = true;
    Calendar calNow = Calendar.getInstance();
    Calendar calSet = (Calendar) calNow.clone();
    SQLiteDatabase database;
    SQLiteHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent myIntent = new Intent(this.context, AlarmReceiver.class);

        // Get the alarm manager service
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //TextView1 show text after user type in task name
        tv1 = (TextView) findViewById(textView1); //TextView is defined as textview in xml
        FirstButton = (Button)findViewById(R.id.FirstButton);
        FirstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Task Names: ");
                final EditText et = new EditText(context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(et);

                // set dialog message
                alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        et.getText().toString();
                        tv1.setText(et.getText().toString());
                    }
                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show alert dialog
                alertDialog.show();
            }
        });

        //TextView2 show text when user choose the radio button
        tv2=(TextView) findViewById(R.id.textView2);
        SecondButton = (Button)findViewById(R.id.SecondButton);

        SecondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog levelDialog;
                //prepare to initialize the item in the alert dialog
                final CharSequence[] items = {" Single Alarm "," Repeating alarms every 10 minutes ",
                        " Repeating alarms every 10 minutes with louder volume alarms",
                        " Repeating alarms every 5 minutes ",
                        " Repeating alarms every 5 minutes with max volume alarms "};
                RadioGroup rg = (RadioGroup)findViewById(R.id.radio_group);
                ContextThemeWrapper cw = new ContextThemeWrapper( context, R.style.AlertDialogTheme );
                AlertDialog.Builder builder = new AlertDialog.Builder(cw);
                builder.setTitle("Types of alarm");
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                tv2.setText("Single Alarm");
                                alarmtype = 1;
                                break;
                            case 1:
                                tv2.setText("Repeating alarms every 10 minutes");
                                alarmtype = 2;
                                break;
                            case 2:
                                tv2.setText("Repeating alarms every 10 minutes with louder volume alarms");
                                alarmtype = 3;
                                break;
                            case 3:
                                tv2.setText("Repeating alarms every 5 minutes");
                                alarmtype = 4;
                                break;
                            case 4:
                                tv2.setText("Repeating alarms every 5 minutes with max volume alarms");
                                alarmtype = 5;
                                break;
                        }
                        dialog.dismiss();
                    }
                });

                levelDialog = builder.create();
                levelDialog.show();

            }
        });
        tv3 = (TextView) findViewById(R.id.textView3); //TextView3 show the notification is checked or not
        ThirdButton = (Button)findViewById(R.id.ThirdButton);
        ThirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"Turn On notification "};
                checkBox1=(CheckBox)findViewById(R.id.Check_Box);
                checkboxnotif = 0;
                //pop out the alart dialog with checkbox
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this)
                        .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                switch (which) {
                                    case 0:
                                        if (isChecked) {
                                            checkboxnotif = 1;
                                            break;
                                        }
                                        else{
                                            checkboxnotif = 0;
                                            break;
                                        }
                                }
                            }
                        });
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(checkboxnotif == 1)
                            tv3.setText("Yes");
                        else if(checkboxnotif == 0)
                            tv3.setText("No");
                    }
                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show alert dialog
                alertDialog.show();
            }
        });
        tv4 = (TextView) findViewById(R.id.textView4); //TextView4 show the ringtone chosen
        FourthButton = (Button)findViewById(R.id.FourthButton);
        FourthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog levelDialog;
                final CharSequence[] items = {" energy "," firefly "," go "};

                RadioGroup rg = (RadioGroup)findViewById(R.id.radio_group);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                tv4.setText("energy");
                                ring_chosen = tv4.getText().toString();
                                break;
                            case 1:
                                tv4.setText("firefly");
                                ring_chosen = tv4.getText().toString();
                                break;
                            case 2:
                                tv4.setText("go");
                                ring_chosen = tv4.getText().toString();
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                levelDialog = builder.create();
                levelDialog.show();
            }



        });
        buttonSetAlarm = (Button)findViewById(R.id.SetAlarm);
        buttonSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Calendar cal = Calendar.getInstance();

                cal.set(cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH),
                        cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE),
                        00);

                if(calSet.compareTo(cal) <= 0){
                    //The set Date/Time already passed
                    Toast.makeText(getApplicationContext(),
                            "Invalid Date/Time",
                            Toast.LENGTH_LONG).show();
                }else{

                    DBCreate();

                    SQLiteDB();
                    if (checkFill == true) {

                        Intent intent = new Intent(context, AlarmReceiver.class);
                        if (alarmtype > 1) {
                            intent.putExtra("REPEAT_ALARM", alarmtype);
                        }
                        intent.putExtra("KEY_TONE_URL", tv1.getText().toString());
                        intent.putExtra("START", "yes");
                        intent.putExtra("CHECK_BOX", checkboxnotif);
                        intent.putExtra("RING_CHOSEN", ring_chosen);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, RQS_1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


                        AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                        int DELAY_UNTIL_NEXT_INCREASE = 30 * 1000;
                        int currentAlarmVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);

                        //set the type of alarm
                        if (alarmtype == 1) {
                            alarmManager.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pendingIntent);
                        } else if (alarmtype == 2) {
                            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(),
                                    1000 * 60 * 10, pendingIntent);
                        } else if (alarmtype == 3) {
                            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(),
                                    1000 * 60 * 10, pendingIntent);
                            if (currentAlarmVolume != audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC)) {
                                int maxVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                                float percent = 0.8f;
                                int eightyVolume = (int) (maxVolume * percent);
                                int checkVolume = (int) (currentAlarmVolume * percent);
                                if (checkVolume < eightyVolume) {
                                    audio.setStreamVolume(AudioManager.STREAM_MUSIC,
                                            eightyVolume, AudioManager.FLAG_SHOW_UI);
                                }
                            }
                        } else if (alarmtype == 4) {
                            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(),
                                    1000 * 60 * 5, pendingIntent);
                        } else if (alarmtype == 5) {
                            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(),
                                    1000 * 60 * 5, pendingIntent);
                            if (currentAlarmVolume != audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC)) { //if we havent reached the max
                                audio.setStreamVolume(AudioManager.STREAM_MUSIC,
                                        audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                                        AudioManager.FLAG_SHOW_UI);
                            }
                        }
                    }
                }
            }
        });
        //textAlarmPrompt show the trigger timing which set by user
        textAlarmPrompt = (TextView)findViewById(R.id.textView5);

        //set the trigger timing
        buttonstartSetDialog = (Button)findViewById(R.id.FifthButton);
        buttonstartSetDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textAlarmPrompt.setText("");
                openTimePickerDialog(false);
            }
        });
        //textAlarmDate show the trigger date which set by user
        textAlarmDate = (TextView)findViewById(R.id.textView6);

        //set the trigger date
        buttonstartDateDialog = (Button)findViewById(R.id.SixthButton);
        buttonstartDateDialog.setOnClickListener(new View.OnClickListener(){

             @Override
             public void onClick(View v){
                 mYear = calSet.get(Calendar.YEAR);
                 mMonth = calSet.get(Calendar.MONTH);
                 mDay = calSet.get(Calendar.DAY_OF_MONTH);

                //Create a datepicker dialog where user can choose date inside calendar
                 DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                         new DatePickerDialog.OnDateSetListener() {

                             @Override
                             public void onDateSet(DatePicker view, int year,
                                                   int monthOfYear, int dayOfMonth) {
                                 calSet.set(Calendar.YEAR, year);
                                 calSet.set(Calendar.MONTH, monthOfYear);
                                 calSet.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                 textAlarmDate.setText("***"+ dayOfMonth + "-" + (monthOfYear + 1) + "-" + year + "***");

                             }
                         }, mYear, mMonth, mDay);
                 datePickerDialog.show();
             }



         }
        );
        //stop alarm or cancel alarm
        buttonCancelAlarm = (Button)findViewById(R.id.Stop);
        buttonCancelAlarm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                cancelAlarm();
            }});


    }

    private void cancelAlarm(){

        textAlarmPrompt.setText(
                "***" + "Alarm Cancelled!" + "***");
        textAlarmDate.setText("***"
                + "Alarm Cancelled!" + "***");
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);

        intent.putExtra("START", "no");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, RQS_1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        sendBroadcast(intent);

        alarmManager.cancel(pendingIntent);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RQS_RINGTONEPICKER && resultCode == RESULT_OK){
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            ringTone = RingtoneManager.getRingtone(getApplicationContext(), uri);
            Toast.makeText(MainActivity.this,
                    ringTone.getTitle(MainActivity.this),
                    Toast.LENGTH_LONG).show();
            ringTone.play();
        }
    }


    private void openTimePickerDialog(boolean is24r){
        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(
                MainActivity.this,
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24r);
        timePickerDialog.setTitle("Set Alarm Time");
        timePickerDialog.show();
    }
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if(calSet.compareTo(calNow) <= 0){
                //Today Set time passed, count to tomorrow
                calSet.add(Calendar.DATE, 1);
            }

            textAlarmPrompt.setText(
                    "***"+ "Alarm is set@ " + calSet.getTime() + "***");

            //After alarm is set, the data will be saved in database

        }};
    public void DBCreate(){
        //create a new database and table if it is not existed
        SQLITEDATABASE = openOrCreateDatabase("DemoDataBase1", Context.MODE_PRIVATE, null);

        SQLITEDATABASE.execSQL("CREATE TABLE IF NOT EXISTS demo(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT, alarmType TEXT, notification TEXT, ringtone TEXT, alarmTiming TEXT, alarmDate TEXT);");
    }

    public void SQLiteDB(){
        //insert data to database after the user set alarm to trigger
        String taskname = tv1.getText().toString();
        String typealarm = tv2.getText().toString();
        String typenotice = tv3.getText().toString();
        String typeringtone = tv4.getText().toString();
        String alarmtiming = textAlarmPrompt.getText().toString();
        String alarmdate = textAlarmDate.getText().toString();

        if(tv1.length() != 0&&tv2.length() != 0&&tv3.length() != 0&&tv4.length() != 0&&textAlarmPrompt.length() != 0&&textAlarmDate.length() != 0){
            SQLiteQuery = "INSERT INTO demo(name,alarmType,notification,ringtone,alarmTiming,alarmDate) VALUES('"+taskname+"', '"+typealarm+"', '"+typenotice+"', '"+typeringtone+"', '"+alarmtiming+"', '"+alarmdate+"');";

            SQLITEDATABASE.execSQL(SQLiteQuery);

            Toast.makeText(MainActivity.this,"Data Submit Successfully", Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(MainActivity.this, "You must fill in all the text field!.",Toast.LENGTH_LONG).show();
            checkFill = false;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //action bar menu selection function
        switch(item.getItemId()){
            case R.id.ViewHistory:
                Intent intent = new Intent(MainActivity.this, ViewListContents.class);
                startActivity(intent);

            case R.id.action_settings:
                finish();
                System.exit(0);
            case R.id.DeleteHistory:
                dbOpenHelper = new SQLiteHelper(this);
                database = dbOpenHelper.getWritableDatabase();
                database.execSQL("DROP TABLE IF EXISTS demo");
                database.execSQL("CREATE TABLE IF NOT EXISTS demo(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "name TEXT, alarmType TEXT, notification TEXT, ringtone TEXT, alarmTiming TEXT, alarmDate TEXT);");
        }
        return true;
    }

}

