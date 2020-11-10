package jibin.ck.hostelapp_user.Home;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;

import jibin.ck.hostelapp_user.Music.Muzic_home;
import jibin.ck.hostelapp_user.OnSwipeTouchListener;
import jibin.ck.hostelapp_user.R;
import jibin.ck.hostelapp_user.Settings.Settings_home;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_four extends Fragment {

LinearLayout swiping;
    public Fragment_four() {
        // Required empty public constructor
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_four,container, false);
        swiping=view.findViewById(R.id.nsv3);
        swiping.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            public void onSwipeTop() {

                Toast.makeText(getActivity(), "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(getActivity(), "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Toast.makeText(getActivity(), "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Intent a=new Intent(getActivity(), Muzic_home.class);
                startActivity(a);
                Toast.makeText(getActivity(), "bottom", Toast.LENGTH_SHORT).show();
            }

        });

return  view;

    }


}

