package jibin.ck.hostelapp_user.Google;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Objects;

import jibin.ck.hostelapp_user.R;
import jibin.ck.hostelapp_user.Settings.SaveTheamSatate;

public class Google_login extends AppCompatActivity {
    SaveTheamSatate saveTheamSatate;
    private  static final int RC_SIGN_IN = 100;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    AuthCredential credential;
    ProgressBar progress_bar;
ImageView googelimg;
Button googelebtn;
TextView googelname,googelemail;
    FirebaseUser user1;
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
        setContentView(R.layout.activity_google_login);
        progress_bar=findViewById(R.id.progress_bar);


        googelimg=findViewById(R.id.googelimg);
        googelname=findViewById(R.id.googelname);
        googelemail=findViewById(R.id.googelemail);
        googelebtn=findViewById(R.id.googelebtn);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("281945789498-keb6anh9skjvjedi9u6rhms2kmga5tgs.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void firebaseAuthWithGoogle(final String idToken) {
         GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            final String personName = Objects.requireNonNull(acct.getDisplayName()).trim();
            final String personEmail = acct.getEmail().trim();
            Uri personPhoto = acct.getPhotoUrl();
            final String proPic = Objects.requireNonNull(personPhoto).toString();
            final String name = personName.substring(0, personName.lastIndexOf(" "));
            //if(personEmail.endsWith("christuniversity.in")) {
            credential = GoogleAuthProvider.getCredential(idToken, null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                 FirebaseUser user = mAuth.getCurrentUser();

                                //Get user email and id from auth
                                String email = Objects.requireNonNull(user).getEmail();
                                String uid = user.getUid();

                                //When user registers store data in firebase database using hashmap
                                HashMap<Object, String> hashMap = new HashMap<>();
                                //put info to hashmap
                                hashMap.putIfAbsent("email", email);
                                hashMap.putIfAbsent("uid", uid);
                                hashMap.putIfAbsent("name", name);
                                hashMap.putIfAbsent("image", proPic);
                                progress_bar.setVisibility(View.GONE);
                                //Firebase database instance
                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                                //Add path to store data "Users"
                                DatabaseReference reference = firebaseDatabase.getReference("Users");

                                //put data within hashmap in database
                                reference.child(uid).setValue(hashMap);

                                Glide.with(Google_login.this)
                                        .load(proPic)
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
                                        .into(googelimg);

                                googelemail.setText(email);
                                googelname.setText(name);
                                googelebtn.setText("Continu With"+"-"+name);
                                //goto profile activity after logging in
                                Toast.makeText(Google_login.this, "ok", Toast.LENGTH_SHORT).show();
                             //   startActivity(new Intent(MainActivity.this, Splash.class));

                            }
                        }

//                        private void loaddilog() {
//
//
//
//
//                            final Dialog dialog = new Dialog(MainActivity.this);
//                             dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
//                            dialog.setContentView(R.layout.dialog_dark);
//                            dialog.setCancelable(false);
//
//                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                            lp.copyFrom(dialog.getWindow().getAttributes());
//                            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
//                            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                            ImageView imageView=dialog.findViewById(R.id.imagenoentry);
//                            TextView textView=dialog.findViewById(R.id.titler);
//                            TextView contentr=dialog.findViewById(R.id.contentr);
//                            TextView statement=dialog.findViewById(R.id.statement);
//
//
//
//
//                            try {
//                Glide.with(MainActivity.this)
//                        .load(proPic)
//                        .centerInside()
//                        .into(imageView);
//            }
//            catch (Exception e) {
//                Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//                            textView.setText(personName);
//                            contentr.setText(personEmail);
//                            statement.setText(personName+"\n"+"Sorry To Say Your Are Not Abel To Login ");
//
//                            ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                    GoogleSignIn.getClient(
//                                            getApplicationContext(),
//                                            new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
//                                    ).signOut();
//
//                                            //checkuserstatus();
//                                   dialog.dismiss();
//
//
//
//
//                                }
//                            });
//
//
//
//                            dialog.show();
//
//                            dialog.getWindow().setAttributes(lp);
//
//
//
//
//
//
//
//                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Google_login.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
//            }
//            else {
//                prgresbarlogin.setVisibility(View.GONE);
//                mGoogleSignInClient.signOut();
//                Toast.makeText(this, "Not a valid Christ account"+personEmail, Toast.LENGTH_SHORT).show();
//            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN ) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
                progress_bar.setVisibility(View.GONE);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}