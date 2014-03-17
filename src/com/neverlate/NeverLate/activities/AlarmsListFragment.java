package com.neverlate.NeverLate.activities;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.neverlate.NeverLate.R;
import com.neverlate.NeverLate.activities.components.AlarmAndroidView;
import com.neverlate.NeverLate.alarms.Alarm;
import com.neverlate.NeverLate.alarms.AlarmClockManager;

/**
 * Created by bigwood928 on 3/8/14.
 */
public class AlarmsListFragment extends Fragment {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reloadAlarms();
    }

    public void reloadAlarms() {
        LinearLayout alarmsLayout = (LinearLayout) getView().findViewById(R.id.alarms_list_view);
        AlarmClockManager manager = AlarmClockManager.getInstance(getActivity());
        alarmsLayout.removeAllViews();
        for(Alarm alarm : manager.getAlarms()) {
            alarmsLayout.addView(new AlarmAndroidView(getView().getContext(), alarm));
        }
        if(manager.getAlarms().isEmpty()) {
            TextView textView = new TextView(getActivity().getApplicationContext());
            textView.setText("No Alarms");
            alarmsLayout.addView(textView);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.alarms_list, container, false);
    }
}
