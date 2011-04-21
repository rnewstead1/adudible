package com.example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

public class SelectTimeActivity extends Activity {
    private int customLength;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_time);

        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new MyAdapter(this));

        TextView introText = (TextView) findViewById(R.id.introText);
        introText.setText("Select  a preset length of time:");


        TextView wheelText = (TextView) findViewById(R.id.wheelText);
        wheelText.setText("Select your own length of time:");

        final WheelView wheelMins = (WheelView) findViewById(R.id.wheel_mins);
        wheelMins.setViewAdapter(new NumericWheelAdapter(this, 0, 59, "%02d"));
        wheelMins.setCyclic(false);

        final WheelView wheelSecs = (WheelView) findViewById(R.id.wheel_secs);
        wheelSecs.setViewAdapter(new NumericWheelAdapter(this, 0, 59, "%02d"));
        wheelSecs.setCyclic(false);

        OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                customLength = wheelMins.getCurrentItem() * 1000 * 60
                        + wheelSecs.getCurrentItem() * 1000;
            }
        };

        wheelMins.addChangingListener(wheelListener);
        wheelSecs.addChangingListener(wheelListener);

        Button customLengthButton = (Button) findViewById(R.id.customLengthButton);
        customLengthButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                submitLength(customLength);
            }
        });
    }

    private void submitLength(int lengthInMillis) {
        Intent intent = new Intent(SelectTimeActivity.this, MonitorTimeActivity.class);
        intent.putExtra("lengthInMillis", lengthInMillis);
        startActivity(intent);
    }

    private class MyAdapter extends BaseAdapter {
        private final Context context;

        private MyAdapter(Context context) {
            this.context = context;
        }

        public int getCount() {
            return ButtonValues.values().length;
        }

        public Object getItem(int i) {
            return ButtonValues.values()[i];
        }

        public long getItemId(int i) {
            return 0;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            Button button = new Button(SelectTimeActivity.this);
            final ButtonValues buttonValues = ButtonValues.values()[i];
            button.setText(buttonValues.buttonText);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    int lengthInMillis = buttonValues.lengthInMillis();
                    submitLength(lengthInMillis);
                }
            });
            return button;
        }

    }

    private enum ButtonValues {
        _10secs("10 secs", 10),
        _30secs("30 secs", 30),
        _5mins("5 mins", 300),
        _10mins("10 mins", 600),
        _20mins("20 mins", 1200),
        _30mins("30 mins", 1800);

        public final String buttonText;
        public final Integer lengthInSeconds;

        private ButtonValues(String buttonText, Integer lengthInSeconds) {
            this.buttonText = buttonText;
            this.lengthInSeconds = lengthInSeconds;
        }

        public int lengthInMillis() {
            return lengthInSeconds * 1000;
        }
    }
}