package com.example.atrt01admin.meinexample03;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="save.db"; //unbedingt .db
    public static final String TABLE_NAME = "records";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ACTIVITY = "activity";
    public static final String COLUMN_LAT = "latitude";
    public static final String COLUMN_LONG = "longitude";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ACTIVITY + " TEXT " +
                COLUMN_LAT  + " TEXT " +
                COLUMN_LONG + " TEXT " +
                COLUMN_TIMESTAMP + " TEXT " +
                ");";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);

    }

    //Add new row to database
    public void addRecord(String activitystring){ //im bsp Product product
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACTIVITY, activitystring); //bsp get.productname()
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    //print db to string
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLUMN_ACTIVITY + " FROM " + TABLE_NAME + " WHERE 1"; //"SELECT * FROM "

        //cursor point to a location in my results
        Cursor c = db.rawQuery(query,null);
        //move to the first row
        c.moveToFirst();

        while (!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("activity"))!=null){//activity statt productname
                dbString += c.getString(c.getColumnIndex("activity"));
                dbString += "\n";
            }
        }
        c.moveToNext(); //aus yt comment
        db.close();
        return dbString;
    }

    public void moveToSD(){

        SQLiteDatabase db = getWritableDatabase();

        try {
            // Backup of database to SDCard
            //db.open();
            File newFile = new File(Environment.getExternalStorageDirectory(), "saveSDcard"); //BackupFileNameHere
            InputStream input = new FileInputStream("/data/data/com.example.atrt01admin.meinexample03/databases/save.db"); ///data/data/com.YourProjectName/databases/DatabaseNameHere
            OutputStream output = new FileOutputStream(newFile);

            // transfer bytes from the Input File to the Output File
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer))>0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            input.close();
            db.close();

        } catch (Exception e) {
        }

    }


}








