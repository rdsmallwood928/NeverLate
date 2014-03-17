package com.neverlate.NeverLate.alarms;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

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
    private Timer countDownTimer = null;
    private List<IAlarmListener> listenerList = new ArrayList<>();
    public static int UNSET_ALARM_ID = -1;
    private int alarmId = UNSET_ALARM_ID;
    private boolean[] days = new boolean[7];
    private int hour;
    private int minute;


    public Alarm() {
        activationTime = new DateTime(0);
        isArmed = false;
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


    public Alarm(int alarmId, int hours, int minutes, boolean[] days, boolean isArmed) {
        this.alarmId = alarmId;
        this.hour = hours;
        this.minute = minutes;
        this.days = days;
        this.setArmed(isArmed);
    }

    public Alarm(Integer hours, Integer minutes, boolean[] days) {
        if(days.length != 7) {
            throw new IllegalArgumentException("Days array must be a length of 7");
        }
        this.days = days;
        this.hour = hours;
        this.minute = minutes;
        activationTime = getNextActivationTime();
    }

    public void setArmed(boolean enabled) {
        this.isArmed = enabled;
        this.activationTime = getNextActivationTime();
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
        } else {
            countDownTimer = null;
        }
        fireAlarmArmed();
    }

    private void fireAlarmArmed() {
        for(IAlarmListener listener : listenerList) {
            listener.fireAlarmArmed(isArmed);
        }
    }

    private DateTime getNextActivationTime() {
        LocalDateTime localDateTime = new LocalDateTime();
        boolean foundDay = days[localDateTime.getDayOfWeek()-1];
        //If its today, make sure its not after the time of day

        if(foundDay) {
            if(hour >= localDateTime.getHourOfDay() && minute > localDateTime.getMinuteOfHour()) {
                foundDay = true;
            } else {
                foundDay = false;
            }
        }
        int day = localDateTime.getDayOfWeek();
        for(int i=0; i<7; i++) {
            foundDay = days[day%days.length];
            day++;
            if(foundDay) break;
        }
        if(!foundDay) {
            day =0;
        }
        LocalDateTime dateTime = new LocalDateTime().withTime(0, 0, 0, 0);
        dateTime = dateTime.plusHours(hour + ((day-localDateTime.getDayOfWeek()) *24));
        dateTime = dateTime.plusMinutes(minute);
        return dateTime.toDateTime();
    }


    private void fireAlarmActivated() {
        for(IAlarmListener listener : listenerList) {
            listener.fireAlarmActivated();
        }
    }

    public String getAlarmString() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm aa");
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

    public boolean isArmed() {
        return isArmed;
    }

    public boolean[] getDays() {
        return days;
    }

    public int getHours() {
        return hour;
    }

    public int getMinutes() {
        return minute;
    }
}
