package jibin.ck.hostelapp_user.Phone;

import android.app.Application;

import com.msg91.sendotpandroid.library.internal.SendOTP;


public class ApplicationClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SendOTP.initializeApp(this);
    }
}
