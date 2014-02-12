package com.neverlate.NeverLate.activities.components;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
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
public class AlarmAndroidView {

    private LinearLayout alarmLayout;

    public AlarmAndroidView(Context context, Alarm alarm) {
        alarmLayout = new LinearLayout(context);
        alarmLayout.setGravity(Gravity.CENTER);
        alarmLayout.setOrientation(LinearLayout.HORIZONTAL);
        ImageView image;
        image = new ImageView(context);
        image.setImageDrawable(context.getResources().getDrawable(R.drawable.alarm48));
        alarmLayout.addView(image);
        TextView textView = new TextView(context);
        textView.setText(alarm.getAlarmString());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 20);
        alarmLayout.addView(textView);
    }

    public View getView() {
        return alarmLayout;
    }
}

