package comjoshsibayan.github.alarm.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import comjoshsibayan.github.alarm.Alarm;
import comjoshsibayan.github.alarm.R;

import static android.content.Context.ALARM_SERVICE;


public class AlarmScheduler {

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    public AlarmScheduler(Context context) {
        Intent intent = new Intent(context, Alarm.AlarmReceiver.class);
        //intent.setAction(context.getString(R.string.alarmaction)); Shit way to protect your receiver
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
    }

    public void snoozeAlarm(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minutes);
        scheduleAlarm(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

    public void scheduleAlarm(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }

    public void cancelAlarm() {
        alarmManager.cancel(pendingIntent);
    }
}
