package jibin.ck.hostelapp_user.Viewholder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.Nullable;

import jibin.ck.hostelapp_user.Hostel_details.View_hostel;
import jibin.ck.hostelapp_user.R;


public class Nearest_viewholder extends RecyclerView.ViewHolder {

    View v;

    public Nearest_viewholder(View itemView) {
        super(itemView);

        v = itemView;

        //item click

    }

    //set details to recycler view row
    @SuppressLint("CheckResult")
    public void setDetails(final Context ctx, final String img ){
        //Views
        ImageView mystatusimage = v.findViewById(R.id.popular_hostel_img);

        final LottieAnimationView progressBar=v.findViewById(R.id.progressbars);
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


        Intent a=new Intent(ctx, View_hostel.class);
        ctx.startActivity(a);
    }
});


    }



}