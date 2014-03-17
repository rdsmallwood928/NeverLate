package com.neverlate.NeverLate.alarms;

import android.text.method.DateTimeKeyListener;
import com.neverlate.NeverLate.alarms.Alarm;
import com.neverlate.NeverLate.alarms.utils.DateUtils;
import junit.framework.Assert;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.junit.Test;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 1/20/14
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */

public class AlarmDateTests {

    @Test
    public void TestDates(){
        Date startOfToday = DateUtils.getStartOfDate(new Date());
        Assert.assertEquals(startOfToday.getHours(), 0);
        Assert.assertEquals(startOfToday.getMinutes(), 0);
        Assert.assertEquals(startOfToday.getSeconds(), 0);
    }

    @Test
    public void testAlarmDays() {

        boolean[] days = new boolean[7];
        for(int i=0; i<days.length; i++) {
            if(i==0) {
                days[i] = true;
            } else {
                days[i] = false;
            }
        }
        Alarm alarm = new Alarm(12, 0, days);
        Assert.assertEquals(new LocalDateTime(alarm.getActivationTime()).getDayOfWeek()-1, 0);
        Assert.assertEquals(new LocalDateTime(alarm.getActivationTime()).toDate().after(new LocalDateTime().toDate()), true);

        days[1] = true;
        alarm = new Alarm(12, 0, days);
        Assert.assertEquals(new LocalDateTime(alarm.getActivationTime()).getDayOfWeek()-1, 0);
        Assert.assertEquals(new LocalDateTime(alarm.getActivationTime()).toDate().after(new LocalDateTime().toDate()), true);

        days[0] = false;
        alarm = new Alarm(12, 0, days);
        Assert.assertEquals(new LocalDateTime(alarm.getActivationTime()).getDayOfWeek()-1, 1);
        Assert.assertEquals(new LocalDateTime(alarm.getActivationTime()).toDate().after(new LocalDateTime().toDate()), true);

        days[1] = false;
        days[2] = true;
        alarm = new Alarm(12, 0, days);
        Assert.assertEquals(new LocalDateTime(alarm.getActivationTime()).getDayOfWeek()-1, 2);
        Assert.assertEquals(new LocalDateTime(alarm.getActivationTime()).toDate().after(new LocalDateTime().toDate()), true);

        days[2] = false;
        days[3] = true;
        alarm = new Alarm(12, 0, days);
        Assert.assertEquals(new LocalDateTime(alarm.getActivationTime()).getDayOfWeek()-1, 3);
        Assert.assertEquals(new LocalDateTime(alarm.getActivationTime()).toDate().after(new LocalDateTime().toDate()), true);

        days[3] = false;
        days[4] = true;
        alarm = new Alarm(12, 0, days);
        Assert.assertEquals(new LocalDateTime(alarm.getActivationTime()).getDayOfWeek()-1, 4);
        Assert.assertEquals(new LocalDateTime(alarm.getActivationTime()).toDate().after(new LocalDateTime().toDate()), true);

        days[4] = false;
        days[5] = true;
        alarm = new Alarm(12, 0, days);
        Assert.assertEquals(new LocalDateTime(alarm.getActivationTime()).getDayOfWeek()-1, 5);
        Assert.assertEquals(new LocalDateTime(alarm.getActivationTime()).toDate().after(new LocalDateTime().toDate()), true);

        days[5] = false;
        days[6] = true;
        alarm = new Alarm(12, 0, days);
        Assert.assertEquals(new LocalDateTime(alarm.getActivationTime()).getDayOfWeek()-1, 6);
        Assert.assertEquals(new LocalDateTime(alarm.getActivationTime()).toDate().after(new LocalDateTime().toDate()), true);

        days[6] = false;
        days[0] = true;
        alarm = new Alarm(12, 0, days);
        Assert.assertEquals(new LocalDateTime(alarm.getActivationTime()).getDayOfWeek()-1, 0);
        Assert.assertEquals(new LocalDateTime(alarm.getActivationTime()).toDate().after(new LocalDateTime().toDate()), true);
    }
}
