package com.neverlate.NeverLate.alarms.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.neverlate.NeverLate.alarms.Alarm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 1/23/14
 * Time: 7:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseTools extends SQLiteOpenHelper {


    public DatabaseTools(Context context, String databaseName) {
        //This seems like a weird hack, but looking at the source for SQLiteOPenHelper, create db only gets called if the version
        //number is 0, however we are not allowed to set the version to 0 without throwing.  I will set it to two for now in order to force
        //onUpgrade...

        //Prolly get rid of all this for hibernate later anyway...
        super(context, databaseName, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String query = "CREATE TABLE IF NOT EXISTS alarms (alarmId INTEGER PRIMARY KEY, time LONG)";
        database.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        if(newVersion > 2) {
            String query = "DROP TABLE IF EXISTS alarms";
            database.execSQL(query);
        }
        onCreate(database);
    }

    public void insertAlarm(Alarm alarm) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("alarmId", alarm.getAlarmId());
        values.put("time", alarm.getActivationTime().getTime());
        database.insert("alarms", null, values);
        database.close();
    }

    public List<List<String>> loadTable(String table) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(false, table, null, null, null, null, null, null, null);
        List<List<String>> tableLists = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                ArrayList<String> row = new ArrayList<>();
                for(int i=0; i<cursor.getColumnCount(); i++) {
                    row.add(cursor.getString(i));
                }
                tableLists.add(row);
            } while (cursor.moveToNext());
        }
        return tableLists;
    }
}
