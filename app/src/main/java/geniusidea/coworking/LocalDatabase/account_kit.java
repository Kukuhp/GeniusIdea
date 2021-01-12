package geniusidea.coworking.LocalDatabase;

import android.content.Context;

import com.orm.SugarContext;

public class account_kit extends com.orm.SugarApp{
    public static final String TAG = account_kit.class
            .getSimpleName();

    private static account_kit mInstance;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        SugarContext.init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

}
