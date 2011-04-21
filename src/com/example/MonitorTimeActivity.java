package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MonitorTimeActivity extends Activity
{
    private long mStartTime;

    private TextView mTimeLabel;
    private Handler mHandler = new Handler();


    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            mTimeLabel.setText("" + System.currentTimeMillis());
            mHandler.postDelayed(mUpdateTimeTask, 100);
        }
    };


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_progress);

//        mTimeLabel = (TextView) findViewById(R.id.timeLabel);
//        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                if (mStartTime == 0L) {
//                    mStartTime = System.currentTimeMillis();
//                    //mHandler.removeCallbacks(mUpdateTimeTask);
//                    mHandler.postDelayed(mUpdateTimeTask, 100);
//                }
//            }
//        });

    }
}
