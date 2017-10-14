package com.example.android.mytodo;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by shruti on 27-09-2017.
 */


public class TimePickerFragment extends DialogFragment implements android.app.TimePickerDialog.OnTimeSetListener {
public int hrs_of_day = 0;
    int minutes = 0;
    String time="";
    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
    //    dispTime  = (EditText) findViewById();
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new android.app.TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
     String time = hourOfDay+":"+minute;



    }
}