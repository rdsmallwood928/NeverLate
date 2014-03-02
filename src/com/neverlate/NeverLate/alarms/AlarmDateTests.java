package com.neverlate.NeverLate.alarms;

import com.neverlate.NeverLate.alarms.Alarm;
import com.neverlate.NeverLate.alarms.utils.DateUtils;
import junit.framework.Assert;
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
}
