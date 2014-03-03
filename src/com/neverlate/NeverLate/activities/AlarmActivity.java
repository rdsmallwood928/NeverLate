package com.neverlate.NeverLate.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.neverlate.NeverLate.R;
import com.neverlate.NeverLate.alarms.AlarmClockManager;
import com.neverlate.NeverLate.alarms.IAlarmListener;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 2/5/14
 * Time: 7:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class AlarmActivity extends Activity implements IAlarmListener{

    protected boolean alarmActivated = false;
    @Override
    public void fireAlarmActivated() {
        alarmActivated = true;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(AlarmActivity.this, AlarmActivatedActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlarmClockManager.getInstance(this).setCurrentView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AlarmClockManager.getInstance(this).setCurrentView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        AlarmClockManager.getInstance(this).onStop();
    }
}
