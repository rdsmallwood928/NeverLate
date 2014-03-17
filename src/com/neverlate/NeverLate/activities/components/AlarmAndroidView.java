package com.neverlate.NeverLate.activities.components;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.neverlate.NeverLate.R;
import com.neverlate.NeverLate.alarms.Alarm;
import com.neverlate.NeverLate.alarms.AlarmClockManager;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 1/20/14
 * Time: 9:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class AlarmAndroidView extends LinearLayout {

    Alarm alarm= null;

    public AlarmAndroidView(Context context, Alarm localAlarm) {
        super(context);
        this.alarm = localAlarm;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = inflater.inflate(R.layout.android_alarm_view, this);
        TextView textView = (TextView)findViewById(R.id.alarm_time_text);
        textView.setText(alarm.getAlarmString());
        if (inflatedView != null) {
            inflatedView.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setTitle("Delete Alarm?").
                            setMessage("Are you sure?").setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AlarmClockManager.getInstance(getContext()).removeAlarm(alarm);
                        }
                    }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Nothing to do
                        }
                    }).show();
                    return true;
                }
            });
        }



        ImageView imageView = (ImageView)findViewById(R.id.alarm_list_icon);
        int deactivatedColor = Color.parseColor("#808080");
        if(alarm.isArmed()) {
            int activatedColor = Color.parseColor("#FF3B38");
            imageView.setImageResource(R.drawable.alarm);
            if(alarm.getDays()[0]){
                ((TextView)findViewById(R.id.mondayTextView)).setTextColor(activatedColor);
            } else {
                ((TextView)findViewById(R.id.mondayTextView)).setTextColor(deactivatedColor);
            }
            if(alarm.getDays()[1]){
                ((TextView)findViewById(R.id.tuesdayTextView)).setTextColor(activatedColor);
            } else {
                ((TextView)findViewById(R.id.tuesdayTextView)).setTextColor(deactivatedColor);
            }
            if(alarm.getDays()[2]){
                ((TextView)findViewById(R.id.wednesdayTextView)).setTextColor(activatedColor);
            } else {
                ((TextView)findViewById(R.id.wednesdayTextView)).setTextColor(deactivatedColor);
            }
            if(alarm.getDays()[3]){
                ((TextView)findViewById(R.id.thursdayTextView)).setTextColor(activatedColor);
            } else {
                ((TextView)findViewById(R.id.thursdayTextView)).setTextColor(deactivatedColor);
            }
            if(alarm.getDays()[4]){
                ((TextView)findViewById(R.id.fridayTextView)).setTextColor(activatedColor);
            } else {
                ((TextView)findViewById(R.id.fridayTextView)).setTextColor(deactivatedColor);
            }
            if(alarm.getDays()[5]){
                ((TextView)findViewById(R.id.saturdayTextView)).setTextColor(activatedColor);
            } else {
                ((TextView)findViewById(R.id.saturdayTextView)).setTextColor(deactivatedColor);
            }
            if(alarm.getDays()[6]){
                ((TextView)findViewById(R.id.sundayTextView)).setTextColor(activatedColor);
            } else {
                ((TextView)findViewById(R.id.sundayTextView)).setTextColor(deactivatedColor);
            }
        } else {
            imageView.setImageResource(R.drawable.alarm_inactive);
            ((TextView)findViewById(R.id.mondayTextView)).setTextColor(deactivatedColor);
            ((TextView)findViewById(R.id.tuesdayTextView)).setTextColor(deactivatedColor);
            ((TextView)findViewById(R.id.wednesdayTextView)).setTextColor(deactivatedColor);
            ((TextView)findViewById(R.id.thursdayTextView)).setTextColor(deactivatedColor);
            ((TextView)findViewById(R.id.fridayTextView)).setTextColor(deactivatedColor);
            ((TextView)findViewById(R.id.saturdayTextView)).setTextColor(deactivatedColor);
            ((TextView)findViewById(R.id.sundayTextView)).setTextColor(deactivatedColor);
        }
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm.setArmed(!alarm.isArmed());
            }
        });
    }
}

