package com.example.yossi.exercise170817;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yossi on 07/08/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "workersInfo";

    // Contacts table name
    private static final String TABLE_WORKERS = "workers";

    // USERS Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_INTIME = "intime";
    private static final String KEY_OUTTIME = "outtime";
    private static final String KEY_APPLOCCATION = "applocation";
    private static final String KEY_USERLOCCATION = "userlocation";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE "
                + TABLE_WORKERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_INTIME + " TEXT,"
                + KEY_OUTTIME + " TEXT,"
                + KEY_APPLOCCATION + " TEXT,"
                + KEY_USERLOCCATION + " TEXT" + ")";

        Log.i("table start",CREATE_CONTACTS_TABLE);//for debugging

        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKERS);
        // Creating tables again
        onCreate(db);

    }

    // Adding new Row to DB
    public void addRow(Worker workerDay) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, workerDay.getName()); // first Name
        values.put(KEY_INTIME, workerDay.getIntime()); // star of the day work
        values.put(KEY_OUTTIME, workerDay.getOuttime()); // end of the day work
        values.put(KEY_APPLOCCATION, workerDay.getApplocation()); // arrival to place of work
        values.put(KEY_USERLOCCATION, workerDay.getUserlocation()); // end place of work

        // Inserting Row
        db.insert(TABLE_WORKERS, null, values);
        db.close(); // Closing database connection
    }

    // Getting All Rows
    public List<Worker> getAllRows() {
        List<Worker> working = new ArrayList<Worker>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WORKERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Worker workerDay = new Worker();
                workerDay.setId(Integer.parseInt(cursor.getString(0)));
                workerDay.setName(cursor.getString(1));
                workerDay.setIntime(cursor.getString(2));
                workerDay.setOuttime(cursor.getString(3));
                workerDay.setApplocation(cursor.getString(4));
                workerDay.setUserlocation(cursor.getString(5));
                // Adding contact to list
                working.add(workerDay);
            } while (cursor.moveToNext());
        }

        // return contact list
        return working;
    }

    // Getting one row user
    public Worker getOneRow(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_WORKERS, new String[]{KEY_ID,
                        KEY_NAME, KEY_INTIME, KEY_OUTTIME, KEY_APPLOCCATION,
                        KEY_USERLOCCATION,}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Worker contact = new Worker(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5));
        // return user
        return contact;
    }

    // Updating a end day
    public int updateEndOfDay(String outtime) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_OUTTIME, outtime);

        // updating row
        return db.update(TABLE_WORKERS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(getLastId())});
    }

    public int getLastId() {

        SQLiteDatabase db = this.getWritableDatabase();
        String ID = "SELECT " + KEY_ID + " FROM " + TABLE_WORKERS
                    + " ORDER BY "+ KEY_ID +" DESC  LIMIT 1";
        Cursor cursor = db.rawQuery(ID, null);

        return Integer.parseInt(cursor.toString());

    }

}
