package com.neverlate.NeverLate.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.neverlate.NeverLate.R;
import com.neverlate.NeverLate.activities.components.NavDrawerItem;
import com.neverlate.NeverLate.activities.components.NavDrawerListAdapter;
import com.neverlate.NeverLate.alarms.Alarm;
import com.neverlate.NeverLate.alarms.AlarmClockManager;

import java.util.ArrayList;
import java.util.List;

public class NeverLateStart extends AlarmActivity {

    private String[] navMenuTitles;
    private ListView navDrawerList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Fragment currentFragment;
    private AddAlarmFragment addAlarmFragment;
    private AlarmsListFragment alarmsListFragment;

    @Override
    public void fireAlarmRemoved() {
        reloadAlarms();
    }

    @Override
    public void fireAlarmArmed(boolean armed) {
        reloadAlarms();
    }

    private void reloadAlarms() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alarmsListFragment.reloadAlarms();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setUpMainActivity(savedInstanceState);
    }


    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 1:
                if(addAlarmFragment == null) {
                    addAlarmFragment = new AddAlarmFragment();
                }
                fragment = addAlarmFragment;
                break;
            default:
                fragment = getAlarmsListFragment();
                break;
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
        currentFragment = fragment;
        // update selected item and title, then close the drawer
        navDrawerList.setItemChecked(position, true);
        navDrawerList.setSelection(position);
        if(position == 0) {
            setTitle(R.string.app_name);
        } else {
            setTitle(navMenuTitles[position]);
        }
        drawerLayout.closeDrawer(navDrawerList);
    }

    private void setUpMainActivity(Bundle savedInstanceState) {
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        List<NavDrawerItem> navDrawerItems = new ArrayList<>();
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], R.drawable.alarm));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], R.drawable.alarm));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], R.drawable.alarm));

        NavDrawerListAdapter adapter = new NavDrawerListAdapter(getApplicationContext(),  navDrawerItems);
        navDrawerList = (ListView) findViewById(R.id.left_drawer);
        navDrawerList.setAdapter(adapter);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.empty, R.string.empty){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(getTitle());
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(getTitle());
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        displayView(0);
        navDrawerList.setOnItemClickListener(new SlideMenuClickListener());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
       if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.action_add_alarm:
                fragment = new AddAlarmFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
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

    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    public void onDoneClicked(View view) {
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                Integer hour = addAlarmFragment.getHours();
                Integer minute = addAlarmFragment.getMinutes();
                boolean[] days = addAlarmFragment.getDays();
                Alarm alarm = new Alarm(hour, minute, days);
                AlarmClockManager.getInstance(NeverLateStart.this).addAlarm(alarm);
                return null;
            }

            @Override
            protected void onPostExecute(Object result) {
                super.onPostExecute(result);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_container, getAlarmsListFragment()).commit();
                setTitle(R.string.app_name);
            }
        };
        task.execute(null);
    }

    private AlarmsListFragment getAlarmsListFragment() {
        if(alarmsListFragment == null) {
            alarmsListFragment = new AlarmsListFragment();
        }
        return alarmsListFragment;
    }
}
