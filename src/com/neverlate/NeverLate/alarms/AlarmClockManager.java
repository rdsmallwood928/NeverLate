package com.neverlate.NeverLate.alarms;

import android.content.Context;
import com.neverlate.NeverLate.alarms.database.DatabaseTools;
import org.joda.time.DateTime;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 1/12/14
 * Time: 9:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class AlarmClockManager implements IAlarmListener{

    private Set<Alarm> alarms;
    public static AlarmClockManager instance = null;
    private IAlarmListener currentView;
    private int nextAlarmId=0;
    public static final String ALARMS_TABLE = "alarms";
    public static final String DATABASE = "neverlatealarms.db";
    DatabaseTools databaseTools;

    private AlarmClockManager (Context context) {
        this.alarms = new HashSet<Alarm>();
        this.databaseTools =  new DatabaseTools(context, DATABASE);
        loadAlarmsFromDB();
    }

    public static AlarmClockManager getInstance(Context context) {
        if(instance == null && context != null) {
            instance = new AlarmClockManager(context);
        }
        return instance;
    }

    public Set<Alarm> getAlarms() {
        return alarms;
    }

    public void addAlarm(Alarm newAlarm) {
        if(newAlarm.getAlarmId() >= nextAlarmId) {
            nextAlarmId = newAlarm.getAlarmId()+1;
        } else {
            newAlarm.setAlarmId(nextAlarmId);
            nextAlarmId++;
        }
        alarms.add(newAlarm);
        newAlarm.addListener(this);
        databaseTools.insertAlarm(newAlarm);
    }

    public void removeAlarm(Alarm alarm) {
        alarms.remove(alarm);
        databaseTools.removeAlarm(alarm);
        currentView.fireAlarmRemoved();
    }

    private void loadAlarmsFromDB() {

        for(List<String> row : databaseTools.loadTable(ALARMS_TABLE)) {
            Alarm alarm = new Alarm(Integer.parseInt(row.get(0)),
                    Integer.parseInt(row.get(1)),
                    Integer.parseInt(row.get(2)),
                    convertStringToDays(row.get(3)),
                    Boolean.parseBoolean(row.get(4)));
            alarms.add(alarm);
            alarm.addListener(this);
            if(alarm.getAlarmId() >= nextAlarmId) {
                nextAlarmId = alarm.getAlarmId() + 1;
            }
        }
    }

    private boolean[] convertStringToDays(String days) {
        boolean[] returnDays =  new boolean[7];
        Integer daysAsInt = Integer.parseInt(days);
        for(int i=0; i<returnDays.length; i++) {
            returnDays[i] = (daysAsInt & 0xffff) == 1;
            daysAsInt = daysAsInt >> 1;
        }
        return returnDays;
    }

    public void setCurrentView(IAlarmListener listener) {
        currentView = listener;
    }

    @Override
    public void fireAlarmActivated() {
        currentView.fireAlarmActivated();
    }

    @Override
    public void fireAlarmRemoved() {

    }

    @Override
    public void fireAlarmArmed(boolean armed) {
        currentView.fireAlarmArmed(armed);

    }

    public int getNextAlarmId() {
        return nextAlarmId;
    }

    public void onStop() {
        databaseTools.close();
    }

    public static void reset() {
        instance = null;
    }
}
