package com.neverlate.NeverLate.activities.components;

import android.app.Activity;
import android.content.Context;
import android.graphics.NinePatch;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.neverlate.NeverLate.R;
import com.neverlate.NeverLate.alarms.Alarm;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 1/20/14
 * Time: 9:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class AlarmAndroidView extends LinearLayout {

    public AlarmAndroidView(Context context, Alarm alarm) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.android_alarm_view, this);
        TextView textView = (TextView)findViewById(R.id.alarm_time_text);
        textView.setText(alarm.getAlarmString());
    }
}

