package jibin.ck.hostelapp_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import java.util.Objects;

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
        EnableRuntimePermission();
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
    public void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(this),
                Manifest.permission.READ_PHONE_NUMBERS)) {

// Toast.makeText(Cpature_image.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(Splash.this, new String[]{
                    Manifest.permission.FOREGROUND_SERVICE,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, 12);


        }
    }
}
