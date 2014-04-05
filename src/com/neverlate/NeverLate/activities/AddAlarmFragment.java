package com.neverlate.NeverLate.activities;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.neverlate.NeverLate.R;
import com.neverlate.NeverLate.activities.components.AlarmAndroidView;
import com.neverlate.NeverLate.activities.components.AndroidClickSpinner;
import com.neverlate.NeverLate.activities.components.AndroidScrollSpinner;
import com.neverlate.NeverLate.activities.components.PieChart;
import com.neverlate.NeverLate.alarms.Alarm;
import com.neverlate.NeverLate.alarms.AlarmClockManager;
import org.joda.time.LocalDate;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 1/15/14
 * Time: 5:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddAlarmFragment extends Fragment {

    private AndroidClickSpinner minuteSpinner;
    private AndroidClickSpinner hourSpinner;
    private AndroidClickSpinner amPmSpinner;
    private ToggleButton mondayToggle;
    private ToggleButton tuesdayToggle;
    private ToggleButton wednesdayToggle;
    private ToggleButton thursdayToggle;
    private ToggleButton fridayToggle;
    private ToggleButton saturdayToggle;
    private ToggleButton sundayToggle;
    private Switch hour24Switch = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_alarm, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createSpinners(view);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void createSpinners(View view) {
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.addAlarmSpinnerLayout);
        hour24Switch = (Switch) view.findViewById(R.id.hour24switch);
        ArrayList<Object> hours = new ArrayList<>();
        if(hour24Switch.isChecked())  {
            for(int i=0;i<24;i++) hours.add(i);
        } else {
            for(int i=1;i<=12;i++) hours.add(i);
        }
        ArrayList<Object> minutes = new ArrayList<>();
        if(!hour24Switch.isChecked()) {
            ArrayList<Object> amPm = new ArrayList<>();
            amPm.add("AM");
            amPm.add("PM");
            amPmSpinner = new AndroidClickSpinner(getActivity(), amPm);
            layout.addView(amPmSpinner);
        }
        mondayToggle = (ToggleButton) view.findViewById(R.id.mondayToggleButton);
        tuesdayToggle = (ToggleButton) view.findViewById(R.id.tuesdayToggleButton);
        wednesdayToggle = (ToggleButton) view.findViewById(R.id.wednesdayToggleButton);
        thursdayToggle = (ToggleButton) view.findViewById(R.id.thursdayToggleButton);
        fridayToggle = (ToggleButton) view.findViewById(R.id.fridayToggleButton);
        saturdayToggle = (ToggleButton) view.findViewById(R.id.saturdayToggleButton);
        sundayToggle = (ToggleButton) view.findViewById(R.id.sundayToggleButton);
        //Prevent no day selected...
        switch (new LocalDate().getDayOfWeek()) {
            case 1:
                mondayToggle.setSelected(true);
                break;
            case 2:
                tuesdayToggle.setSelected(true);
                break;
            case 3:
                wednesdayToggle.setSelected(true);
                break;
            case 4:
                thursdayToggle.setSelected(true);
                break;
            case 5:
                fridayToggle.setSelected(true);
                break;
            case 6:
                saturdayToggle.setSelected(true);
                break;
            case 7:
                sundayToggle.setSelected(true);
                break;
        }
    }

    public Integer getHours() {
        Integer hour = Integer.parseInt(hourSpinner.getSelectedItem().toString());
        if(!hour24Switch.isChecked()) {
            if(hour == 12) {
                hour = 0;
            }
            if(amPmSpinner.getSelectedItem().equals("PM")) {
                hour = hour + 12;
            }
        }
        return hour;
    }

    public Integer getMinutes() {
        return Integer.parseInt(minuteSpinner.getSelectedItem().toString());
    }

    public boolean[] getDays() {
        boolean[] days = new boolean[7];
        days[0] = mondayToggle.isChecked();
        days[1] = tuesdayToggle.isChecked();
        days[2] = wednesdayToggle.isChecked();
        days[3] = thursdayToggle.isChecked();
        days[4] = fridayToggle.isChecked();
        days[5] = saturdayToggle.isChecked();
        days[6] = sundayToggle.isChecked();
        return days;
    }
}
