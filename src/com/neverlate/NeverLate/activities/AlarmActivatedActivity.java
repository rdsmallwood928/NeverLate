package com.neverlate.NeverLate.activities;

import android.os.Bundle;
import com.neverlate.NeverLate.R;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 2/7/14
 * Time: 7:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class AlarmActivatedActivity extends AlarmActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.show_alarm);
    }
}
