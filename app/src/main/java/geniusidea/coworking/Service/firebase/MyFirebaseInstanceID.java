package geniusidea.coworking.Service.firebase;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;

import geniusidea.coworking.Service.Config;

public class MyFirebaseInstanceID extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
      //  saveToken(FirebaseInstanceId.getInstance().getToken());
        storeRegIdInPref(refreshedToken);

    }

    private void storeRegIdInPref(String token){
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.commit();
    }

//    private void saveToken(String tokenId) {
//        FirebaseToken token = new FirebaseToken(tokenId);
//        EventBus.getDefault().postSticky(token);
//    }
}
