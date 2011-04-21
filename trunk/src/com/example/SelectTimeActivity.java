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

public class SelectTimeActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_time);

        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new MyAdapter(this));
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
                    Intent intent = new Intent(SelectTimeActivity.this, MonitorTimeActivity.class);
                    intent.putExtra("lengthInMillis", buttonValues.lengthInMillis());
                    startActivity(intent);
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
        _30mins("30 mins", 18000);

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