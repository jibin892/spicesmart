package jibin.ck.hostelapp_user.Home;


import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import jibin.ck.hostelapp_user.Adapter.Nearest_Adapter;
import jibin.ck.hostelapp_user.Adapter.Popular_Adapter;
import jibin.ck.hostelapp_user.Aother.SaveLocationSatate;
import jibin.ck.hostelapp_user.Login.Login_page;
import jibin.ck.hostelapp_user.R;
import jibin.ck.hostelapp_user.Settings.Settings_home;
import jibin.ck.hostelapp_user.Splash;
import jibin.ck.hostelapp_user.View_All.Viewall;
import jibin.ck.hostelapp_user.Viewholder.Nearest_viewholder;
import jibin.ck.hostelapp_user.Viewholder.Popular_viewholder;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.ContentValues.TAG;
import static android.content.Context.LOCATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Home extends Fragment {
          LinearLayoutManager mLayoutManager; //for sorting
    private SwipeRefreshLayout swipeRefreshLayout;

    RecyclerView mRecyclerView,nearest;
    FirebaseDatabase mFirebaseDatabase,mFirebaseDatabase1;
    DatabaseReference mRef,mRef1;
TextView popularviewall,nearestviewall;
LinearLayout locationon,current_location_layout;
    ShimmerFrameLayout popular_shimer,nearest_shimer;
    Location location;
    Button locationonbtn;
    String address, city, state, country, postalCode, knownName;
    public Fragment_Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home,container, false);
        mRecyclerView =  view.findViewById(R.id.popular);
        locationon=view.findViewById(R.id.locationon);
        locationonbtn=view.findViewById(R.id.locationonbtn);

          new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                final LocationManager manager = (LocationManager)getActivity(). getSystemService( Context.LOCATION_SERVICE );

                if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    //  buildAlertMessageNoGps();
                    mRecyclerView.setVisibility(View.GONE);
                }
                else{

                    locationon.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);


                }
            }
        }, 1);
        locationonbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                buildAlertMessageNoGps();

            }
        });


        LocationManager locationManager = (LocationManager)getActivity().getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, new Fragment_Home.Listener());
        // Have another for GPS provider just in case.
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, new Fragment_Home.Listener());
        // Try to request the location immediately
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (location != null) {
            handleLatLng(location.getLatitude(), location.getLongitude());


            Toast.makeText(getActivity(),  postalCode, Toast.LENGTH_SHORT).show();

        }


        popularviewall= view.findViewById(R.id.popularviewall);
        nearestviewall= view.findViewById(R.id.nearestviewall);



        nearestviewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Viewall.class);
                i.putExtra("key",postalCode);
                i.putExtra("child",postalCode);

                startActivity(i);
            }
        });


        popularviewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Viewall.class);
                i.putExtra("key", postalCode);
                i.putExtra("child","postalCode");

                startActivity(i);
            }
        });
        swipeRefreshLayout = view.findViewById(R.id.swiping);
          popular_shimer = view.findViewById(R.id.popular_shimer);
        popular_shimer.startShimmer();
        nearest_shimer = view.findViewById(R.id.nearest_shimer);
        nearest_shimer.startShimmer();
        mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);

        mLayoutManager.setReverseLayout(true);

        mLayoutManager.setStackFromEnd(true);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onStart();
                        swipeRefreshLayout.setRefreshing(false);
                        Log.d(TAG, "Completed");
                    }
                }, 3000);
            }
        });


                if(checkPermission()) {



                }
                else {

                    requestPermission();
                }








        nearest =  view.findViewById(R.id.nearest);

        //set layout as LinearLayout
        mRecyclerView.setLayoutManager(mLayoutManager);

        //send Query to FirebaseDatabase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("Popular") ;
        GridLayoutManager mLayoutManager1 = new GridLayoutManager(getActivity(),2);


         nearest =  view.findViewById(R.id.nearest);


        //set layout as LinearLayout
        nearest.setLayoutManager(mLayoutManager1);

        //send Query to FirebaseDatabase
        mFirebaseDatabase1 = FirebaseDatabase.getInstance();

        mRef1 =  FirebaseDatabase.getInstance().getReference().child("Populars");



        return view;
    }



    @Override
    public void onStart() {
        super.onStart();
        loads();

        Query firebaseSearchQuery = mRef.orderByChild("postalCode").equalTo("685552");
        FirebaseRecyclerAdapter<Popular_Adapter, Popular_viewholder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Popular_Adapter, Popular_viewholder>(
                         Popular_Adapter.class,
                        R.layout.popular_view,
                        Popular_viewholder.class,
                        firebaseSearchQuery) {
                    @Override
                    protected void populateViewHolder(Popular_viewholder viewHolder, Popular_Adapter model, int position) {
                        viewHolder.setDetails(getActivity(), model.getProductimg());




                    }

                    @Override
                    public Popular_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

                        Popular_viewholder viewHolder = super.onCreateViewHolder(parent, viewType);



                        return viewHolder;
                    }

                };
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                popular_shimer.stopShimmer();
                popular_shimer.setVisibility(View.GONE);



                if(dataSnapshot.exists()){

                    popular_shimer.setVisibility(View.GONE);

                    popular_shimer.stopShimmer();


                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //set adapter to recyclerview
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private void loads() {


        FirebaseRecyclerAdapter<Nearest_Adapter, Nearest_viewholder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Nearest_Adapter, Nearest_viewholder>(
                        Nearest_Adapter.class,
                        R.layout.nearest_view,
                        Nearest_viewholder.class,
                        mRef1) {
                    @Override
                    protected void populateViewHolder(Nearest_viewholder viewHolder, Nearest_Adapter model, int position) {
                        viewHolder.setDetails(getActivity(), model.getProductimg());




                    }

                    @Override
                    public Nearest_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

                        Nearest_viewholder viewHolder = super.onCreateViewHolder(parent, viewType);



                        return viewHolder;
                    }

                };
        mRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                nearest_shimer.setVisibility(View.GONE);

                nearest_shimer.stopShimmer();

                if(dataSnapshot.exists()){
//
                    nearest_shimer.stopShimmer();
                    nearest_shimer.setVisibility(View.GONE);

                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //set adapter to recyclerview
        nearest.setAdapter(firebaseRecyclerAdapter);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION);
        // int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);

        return result == PackageManager.PERMISSION_GRANTED ;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_FINE_LOCATION},333);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 333:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    // boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted)
                    {

                    }
                    //  Toast.makeText(, "", Toast.LENGTH_SHORT).show();
                    //  Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                    else {

                        // Snackbar.make(view, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(new String[]{ACCESS_FINE_LOCATION},
                                                    333);
                                        }
                                    }
                                };
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }



    private void handleLatLng(final double latitude, final double longitude) {
        Log.v("TAG", "(" + latitude + "," + longitude + ")");
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}






