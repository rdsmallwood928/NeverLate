package com.neverlate.NeverLate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import com.neverlate.NeverLate.R;
import com.neverlate.NeverLate.activities.components.AndroidClickSpinner;
import com.neverlate.NeverLate.alarms.Alarm;
import com.neverlate.NeverLate.alarms.AlarmClockManager;

import java.util.ArrayList;

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
    private AndroidClickSpinner amPmSpinner;
    private Switch hour24Switch = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.add_alarm);
        hour24Switch = (Switch) findViewById(R.id.hour24switch);
        hour24Switch.setChecked(false);
        createSpinners(hour24Switch);

    }



    public void createSpinners(View view) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.addAlarmSpinnerLayout);
        layout.removeAllViews();
        ArrayList<Object> hours = new ArrayList<>();
        if(hour24Switch.isChecked())  {
            for(int i=0;i<24;i++) hours.add(i);
        } else {
            for(int i=1;i<=12;i++) hours.add(i);
        }


        hourSpinner = new AndroidClickSpinner(this, hours);
        layout.addView(hourSpinner);
        ArrayList<Object> minutes = new ArrayList<>();
        for(int i=0;i<=60;i++) minutes.add(i);
        minuteSpinner = new AndroidClickSpinner(this, minutes);
        layout.addView(minuteSpinner);
        if(!hour24Switch.isChecked()) {
            ArrayList<Object> amPm = new ArrayList<>();
            amPm.add("AM");
            amPm.add("PM");
            amPmSpinner = new AndroidClickSpinner(this, amPm);
            layout.addView(amPmSpinner);
        }
    }

    public void onDoneClicked(View view) {
        Integer hour = Integer.parseInt(hourSpinner.getSelectedItem().toString());
        Integer minute = Integer.parseInt(minuteSpinner.getSelectedItem().toString());
        if(!hour24Switch.isChecked()) {
            if(hour == 12) {
                hour = 0;
            }
            if(amPmSpinner.getSelectedItem().equals("PM")) {
                hour = hour + 12;
            }
        }
        Alarm alarm = new Alarm(hour, minute);
        AlarmClockManager.getInstance(this).addAlarm(alarm);
        alarm.setEnabled(true);
        Intent intent = new Intent(AddAlarmActivity.this, NeverLateStart.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
