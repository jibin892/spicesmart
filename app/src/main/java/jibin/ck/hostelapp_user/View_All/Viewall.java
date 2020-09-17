
package jibin.ck.hostelapp_user.View_All;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import jibin.ck.hostelapp_user.Adapter.Popular_Adapter;
import jibin.ck.hostelapp_user.R;
import jibin.ck.hostelapp_user.Settings.SaveTheamSatate;
import jibin.ck.hostelapp_user.Viewholder.Nearest_viewholder;

public class Viewall extends AppCompatActivity {
    SaveTheamSatate saveTheamSatate;
    private SwipeRefreshLayout swipeRefreshLayout;
Intent key;
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    ShimmerFrameLayout viealshimmer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        saveTheamSatate = new SaveTheamSatate(this);
        if(saveTheamSatate.looadNightModeState()){
            setTheme(R.style.NightMode);
        }else {
            setTheme(R.style.DayMode);
        }
        setContentView(R.layout.activity_viewall);
key=getIntent();


        GridLayoutManager mLayoutManager1 = new GridLayoutManager(getApplicationContext(),2);
        mRecyclerView =   findViewById(R.id.viewall);
        viealshimmer =  findViewById(R.id.popular_shimer1);
        viealshimmer.startShimmer();

        //set layout as LinearLayout
        mRecyclerView.setLayoutManager(mLayoutManager1);

        //send Query to FirebaseDatabase
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mRef =  FirebaseDatabase.getInstance().getReference().child("Popular");
    }

    @Override
    public void onStart() {
        super.onStart();

        Query firebaseSearchQuery = mRef.orderByChild(key.getStringExtra("child")).equalTo(key.getStringExtra("key"));
        FirebaseRecyclerAdapter<Popular_Adapter, Nearest_viewholder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Popular_Adapter, Nearest_viewholder>(
                        Popular_Adapter.class,
                        R.layout.viewall,
                        Nearest_viewholder.class,
                        firebaseSearchQuery) {
                    @Override
                    protected void populateViewHolder(Nearest_viewholder viewHolder, Popular_Adapter model, int position) {
                        viewHolder.setDetails(getApplicationContext(), model.getProductimg());




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

                viealshimmer.stopShimmer();
                viealshimmer.setVisibility(View.GONE);



                if(dataSnapshot.exists()){
//
                    viealshimmer.setVisibility(View.GONE);

                    viealshimmer.stopShimmer();


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