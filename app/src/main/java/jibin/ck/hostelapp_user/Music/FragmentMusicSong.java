package jibin.ck.hostelapp_user.Music;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.List;

import jibin.ck.hostelapp_user.Adapter.Music_Adapter;
import jibin.ck.hostelapp_user.Adapter.Popular_Adapter;
import jibin.ck.hostelapp_user.R;
import jibin.ck.hostelapp_user.Viewholder.Music_viewholder;
import jibin.ck.hostelapp_user.Viewholder.Nearest_viewholder;

public class FragmentMusicSong extends Fragment {
    FirebaseDatabase mFirebaseDatabase,mFirebaseDatabase1;
    DatabaseReference mRef,mRef1;
     RecyclerView mRecyclerView,nearest;
    LinearLayoutManager mLayoutManager; //for sorting
    public FragmentMusicSong() {
    }

    public static FragmentMusicSong newInstance() {
        FragmentMusicSong fragment = new FragmentMusicSong();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_music_song, container, false);

         mRecyclerView =  root.findViewById(R.id.music);

         mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);

        mLayoutManager.setReverseLayout(true);

        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("music") ;



        // on item list clicked




        return root;
    }
    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Music_Adapter, Music_viewholder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Music_Adapter, Music_viewholder>(
                        Music_Adapter.class,
                        R.layout.musiclist,
                        Music_viewholder.class,
                        mRef) {
                    @Override
                    protected void populateViewHolder(Music_viewholder viewHolder, Music_Adapter model, int position) {
                        viewHolder.setDetails(getActivity(), model.getImg(), model.getMusic(), model.getName(), model.getSinger());




                    }

                    @Override
                    public Music_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

                        Music_viewholder viewHolder = super.onCreateViewHolder(parent, viewType);



                        return viewHolder;
                    }

                };
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                nearest_shimer.stopShimmer();
//                nearest_shimer.setVisibility(View.GONE);
//


                if(dataSnapshot.exists()){

//                    nearest_shimer.setVisibility(View.GONE);
//
//                    nearest_shimer.stopShimmer();


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