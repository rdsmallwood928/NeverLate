package com.neverlate.NeverLate.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.neverlate.NeverLate.R;
import com.neverlate.NeverLate.activities.components.AlarmAndroidView;
import com.neverlate.NeverLate.alarms.Alarm;
import com.neverlate.NeverLate.alarms.AlarmClockManager;
import com.neverlate.NeverLate.alarms.database.DatabaseTools;

import java.util.Collection;

public class NeverLateStart extends AlarmActivity {
    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
        setUpMainActivity();
    }

    private void setUpMainActivity() {
        super.onStart();
        if(!alarmActivated) {
            AlarmClockManager manager = AlarmClockManager.getInstance(this);
            LinearLayout alarmLayout = (LinearLayout) findViewById(R.id.current_alarms_layout);
            alarmLayout.removeAllViews();
            for(Alarm alarm : manager.getAlarms()) {
                alarmLayout.addView(new AlarmAndroidView(this.getApplicationContext(), alarm));
            }
        } else {
            this.setContentView(R.layout.show_alarm);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add_alarm:
                Intent intent = new Intent(this, AddAlarmActivity.class);
                startActivity(intent);
                return true;
            case R.id.close_application_item:
                onDestroy();
                return true;
            case R.id.run_db_tests:
                AlarmClockManager.reset();
                AlarmClockManager.getInstance(this);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

}
