package com.example;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class MonitorTimeActivity extends Activity {
    public static boolean started = false;

    private long endTime;

    private static TextView mTimeLabel;
    private static Handler mHandler = new Handler();


    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > endTime) {
                mTimeLabel.setText("Get out of there");

            } else {
                long millis = endTime - System.currentTimeMillis();

                int seconds = (int) (millis / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;

                if (seconds < 10) {
                    mTimeLabel.setText("" + minutes + ":0" + seconds);
                } else {
                    mTimeLabel.setText("" + minutes + ":" + seconds);
                }

                mHandler.postDelayed(mUpdateTimeTask, 100);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_progress);
        mTimeLabel = (TextView) findViewById(R.id.timeLabel);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!started) {
            Integer lengthInMillis = getLengthInMillis();
            endTime = System.currentTimeMillis() + lengthInMillis;

            mHandler.postDelayed(mUpdateTimeTask, 100);

            mHandler.postDelayed(playSound(R.raw.ding), lengthInMillis / 2);
            mHandler.postDelayed(playSound(R.raw.ding), (int)(lengthInMillis * 0.75));
            mHandler.postDelayed(playSound(R.raw.ding), (int)(lengthInMillis * 0.9));
            mHandler.postDelayed(playSound(R.raw.ding), (int)(lengthInMillis * 0.95));
            mHandler.postDelayed(playSound(R.raw.ding), (int)(lengthInMillis * 0.99));
            mHandler.postDelayed(playSound(R.raw.annoying_alarm), lengthInMillis);

            started = true;
        }
    }

    private Runnable playSound(final int sound) {
        return new Runnable() {
            public void run() {
                MediaPlayer mp = MediaPlayer.create(getBaseContext(), sound);
                mp.start();
            }
        };
    }

    private Integer getLengthInMillis() {
        return (Integer) getIntent().getExtras().get("lengthInMillis");
    }
}
