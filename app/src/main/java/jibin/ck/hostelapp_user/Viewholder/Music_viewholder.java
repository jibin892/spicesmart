package jibin.ck.hostelapp_user.Viewholder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import jibin.ck.hostelapp_user.Hostel_details.View_hostel;
import jibin.ck.hostelapp_user.Music.MusicUtils;
import jibin.ck.hostelapp_user.R;


public class Music_viewholder extends RecyclerView.ViewHolder {
    private MusicUtils utils;
    ImageView playbutton;
     View v;
    private MediaPlayer mp;
    private Handler mHandler = new Handler();
    AppCompatSeekBar  seek_song_progressbar;
    public Music_viewholder(View itemView) {
        super(itemView);

        v = itemView;

        //item click

    }

    //set details to recycler view row
    @SuppressLint("CheckResult")
    public void setDetails(final Context ctx, final String img ,final String music , String name, String singer){
        //Views
        ImageView imagemusic = v.findViewById(R.id.imagemusic);
        TextView titlemusic = v.findViewById(R.id.titlemusic);
            playbutton = v.findViewById(R.id.playbutton);
           seek_song_progressbar = (AppCompatSeekBar)v. findViewById(R.id.seek_song_progressbar);
        seek_song_progressbar.setProgress(0);
        seek_song_progressbar.setMax(MusicUtils.MAX_PROGRESS);

        mp = new MediaPlayer();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // Changing button image to play button
                playbutton.setImageResource(R.drawable.play);
            }
        });

        try {
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            AssetFileDescriptor afd = ctx.getAssets().openFd("short_music.mp3");
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mp.prepare();
        } catch (Exception e) {
           // Snackbar.make(parent_view, "Cannot load audio file", Snackbar.LENGTH_SHORT).show();
        }

        seek_song_progressbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // remove message Handler from updating progress bar
                mHandler.removeCallbacks(mUpdateTimeTask);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mUpdateTimeTask);
                int totalDuration = mp.getDuration();
                int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

                // forward or backward to certain seconds
                mp.seekTo(currentPosition);

                // update timer progress again
                mHandler.post(mUpdateTimeTask);
            }
        });
        Uri myUri = Uri.parse(music); // initialize Uri here
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(ctx, myUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        if (mp.isPlaying()) {
            mp.pause();
            // Changing button image to play button
            playbutton.setImageResource(R.drawable.play);
        } else {

            mp.start();
            // Changing button image to pause button
            playbutton.setImageResource(R.drawable.pause);
            // Updating progress bar
            mHandler.post(mUpdateTimeTask);
        }
       // buttonPlayerAction();



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
                .into(imagemusic);



    }
//    private void buttonPlayerAction() {
//        playbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                Uri myUri = Uri.parse(music); // initialize Uri here
//                MediaPlayer mediaPlayer = new MediaPlayer();
//                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                try {
//                    mediaPlayer.setDataSource(ctx, myUri);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    mediaPlayer.prepare();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                mediaPlayer.start();
//                if (mp.isPlaying()) {
//                    mp.pause();
//                    // Changing button image to play button
//                    playbutton.setImageResource(R.drawable.play);
//                } else {
//                    Uri myUri1 = Uri.parse(music); // initialize Uri here
//                    MediaPlayer mediaPlayer1 = new MediaPlayer();
//                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                    try {
//                        mediaPlayer.setDataSource(ctx, myUri1);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        mediaPlayer1.prepare();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    mediaPlayer.start();
//                    mp.start();
//                    // Changing button image to pause button
//                    playbutton.setImageResource(R.drawable.pause);
//                    // Updating progress bar
//                    mHandler.post(mUpdateTimeTask);
//                }
//
//            }
//        });
//    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mp.getDuration();
            long currentDuration = mp.getCurrentPosition();

            // Updating progress bar
            int progress = (int) (utils.getProgressSeekBar(currentDuration, totalDuration));
            seek_song_progressbar.setProgress(progress);

            // Running this thread after 10 milliseconds
            if (mp.isPlaying()) {
                mHandler.postDelayed(this, 100);
            }
        }
    };

    // stop player when destroy

}