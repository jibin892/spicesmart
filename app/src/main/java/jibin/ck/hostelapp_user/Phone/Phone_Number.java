package jibin.ck.hostelapp_user.Phone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.msg91.sendotpandroid.library.internal.Iso2Phone;
import com.msg91.sendotpandroid.library.utils.PhoneNumberFormattingTextWatcher;
import com.msg91.sendotpandroid.library.utils.PhoneNumberUtils;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import jibin.ck.hostelapp_user.R;
import jibin.ck.hostelapp_user.Settings.SaveTheamSatate;


public class Phone_Number extends AppCompatActivity {
    public static final String INTENT_PHONENUMBER = "phonenumber";
    public static final String INTENT_COUNTRY_CODE = "code";

    private EditText mPhoneNumber;
    String mPhoneNumber1;
    private Button mSmsButton;
    private String mCountryIso;
    private boolean loggedIn = false;
    SharedPreferences log, sharedPreferences;
    SaveTheamSatate saveTheamSatate;
    String imeiSIM1,imeiSIM2;
    String simTwoNumber,simOneNumber;
    private   TelephonyManager telephonyManager;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        saveTheamSatate = new SaveTheamSatate(this);
        if (saveTheamSatate.looadNightModeState()) {
            setTheme(R.style.NightMode);
        } else {
            setTheme(R.style.DayMode);
        }
        setContentView(R.layout.activity_phone__number);
        log = getSharedPreferences("log", MODE_PRIVATE);
        loggedIn = log.getBoolean("id", false);
//        sharedPreferences = getSharedPreferences("phone", MODE_PRIVATE);
//        if (loggedIn) {
//            startActivity(new Intent(getApplicationContext(), Home_main.class));
//            // Snackbar.make(v,"Enter emergency number",Snackbar.LENGTH_SHORT).show();
//
//        }
        EnableRuntimePermission();
        mPhoneNumber = findViewById(R.id.phoneNumber);
        mSmsButton = findViewById(R.id.smsVerificationButton);
        mSmsButton.setVisibility(View.GONE);

        mPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    mPhoneNumber.setHint("Enter Phone Number");
                } else {
                    mPhoneNumber.setHint(" ");
                }
            }
        });

        mPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //write code here, what it should do before text is entered
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 10) {
                    mSmsButton.setVisibility(View.VISIBLE);
                    mSmsButton.animate()
                            .setInterpolator(new BounceInterpolator())
                            .setDuration(2000)
                            .scaleXBy(0)
                            .scaleX(1)
                            .scaleYBy(0)
                            .scaleY(1);
                } else if (charSequence.length() < 10) {
                    mSmsButton.animate().setInterpolator(new BounceInterpolator()).scaleXBy(1).scaleX(0).scaleYBy(1).scaleY(0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //write code here, what it should do after text is entered,
                //that is validations.
            }
        });

        telephonyManager = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            //  loaddilog();

            return;
        }
        telephonyManager.getLine1Number();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                SubscriptionManager subManager = (SubscriptionManager) getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
                List<SubscriptionInfo> subInfoList = null;
                subInfoList = subManager.getActiveSubscriptionInfoList();
                if (subInfoList != null && subInfoList.size() > 0) {
                    switch (subInfoList.size()) {
                        case 2:
                            simTwoNumber = subInfoList.get(1).getNumber();
                        case 1:
                            simOneNumber = subInfoList.get(0).getNumber();
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



}








    private void tryAndPrefillPhoneNumber() {
        if (checkCallingOrSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            mPhoneNumber.setText(manager.getLine1Number().substring(2));
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        //    loaddilog();
            tryAndPrefillPhoneNumber();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "This application needs permission to read your phone number to automatically "
                        + "pre-fill it", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void openActivity(String phoneNumber) {
        Intent verification = new Intent(this, Otp_Verification.class);
        verification.putExtra(INTENT_PHONENUMBER, phoneNumber);
        verification.putExtra(INTENT_COUNTRY_CODE,"+91");
        startActivity(verification);
    }

    private void setButtonsEnabled(boolean enabled) {
        mSmsButton.setEnabled(enabled);
    }

    public void onButtonClicked(View view) {
        openActivity(getE164Number());
    }



    private boolean isPossiblePhoneNumber() {
        return PhoneNumberUtils.isPossibleNumber(mPhoneNumber.getText().toString(), "+91");
    }

    private String getE164Number() {
        return mPhoneNumber.getText().toString().replaceAll("\\D", "").trim();
        // return PhoneNumberUtils.formatNumberToE164(mPhoneNumber.getText().toString(), mCountryIso);
    }



    public void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(this),
                Manifest.permission.READ_PHONE_NUMBERS)) {

// Toast.makeText(Cpature_image.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(Phone_Number.this, new String[]{
                   Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_SMS,Manifest.permission.READ_PHONE_NUMBERS}, 12);


        }
    }
}
