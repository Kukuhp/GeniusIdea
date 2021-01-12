package geniusidea.coworking;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import geniusidea.coworking.LocalDatabase.Data_User_Login;
import geniusidea.coworking.LocalDatabase.Data_User_Token;

public class MainActivity extends AppCompatActivity {
    Handler handler;
    Runnable myRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

   //     Data_User_Login data_user_login = Data_User_Login.findById(Data_User_Login.class,1L);
        Data_User_Token data = Data_User_Token.findById(Data_User_Token.class,1L);
        if((int)data.count(Data_User_Login.class, "", null) == 0) {
            Data_User_Token data3 = new Data_User_Token("");
            data3.save();
            Data_User_Login data1 = new Data_User_Login(false);
            data1.save();
        }

        handler =  new Handler();
        myRunnable = new Runnable() {
            public void run() {
                // do something
                startActivity(new Intent(MainActivity.this,Utama.class));
            }
        };
        handler.postDelayed(myRunnable,2000);
    }
}
