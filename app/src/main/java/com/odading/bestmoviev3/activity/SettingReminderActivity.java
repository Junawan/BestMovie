package com.odading.bestmoviev3.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.odading.bestmoviev3.R;
import com.odading.bestmoviev3.schedule.AlarmReceiverDaily;

public class SettingReminderActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    Switch dailyreminder, releaseReminder;
    SharedPreferences sharedPreferences;

    private AlarmReceiverDaily alarmReceiver;

    public static final String TAG = SettingReminderActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_reminder);

        alarmReceiver = new AlarmReceiverDaily();

        dailyreminder = findViewById(R.id.daily_reminder);
        releaseReminder = findViewById(R.id.release_reminder);

        dailyreminder.setOnCheckedChangeListener(this);
        releaseReminder.setOnCheckedChangeListener(this);

        boolean value = false; // default value if no value was found

        sharedPreferences = getSharedPreferences("isChecked", 0);
        sharedPreferences = getSharedPreferences("isCheckedd", 0);

        value = sharedPreferences.getBoolean("isChecked", value); // retrieve the value of your key
        dailyreminder.setChecked(value);
        value = sharedPreferences.getBoolean("isCheckedd", value);
        releaseReminder.setChecked(value);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {
            case R.id.daily_reminder:
                if (isChecked) {
                    String repeatTime = "07:00";
                    String repeatMessage = "Sudahkah kamu cek film terbaru? Ayo buka aplikasinya";

                    alarmReceiver.setRepeatingAlarm(this, AlarmReceiverDaily.TYPE_REPEATING, repeatTime, repeatMessage);
                    sharedPreferences.edit().putBoolean("isChecked", true).apply();
                    Toast.makeText(this, "Pengingat dinyalakan", Toast.LENGTH_SHORT).show();
                } else {
                    alarmReceiver.cancelAlarm(this, AlarmReceiverDaily.TYPE_REPEATING);
                    sharedPreferences.edit().putBoolean("isChecked", false).apply();
                    Toast.makeText(this, "Pengingat dimatikan", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.release_reminder:
                if (isChecked) {
                    alarmReceiver.setOneTimeAlarm(this, AlarmReceiverDaily.TYPE_ONE_TIME);
                    sharedPreferences.edit().putBoolean("isCheckedd", true).apply();
                    Toast.makeText(this, "Pengingat dinyalakan", Toast.LENGTH_SHORT).show();
                } else {
                    alarmReceiver.cancelAlarm(this, AlarmReceiverDaily.TYPE_ONE_TIME);
                    sharedPreferences.edit().putBoolean("isCheckedd", false).apply();
                    Toast.makeText(this, "Pengingat dimatikan", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
