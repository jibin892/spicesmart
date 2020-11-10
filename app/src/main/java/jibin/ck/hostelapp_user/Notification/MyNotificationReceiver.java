package jibin.ck.hostelapp_user.Notification;

import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import jibin.ck.hostelapp_user.Home.Home;

public class MyNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("Message");


        Intent a=new Intent(context, Home.class);
        context.startActivity(a);
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

        //For Messaging Builder
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);

       /* if(remoteInput!=null){
            CharSequence replyText = remoteInput.getCharSequence("text_reply_key");
            DummyMessages answers = new DummyMessages(replyText, null);
            MessagingBuilder.MESSAGES.add(answers);

            MessagingBuilder.sendNotifications(context);
        }*/
    }
}
