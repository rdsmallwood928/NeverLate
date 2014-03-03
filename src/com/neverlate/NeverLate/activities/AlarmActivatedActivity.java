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

    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.show_alarm);
        player = MediaPlayer.create(this, R.raw.alarm);
        player.setLooping(true);
        player.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(player.isPlaying()) {
            player.stop();
        }
    }
}
