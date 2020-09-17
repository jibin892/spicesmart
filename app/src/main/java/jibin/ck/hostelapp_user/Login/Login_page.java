package jibin.ck.hostelapp_user.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import jibin.ck.hostelapp_user.Facebook.Facebook_login;
import jibin.ck.hostelapp_user.Google.Google_login;
import jibin.ck.hostelapp_user.Phone.Phone_Number;
import jibin.ck.hostelapp_user.R;
import jibin.ck.hostelapp_user.Settings.SaveTheamSatate;
import jibin.ck.hostelapp_user.Splash;

public class Login_page extends AppCompatActivity {
    SaveTheamSatate saveTheamSatate;
    private BottomSheetDialog mBottomSheetDialog;
    FloatingActionButton phonenuberbutton,googlebutton,facebookbutton;
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
        setContentView(R.layout.activity_login_page);
        phonenuberbutton= findViewById(R.id.phonenuberbutton);
        googlebutton= findViewById(R.id.googlebutton);
        facebookbutton= findViewById(R.id.facebookbutton);
  phonenuberbutton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {


          Intent ph = new Intent( Login_page.this, Phone_Number.class);
          startActivity(ph);

      }
  });


        googlebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent ph = new Intent( Login_page.this, Google_login.class);
                startActivity(ph);

            }
        });


        facebookbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent ph = new Intent( Login_page.this, Facebook_login.class);
                startActivity(ph);

            }
        });


    }

//    private void darkmode() {
//
//
//
//        final View view = getLayoutInflater().inflate(R.layout.nightmoode, null);
//        SwitchCompat switchCompat=view.findViewById(R.id.switchButtonnight);
//        if(saveTheamSatate.looadNightModeState()){
//            switchCompat.setChecked(true);
//        }
//
//        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    saveTheamSatate.setNightModeState(true);
//                    restartApp();
//                }else {
//                    saveTheamSatate.setNightModeState(false);
//                    restartApp();
//                }
//            }
//
//            private void restartApp() {
//                Intent i = new Intent( Login_page.this, Splash.class);
//                startActivity(i);
//                finish();
//            }
//        });
//        mBottomSheetDialog = new BottomSheetDialog(this);
//        mBottomSheetDialog.setContentView(view);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mBottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//
//        mBottomSheetDialog.show();
//        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                mBottomSheetDialog = null;
//            }
//        });
//
//
//
//
//    }
}


