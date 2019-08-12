package com.example.praka.notification;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Databasehelper extends SQLiteOpenHelper {


    private static final String Table_name = "notificationtable";
    private static final String Col1 = "ID";
    private static final String Col2 = "notificationdate";

    public Databasehelper(Context context) {
        super(context, Table_name, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createtable = "CREATE TABLE artists(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";
        db.execSQL(createtable);


    }
public Cursor alldata(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from artists",null);
        return cursor;
}
    /*public boolean selectTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT id,name FROM artists ", null);
        if (c.moveToFirst()){
            do {
                // Passing values
                String column1 = c.getString(0);
                String column2 = c.getString(1);
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
                Date currentLocalTime = cal.getTime();
                DateFormat date = new SimpleDateFormat("h:mm a");
// you can get seconds by adding  "...:ss" to it
                date.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));

                String localTime = date.format(currentLocalTime);

                if(column2.equalsIgnoreCase(localTime)){
                   return true;
                }
                // Do something Here with values
            } while(c.moveToNext());
        }
        c.close();
        db.close();

      return false;
    }*/


    public boolean addData(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",item);
        System.out.println("addData: Adding" + item + "to artists");

        long result = db.insert("artists",null,contentValues);

        if(result == -1){
            return false;
        }
        else {
            db.close();
            return true;

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sqlexecx = "DROP TABLE IF EXISTS artists";
        db.execSQL(sqlexecx);


        onCreate(db);

    }


}
