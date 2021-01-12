package geniusidea.coworking.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;


import androidx.annotation.Nullable;

import geniusidea.coworking.Service.firebase.BroadcastReceiverFirebase;
import geniusidea.coworking.Service.firebase.MyFirebaseMessagingService;

/**
 * Created by HP-PC on 05/04/2018.
 */

public class PushService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            /*getApplicationContext().startForegroundService(new Intent(getApplicationContext(), MyFirebaseMessagingService.class));
            startForeground(1, new Notification());*/
        }
        else
        {
            startService(new Intent(getApplicationContext(), MyFirebaseMessagingService.class));
        }
        return START_STICKY;

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        //super.onTaskRemoved(rootIntent);
        PendingIntent service = PendingIntent.getService(
                getApplicationContext(),
                1001,
                new Intent(getApplicationContext(), BroadcastReceiver.class),
                PendingIntent.FLAG_ONE_SHOT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 2000, service);
        sendBroadcast(new Intent("restartFirebase"));
        //Toast.makeText(getApplicationContext(),"OnTask Removed",Toast.LENGTH_SHORT).show();

        /*startService(new Intent(getApplicationContext(), Restarter.class));
        startService(new Intent(getApplicationContext(), PushService.class));*/

    }

    @Override
    public void onDestroy() {
        //super.onDestroy();
        PendingIntent service = PendingIntent.getService(
                getApplicationContext(),
                1001,
                new Intent(getApplicationContext(), BroadcastReceiverFirebase.class),
                PendingIntent.FLAG_ONE_SHOT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 2000, service);
        sendBroadcast(new Intent("restartFirebase"));
        //Toast.makeText(getApplicationContext(),"On Destroy",Toast.LENGTH_SHORT).show();

        /*startService(new Intent(getApplicationContext(), Restarter.class));
        startService(new Intent(getApplicationContext(), PushService.class));*/

    }


}
