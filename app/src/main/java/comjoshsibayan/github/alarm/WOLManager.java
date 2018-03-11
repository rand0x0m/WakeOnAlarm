package comjoshsibayan.github.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import comjoshsibayan.github.alarm.wifi.MagicPacketScheduler;
import comjoshsibayan.github.alarm.wifi.MagicPacketSchedulerSingleton;
import comjoshsibayan.github.alarm.wifi.WifiOperator;
import comjoshsibayan.github.alarm.wifi.WifiOperatorSingleton;


public class WOLManager {

    //private static volatile ReentrantLock lock;
    private static boolean startedWifi;
    private static Byte[] defaultMac;
    private static WifiOperator wifiOperator;

    public WOLManager(Context context, Byte[] defaultMac) {
        this.defaultMac = defaultMac;

        new MagicPacketSchedulerSingleton(context); //Initialize singleton
        new WifiOperatorSingleton(context);

        wifiOperator = WifiOperatorSingleton.getInstance();
    }

    public void send(Byte[] mac) {
        if (!wifiOperator.isWifiEnabled()) {
           wifiOperator.start();
        } else {
            MagicPacketScheduler mps = MagicPacketSchedulerSingleton.getInstance();
            mps.send(mac);
        }
    }

    public void send() {
        send(defaultMac);
    }

    //Sends packet using the default mac of the class.
    //Invoked by ui thread no need for volatile
    public static class ConnectionReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (wifiOperator != null && wifiOperator.isInternalStart() && networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI && networkInfo.isConnected()) {
                MagicPacketScheduler mps = MagicPacketSchedulerSingleton.getInstance();
                mps.send(defaultMac);
            }
        }
    }
}
