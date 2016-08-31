package comjoshsibayan.github.alarm;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Josh on 8/31/2016.
 */
public class RingtonePlayingService extends Service {

    MediaPlayer media_song;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        // Create instance of media player
        media_song = MediaPlayer.create(this, R.raw.kalimba);
        media_song.start();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // Tell the user we stopped.
        Toast.makeText(this, "onDestroy called", Toast.LENGTH_SHORT).show();
    }

}