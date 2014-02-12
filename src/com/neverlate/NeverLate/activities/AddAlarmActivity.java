package com.neverlate.NeverLate.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import com.neverlate.NeverLate.R;
import com.neverlate.NeverLate.activities.components.AndroidClickSpinner;
import com.neverlate.NeverLate.alarms.Alarm;
import com.neverlate.NeverLate.alarms.AlarmClockManager;
import com.neverlate.NeverLate.alarms.IAlarmListener;
import com.neverlate.NeverLate.alarms.database.DatabaseTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 1/15/14
 * Time: 5:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddAlarmActivity extends AlarmActivity {

    private AndroidClickSpinner minuteSpinner;
    private AndroidClickSpinner hourSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.addalarm);
        LinearLayout layout = (LinearLayout) findViewById(R.id.addAlarmSpinnerLayout);
        Switch hour24Switch = (Switch) findViewById(R.id.hour24switch);
        hour24Switch.setChecked(false);
        List<Object> hours = new ArrayList<Object>();
        for(int i=0;i<=24;i++) hours.add(i);
        hourSpinner = new AndroidClickSpinner(this, hours);
        layout.addView(hourSpinner);
        ArrayList<Object> minutes = new ArrayList<Object>();
        for(int i=0;i<=60;i++) minutes.add(i);
        minuteSpinner = new AndroidClickSpinner(this, minutes);
        layout.addView(minuteSpinner);
    }

    public void onDoneClicked(View view) {
        Integer hour = Integer.parseInt(hourSpinner.getSelectedItem().toString());
        Integer minute = Integer.parseInt(minuteSpinner.getSelectedItem().toString());
        Alarm alarm = new Alarm(hour, minute);
        AlarmClockManager.getInstance(this).addAlarm(alarm);
        alarm.setEnabled(true);
        Intent intent = new Intent(AddAlarmActivity.this, NeverLateStart.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
