package com.neverlate.NeverLate.alarms;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 2/5/14
 * Time: 7:20 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IAlarmListener {
    public void fireAlarmActivated();
    public void fireAlarmRemoved();
    public void fireAlarmArmed(boolean armed);
}
