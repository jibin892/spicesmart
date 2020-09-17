package jibin.ck.hostelapp_user.Viewholder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.jetbrains.annotations.Nullable;

import jibin.ck.hostelapp_user.R;


public class Search_viewholder extends RecyclerView.ViewHolder {

    View v;

    public Search_viewholder(View itemView) {
        super(itemView);

        v = itemView;

        //item click

    }

    //set details to recycler view row
    @SuppressLint("CheckResult")
    public void setDetails(final Context ctx, final String img ){
        //Views
        ImageView mystatusimage = v.findViewById(R.id.imageserchhostel);

         Glide.with(ctx)
                .load(img)
//

                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                         return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                         return false;
                    }
                })
                .into(mystatusimage);


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