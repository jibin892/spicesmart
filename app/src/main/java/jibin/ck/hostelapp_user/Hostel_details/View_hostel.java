package jibin.ck.hostelapp_user.Hostel_details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import jibin.ck.hostelapp_user.Adapter.Popular_Adapter;
import jibin.ck.hostelapp_user.Location.Tools;
import jibin.ck.hostelapp_user.R;
import jibin.ck.hostelapp_user.Settings.SaveTheamSatate;
import jibin.ck.hostelapp_user.Viewholder.Hostelimg_viewholder;
import jibin.ck.hostelapp_user.Viewholder.Review_viewholder;

public class View_hostel extends AppCompatActivity {
   SaveTheamSatate saveTheamSatate ;
    private boolean isOpen = false;
    LinearLayoutManager mLayoutManager,mLayoutManager1; //for sorting
    RecyclerView mRecyclerView,review;
    FirebaseDatabase mFirebaseDatabase,mFirebaseDatabase1;
    DatabaseReference mRef,mRef1;
    private GoogleMap mMap;

    TextView location_permisionbtn;
    LinearLayout permision_layout,current_location_layout;
    ShimmerFrameLayout hostelview_shimer,hostelview_shimer1;
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
        setContentView(R.layout.activity_view_hostel);
      //  FloatingActionButton floatingActionButton = findViewById(R.id.fab_main);
//        final LinearLayout linearLayout = findViewById(R.id.hided_layer1);
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                isOpen = !isOpen;
//                if (isOpen) {
//                    linearLayout.setVisibility(View.VISIBLE);
//                } else {
//                    linearLayout.setVisibility(View.GONE);
//                }
//
//            }
//        });

        initMapFragment();


        hostelview_shimer =  findViewById(R.id.hostelview_shimer);
        hostelview_shimer.startShimmer();

        hostelview_shimer1 =  findViewById(R.id.hostelview_shimer1);
        hostelview_shimer1.startShimmer();

        mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView =   findViewById(R.id.hostelview);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("Popular");




        mLayoutManager1 = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        mLayoutManager1.setReverseLayout(true);
        mLayoutManager1.setStackFromEnd(true);
        review =   findViewById(R.id.review);
        review.setLayoutManager(mLayoutManager1);
        mFirebaseDatabase1 = FirebaseDatabase.getInstance();
        mRef1 = mFirebaseDatabase.getReference("Popular");




        FirebaseRecyclerAdapter<Popular_Adapter, Review_viewholder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Popular_Adapter, Review_viewholder>(
                        Popular_Adapter.class,
                        R.layout.review_layout,
                        Review_viewholder.class,
                        mRef1) {
                    @Override
                    protected void populateViewHolder(Review_viewholder viewHolder, Popular_Adapter model, int position) {
                        viewHolder.setDetails(getApplicationContext(), model.getProductimg());




                    }

                    @Override
                    public Review_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

                        Review_viewholder viewHolder = super.onCreateViewHolder(parent, viewType);



                        return viewHolder;
                    }

                };
        mRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
hostelview_shimer.setVisibility(View.GONE);
                 hostelview_shimer.stopShimmer();



                if(dataSnapshot.exists()){
//
                    hostelview_shimer.setVisibility(View.GONE);
                    hostelview_shimer.stopShimmer();

                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //set adapter to recyclerview
        review.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Popular_Adapter, Hostelimg_viewholder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Popular_Adapter, Hostelimg_viewholder>(
                        Popular_Adapter.class,
                        R.layout.hostel_img_view,
                        Hostelimg_viewholder.class,
                        mRef) {
                    @Override
                    protected void populateViewHolder(Hostelimg_viewholder viewHolder, Popular_Adapter model, int position) {
                        viewHolder.setDetails(getApplicationContext(), model.getProductimg());




                    }

                    @Override
                    public Hostelimg_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

                        Hostelimg_viewholder viewHolder = super.onCreateViewHolder(parent, viewType);



                        return viewHolder;
                    }

                };
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                hostelview_shimer1.setVisibility(View.GONE);
                hostelview_shimer.stopShimmer();

                if(dataSnapshot.exists()){
//
                    hostelview_shimer1.setVisibility(View.GONE);
                    hostelview_shimer1.stopShimmer();

                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //set adapter to recyclerview
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    private void initMapFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = Tools.configActivityMaps(googleMap);
                MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(37.7610237, -122.4217785));
                mMap.addMarker(markerOptions);
                mMap.moveCamera(zoomingLocation());
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        try {
                            mMap.animateCamera(zoomingLocation());
                        } catch (Exception e) {
                        }
                        return true;
                    }
                });
            }
        });
    }

    private CameraUpdate zoomingLocation() {
        return CameraUpdateFactory.newLatLngZoom(new LatLng(37.76496792, -122.42206407), 13);
    }
}