package com.example.android.mytodo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shruti on 16-09-2017.
 */

public class ToDoOpenHelper extends SQLiteOpenHelper {

 //   private static String
    public ToDoOpenHelper(Context context) {
        super(context, "ToDoDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
 String  query = "CREATE TABLE "+ constatnts.ToDo_Table_name+" ( "+
         constatnts.ToDo_col_title+" TEXT,"+
         constatnts.ToDo_col_desc+" TEXT,"+
         constatnts.Todo_Time+" TEXT,"+
         constatnts.Todo_Date+" TEXT,"+
         constatnts.ToDo_Id+" INTEGER PRIMARY KEY AUTOINCREMENT );";
        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
