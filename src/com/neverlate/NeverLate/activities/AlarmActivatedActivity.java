package com.neverlate.NeverLate.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.webkit.URLUtil;
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
        MediaPlayer player = MediaPlayer.create(this, R.raw.alarm);
        player.setLooping(true);
        player.start();
    }
}
