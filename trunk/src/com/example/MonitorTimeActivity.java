package com.example;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MonitorTimeActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_progress);

        TextView mTimeLabel = (TextView) findViewById(R.id.timeLabel);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Timer timer = Timer.getTimer();
        timer.register(getBaseContext(), mTimeLabel, progressBar);
        timer.go(getLengthInMillis());
        progressBar.setProgress(0);
        View screen = findViewById(R.id.wholeScreen);
        screen.setOnClickListener(new android.view.View.OnClickListener() {
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
    public void finish() {
        Timer.getTimer().reset();
        super.finish();
    }

    private Integer getLengthInMillis() {
        return (Integer) getIntent().getExtras().get("lengthInMillis");
    }
}
