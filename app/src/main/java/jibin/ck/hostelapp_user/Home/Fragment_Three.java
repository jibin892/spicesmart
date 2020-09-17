package jibin.ck.hostelapp_user.Home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import jibin.ck.hostelapp_user.Fragment_Profile.Fragment_Profile_home;
import jibin.ck.hostelapp_user.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Three extends Fragment {

    private TabLayout tabLayout;

    public Fragment_Three() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_three,container, false);
        tabLayout =view. findViewById(R.id.tabs81);

        ViewPager viewPager =view. findViewById(R.id.vp61);

        BottomNavWithTabsAdapter tabsAdapter = new BottomNavWithTabsAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager());
        viewPager.setAdapter(tabsAdapter);

        Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(0))
                .setText("Profile").getIcon())
                .setTint(ContextCompat.getColor(getActivity(), android.R.color.white));

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
//        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (int i = 0 ; i < tabLayout.getTabCount() ; i++){
            View tabs = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tabs.getLayoutParams();
            p.setMargins(16,16,16,16);
            tabs.requestLayout();
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.sd_scale_fade_and_translate_in);
                switch (position) {
                    case 0:
                        currentTab("Profile", animation, tab);
                        break;
                    case 1:
                        currentTab("Transaction", animation, tab);
                        break;
                    case 2:
                        currentTab("Pay", animation, tab);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Objects.requireNonNull(tab.getIcon()).setTint(ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.light500));
                tab.setText(null);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
return  view;

    }

    private void currentTab(String string, Animation animation, TabLayout.Tab tab) {
        tabLayout.getChildAt(0).setAnimation(animation);
        tab.setText(string);
        Objects.requireNonNull(tab.getIcon()).setTint(ContextCompat.getColor(Objects.requireNonNull(getActivity()), android.R.color.white));
    }

    private static class BottomNavWithTabsAdapter extends FragmentPagerAdapter {

        BottomNavWithTabsAdapter(FragmentManager fm) {
            super(fm);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Fragment_Profile_home();
                case 1:
                    return new Fragment_Two();
                case 2:
                    return new Fragment_Home();
            }
            return new Fragment_Profile_home();
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}

