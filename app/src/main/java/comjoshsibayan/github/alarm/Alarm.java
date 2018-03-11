package comjoshsibayan.github.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import comjoshsibayan.github.alarm.alarm.AlarmScheduler;
import comjoshsibayan.github.alarm.alarm.NotificationSingleton;
import comjoshsibayan.github.alarm.alarm.RingServiceSingleton;


public class Alarm {

    private static NotificationSingleton notificationSingleton;
    private static AlarmScheduler alarmScheduler;
    private static RingServiceSingleton ringServiceSingleton;
    private static State state;
    private static WOLManager wolManager;

    private enum State {
        READY,
        SCHEDULED,
        RINGING
    }

    //Invoked by ui thread, no need for volatile
    public static class AlarmReceiver extends BroadcastReceiver {
        public AlarmReceiver() {}

        @Override
        public void onReceive(Context context, Intent intent) {
            ringServiceSingleton.start();
            notificationSingleton.sendNotification();
            state = State.RINGING;

            Log.d("APPINFO", "STATECHANGE RINGING");
        }
    }

    public Alarm(Context context, Byte[] mac) {
        wolManager = new WOLManager(context, mac);
        notificationSingleton = new NotificationSingleton(context);
        alarmScheduler = new AlarmScheduler(context);
        ringServiceSingleton = new RingServiceSingleton(context);
        state = State.READY;

        Log.d("APPINFO", "STATECHANGE RINGING");
    }

    public void schedule(int hour, int minute) {
        alarmScheduler.scheduleAlarm(hour, minute);
        state = State.SCHEDULED;

        Log.d("APPINFO", "STATECHANGE SCHEDULED");

    }

    public void cancel() {
        if (state == State.SCHEDULED) {
            alarmScheduler.cancelAlarm();
        } else if (state == State.RINGING){
            stop();
        }

        Log.d("APPINFO", "STATECHANGE READY");
    }

    public void stop() {
        if (state == State.RINGING) {
            ringServiceSingleton.stop();
            notificationSingleton.deleteNotification();
            wolManager.send();
            state = State.READY;
        }
            Log.d("APPINFO", "STATECHANGE READY");
    }

    public void snooze(int minute) {
        if (state == State.RINGING) {
            stop(); //Sets state to READY
            alarmScheduler.snoozeAlarm(minute);
            state = State.SCHEDULED;

            Log.d("APPINFO", "STATECHANGE SCHEDULED");
        }
    }

    public static State getState() {
        return state;
    }
}
