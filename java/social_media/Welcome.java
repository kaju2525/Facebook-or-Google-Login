package com.example.kaju.social_media;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;


public class Welcome extends AppCompatActivity {

    String sec_id,name,email,url_image;
    GoogleApiClient mGoogleApiClient;
    TextView tv;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tv=(TextView)findViewById(R.id.txt_name);
        img=(ImageView)findViewById(R.id.imageView);

        DisplayOperation();
    }

    private void DisplayOperation() {

        Bundle bundle=getIntent().getExtras();

        sec_id=bundle.getString("sessin_id");
        name=bundle.getString("name");
        email=bundle.getString("email");
        url_image=bundle.getString("url_image");

        tv.append(name  +"\n"+email);
        Picasso.with(this).load(url_image.toString()).resize(200, 180).into(img);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {

            if(sec_id.equals("1") )
            {
                signOutFromGplus();
                finish();
            }
            if(sec_id.equals("2") )
            {
                logoutfb();
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logoutfb(){
        LoginManager.getInstance().logOut();
        Intent login = new Intent(Welcome.this,MainActivity.class);
        startActivity(login);
        finish();
    }


    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }

    public void signOutFromGplus() {

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Intent i=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                    }
                });
       }
}
