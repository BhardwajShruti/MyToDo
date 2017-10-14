package com.example.android.mytodo;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import static com.example.android.mytodo.DetailActivity.RESULT_SAVE;

public class AddActivity extends AppCompatActivity {
EditText ed1,ed2;
    Button timeButton,dateButton;
  //  int position;
    int mHour,mDay,mMinute,mMonth,mYear;
    String  sHour,sDay,sMinute,sMonth,sYear;
    public static final  int RESULT_SAVE=20;
    int hrs = 0;
    int mins = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent intent = getIntent();
     //   schedules sch = (schedules)intent.getSerializableExtra(constatnts.KEY_SCHEDULE);
        ed1 = (EditText) findViewById(R.id.EditedTitle);
        ed2 = (EditText) findViewById(R.id.editedDescription);

timeButton = (Button) findViewById(R.id.time_button);
        dateButton = (Button) findViewById(R.id.date_button);
//        position = intent.getIntExtra(constatnts.KEY_POSITION,0);


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
                        mHour = hourOfDay;
                        mMinute = minute;
                        timeButton.setText(hourOfDay + ":" + minute);
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

                        dateButton.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
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
      //  Calendar calendar = Calendar.getInstance();
       ToDoOpenHelper openHelper = new ToDoOpenHelper(getApplicationContext());

        SQLiteDatabase db = openHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(constatnts.ToDo_col_title,updatedTitle);
        cv.put(constatnts.ToDo_col_desc,updatedDesc);
        cv.put(constatnts.Todo_Date,sDay+"/"+sMonth+"/"+mYear);
        cv.put(constatnts.Todo_Time,mHour+":"+mMinute+":"+"00");

      //  db.insert(constatnts.ToDo_Table_name,null,cv);

//NotificationCompat.Builder nb =  new NotificationCompat.Builder(this);




        long id = db.insert(constatnts.ToDo_Table_name,null,cv);



//        Expense expense = new Expense(titleText,amount);
        Intent result = new Intent();
        result.putExtra(constatnts.ToDo_Id,id);

        setResult(RESULT_SAVE, result);
        finish();

      //  NotificationCompat.Builder nb =new NotificationCompat().Builder(Context);
//        Intent i = new Intent();
//        i.putExtra(constatnts.KEY_TITLE,updatedTitle);
//        i.putExtra(constatnts.KEY_DISC,updatedDesc);
//        i.putExtra(constatnts.KEY_POSITION,position);
//        setResult(RESULT_SAVE,i);
//        finish();


    }
}

