package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.TextView;

public class MonitorTimeActivity extends Activity {
    
    private long length = 5 * 1000;
    private long endTime;

    private TextView mTimeLabel;
    private Handler mHandler = new Handler();


    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > endTime) {
                mTimeLabel.setText("Get out of there");
            }
            else {
                mTimeLabel.setText("" + (endTime - System.currentTimeMillis()));
                mHandler.postDelayed(mUpdateTimeTask, 100);
            }
        }
    };


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_progress);

        mTimeLabel = (TextView) findViewById(R.id.timeLabel);
    }


    @Override
    protected void onStart() {
        super.onStart();
        endTime = System.currentTimeMillis()+ length;
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }
}
