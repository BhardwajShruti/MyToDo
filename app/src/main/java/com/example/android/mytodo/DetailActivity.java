package com.example.android.mytodo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import static android.R.attr.id;

public class DetailActivity extends AppCompatActivity {
    EditText ed1;
    EditText ed2;
    Button dateB;
    Button timeB;
    int position;
    int mHour,mDay,mMinute,mMonth,mYear;
    String  sHour,sDay,sMinute,sMonth,sYear;

    int hrs = 0;
    int mins = 0;

    public static final int RESULT_SAVE = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();

        ToDoOpenHelper openHelper = new ToDoOpenHelper(getApplicationContext());
        SQLiteDatabase db = openHelper.getReadableDatabase();
        ed1 = (EditText) findViewById(R.id.EditedTitle);
        ed2 = (EditText) findViewById(R.id.editedDescription);
    dateB = (Button)findViewById(R.id.dateB);
        timeB =(Button)findViewById(R.id.timeB);
        long id   =  intent.getLongExtra(constatnts.KEY_ID,-1);
        if(id > -1){

            Cursor cursor = db.query(constatnts.ToDo_Table_name,null,
                    constatnts.ToDo_Id + " = ?",new String[]{id + ""}
                    ,null,null,null);

            if(cursor.moveToFirst()) {
                String title = cursor.getString(cursor.getColumnIndex(constatnts.ToDo_col_title));
                String desc = cursor.getString(cursor.getColumnIndex(constatnts.ToDo_col_desc));
                String date = cursor.getString(cursor.getColumnIndex(constatnts.Todo_Date));
                String time = cursor.getString(cursor.getColumnIndex(constatnts.Todo_Time));
                schedules sch = new schedules(title, desc, id,date,time);

                dateB.setText(sch.getDate());
                timeB.setText(sch.getTime());
                ed1.setText(sch.getTitle());
                ed2.setText(sch.getDescription());
                position = intent.getIntExtra(constatnts.KEY_POSITION,0);
            }}
        //schedules sch = (schedules)intent.getSerializableExtra(constatnts.KEY_SCHEDULE);



    }

    public void showTimePickerDialog(View v) { final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                            mHour  = hourOfDay;
                                mMinute = minute;
                        timeB.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
    public void showDatePickerDialog(View v){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dateB.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;

                        if((mMonth%10)==mMonth){
                            sMonth = "0"+mMonth;
                        }
                        else
                            sMonth=mMonth+"";
                        if((mDay%10)==mDay){
                            sDay = "0"+mDay;
                        }
                        else
                            sDay=mDay+"";
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    public void saveEdit(View view){
        String updatedTitle = ed1.getEditableText().toString();
        String updatedDesc = ed2.getEditableText().toString();

        ContentValues cv = new ContentValues();
        cv.put(constatnts.ToDo_col_title,updatedTitle);
        cv.put(constatnts.ToDo_col_desc,updatedDesc);
        cv.put(constatnts.Todo_Date,sDay+"/"+sMonth+"/"+mYear);
        cv.put(constatnts.Todo_Time,mHour+":"+mMinute);

        ToDoOpenHelper openHelper = new ToDoOpenHelper(getApplicationContext());
        SQLiteDatabase db = openHelper.getReadableDatabase();
Intent intent =getIntent();
        long id   =  intent.getLongExtra(constatnts.KEY_ID,-1);
        db.update(constatnts.ToDo_Table_name,cv,constatnts.ToDo_Id + " = ?",new String[]{id + ""});
        Intent i = new Intent();

        i.putExtra(constatnts.KEY_POSITION,position);
        setResult(RESULT_SAVE,i);
        finish();


    }
}
