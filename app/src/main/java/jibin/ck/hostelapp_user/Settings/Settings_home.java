package jibin.ck.hostelapp_user.Settings;

import android.content.Intent;
import android.os.Bundle;

import android.view.WindowManager;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import jibin.ck.hostelapp_user.R;
import jibin.ck.hostelapp_user.Splash;


public class Settings_home extends AppCompatActivity {

    private SwitchCompat changeSettings;
    private SaveTheamSatate saveTheamSatate;

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
        setContentView(R.layout.activity_settings);

        changeSettings = findViewById(R.id.switchButton);

        if(saveTheamSatate.looadNightModeState()){
            changeSettings.setChecked(true);
        }

        changeSettings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    saveTheamSatate.setNightModeState(true);
                    restartApp();
                }else {
                    saveTheamSatate.setNightModeState(false);
                    restartApp();
                }
            }
        });
    }

    private void restartApp() {
        Intent i = new Intent( this, Splash.class);
        startActivity(i);
        finish();
    }
}
