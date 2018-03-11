package comjoshsibayan.github.alarm.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import comjoshsibayan.github.alarm.MainActivity;
import comjoshsibayan.github.alarm.R;


public class NotificationSingleton {
    private static boolean isRunning;
    private static Notification notification;
    private static NotificationManager notificationManager;

    public NotificationSingleton(Context context) {
        if (notification == null || notificationManager == null) {
            //Snooze
            Intent intentMainActivitySnooze = new Intent(context, MainActivity.class).setAction("SNOOZE").setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent piMainActivitySnooze =
                    PendingIntent.getActivity(context, 2, intentMainActivitySnooze, PendingIntent.FLAG_UPDATE_CURRENT);

            //Stop
            Intent intentMainActivityStop = new Intent(context, MainActivity.class).setAction("STOP").setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent piMainActivityStop =
                    PendingIntent.getActivity(context, 3, intentMainActivityStop, PendingIntent.FLAG_UPDATE_CURRENT);

            this.notification =
                    new Notification.Builder(context)
                            .setContentTitle("Alarm is going off!")
                            .setContentText("Please snooze or stop the alarm")
                            .setOngoing(true)
                            .setAutoCancel(true)
                            .addAction(R.mipmap.double_forward96, "SNOOZE", piMainActivitySnooze)
                            .addAction(R.mipmap.delete96, "STOP", piMainActivityStop)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .build();

            this.notificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    public void deleteNotification() {
        if (isRunning) {
            notificationManager.cancel(2);
            isRunning = false;
        }
    }

    public void sendNotification() {
        if (!isRunning) {
            notificationManager.notify(2, notification);
            isRunning = true;
        }
    }
}
