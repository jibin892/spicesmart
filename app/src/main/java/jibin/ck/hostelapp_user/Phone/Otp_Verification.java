package jibin.ck.hostelapp_user.Phone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.msg91.sendotpandroid.library.internal.SendOTP;
import com.msg91.sendotpandroid.library.listners.VerificationListener;
import com.msg91.sendotpandroid.library.roots.RetryType;
import com.msg91.sendotpandroid.library.roots.SendOTPConfigBuilder;
import com.msg91.sendotpandroid.library.roots.SendOTPResponseCode;

import jibin.ck.hostelapp_user.R;
import jibin.ck.hostelapp_user.Settings.SaveTheamSatate;


public class Otp_Verification  extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback, VerificationListener {
    private static final String TAG = "VerificationActivity";
    private static final int OTP_LNGTH = 4;
    TextView resend_timer;
    //private EditText mOtpEditText;
    String phoneNumber;
    SaveTheamSatate saveTheamSatate;
    private EditText text1, text2, text3, text4;

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
        setContentView(R.layout.activity_otp__verification);
        resend_timer = (TextView) findViewById(R.id.resend_timer);
        resend_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResendCode();
            }
        });
        startTimer();
        text1 = findViewById(R.id.otp1);
        text2 = findViewById(R.id.otp2);
        text3 = findViewById(R.id.otp3);
        text4 = findViewById(R.id.otp4);
        shiftRequest(text1, text2);
        shiftRequest(text2, text3);
        shiftRequest(text3, text4);
       // mOtpEditText = findViewById(R.id.inputCode);
        enableInputField(true);
        initiateVerification();
    }

    void createVerification(String phoneNumber, int countryCode) {
        new SendOTPConfigBuilder()
                .setCountryCode(+91)
                .setMobileNumber(phoneNumber)
                //////////////////direct verification while connect with mobile network/////////////////////////
                .setVerifyWithoutOtp(true)
                //////////////////Auto read otp from Sms And Verify///////////////////////////
                .setAutoVerification(Otp_Verification.this)
                .setOtpExpireInMinute(5)//default value is one day
                .setOtpHits(10) //number of otp request per number
                .setOtpHitsTimeOut(0L)//number of otp request time out reset in milliseconds default is 24 hours
                .setSenderId("ABCDEF")
                .setMessage("##OTP## is Your verification digits.")
                .setOtpLength(OTP_LNGTH)
                .setVerificationCallBack(this).build();

        SendOTP.getInstance().getTrigger().initiate();


    }


    void initiateVerification() {
        Intent intent = getIntent();
        if (intent != null) {
            DataManager.getInstance().showProgressMessage(this, "");
              phoneNumber = intent.getStringExtra(Phone_Number.INTENT_PHONENUMBER);
            TextView phoneText = (TextView) findViewById(R.id.numberText);
            phoneText.setText("+" + 91 + phoneNumber);
            createVerification(phoneNumber, +91);
        }
    }

    public void ResendCode() {
        startTimer();
        SendOTP.getInstance().getTrigger().resend(RetryType.VOICE);
    }

    public void onSubmitClicked(View view) {
        String code = text1.getText().toString()+text2.getText().toString()+text3.getText().toString()+text4.getText().toString();
        if (!code.isEmpty()) {
            hideKeypad();
            verifyOtp(code);
            DataManager.getInstance().showProgressMessage(this, "");
//            TextView messageText = (TextView) findViewById(R.id.textView);
//            messageText.setText("Verification in progress");
            enableInputField(false);

        }

    }


    void enableInputField(final boolean enable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
              ;
                if (enable) {

                    text1.requestFocus();
                     text2.requestFocus();
                    text3.requestFocus();
                    text4.requestFocus();

                } else {
                 }
                TextView resend_timer = (TextView) findViewById(R.id.resend_timer);
                resend_timer.setClickable(false);
            }
        });

    }

    void hideProgressBarAndShowMessage(int message) {
        hideProgressBar();

    }

    void hideProgressBar() {

     }

    void showProgress() {

    }

    void showCompleted(boolean isDirect) {

        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_LONG);
        //inflate view
        View custom_view = LayoutInflater.from(this).inflate(R.layout.custam_tost,null);
        ((TextView) custom_view.findViewById(R.id.message)).setText("OTP Verification Success");
        ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(getResources().getColor(R.color.colorAccent2));

        toast.setView(custom_view);
        toast.show();
//
//        Intent createaccount = new Intent(this, Create_new_account.class);
//        createaccount.putExtra("phone_number", phoneNumber);
//        createaccount.putExtra("contry_code","+91");
//        startActivity(createaccount);



     }

    public void verifyOtp(String otp) {
        SendOTP.getInstance().getTrigger().verify(otp);
    }


    @Override
    public void onSendOtpResponse(final SendOTPResponseCode responseCode, final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "onSendOtpResponse: " + responseCode.getCode() + "=======" + message);
                if (responseCode == SendOTPResponseCode.DIRECT_VERIFICATION_SUCCESSFUL_FOR_NUMBER || responseCode == SendOTPResponseCode.OTP_VERIFIED) {
                    DataManager.getInstance().hideProgressMessage();
                    enableInputField(false);
                    hideKeypad();
                      TextView phoneText = (TextView) findViewById(R.id.numberText);

                     phoneText.setVisibility(View.GONE);

                    hideProgressBarAndShowMessage(R.string.verified);
                    showCompleted(responseCode == SendOTPResponseCode.DIRECT_VERIFICATION_SUCCESSFUL_FOR_NUMBER);
                } else if (responseCode == SendOTPResponseCode.READ_OTP_SUCCESS) {
                    DataManager.getInstance().hideProgressMessage();
                    //mOtpEditText.setText(message);

                } else if (responseCode == SendOTPResponseCode.SMS_SUCCESSFUL_SEND_TO_NUMBER || responseCode == SendOTPResponseCode.DIRECT_VERIFICATION_FAILED_SMS_SUCCESSFUL_SEND_TO_NUMBER) {
                    DataManager.getInstance().hideProgressMessage();
                } else if (responseCode == SendOTPResponseCode.NO_INTERNET_CONNECTED) {
                    DataManager.getInstance().hideProgressMessage();
                } else {
                    DataManager.getInstance().hideProgressMessage();
                    hideKeypad();
                    hideProgressBarAndShowMessage(R.string.failed);
                    enableInputField(true);
                    Toast.makeText(Otp_Verification.this, "no", Toast.LENGTH_SHORT).show();
                    errordilog();
                }
            }

            private void errordilog() {


                final Dialog dialog = new Dialog(Otp_Verification.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                dialog.setContentView(R.layout.error_dialog);
                dialog.setCancelable(true);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;



                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dialog.getWindow().setAttributes(lp);








            }





        });
    }

    private void startTimer() {
        resend_timer.setClickable(false);
        resend_timer.setTextColor(ContextCompat.getColor(Otp_Verification.this, R.color.colorAccent2));
        new CountDownTimer(30000, 1000) {
            int secondsLeft = 0;

            public void onTick(long ms) {
                if (Math.round((float) ms / 1000.0f) != secondsLeft) {
                    secondsLeft = Math.round((float) ms / 1000.0f);
                    resend_timer.setText("Resend via call ( " + secondsLeft + " )");
                }
            }

            public void onFinish() {
                resend_timer.setClickable(true);
                resend_timer.setText("Resend via call");
                resend_timer.setTextColor(ContextCompat.getColor(Otp_Verification.this, R.color.colorAccent2));
            }
        }.start();
    }

    private void hideKeypad() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SendOTP.getInstance().getTrigger().stop();
    }

    private void shiftRequest(final EditText from, final EditText to) {
        from.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String otp1 = from.getText().toString();
                if (otp1.length() > 0) {
                    from.clearFocus();
                    to.requestFocus();
                    to.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
}
