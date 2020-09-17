package jibin.ck.hostelapp_user.Home;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import jibin.ck.hostelapp_user.Adapter.Popular_Adapter;
import jibin.ck.hostelapp_user.Adapter.Search_Adapter;
import jibin.ck.hostelapp_user.R;
import jibin.ck.hostelapp_user.Viewholder.Popular_viewholder;
import jibin.ck.hostelapp_user.Viewholder.Search_viewholder;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Two extends Fragment {
LottieAnimationView sechnodata;
    LinearLayoutManager mLayoutManager; //for sorting
    SharedPreferences mSharedPref; //for saving sort settings
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    ShimmerFrameLayout mShimmerViewContainer;
    String amounttotal;
    private BottomSheetDialog mBottomSheetDialog;
    TextView todayfuulammount;
    ImageButton todayscolluctionadninview;
    EditText et_search;
    String  start,end;
    View view;
    LinearLayout findnearbyme;
    public Fragment_Two() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two,container, false);
        sechnodata =  view.findViewById(R.id.sechnodata);



        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);


        //RecyclerView
        findnearbyme  =  view.findViewById(R.id.findnearbyme);
//        Animation     animation = AnimationUtils.loadAnimation(getActivity(), R.anim.layout_animation_fall_down);


      //  findnearbyme.startAnimation(animation);
        et_search =  view.findViewById(R.id.et_search);

        mRecyclerView =  view.findViewById(R.id.search);
        mRecyclerView.setHasFixedSize(true);

        //set layout as LinearLayout
        mRecyclerView.setLayoutManager(mLayoutManager);

        //send Query to FirebaseDatabase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("Populars");

        et_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = et_search.getText().toString();

                Query firebaseSearchQuery = mRef.orderByChild("producname").startAt(query).endAt(query + "\uf8ff");

                FirebaseRecyclerAdapter<Search_Adapter, Search_viewholder> firebaseRecyclerAdapter =
                        new FirebaseRecyclerAdapter<Search_Adapter, Search_viewholder>(
                                Search_Adapter.class,
                                R.layout.serch_layout,
                                Search_viewholder.class,
                                firebaseSearchQuery) {
                            @Override
                            protected void populateViewHolder(Search_viewholder viewHolder, Search_Adapter model, int position) {
                                viewHolder.setDetails(getActivity(), model.getProductimg());

if(model.getProducname().isEmpty()){
    sechnodata.setVisibility(View.VISIBLE);
}
else{
    sechnodata.setVisibility(View.GONE);

}


                            }

                            @Override
                            public Search_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

                                Search_viewholder viewHolder = super.onCreateViewHolder(parent, viewType);



                                return viewHolder;

                            }


                        };

                //set adapter to recyclerview
                mRecyclerView.setAdapter(firebaseRecyclerAdapter);
            }
        });

        return view;
    }
}
