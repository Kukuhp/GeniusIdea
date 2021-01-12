package geniusidea.coworking.Service.firebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import geniusidea.coworking.Service.PushService;
import geniusidea.coworking.Service.Restarter;

public class BroadcastReceiverFirebase extends BroadcastReceiver {
    private static final String TAG = "RestartServiceReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive");
        //Toast.makeText(context, "Firebase and Restarter Active Again"     , Toast.LENGTH_LONG).show();
        context.startService(new Intent(context.getApplicationContext(), MyFirebaseMessagingService.class));
        context.startService(new Intent(context.getApplicationContext(), PushService.class));
        context.startService(new Intent(context.getApplicationContext(), Restarter.class));

//        Data_User_Setting data3 = Data_User_Setting.findById(Data_User_Setting.class,1L);
//        if(data3.able_work == 1)
//        {
//            context.startService(new Intent(context.getApplicationContext(), UpdateLocationService.class));
//        }
    }
}
