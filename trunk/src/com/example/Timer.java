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
    private Runnable dingTask;
    private Runnable annoyingTask;
    private MediaPlayer dingPlayer;
    private MediaPlayer annoyingPlayer;

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

            dingPlayer = MediaPlayer.create(context, R.raw.ding);
            annoyingPlayer = MediaPlayer.create(context, R.raw.annoying_alarm);
            dingTask = playSound(dingPlayer);
            annoyingTask = playSound(annoyingPlayer);

            timerHandler.postDelayed(dingTask, lengthInMillis / 2);
            timerHandler.postDelayed(dingTask, (int) (lengthInMillis * 0.75));
            timerHandler.postDelayed(dingTask, (int) (lengthInMillis * 0.9));
            timerHandler.postDelayed(dingTask, (int) (lengthInMillis * 0.95));
            timerHandler.postDelayed(dingTask, (int) (lengthInMillis * 0.99));
            timerHandler.postDelayed(annoyingTask, lengthInMillis);


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
        timerHandler.removeCallbacks(annoyingTask);
        timerHandler.removeCallbacks(dingTask);
        timerHandler.removeCallbacks(mUpdateTimeTask);
        if(dingPlayer.isPlaying()) {
            dingPlayer.stop();
        }
        if(annoyingPlayer.isPlaying()) {
            annoyingPlayer.stop();
        }
        started = false;
    }

    private Runnable playSound(final MediaPlayer mediaPlayer) {
        return new Runnable() {
            public void run() {
                MediaPlayer mp = mediaPlayer;
                mp.start();
            }
        };
    }


}
