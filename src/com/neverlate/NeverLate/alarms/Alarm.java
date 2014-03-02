package com.neverlate.NeverLate.alarms;

import com.neverlate.NeverLate.alarms.utils.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 1/12/14
 * Time: 9:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class Alarm{
    private DateTime activationTime = new DateTime(0);
    private boolean isArmed = false;
    private Days day;
    private Timer countDownTimer = null;
    private List<IAlarmListener> listenerList = new ArrayList<>();
    public static int UNSET_ALARM_ID = -1;
    private int alarmId = UNSET_ALARM_ID;


    public Alarm() {
        activationTime = new DateTime(0);
        isArmed = false;
        day = Days.MONDAY;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Alarm) {
            Alarm other = (Alarm)o;
            if(other.getActivationTime().equals(this.getActivationTime())) {
                return true;
            }

        }
        return false;
    }

    @Override
    public int hashCode() {
        return activationTime.hashCode();
    }

    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        StringBuilder builder = new StringBuilder();
        builder.append("alarmId: ").append(alarmId)
                .append("activationTime: ") .append(simpleDateFormat.format(activationTime.toDate()));
        return builder.toString();
    }


    public Alarm(int alarmId, DateTime activationTime) {
        this.activationTime = activationTime;
        this.alarmId = alarmId;
    }

    public Alarm(Integer hours, Integer minutes) {
        this.activationTime = new LocalTime(hours, minutes).toDateTimeToday();
    }

    public void setEnabled(boolean enabled) {
        this.isArmed = enabled;
        if(isArmed) {
            if(countDownTimer == null) {
                countDownTimer = new Timer();
                countDownTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        countDownTimer = null;
                        fireAlarmActivated();
                    }

                }, activationTime.toDate());
            }
        }
    }



    private void fireAlarmActivated() {
        for(IAlarmListener listener : listenerList) {
            listener.fireAlarmActivated();
        }
    }

    public String getAlarmString() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(activationTime.toDate());
    }

    public int getAlarmId() {
        return alarmId;
    }

    public Date getActivationTime() {
        return activationTime.toDate();
    }

    public void addListener(IAlarmListener listener) {
        listenerList.add(listener);
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

}
