package com.example.android.mytodo;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.nfc.Tag;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity  {



    public static final String MY_ACTION = "my_action";

    BroadcastReceiver broadcastReceiver;
    LocalBroadcastManager broadcastManager;


    ListView listView;
    CheckBox checkBox;

    ArrayList<schedules> schedule;
public  static  final int REQUEST_CODE = 1;
    public  static  final int REQUEST_CODE2 = 2;
    customAdapter adapter;

   /* @Override
    protected void onStart() {
        super.onStart();
        Log.i("activity start info","start");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("activity resume info","resume");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("activity Restart info","Restart");
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        broadcastManager = LocalBroadcastManager.getInstance(this);


        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(MainActivity.this, "Custom Broadcast", Toast.LENGTH_SHORT).show();
            }
        };

        listView = (ListView) findViewById(R.id.listView);

        schedule = new ArrayList<schedules>();
// final SharedPreferences sharedPreferences = getSharedPreferences("myToDo",MODE_PRIVATE);
//      Set set1=  sharedPreferences.getStringSet("mySet",null);
//

//        schedule = new ArrayList(set1);
//
//        int count = sharedPreferences.getInt("total",0);
//        for(int i =1;i<=count;i++) {
//            String temp  = "sch"+i+"";
//            schedule.add(sharedPreferences.getString(temp, ""));
//
//        }
//adapter.notifyDataSetChanged();

//        for(int i = 0;i<20;i++){
//            expenses.add("Expense" + i);
//        }

        ToDoOpenHelper openHelper = new ToDoOpenHelper(getApplicationContext());
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.query(constatnts.ToDo_Table_name, null, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(constatnts.ToDo_col_title));
            String desc = cursor.getString(cursor.getColumnIndex(constatnts.ToDo_col_desc));
            String dayTodo = cursor.getString(cursor.getColumnIndex(constatnts.Todo_Date));
            String timeTodo = cursor.getString(cursor.getColumnIndex(constatnts.Todo_Time));
        //    Toast.makeText(this, dayTodo, Toast.LENGTH_SHORT).show();
            long id = cursor.getLong(cursor.getColumnIndex(constatnts.ToDo_Id));
            schedules sch = new schedules(title, desc, id,dayTodo,timeTodo);
            schedule.add(sch);
        }
        adapter = new customAdapter(this, schedule);

        listView.setAdapter(adapter);
//listView.set

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete todo");
                builder.setMessage("Are you sure?");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        schedules sch = schedule.get(position);
                        long Todoid = sch.getToDoID();
                        ToDoOpenHelper openHelper = new ToDoOpenHelper(getApplicationContext());

                        SQLiteDatabase db = openHelper.getWritableDatabase();
//
                        db.delete(constatnts.ToDo_Table_name, constatnts.ToDo_Id + " = ?", new String[]{Todoid + ""});
                        schedule.remove(position);
                        adapter.notifyDataSetChanged();

                    }
                });
                builder.setNegativeButton("No", null);

                AlertDialog dialog = builder.create();
                dialog.show();

                return false;
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();

                schedules sch = schedule.get(i);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(constatnts.KEY_ID, sch.getToDoID());
                intent.putExtra(constatnts.KEY_POSITION, i);
                startActivityForResult(intent, REQUEST_CODE);

                // = (String) adapterView.getAdapter().getItem(i);
//
//
            }
        });
        sendBroadcast();
    }

        @Override
        protected void onResume () {
            super.onResume();
            IntentFilter intentFilter = new IntentFilter(MY_ACTION);
            broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
        }

    public void sendBroadcast(){

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent alarmIntent = new Intent(this,AlarmReceiver.class);
String time = "08:45:00";
        SimpleDateFormat dt = new SimpleDateFormat("hh:mm");
        Date date= null; // GET TIME HERE
        try {
            date = dt.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal=Calendar.getInstance();

        cal.setTime(date);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,alarmIntent,0);
        //alarmManager.setExact(AlarmManager.ELAPSED_REALTIME,cal.getTimeInMillis(),pendingIntent);
        //alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,cal.getTimeInMillis(),60000,pendingIntent);
        Log.i("TAG","initiated");

            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 5000,60000,pendingIntent);
       // Toast.makeText(this, "broadcast", Toast.LENGTH_SHORT).show();


//        Intent intent = new Intent(this,AlarmReceiver.class);
        //intent.setAction(MY_ACTION);
//        sendBroadcast(intent);
        //broadcastManager.sendBroadcast(intent);

    }

    @Override
    protected void onPause() {

        broadcastManager.unregisterReceiver(broadcastReceiver);
        super.onPause();


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return  true;
    }
    public void onCheck(View view){
       // checkBox = (CheckBox) view.findViewById(R.id.checkBox) ;
        Toast.makeText(this, "item is checked", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add) {

/*           AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.dialog_layout,null);

            final TextView dialogEditView = (EditText)view.findViewById(R.id.addSchedule);
            final TextView dialogEditView2 = (EditText)view.findViewById(R.id.addTitle);
builder.setView(view);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String data = dialogEditView.getText().toString();
                    String title = dialogEditView2.getText().toString();
                    schedule.add(new schedules(title,data));//                    final SharedPreferences sharedPreferences = getSharedPreferences("myToDo",MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                    Set set = new HashSet(schedule);
//                    editor.putStringSet("mySet",set);
//      editor.commit();
                    adapter.notifyDataSetChanged();

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

*/
           Intent intent  = new Intent(MainActivity.this,AddActivity.class);

            //intent.putExtra(constatnts.KEY_POSITION,schedule.size()-1);
            startActivityForResult(intent,REQUEST_CODE2);


            adapter.notifyDataSetChanged();



        }else if(id == R.id.remove){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete todo");
            builder.setMessage("Are you sure?");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    schedule.remove(schedule.size() -1);
                    adapter.notifyDataSetChanged();

                }
            });
            builder.setNegativeButton("No", null);

            AlertDialog dialog = builder.create();
            dialog.show();

        }else if(id==R.id.about){
            Intent about = new Intent();
            about.setAction(Intent.ACTION_VIEW);
            about.setData(Uri.parse("https://codingninjas.in"));
            startActivity(about);
        }
        else if(id==R.id.feedback){
//hello'
            //kllggit add -Agit commit -m "first commit"
            Intent feedback = new Intent();
            feedback.setAction(Intent.ACTION_SENDTO);
            feedback.setData(Uri.parse("mailto:"));
            feedback.putExtra(Intent.EXTRA_SUBJECT,"feedback");
            startActivity(feedback);
        }
        else if(id==R.id.camera){

            Intent camera  = new Intent();
            camera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//            feedback.setData(Uri.parse("mailto:"));
//            feedback.putExtra(Intent.EXTRA_SUBJECT,"feedback");
            startActivity(camera);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE){

            if(resultCode == DetailActivity.RESULT_SAVE){


                int position = data.getIntExtra(constatnts.KEY_POSITION,0);

                schedules sch = schedule.get(position);
                long id = sch.getToDoID();

                ToDoOpenHelper openHelper = new ToDoOpenHelper(getApplicationContext());
                SQLiteDatabase db = openHelper.getReadableDatabase();
                if(id > -1){

                    Cursor cursor = db.query(constatnts.ToDo_Table_name,null,
                            constatnts.ToDo_Id + " = ?",new String[]{id + ""}
                            ,null,null,null);

                    if(cursor.moveToFirst()) {
                        String title = cursor.getString(cursor.getColumnIndex(constatnts.ToDo_col_title));
                        String desc = cursor.getString(cursor.getColumnIndex(constatnts.ToDo_col_desc));
                        sch.setTitle(title);
                        sch.setDescription(desc);
                        adapter.notifyDataSetChanged();

                    }}






            }}
           else if(requestCode==REQUEST_CODE2) {
                if (resultCode == AddActivity.RESULT_SAVE) {
                    Toast.makeText(this, "extracting", Toast.LENGTH_SHORT).show();
                    ToDoOpenHelper openHelper = new ToDoOpenHelper(getApplicationContext());
                    SQLiteDatabase db = openHelper.getReadableDatabase();


                    long id   =  data.getLongExtra(constatnts.ToDo_Id,-1L);
                    if(id > -1){

                        Cursor cursor = db.query(constatnts.ToDo_Table_name,null,
                                constatnts.ToDo_Id + " = ?",new String[]{id + ""}
                                ,null,null,null);

                        if(cursor.moveToFirst()){
                            String title = cursor.getString(cursor.getColumnIndex(constatnts.ToDo_col_title));
                          String desc = cursor.getString(cursor.getColumnIndex(constatnts.ToDo_col_desc));
                            String dateTodo = cursor.getString(cursor.getColumnIndex(constatnts.Todo_Date));
                            String timTodo = cursor.getString(cursor.getColumnIndex(constatnts.Todo_Time));

                            schedules sch = new schedules(title,desc,id,dateTodo,timTodo);
                            schedule.add(sch);
                            adapter.notifyDataSetChanged();
                        }



        }}}


        super.onActivityResult(requestCode, resultCode, data);


    }

}
