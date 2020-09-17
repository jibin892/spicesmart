package jibin.ck.hostelapp_user.Aother;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveLocationSatate {

    SharedPreferences mySharedPref;

    public SaveLocationSatate(Context context){
        mySharedPref = context.getSharedPreferences("filename", Context.MODE_PRIVATE);
    }

    public void setNightModeState(Boolean state){
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putBoolean("1",state);
        editor.apply();
    }

    public Boolean looadlocationState(){
        Boolean state = mySharedPref.getBoolean("1",false);
        return state;
    }
}
