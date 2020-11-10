package jibin.ck.hostelapp_user.Home;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import jibin.ck.hostelapp_user.Adapter.Popular_Adapter;
import jibin.ck.hostelapp_user.R;
import jibin.ck.hostelapp_user.Settings.Settings_home;
import jibin.ck.hostelapp_user.Viewholder.Nearest_viewholder;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Three extends Fragment {
    ImageView menuIcon,profileimage;
    DrawerLayout drawer;
    FirebaseDatabase mFirebaseDatabase,mFirebaseDatabase1;
    DatabaseReference mRef,mRef1;
    ShimmerFrameLayout  nearest_shimer;
    RecyclerView mRecyclerView,nearest;
    LinearLayoutManager mLayoutManager; //for sorting

    public Fragment_Three() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_three,container, false);

        menuIcon = view. findViewById(R.id.navprofile);
        drawer = view. findViewById(R.id.drawer_layout);
        nearest_shimer = view.findViewById(R.id.nearest_shimer1);
        mRecyclerView =  view.findViewById(R.id.nearests);

        nearest_shimer.startShimmer();
        mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);

        mLayoutManager.setReverseLayout(true);

        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("Popular") ;
        menuIcon.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("RtlHardcoded")
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.RIGHT);
            }
        });
        NavigationView navigationView = (NavigationView) view. findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);


return  view;

    }

    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.settings:
                Intent i = new Intent(getActivity(), Settings_home.class);
                startActivity(i);
break;
            case R.id.settings1:
                break;
        }





        drawer.closeDrawer(Gravity.RIGHT);

    }

    @Override
    public void onStart() {
        super.onStart();

         FirebaseRecyclerAdapter<Popular_Adapter, Nearest_viewholder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Popular_Adapter, Nearest_viewholder>(
                        Popular_Adapter.class,
                        R.layout.navrecycel,
                        Nearest_viewholder.class,
                        mRef) {
                    @Override
                    protected void populateViewHolder(Nearest_viewholder viewHolder, Popular_Adapter model, int position) {
                        viewHolder.setDetails(getActivity(), model.getProductimg());




                    }

                    @Override
                    public Nearest_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

                        Nearest_viewholder viewHolder = super.onCreateViewHolder(parent, viewType);



                        return viewHolder;
                    }

                };
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                nearest_shimer.stopShimmer();
                nearest_shimer.setVisibility(View.GONE);



                if(dataSnapshot.exists()){

                    nearest_shimer.setVisibility(View.GONE);

                    nearest_shimer.stopShimmer();


                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //set adapter to recyclerview
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

}

