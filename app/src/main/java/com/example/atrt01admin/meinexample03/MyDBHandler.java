package com.example.atrt01admin.meinexample03;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="save4.db"; //unbedingt .db
    public static final String TABLE_NAME = "records";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ACTIVITY = "activity";
    public static final String COLUMN_LAT = "latitude";
    public static final String COLUMN_LONG = "longitude";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //versuch zum neuen table
        String query = "CREATE TABLE " + TABLE_NAME + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ACTIVITY + " TEXT, " +
                COLUMN_LAT  + " TEXT, " +
                COLUMN_LONG + " TEXT, " +
                COLUMN_TIMESTAMP + " TEXT" +
                ");";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);

    }

    //Add new row to database
    public void addRecord(RecordItem recordItem){ //im bsp Product product
        //System.out.println("\n add record: " + recordItem.recordItemToString());
        // 1. get reference to writable DB
        SQLiteDatabase db = getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        System.out.println(recordItem.getLatitude());
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACTIVITY, recordItem.getActivity()); //bsp get.productname()
        values.put(COLUMN_LAT, recordItem.getLatitude());
        values.put(COLUMN_LONG, recordItem.getLongitude());
        values.put(COLUMN_TIMESTAMP, recordItem.getTime().toString());
        System.out.println("values aus DB: " + values.toString());
        // 3. insert
        db.insert(TABLE_NAME, null, values);
        // 4. close
        db.close();
    }
    // Select lat and long:
// http://stackoverflow.com/questions/12672740/ordering-with-sqlite-by-nearest-latitude-longitude-coordinates
    public List<RecordItem> getAllRecordItems(){
        List<RecordItem> recordItemList = new LinkedList<>();
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_NAME;
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // 3. go over each row, build item and add it to list
        RecordItem recordItem = null;
        if (cursor.moveToFirst()) {
            do {
                recordItem = new RecordItem();
                recordItem.setActivity(cursor.getString(1));
                recordItem.setLatitude(Double.parseDouble(cursor.getString(2)));
                recordItem.setLongitude(Double.parseDouble(cursor.getString(3)));
                recordItem.setTime(cursor.getString(4));

                // Add record to list
                recordItemList.add(recordItem);
            } while (cursor.moveToNext());
        }

        Log.d("getAllRecordItems()", recordItemList.toString());
        // return list
        return recordItemList;
    }
    //**********************    SQL QUERIES   *****************************************************
//http://stackoverflow.com/questions/5202269/sqlite-query-in-android-to-count-rows
    public float countActitiyWalkinginAllRecords(){
        SQLiteDatabase db = getReadableDatabase();
        //no such column: walking (code 1): , while compiling: select count(*) from records where activity=walking
        float i = (float) DatabaseUtils.queryNumEntries(db, TABLE_NAME,
                "activity=='Walking'");
        //String query = "Select COUNT "
        return i;
    }

    public float countActitiyRunninginAllRecords(){
        SQLiteDatabase db = getReadableDatabase();
        //no such column: walking (code 1): , while compiling: select count(*) from records where activity=walking
        float i = (float) DatabaseUtils.queryNumEntries(db, TABLE_NAME,
                "activity=='Running'");
        //String query = "Select COUNT "
        return i;
    }
    public float countActitiyStillinAllRecords(){
        SQLiteDatabase db = getReadableDatabase();
        //no such column: walking (code 1): , while compiling: select count(*) from records where activity=walking
        float i = (float) DatabaseUtils.queryNumEntries(db, TABLE_NAME,
                "activity=='Still'");
        //String query = "Select COUNT "
        return i;
    }


    public String getTableAsString() {
        Log.d("getTableAsString", "getTableAsString called");
        SQLiteDatabase db = this.getReadableDatabase();
        String tableString = String.format("Table %s:\n", TABLE_NAME);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name: columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }

        return tableString;
    }
}

