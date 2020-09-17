package jibin.ck.hostelapp_user.Viewholder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.jetbrains.annotations.Nullable;

import jibin.ck.hostelapp_user.Full_screen_img.Photoview;
import jibin.ck.hostelapp_user.Login.Login_page;
import jibin.ck.hostelapp_user.Phone.Phone_Number;
import jibin.ck.hostelapp_user.R;


public class Hostelimg_viewholder extends RecyclerView.ViewHolder {

    View v;

    public Hostelimg_viewholder(View itemView) {
        super(itemView);

        v = itemView;

        //item click

    }

    //set details to recycler view row
    @SuppressLint("CheckResult")
    public void setDetails(final Context ctx, final String img ){
        //Views
        ImageView mystatusimage = v.findViewById(R.id.hostel_img_view);
        final CardView cardView = v.findViewById(R.id.view);

        final LottieAnimationView progressBar=v.findViewById(R.id.progressbarshostel);
      progressBar.setVisibility(View.VISIBLE);
        Glide.with(ctx)
                .load(img)
//

                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                         progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(mystatusimage);
        mystatusimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Photoview(ctx, R.layout.photoview,cardView,img, null);



            }
        });

//        try {
//            Glide.with(ctx)
//                    .load(img)
//                    .centerCrop()
//                    .placeholder(R.drawable.user)
//                    .into(mystatusimage);
//        } catch (Exception e) {
//            Toast.makeText(ctx, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//        }





    }



}