package comjoshsibayan.github.alarm.wifi;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Observable;


public class MagicPacket extends Observable {

    public void send(Byte[] mac) {
        MagicPacketSender mps = new MagicPacketSender();
        this.setChanged();

        Log.d("APPINFO", "PACKET SENT!");
        mps.execute(mac);
    }

    private class MagicPacketSender extends AsyncTask<Byte[], Void, AsyncTaskResult<Boolean>> {

        @Override
        protected AsyncTaskResult<Boolean> doInBackground(Byte[]... bytes) {
            Byte[] mac;
            if (bytes != null && bytes.length >= 1) {
                if (bytes[0].length != 6) {
                    return new AsyncTaskResult<>(new IllegalArgumentException("Mac address length must be 6 bytes"));
                }
                mac = bytes[0];
            } else {
                return new AsyncTaskResult<>(new IllegalArgumentException("No arguments were provided"));
            }

            DatagramSocket socket = null;
            try {
                byte[] magicPacket = new byte[6 + 16 * mac.length];
                createMagicPacket(magicPacket, mac);

                //Enter your network's broadcast address and WOL port of your pc here.
                DatagramPacket packet = new DatagramPacket(magicPacket, magicPacket.length, InetAddress.getByName("255.255.255.255"), 9);
                socket = new DatagramSocket();
                socket.send(packet);
            } catch (IOException e) {
            } finally {
                if (socket != null) {
                    socket.close();

                }
            }

            return new AsyncTaskResult<>(true);
        }

        @Override
        protected void onPostExecute(AsyncTaskResult<Boolean> booleanAsyncTaskResult) {
            super.onPostExecute(booleanAsyncTaskResult);
            notifyObservers(booleanAsyncTaskResult);
            deleteObservers();
        }

        private void createMagicPacket(byte[] magicPacket, Byte[] mac) {
            for (int i = 0; i < 6; i++) {
                magicPacket[i] = (byte) 0xff;
            }

            for (int i = 1; i <= 16; i++) {
                for (int j = 0; j < 6; j++) {
                    magicPacket[i * 6 + j] = mac[j];
                }
            }
        }
    }
}