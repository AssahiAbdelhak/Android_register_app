package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                if(firebaseUser != null){
                    startActivity(new Intent(splash.this,reception.class));
                    finish();
                }else{
                    startActivity(new Intent(splash.this,MainActivity.class));
                    finish();
                }
            }
        },3000);


    }
}