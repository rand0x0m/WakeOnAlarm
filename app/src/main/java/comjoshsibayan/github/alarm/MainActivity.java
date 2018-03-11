package comjoshsibayan.github.alarm;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TimePicker;


public class MainActivity extends AppCompatActivity {

    private Switch alarmSwitch;
    private TimePicker timePicker;
    private Alarm alarm;
    private Byte[] mac = new Byte[]{}; //Enter your pc's mac here!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        alarmSwitch = (Switch) findViewById(R.id.switch1);
        timePicker = (TimePicker) findViewById(R.id.time_picker);
        alarm = new Alarm(getApplicationContext(), mac);

        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
               if (isChecked) {
                   alarm.schedule(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
               } else {
                   alarm.cancel();
               }
           }});
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        alarm.stop();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String action = intent.getAction();
        if (action.equals("STOP")) {
            alarmSwitch.setChecked(false);
            alarm.stop();
        } else if (action.equals("SNOOZE")) {
            alarm.snooze(5);
        }
    }
}
