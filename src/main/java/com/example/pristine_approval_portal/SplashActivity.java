package com.example.pristine_approval_portal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SplashActivity extends AppCompatActivity {
      private static final int PERMISSION_REQUEST_CODE = 200;
      ImageView img_pristine_logo;
      TextView tv_company_name;
      Animation animationTogethor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getSupportActionBar().hide();
        }catch (Exception e){}
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        //todo rotate image
        img_pristine_logo = findViewById(R.id.img_pristine_logo);
        //todo rotate textview
        tv_company_name = findViewById(R.id.tv_company_name);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                img_pristine_logo.clearAnimation();
                tv_company_name.clearAnimation();
            }
        },1300);
        animationTogethor = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.side_animation_left_to_center);
        img_pristine_logo.startAnimation(animationTogethor);
        tv_company_name.startAnimation(animationTogethor);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(checkPermission()){
                    gotoLogin();
                }else {
                    requestPermissions();
                }
            }
        },2000);
    }

    private void gotoLogin() {
        Intent loginIntent = new Intent(SplashActivity.this,MainActivity.class);
        startActivity(loginIntent);
        finish();
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,new String[]{READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE,CAMERA},PERMISSION_REQUEST_CODE);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),READ_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),WRITE_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(),CAMERA);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED &&
                result2==PackageManager.PERMISSION_GRANTED;
    }
    //todo when request permision hit
    public void onRequestPermissionsResult(int requestCode, String[]permissions,int[]grantResults){
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length>0){
                    boolean readAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean writeAccepted = grantResults[1]==PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[2]==PackageManager.PERMISSION_GRANTED;
                 if(readAccepted && writeAccepted && cameraAccepted){
                     gotoLogin();
                 }else {
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                     if(shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)){
                         showMessageOkCancel("You need to allow access to both the permissions",
                                 new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialogInterface, int i) {
                                         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                             requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, CAMERA},
                                                     PERMISSION_REQUEST_CODE);
                                         }
                                     }
                                 });
                         return;
                     }
                    }
                 }
                }
                break;
        }
    }

    private void showMessageOkCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(SplashActivity.this)
                .setMessage(message)
                .setPositiveButton("OK",okListener)
                .setNegativeButton("Cancel",okListener)
                .create()
                .show();
    }
}