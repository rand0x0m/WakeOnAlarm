package comjoshsibayan.github.alarm.wifi;

import android.content.Context;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;


public class MagicPacketScheduler implements Observer {

    private HashSet<MagicPacket> packetsSet;

    public MagicPacketScheduler(Context context) {
        packetsSet = new HashSet<>();
    }

    //update is called from the ui thread
    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof MagicPacket) {
            packetsSet.remove(observable);
            if (packetsSet.isEmpty()) {
                WifiOperator wifiOperator = WifiOperatorSingleton.getInstance();
                if (wifiOperator != null) {
                    wifiOperator.reset();
                }
            }
        }
    }

    public void send(Byte[] mac) {
        MagicPacket mp = new MagicPacket();
        mp.addObserver(this);
        packetsSet.add(mp);
        mp.send(mac);
    }
}
