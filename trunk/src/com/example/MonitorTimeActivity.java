package com.example;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class MonitorTimeActivity extends Activity {

    private long endTime;

    private TextView mTimeLabel;
    private Handler mHandler = new Handler();


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
        endTime = System.currentTimeMillis() + getLengthInMillis();
        mHandler.postDelayed(mUpdateTimeTask, 100);

        mHandler.postDelayed(new Runnable() {
            public void run() {
                MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.annoying_alarm);
                mp.start();
            }
        }, getLengthInMillis());

    }

    private Integer getLengthInMillis() {
        return (Integer) getIntent().getExtras().get("lengthInMillis");
    }
}
