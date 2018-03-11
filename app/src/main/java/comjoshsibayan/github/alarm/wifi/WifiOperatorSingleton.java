package comjoshsibayan.github.alarm.wifi;

import android.content.Context;
import android.support.annotation.Nullable;


public class WifiOperatorSingleton{

    private static WifiOperator wifiOperator;

    public WifiOperatorSingleton(Context context) {
        if (wifiOperator == null) {
            wifiOperator = new WifiOperator(context);
        }
    }

    @Nullable
    public static WifiOperator getInstance() {
        if (wifiOperator != null) {
            return wifiOperator;
        }
        return null;
    }
}
