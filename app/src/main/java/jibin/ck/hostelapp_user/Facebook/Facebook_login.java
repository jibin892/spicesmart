package jibin.ck.hostelapp_user.Facebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import jibin.ck.hostelapp_user.R;
import jibin.ck.hostelapp_user.Settings.SaveTheamSatate;

public class Facebook_login extends AppCompatActivity {
    SaveTheamSatate saveTheamSatate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        saveTheamSatate = new SaveTheamSatate(this);
        if(saveTheamSatate.looadNightModeState()){
            setTheme(R.style.NightMode);
        }else {
            setTheme(R.style.DayMode);
        }
        setContentView(R.layout.activity_facebook_login);
    }
}