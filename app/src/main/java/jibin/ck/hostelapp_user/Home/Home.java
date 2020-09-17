package jibin.ck.hostelapp_user.Home;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import jibin.ck.hostelapp_user.Aother.SaveLocationSatate;
import jibin.ck.hostelapp_user.Facebook.Facebook_login;
import jibin.ck.hostelapp_user.Google.Google_login;
import jibin.ck.hostelapp_user.Phone.Phone_Number;
import jibin.ck.hostelapp_user.R;
import jibin.ck.hostelapp_user.Settings.SaveTheamSatate;
import jibin.ck.hostelapp_user.Settings.Settings_home;

public class Home extends AppCompatActivity {
    SaveTheamSatate saveTheamSatate;
    TabLayout tabLayout;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
     private NavigationView navigationView;
    ImageView menuIcon,profileimage;
    DrawerLayout drawer;
    Location location;
    String address, city, state, country, postalCode, knownName;
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
        setContentView(R.layout.navigation);


        LocationManager locationManager = (LocationManager) Home.this.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(Home.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, new Home.Listener());
        // Have another for GPS provider just in case.
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, new Home.Listener());
        // Try to request the location immediately
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (location != null) {
            handleLatLng(location.getLatitude(), location.getLongitude());


          //  Toast.makeText(this,  postalCode, Toast.LENGTH_SHORT).show();

        }

        tabLayout = findViewById(R.id.tabs8);

           menuIcon =  findViewById(R.id.navimage);
        drawer =  findViewById(R.id.drawer_layout);
        profileimage =  findViewById(R.id.profileimage);




profileimage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        login();
    }
});




        menuIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        NavigationView navigationView = (NavigationView)  findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);



        ViewPager viewPager = findViewById(R.id.vp6);

        BottomNavWithTabsAdapter tabsAdapter = new BottomNavWithTabsAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabsAdapter);

        Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(0))
                .setText("Home").getIcon())
                .setTint(ContextCompat.getColor(getApplicationContext(), android.R.color.white));

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        for (int i = 0 ; i < tabLayout.getTabCount() ; i++){
            View tabs = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tabs.getLayoutParams();
            p.setMargins(16,16,16,16);
            tabs.requestLayout();
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sd_scale_fade_and_translate_in);
                switch (position) {
                    case 0:
                        tabLayout.setSelectedTabIndicator(getDrawable(R.drawable.tab_selector_new1));
                        currentTab("Home", animation, tab);
                        break;
                    case 1:
                        currentTab("Search", animation, tab);
                        tabLayout.setSelectedTabIndicator(getDrawable(R.drawable.tab_selector_new));

                        break;
                    case 2:
                        currentTab("Profile", animation, tab);
                        tabLayout.setSelectedTabIndicator(getDrawable(R.drawable.tab_selector_new1));

                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Objects.requireNonNull(tab.getIcon()).setTint(ContextCompat.getColor(getApplicationContext(), R.color.light500));
                tab.setText(null);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void currentTab(String string, Animation animation, TabLayout.Tab tab) {
        tabLayout.getChildAt(0).setAnimation(animation);
        tab.setText(string);
        Objects.requireNonNull(tab.getIcon()).setTint(ContextCompat.getColor(getApplicationContext(), android.R.color.white));
    }

    private class BottomNavWithTabsAdapter extends FragmentPagerAdapter {

        BottomNavWithTabsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Fragment_Home();
                case 1:
                    return new Fragment_Two();
                case 2:
                    return new Fragment_Three();
            }
            return new Fragment_Home();
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

     public void onClick(View v) {

        switch (v.getId()) {


            case R.id.settings:
                Intent i = new Intent(this, Settings_home.class);
                startActivity(i);


        }





        drawer.closeDrawer(Gravity.LEFT);

    }

    private void login() {



        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.activity_login_page);

        FloatingActionButton phonenuberbutton=dialog.findViewById(R.id.phonenuberbutton);
        FloatingActionButton googellogin=dialog.findViewById(R.id.googellogin);
        FloatingActionButton facebooklogin=dialog.findViewById(R.id.facebooklogin);
phonenuberbutton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent i = new Intent(Home.this, Phone_Number.class);
        startActivity(i);



    }
});


        googellogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Home.this, Google_login.class);
                startActivity(i);



            }
        });
        facebooklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Home.this, Facebook_login.class);
                startActivity(i);



            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();

    }


    private void handleLatLng(final double latitude, final double longitude) {
        Log.v("TAG", "(" + latitude + "," + longitude + ")");
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(Home.this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert addresses != null;
        address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        city = addresses.get(0).getLocality();
        state = addresses.get(0).getAdminArea();
        country = addresses.get(0).getCountryName();
        postalCode = addresses.get(0).getPostalCode();
        knownName = addresses.get(0).getFeatureName();





    }

    class Listener implements LocationListener {
        public void onLocationChanged(Location location) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            handleLatLng(latitude, longitude);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}