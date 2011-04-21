package com.example;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Timer {
    private static Timer timer;
    private boolean started;
    private long endTime;

    private Handler timerHandler = new Handler();
    private Context context;
    private TextView mTimeLabel;
    private ProgressBar progressBar;
    private int lengthInMillis;

    public synchronized static Timer getTimer() {
        if (timer == null) {
            timer = new Timer();
        }
        return timer;
    }

    private Timer() {}

    public void go(int lengthInMillis) {
        if (!started) {
            this.lengthInMillis = lengthInMillis;
            endTime = System.currentTimeMillis() + lengthInMillis;

            timerHandler.postDelayed(mUpdateTimeTask, 100);

            timerHandler.postDelayed(playSound(R.raw.ding), lengthInMillis / 2);
            timerHandler.postDelayed(playSound(R.raw.ding), (int) (lengthInMillis * 0.75));
            timerHandler.postDelayed(playSound(R.raw.ding), (int) (lengthInMillis * 0.9));
            timerHandler.postDelayed(playSound(R.raw.ding), (int) (lengthInMillis * 0.95));
            timerHandler.postDelayed(playSound(R.raw.ding), (int) (lengthInMillis * 0.99));
            timerHandler.postDelayed(playSound(R.raw.annoying_alarm), lengthInMillis);


            started = true;
        }
    }

    public void register(Context context, TextView mTimeLabel, ProgressBar progressBar) {
        this.context = context;
        this.mTimeLabel = mTimeLabel;
        this.progressBar = progressBar;
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > endTime) {
                mTimeLabel.setText("Get out of there");
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

                progressBar.setProgress(progressBar.getMax() - (int) ((double) progressBar.getMax() * ((double) elapsedMillis / (double) lengthInMillis)));
                timerHandler.postDelayed(mUpdateTimeTask, 100);
            }
        }
    };

    public void reset() {
        started = false;
    }

    private Runnable playSound(final int sound) {
        return new Runnable() {
            public void run() {
                MediaPlayer mp = MediaPlayer.create(context, sound);
                mp.start();
            }
        };
    }


}
