package comjoshsibayan.github.alarm.wifi;

import android.content.Context;
import android.support.annotation.Nullable;


public class MagicPacketSchedulerSingleton {
    private static MagicPacketScheduler magicPacketScheduler;

    //Should be protected :P
    public MagicPacketSchedulerSingleton(Context context) {
        if (magicPacketScheduler == null) {
            magicPacketScheduler = new MagicPacketScheduler(context);
        }
    }

    @Nullable
    public static MagicPacketScheduler getInstance() {
        if (magicPacketScheduler != null) {
            return magicPacketScheduler;
        } else {
            return null;
        }
    }
}
