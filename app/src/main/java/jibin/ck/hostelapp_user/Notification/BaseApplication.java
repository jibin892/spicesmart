package jibin.ck.hostelapp_user.Notification;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class BaseApplication extends Application {
    public static final String CATEGORY_1 = "HIGH_CATEGORY";
    public static final String CATEGORY_2 = "LOW_CATEGORY";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationCategory();
    }

    private void createNotificationCategory() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel category1 = new NotificationChannel(
                    CATEGORY_1,
                    "High Category",
                    NotificationManager.IMPORTANCE_HIGH
            );
            category1.setDescription("This is high category message");

            NotificationChannel category2 = new NotificationChannel(
                    CATEGORY_2,
                    "Low Category",
                    NotificationManager.IMPORTANCE_LOW
            );
            category2.setDescription("This is low category message");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if(notificationManager != null){
                notificationManager.createNotificationChannel(category1);
                notificationManager.createNotificationChannel(category2);
            }
        }
    }
}
