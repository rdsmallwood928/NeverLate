package com.neverlate.NeverLate.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.neverlate.NeverLate.R;
import com.neverlate.NeverLate.activities.components.AlarmAndroidView;
import com.neverlate.NeverLate.activities.components.NavDrawerItem;
import com.neverlate.NeverLate.activities.components.NavDrawerListAdapter;
import com.neverlate.NeverLate.alarms.Alarm;
import com.neverlate.NeverLate.alarms.AlarmClockManager;
import com.neverlate.NeverLate.alarms.database.DatabaseTools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NeverLateStart extends AlarmActivity {

    private String[] navMenuTitles;
    private ListView navDrawerList;
    private DrawerLayout drawerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }


    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            navDrawerList.setItemChecked(position, true);
            navDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            drawerLayout.closeDrawer(navDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
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

            navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            List<NavDrawerItem> navDrawerItems = new ArrayList<>();
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], R.drawable.alarm));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], R.drawable.alarm));

            NavDrawerListAdapter adapter = new NavDrawerListAdapter(getApplicationContext(),  navDrawerItems);
            navDrawerList = (ListView) findViewById(R.id.left_drawer);
            navDrawerList.setAdapter(adapter);

            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setHomeButtonEnabled(true);

            ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.app_name, R.string.app_name){
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
            displayView(0);
            navDrawerList.setOnItemClickListener(new SlideMenuClickListener());
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

    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }
}
