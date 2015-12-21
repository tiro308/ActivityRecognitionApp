package com.example.atrt01admin.meinexample03;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="save2.db"; //unbedingt .db
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
                COLUMN_LAT  + " TEXT, " +
                COLUMN_ACTIVITY + " TEXT, " +

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
        values.put(COLUMN_LAT, recordItem.getLatitude());
        values.put(COLUMN_TIMESTAMP, recordItem.getTime().toString());
        values.put(COLUMN_LONG, recordItem.getLongitude());
        values.put(COLUMN_ACTIVITY, recordItem.getActivity()); //bsp get.productname()
        System.out.println("values aus DB: " + values.toString());
        // 3. insert
        db.insert(TABLE_NAME, null, values);
        // 4. close
        db.close();
    }

    public List<RecordItem> getAllRecordItems(){
        List<RecordItem> recordItemList = new LinkedList<>();
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_NAME;
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // 3. go over each row, build book and add it to list
        RecordItem recordItem = null;
        if (cursor.moveToFirst()) {
            do {
                recordItem = new RecordItem();
                recordItem.setActivity(cursor.getString(1));
                recordItem.setLatitude(Double.parseDouble(cursor.getString(2)));
                recordItem.setLongitude(Double.parseDouble(cursor.getString(3)));
                //recordItem.setTime(Date);

                // Add record to list
                recordItemList.add(recordItem);
            } while (cursor.moveToNext());
        }

        Log.d("getAllBooks()", recordItemList.toString());
        // return books
        return recordItemList;
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

//    //print db to string
//    public String databaseToString(){
//        String dbString = "";
//        SQLiteDatabase db = getWritableDatabase();
//        String query = "SELECT " + COLUMN_ACTIVITY + " FROM " + TABLE_NAME + " WHERE 1"; //"SELECT * FROM "
//
//        //cursor point to a location in my results
//        Cursor c = db.rawQuery(query,null);
//        //move to the first row
//        c.moveToFirst();
//
//        while (!c.isAfterLast()){
//            if(c.getString(c.getColumnIndex("activity"))!=null){//activity statt productname
//                dbString += c.getString(c.getColumnIndex("activity"));
//                dbString += "\n";
//            }
//        }
//        c.moveToNext(); //aus yt comment
//        db.close();
//        return dbString;
//    }
//
//    public void moveToSD(){
//
//        SQLiteDatabase db = getWritableDatabase();
//
//        try {
//            // Backup of database to SDCard
//            //db.open();
//            File newFile = new File(Environment.getExternalStorageDirectory(), "saveSDcard"); //BackupFileNameHere
//            InputStream input = new FileInputStream("/data/data/com.example.atrt01admin.meinexample03/databases/save.db"); ///data/data/com.YourProjectName/databases/DatabaseNameHere
//            OutputStream output = new FileOutputStream(newFile);
//
//            // transfer bytes from the Input File to the Output File
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = input.read(buffer))>0) {
//                output.write(buffer, 0, length);
//            }
//            output.flush();
//            output.close();
//            input.close();
//            db.close();
//
//        } catch (Exception e) {
//        }
//
//    }


}








