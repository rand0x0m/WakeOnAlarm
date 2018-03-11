package comjoshsibayan.github.alarm.alarm;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import comjoshsibayan.github.alarm.R;


public class RingService extends Service {

    private MediaPlayer mediaSong;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaSong = MediaPlayer.create(this, R.raw.arrival_of_birds);
        mediaSong.setLooping(true);
        mediaSong.start();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaSong.isPlaying()) {
            mediaSong.stop();
        }
    }
}
