package com.example;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MonitorTimeActivity extends Activity {
    public static boolean started = false;

    private long endTime;

    private static TextView mTimeLabel;
    private static Handler timerHandler = new Handler();
    private static Handler progressHandler = new Handler();


    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > endTime) {
                mTimeLabel.setText("Get out of there");
                started = false;
            } else {
                long elapsedMillis = endTime - System.currentTimeMillis();

                int seconds = (int) (elapsedMillis / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;

                if (seconds < 10) {
                    mTimeLabel.setText("" + minutes + ":0" + seconds);
                } else {
                    mTimeLabel.setText("" + minutes + ":" + seconds);
                }

                progressBar.setProgress(100 * (int) (elapsedMillis / getLengthInMillis()));
                timerHandler.postDelayed(mUpdateTimeTask, 100);
            }
        }
    };


    private Runnable mUpdateProgressTask = new Runnable() {
        public void run() {
            long elapsedMillis = endTime - System.currentTimeMillis();


            progressBar.setProgress(100 * (int) (elapsedMillis / getLengthInMillis()));
            timerHandler.postDelayed(mUpdateProgressTask, 100);
        }
    };
    private static ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_progress);
        mTimeLabel = (TextView) findViewById(R.id.timeLabel);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        progressBar.setVisibility(ProgressBar.VISIBLE);
        progressBar.setProgress(0);
        mTimeLabel.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!started) {
            Integer lengthInMillis = getLengthInMillis();
            endTime = System.currentTimeMillis() + lengthInMillis;

            timerHandler.postDelayed(mUpdateTimeTask, 100);
            progressHandler.postDelayed(mUpdateProgressTask, 100);

            timerHandler.postDelayed(playSound(R.raw.ding), lengthInMillis / 2);
            timerHandler.postDelayed(playSound(R.raw.ding), (int) (lengthInMillis * 0.75));
            timerHandler.postDelayed(playSound(R.raw.ding), (int) (lengthInMillis * 0.9));
            timerHandler.postDelayed(playSound(R.raw.ding), (int) (lengthInMillis * 0.95));
            timerHandler.postDelayed(playSound(R.raw.ding), (int) (lengthInMillis * 0.99));
            timerHandler.postDelayed(playSound(R.raw.annoying_alarm), lengthInMillis);


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
