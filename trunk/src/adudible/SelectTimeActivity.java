package adudible;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import com.example.R;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

public class SelectTimeActivity extends Activity {
    private int customLength;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_time);

        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new DynamicButtonGridAdapter(this));

        setText(R.id.introText, "Select  a preset length of time:");
        setText(R.id.wheelText, "Select your own length of time:");

        buildWheel();
    }

    private void buildWheel() {
        final WheelView wheelMins = (WheelView) findViewById(R.id.wheel_mins);
        wheelMins.setViewAdapter(new NumericWheelAdapter(this, 0, 59, "%02d"));

        final WheelView wheelSecs = (WheelView) findViewById(R.id.wheel_secs);
        wheelSecs.setViewAdapter(new NumericWheelAdapter(this, 0, 59, "%02d"));

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

    private void setText(int id, String text) {
        TextView introText = (TextView) findViewById(id);
        introText.setText(text);
    }

    private void submitLength(int lengthInMillis) {
        Intent intent = new Intent(SelectTimeActivity.this, MonitorTimeActivity.class);
        intent.putExtra("lengthInMillis", lengthInMillis);
        startActivity(intent);
    }

    private enum PresetValue {
        CupOfTea("Cup of Tea", 180),
        StandUp("Stand Up", 420),
        _5mins("5 mins", 300),
        _10mins("10 mins", 600),
        _20mins("20 mins", 1200),
        _30mins("30 mins", 1800);

        public final String buttonText;
        public final Integer lengthInSeconds;

        private PresetValue(String buttonText, Integer lengthInSeconds) {
            this.buttonText = buttonText;
            this.lengthInSeconds = lengthInSeconds;
        }

        public int lengthInMillis() {
            return lengthInSeconds * 1000;
        }
    }

    private class DynamicButtonGridAdapter extends BaseAdapter {

        private SelectTimeActivity selectTimeActivity;

        public DynamicButtonGridAdapter(SelectTimeActivity selectTimeActivity) {
            this.selectTimeActivity = selectTimeActivity;
        }

        public int getCount() {
            return SelectTimeActivity.PresetValue.values().length;
        }

        public Object getItem(int i) {
            return SelectTimeActivity.PresetValue.values()[i];
        }

        public long getItemId(int i) {
            return 0;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            Button button = new Button(selectTimeActivity);
            final SelectTimeActivity.PresetValue presetValue = SelectTimeActivity.PresetValue.values()[i];
            button.setText(presetValue.buttonText);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    int lengthInMillis = presetValue.lengthInMillis();
                    selectTimeActivity.submitLength(lengthInMillis);
                }
            });
            return button;
        }
    }
}