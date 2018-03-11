package comjoshsibayan.github.alarm.wifi;

import android.content.Context;
import android.net.wifi.WifiManager;


public class WifiOperator {
    private WifiManager wifiManager;
    private boolean internalStart;

    public WifiOperator(Context context) {
        wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    public void start() {
        boolean previous = wifiManager.isWifiEnabled();
        if (!previous) {
            wifiManager.setWifiEnabled(true);
            internalStart = true;
        }
    }

    public void reset() {
        if (internalStart) {
            wifiManager.setWifiEnabled(false);
            internalStart = false;
        }
    }

    public boolean isWifiEnabled() {
        return wifiManager.isWifiEnabled();
    }
    
    public boolean isInternalStart() {
        return internalStart;
    }
}