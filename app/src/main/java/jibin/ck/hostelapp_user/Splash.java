package jibin.ck.hostelapp_user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import jibin.ck.hostelapp_user.Home.Home;
import jibin.ck.hostelapp_user.Login.Login_page;
import jibin.ck.hostelapp_user.Phone.Phone_Number;
import jibin.ck.hostelapp_user.Settings.SaveTheamSatate;
import jibin.ck.hostelapp_user.Settings.Settings_home;

public class Splash extends AppCompatActivity {
    SaveTheamSatate saveTheamSatate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        saveTheamSatate = new SaveTheamSatate(this);
        if(saveTheamSatate.looadNightModeState()){
            setTheme(R.style.NightMode);
        }else {
            setTheme(R.style.DayMode);
        }
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(Splash.this, Home.class);
                startActivity(i);
                finish();
            }
        }, 1500);


    }

}
