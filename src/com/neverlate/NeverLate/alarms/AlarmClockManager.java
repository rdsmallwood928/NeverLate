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
    public static final String DATABASE = "alarms.db";
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

    private void loadAlarmsFromDB() {
        for(List<String> row : databaseTools.loadTable(ALARMS_TABLE)) {
            addAlarm(new Alarm(Integer.parseInt(row.get(0)), new DateTime(Long.parseLong(row.get(1)))));
        }
    }

    public void setCurrentView(IAlarmListener listener) {
        currentView = listener;
    }

    @Override
    public void fireAlarmActivated() {
        currentView.fireAlarmActivated();
    }

    public int getNextAlarmId() {
        return nextAlarmId;
    }
}
