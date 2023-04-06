package ro.pub.cs.systems.eim.practicaltest01var03;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PracticalTest01Var03Service extends Service {
    public PracticalTest01Var03Service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int top = intent.getIntExtra(Constants.TOP_TEXT, 0);
        int bottom = intent.getIntExtra(Constants.BOTTOM_TEXT, 0);

        ProcessingThread processingThread = new ProcessingThread(this, top, bottom);
        processingThread.start();

        return Service.START_REDELIVER_INTENT;
    }
}