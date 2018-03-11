package comjoshsibayan.github.alarm.alarm;

import android.content.Context;
import android.content.Intent;


public class RingServiceSingleton {

    private static boolean isRunning;
    private static Context context;
    private static Intent intent;

    public RingServiceSingleton(Context context) {
        if (context == null || intent == null) {
            RingServiceSingleton.context = context;
            RingServiceSingleton.intent = new Intent(context, RingService.class); //Explicit intent
        }
    }

    public void start() {
        if (!isRunning) {
            isRunning = true;
            context.startService(intent);
        }
    }

    public void stop() {
        if (isRunning) {
            context.stopService(intent);
            isRunning = false;
        }
    }

    public boolean getStatus() {
        return isRunning;
    }
}
