package com.neverlate.NeverLate.alarms.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.neverlate.NeverLate.alarms.Alarm;

import java.util.ArrayList;
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
        super(context, databaseName, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String query = "CREATE TABLE IF NOT EXISTS alarms (alarmId INTEGER PRIMARY KEY, minutes INTEGER, hour INTEGER, days INTEGER, armed BOOLEAN)";
        database.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        if(newVersion > 3) {
            String query = "DROP TABLE IF EXISTS alarms";
            database.execSQL(query);
        }
        onCreate(database);
    }

    public void insertAlarm(Alarm alarm) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("alarmId", alarm.getAlarmId());
        values.put("hour", alarm.getHours());
        values.put("minutes", alarm.getMinutes());
        values.put("days", convertDaysToInt(alarm.getDays()));
        values.put("armed", alarm.isArmed());
        database.insert("alarms", null, values);
        database.close();
    }

    private int convertDaysToInt(boolean[] days) {
        int daysInt = 0;
        for(int i=0; i<days.length; i++) {
            if(days[i]) {
                daysInt++;
            }
            daysInt = daysInt << 1;
        }
        return daysInt;
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

    public void removeAlarm(Alarm alarm) {
        SQLiteDatabase database = getWritableDatabase();
        String[] args = new String[1];
        args[0] = String.valueOf(alarm.getAlarmId());
        database.delete("alarms", "alarmId = ?", args);
        database.close();
    }
}
